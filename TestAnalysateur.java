import java.lang.invoke.StringConcatFactory;
import java.util.List;

public class TestAnalysateur {
    //Classe de tests du découpage des trames, utiliser test.txt pour avoir beaucoup de trames, et testTrames.txt pour un fichier n'en contenant que 2
    public static void main(String[] args) {
        Analyseur a = new Analyseur("tp1.txt"); //Se charge de tous les découpages
        a.afficherTrames(); //Affiche les toString de toutes les trames, donc tous les champs récupérés
        List<String> ListIp = a.diffIp(); //Sert pour l'affichage graphique, voir TestGraphqiue.java
        for (String s : ListIp) {
            System.out.println(s);
        }
    }
}
