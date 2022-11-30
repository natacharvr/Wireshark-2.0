import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyseur {
    private File fichier; //le fichier texte contenant des trames
    private List<Trame> trames; //une liste de Trame extraite du fichier

    /**
     * @return un tableau dans avec les correspondances entre la colonne de l'ip dans l'affichage et les trames (pour avoir les x pour les flèches)
     */
    public int[][] sourceDest(){
        List<String> l = diffIp();
        int[][] res = new int[trames.size()][2];
        for (int i = 0; i < trames.size(); i++){
            res[i][0] = trames.get(i).indiceSource(l);
            res[i][1] = trames.get(i).indiceDestination(l);
        }
        return res;
    }

    public Analyseur(String path){
        fichier = new File(path);  
        trames = new ArrayList<Trame>();
        découpage();
    }

    public int nbTrames(){
        return trames.size();
    }

    /**
     * Découpe le fichier texte reçu en une liste de Trames
     */
    public void découpage(){
        Scanner scan;
        //Le pattern p correspond à une ligne sur laquelle il y a 4 caractères qui correspondent à l'indice de la ligne, puis entre 1 et 3 espaces, puis entre 1 et n couples de caractères séparés par un espace, et enfin m caractères ne correspondant pas à des octets
        //Donc chaque ligne contient au moins 1 octet pour fonctionner
        Pattern p = Pattern.compile("^([\\w]{4})\s{1,3}(([\\w]{2}\s)*([\\w]{2})).*$");

        try {
            scan = new Scanner(fichier);
        }
        catch(FileNotFoundException e){
            System.out.println("Le fichier " + fichier + " n'existe pas");
            return;
        }
        
        String line;
        String trame = "";
        while (scan.hasNextLine()){ //tant qu'on n'arrive pas à la fin du fichier
            line = scan.nextLine(); //On récupère la prochaine ligne
            Matcher m = p.matcher(line);
            if (m.find()){ //Si la ligne correspond au motif attendu
                 //On crée une nouvelle trame si on arrive sur une ligne qui commence par 0000 (donc nouvelle trame) et que trame n'est pas vide
                if (!(trame.equals("")) && (m.group(1).compareTo("0000") == 0)){
                    trames.add(new Trame(trame));
                    trame = ""; //On remet trame à la chaine vide pour la trame suivante
                }
                //On ajoute à la string trame la suite des octets
                trame += " " + m.group(2);
            }
            
        }
        if (trame != "") trames.add(new Trame(trame)); //A la fin de la boucle, trame n'est pas vide (normalement) car on n'a pas ajouté la dernière trame, n'étant pas tombés sur un nouveau début, donc on l'ajoute
        scan.close();
    }

    /**
     * Affiche le toString de chaque trame
     */
    public void afficherTrames(){
        String res = "";
        int i = 0;
        for (Trame t : trames){
            res += i + " : " + t.toString() + "\n";
            i ++;
        }
        System.out.println(res);
    }

    /**
     * @param i l'indice de la trame souhaitée
     * @return le toString de la trame à l'indice i
     */
    public String DataTrameI(int i){
        return trames.get(i).toString();
    }

    public int getSourcePortI(int i){
        return trames.get(i).getSourcePort();
    }

    public int getDestinationPortI(int i){
        return  trames.get(i).getDestinationPort();
    }

    /**
     * Itère sur les trames et crée un tableau des ip qui interragissent, sans doublons
     * @return la liste des différentes Ip qui interagissent dans les trames fournies
     */
    public List<String> diffIp(){
        List<String> res = new ArrayList<String>();
        String ipS;
        String ipD;
        for (Trame t : trames){
            ipS = t.getSourceIp();
            if (!res.contains(ipS)){
                res.add(ipS);
            }
            ipD = t.getDestinationIp();
            if (!res.contains(ipD)){
                res.add(ipD);
            }
        }
        return res;
    }

    public List<String> interragitAvec(String s){
        List<String> res = new ArrayList<String>();
        for (Trame t : trames){
            if (t.getSourceIp().equals(s)) res.add(t.getDestinationIp());
            if (t.getDestinationIp().equals(s)) res.add(t.getSourceIp());
        }
        return res;
    }
}
