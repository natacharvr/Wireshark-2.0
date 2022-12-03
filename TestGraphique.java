import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TestGraphique {
    public static void main(String[] args) {
        //Les datas des trames
        Analyseur a = new Analyseur("test.txt");
        List<String> ListIp = a.diffIp();

        //Outil pour afficher les borders des panels
        Border blackline = BorderFactory.createLineBorder(Color.black);

        //La fenetre :
        JFrame fenetre = new JFrame("Wireshark 2.0");
        fenetre.setLayout(new BorderLayout());
        fenetre.setSize(1000, 600);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Les ckeckbox pour les filtres
        JPanel checkPanel = new JPanel();
        checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.Y_AXIS));
        checkPanel.add(new JLabel("Selectionnez les Ip dont vous voulez visualiser les trames (entrantes et sortantes)"));
        JCheckBox  checkBox;
        List<JCheckBox> listCheck = new ArrayList<JCheckBox>();

        for (String s : ListIp) {
            checkBox = new JCheckBox(s, true);
            checkPanel.add(checkBox);
            listCheck.add(checkBox);
        }

        JButton btn1 = new JButton("Soumettre");

        checkPanel.add(btn1);
        checkPanel.add(new JLabel("Attention, le traitement est long. Patientez quelques secondes après avoir pressé le bouton Soumettre"));
        checkPanel.add(new JLabel("Veillez à bien selectionner au moins une Ip, un écran blanc n'est pas très passionant"));

        fenetre.add(checkPanel);

        //update de la fenetre quand on clique sur le bouton
        btn1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //récupération des Ip sélectionnées et retirer de la liste des Ip celles qui ne le sont pas
                for (JCheckBox c : listCheck){
                    if (!c.isSelected()){
                        String text = c.getText();
                        // System.out.println(text);
                        ListIp.remove(text);
                    }
                }

                //Récupérer les Ip avec lesquelles interragissent celles selectionnées (pour un affichage au top)
                //Utilisation d'un set pour éliminer les doublons
                HashSet<String> temp = new HashSet<String>(); 
                for (String s : ListIp){
                    // System.out.println("s: " + s);
                    for (String s1 : a.interragitAvec(s)){
                        if (!ListIp.contains(s1)){
                            temp.add(s1);
                        }
                    }
                }
                //ListIpConcernees contient toutes les Ip dont on devra afficher une colonne (celles qui nous interressent + celles avec qui elles interragissent)
                ArrayList<String> ListeIpConcernees = new ArrayList<String>(ListIp);
                ListeIpConcernees.addAll(temp);

                //Après le traitement des données fournies par l'utilisateur, on retire le sondage et on va afficher ce qu'il demande
                fenetre.remove(checkPanel);
                //Panneau avec toutes les légendes et le graphique
                JPanel total = new JPanel();
                total.setLayout(new BoxLayout(total, BoxLayout.X_AXIS));
                total.setAlignmentY(JPanel.TOP_ALIGNMENT);


                //Panneau du graphique et de sa légende
                JPanel graphique = new JPanel();
                graphique.setLayout(new BoxLayout(graphique, BoxLayout.Y_AXIS));
                graphique.setAlignmentY(JPanel.TOP_ALIGNMENT);
                graphique.setBorder(blackline);


                //Le graphique
                Graph graph = new Graph(ListeIpConcernees.size(), a.nbTramesConcernee(ListIp), a.sourceDest(ListeIpConcernees, ListIp), a);
                
                //La légende
                JPanel legende = new JPanel();
                legende.setLayout(new BoxLayout(legende, BoxLayout.X_AXIS));
                legende.setAlignmentX(Component.LEFT_ALIGNMENT);
                legende.setSize(graph.getWidth(), 50);
                for (String s : ListeIpConcernees) {
                    JLabel txt = new JLabel(s);
                    JLabel space = new JLabel("                          ");
                    legende.add(space);
                    legende.add(txt);
                } 
                JLabel spac = new JLabel("                                 ");
                legende.add(spac);
                legende.setBorder(blackline);


                //Ajout au panel
                graphique.add(legende);
                graphique.add(graph);
                graphique.setPreferredSize(graph.getPreferredSize());        
                
                 //Panneau des descriptions de trames
                JPanel descrTrames = new JPanel();
                descrTrames.setLayout(new BoxLayout(descrTrames, BoxLayout.Y_AXIS));
                descrTrames.setAlignmentY(JPanel.TOP_ALIGNMENT);
                //Sauts de lignes pour aligner avec les flèches
                descrTrames.add(new JLabel(" "));
                descrTrames.add(new JLabel(" "));
                descrTrames.add(new JLabel(" "));
                
                for (int i = 0; i < a.nbTramesConcernee(ListIp); i ++){            
                    JLabel txt = new JLabel(a.DataTrameI(i));
                    descrTrames.add(txt);
                    JLabel space4 = new JLabel(" ");
                    descrTrames.add(space4);
                    JLabel space5 = new JLabel(" ");
                    descrTrames.add(space5);
                    
                }
                
                //ajout au total
                // total.add(checkPanel);
                total.add(graphique);
                total.add(descrTrames);
                
                JScrollPane scrollGraphLegende = new JScrollPane(total);
                scrollGraphLegende.getVerticalScrollBar().setUnitIncrement(16);
                scrollGraphLegende.getHorizontalScrollBar().setUnitIncrement(16);

                fenetre.add(scrollGraphLegende);
            
                //Affichage des éléments :
                fenetre.setVisible(true);
                

                //export 
                BufferedImage image = new BufferedImage(total.getSize().width, total.getSize().height, BufferedImage.TYPE_3BYTE_BGR); 
                
                Graphics g = image.createGraphics();
                total.paint(g);
                g.dispose();
                try{
                    ImageIO.write(image,"png",new File("test.png"));
                }catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                //Affichage off puis on pour refresh
                fenetre.setVisible(false);
                fenetre.setVisible(true);            
            }
        } );
        fenetre.setVisible(true);

    }
}
