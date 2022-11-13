import java.awt.*;
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
        // total.setBorder(blackline);


        //Panneau du graphique et de sa légende
        JPanel graphique = new JPanel();
        graphique.setLayout(new BoxLayout(graphique, BoxLayout.Y_AXIS));
        graphique.setAlignmentY(JPanel.TOP_ALIGNMENT);

        // graphique.setBorder(blackline);


        //Le graphique
        Graph graph = new Graph(ListIp.size(), a.nbTrames(), a.sourceDest());
        JScrollPane scrollGraph = new JScrollPane(graph);
        // scrollGraph.setBorder(blackline);

        
        //La légende
        JPanel legende = new JPanel();
        legende.setLayout(new BoxLayout(legende, BoxLayout.X_AXIS));
        legende.setAlignmentX(Component.LEFT_ALIGNMENT);
        legende.setSize(graph.getWidth(), 50);
        for (String s : ListIp) {
            JLabel txt = new JLabel(s);
            JLabel space = new JLabel("                         ");
            legende.add(space);
            legende.add(txt);
        } 
        // legende.setBorder(blackline);


        //Ajout au panel
        graphique.add(legende);
        graphique.add(scrollGraph);

        //Panneau des descriptions de trames
        JPanel descrTrames = new JPanel();
        descrTrames.setLayout(new BoxLayout(descrTrames, BoxLayout.Y_AXIS));
        descrTrames.setAlignmentY(JPanel.TOP_ALIGNMENT);
        descrTrames.setBorder(blackline);
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
        total.add(graphique);
        total.add(descrTrames);

        JScrollPane scrollGraphLegende = new JScrollPane(total);
        fenetre.add(scrollGraphLegende);

        //Affichage des éléments :
        fenetre.setVisible(true);
    }
}
