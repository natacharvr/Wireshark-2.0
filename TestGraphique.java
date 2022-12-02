import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

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
        
        //La légende (encadré en haut avec les IP)
        JPanel legende = new JPanel();
        legende.setLayout(new BoxLayout(legende, BoxLayout.X_AXIS));
        legende.setAlignmentX(Component.LEFT_ALIGNMENT);
        legende.setSize(graph.getWidth(), 50);
        legende.add(new JLabel("           ")); //Décalage initial pour centrer l'IP avec la ligne verticale
        for (String s : ListIp) {
            JLabel txt = new JLabel(s);
            JLabel space = new JLabel("               ");
            legende.add(space);
            legende.add(txt);
        } 
        JLabel spac = new JLabel("                                 "); //Décalage pour que la bordure droite s'aligne avec l'encadré
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
        
        for (int i = 0; i < a.nbTrames(); i ++){            
            JLabel txt = new JLabel(a.DataTrameI(i));
            descrTrames.add(txt);
            descrTrames.add(new JLabel(" "));
            descrTrames.add(new JLabel(" "));
        }
        
        //ajout au total
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
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
