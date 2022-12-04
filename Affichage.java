import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class Affichage {
    public static final Border blackline = BorderFactory.createLineBorder(Color.black);
    private static boolean nomAcquis = false;
    private static boolean ipDefinies = false;
    private static String nom = "";

    public static JFrame fenetre(){
        JFrame fenetre = new JFrame("Wireshark 2.0");
        fenetre.setLayout(new BorderLayout());
        fenetre.setSize(1000, 600);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return fenetre;
    }

    public static String recupNom(JFrame fenetre){
        //Récupérer le nom du fichier :
        JPanel recup = new JPanel();
        recup.setLayout(new BoxLayout(recup, BoxLayout.Y_AXIS));
        recup.add(new JLabel("Entrez le chemin absolu du fichier contenant les traces"));
        JTextArea nomFichier = new JTextArea(".txt");
        nomFichier.setSize(100, 10);
        recup.add(nomFichier);
        JButton envoyerNom = new JButton("Soumettre");
        recup.add(envoyerNom);
        envoyerNom.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                nom = nomFichier.getText();
                fenetre.remove(recup);
                fenetre.setVisible(false);
                nomAcquis = true;
            }
        });

        fenetre.add(recup);
        fenetre.setVisible(true);
        while (!nomAcquis){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        return nom;
    }

    public static void selectIp(JFrame fenetre, List<String> ListIp){
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
        checkPanel.add(new JLabel("Attention, le traitement est long. Patientez quelques secondes après avoir presse le bouton Soumettre"));
        checkPanel.add(new JLabel("Veillez à bien selectionner au moins une Ip, un ecran blanc n'est pas très passionant"));

        fenetre.add(checkPanel);
        fenetre.setVisible(true);

        //update de la fenetre quand on clique sur le bouton
        btn1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //recuperation des Ip selectionnees et retirer de la liste des Ip celles qui ne le sont pas
                for (JCheckBox c : listCheck){
                    if (!c.isSelected()){
                        String text = c.getText();
                        // System.out.println(text);
                        ListIp.remove(text);
                    }
                }

                //Affichage off puis on pour refresh
                fenetre.setVisible(false);

                //Après le traitement des donnees fournies par l'utilisateur, on retire le sondage et on va afficher ce qu'il demande
                fenetre.remove(checkPanel);
                ipDefinies = true;
            }
        } );


        while (!ipDefinies){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static List<String> getIpConcernees(List<String> ListIp, Analyseur a){
        //Utilisation d'un set pour eliminer les doublons
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
        return ListeIpConcernees;
    }

    public static void export(JPanel total){
        BufferedImage image = new BufferedImage(total.getSize().width, total.getSize().height, BufferedImage.TYPE_3BYTE_BGR); 
        
        Graphics g = image.createGraphics();
        total.paint(g);
        g.dispose();
        try{
            ImageIO.write(image,"png",new File(nom + ".png"));
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static JPanel descriptionTames(Analyseur a, List<String> ListIp){
        JPanel descrTrames = new JPanel();
        descrTrames.setLayout(new BoxLayout(descrTrames, BoxLayout.Y_AXIS));
        descrTrames.setAlignmentY(JPanel.TOP_ALIGNMENT);
        //Sauts de lignes pour aligner avec les flèches
        descrTrames.add(new JLabel(" "));
        descrTrames.add(new JLabel(" "));
        descrTrames.add(new JLabel(" "));
        
        for (int i = 0; i < a.nbTramesConcernee(ListIp); i ++){            
            JLabel txt = new JLabel("<html>Trame n°" + i + " " + a.DataTrameI(i) + "</html>");
            descrTrames.add(txt);
            JLabel space4 = new JLabel(" ");
            descrTrames.add(space4);
            JLabel space5 = new JLabel(" ");
            descrTrames.add(space5);
        }
        return descrTrames;
    }

    public static JPanel legende(List<String> ListeIpConcernees, int width){
        JPanel legende = new JPanel();
        legende.setLayout(new BoxLayout(legende, BoxLayout.X_AXIS));
        legende.setAlignmentX(Component.LEFT_ALIGNMENT);
        legende.setSize(width, 50);
        legende.add(new JLabel("                                     "));
        for (String s : ListeIpConcernees) {
            legende.add(new JLabel(s));
            legende.add(new JLabel("                          "));
        } 
        JLabel spac = new JLabel("                                 ");
        legende.add(spac);
        legende.setBorder(blackline);
        return legende;
    }

    public static JPanel total(){
        JPanel total = new JPanel();
        total.setLayout(new BoxLayout(total, BoxLayout.X_AXIS));
        total.setAlignmentY(JPanel.TOP_ALIGNMENT);
        return total;
    }

    public static JPanel graphique(){
        JPanel graphique = new JPanel();
        graphique.setLayout(new BoxLayout(graphique, BoxLayout.Y_AXIS));
        graphique.setAlignmentY(JPanel.TOP_ALIGNMENT);
        graphique.setBorder(blackline);
        return graphique;
    }
}
