import javax.swing.*;

import java.util.List;

public class Wireshark2 {
    public static void main(String[] args) {
        //La fenetre :
        JFrame fenetre = Affichage.fenetre();
        
        String nom = "";
        nom = Affichage.recupFichier(fenetre);
        
        //Les datas des trames
        Analyseur a = new Analyseur(nom);
        List<String> ListIp = a.diffIp();

        //Les ckeckbox pour les filtres
        Affichage.selectIp(fenetre, ListIp);

        //Recuperer les Ip avec lesquelles interragissent celles selectionnees (pour un affichage au top)
        List<String> ListIpConcernees = Affichage.getIpConcernees(ListIp, a);

        //Panneau avec toutes les legendes et le graphique
        JPanel total = Affichage.total();

        //Panneau du graphique et de sa legende
        JPanel graphique = Affichage.graphique();

        //Le graphique
        Graph graph = new Graph(ListIpConcernees.size(), a.nbTramesConcernee(ListIp), a.sourceDest(ListIpConcernees, ListIp), a);
        
        //La legende
        JPanel legende = Affichage.legende(ListIpConcernees, graph.getWidth());
        
        //Ajout au panel
        graphique.add(legende);
        graphique.add(graph);
        graphique.setPreferredSize(graph.getPreferredSize());        
        
        //Panneau des descriptions de trames
        JPanel descrTrames = Affichage.descriptionTames(a, ListIp);
        
        //ajout au total
        total.add(graphique);
        total.add(descrTrames);
        
        JScrollPane scrollGraphLegende = new JScrollPane(total);
        scrollGraphLegende.getVerticalScrollBar().setUnitIncrement(16);
        scrollGraphLegende.getHorizontalScrollBar().setUnitIncrement(16);
        fenetre.add(scrollGraphLegende);
    
        //Affichage des elements :
        fenetre.setVisible(true);
        
        //export 
        Affichage.export(total);
        fenetre.setVisible(true);
    }
}
