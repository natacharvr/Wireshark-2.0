import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.util.ArrayList;
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
        fenetre.add(checkPanel);

        //update de la fenetre quand on clique sur le bouton
        btn1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JCheckBox c : listCheck){
                    if (!c.isSelected()){
                        ListIp.remove(c.getText());
                    }
                }
                for (String s : ListIp){
                    for (String s1 : a.interragitAvec(s)){
                        if (!ListIp.contains(s1)){
                            // ListIp.add(s1);
                            System.out.println(s1);
                        }
                    }
                }
                for (String s : ListIp){System.out.println(s);}
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
                Graph graph = new Graph(ListIp.size(), a.nbTrames(), a.sourceDest(), a);
                
                //La légende
                JPanel legende = new JPanel();
                legende.setLayout(new BoxLayout(legende, BoxLayout.X_AXIS));
                legende.setAlignmentX(Component.LEFT_ALIGNMENT);
                legende.setSize(graph.getWidth(), 50);
                for (String s : ListIp) {
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
                JLabel space = new JLabel(" ");
                descrTrames.add(space);
                JLabel space1 = new JLabel(" ");
                descrTrames.add(space1);
                JLabel space2 = new JLabel(" ");
                descrTrames.add(space2);
                JLabel space3 = new JLabel(" ");
                descrTrames.add(space3);
                
                for (int i = 0; i < a.nbTrames(); i ++){            
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
                fenetre.setVisible(false);
                fenetre.setVisible(true);            
            }
        } );
        fenetre.setVisible(true);

    }
}
