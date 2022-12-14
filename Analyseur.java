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
    private List<Trame> tramesConcernees; //une liste de trames après un filtre applique par l'utilisateur


    public Analyseur(String path){
        fichier = new File(path);  
        trames = new ArrayList<Trame>();
        decoupage();
    }

    public int nbTrames(){
        return trames.size();
    }

    /**
     * Decoupe le fichier texte reçu en une liste de Trames
     */
    public void decoupage(){
        Scanner scan;
        //Le pattern p correspond à une ligne sur laquelle il y a 4 caractères qui correspondent à l'indice
        // de la ligne, puis entre 1 et 3 espaces, puis entre 1 et n couples de caractères separes par un espace,
        // et enfin m caractères ne correspondant pas à des octets. Donc chaque ligne contient au moins 1 octet pour fonctionner
        Pattern p = Pattern.compile("^([\\w]{4})\s+(([\\w]{2}\s)*([\\w]{2})).*$");

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
            line = scan.nextLine(); //On recupère la prochaine ligne
            Matcher m = p.matcher(line);
            if (m.find()){ //Si la ligne correspond au motif attendu
                //On cree une nouvelle trame si on arrive sur une ligne qui commence par 0000 (donc nouvelle trame) et que trame n'est pas vide
                if (!(trame.equals("")) && (m.group(1).compareTo("0000") == 0)){
                    trames.add(new Trame(trame));
                    trame = ""; //On remet trame à la chaine vide pour la trame suivante
                }
                //On ajoute à la string trame la suite des octets
                trame += " " + m.group(2); //m.group() permet de selectionner une partie de la regex specifiee avec les parenthèses
            }
            
        }
        //A la fin de la boucle, trame n'est pas vide (normalement) car on n'a pas ajoute la dernière trame,
        // n'etant pas tombes sur un nouveau debut, donc on l'ajoute
        if (trame != "") trames.add(new Trame(trame)); 
        scan.close();
    }

    /**
     * Affiche le toString de chaque trame
     */
    public void afficherTrames(){
        String res = ""; //Possibilite d'utiliser un string Builder, legère optimisation 
        int i = 0;
        for (Trame t : trames){
            res += i + " : \n" + t.toString() + "\n\n";
            i ++;
        }
        System.out.println(res);
    }

    /**
     * Cette fonction trie les trames du fichier pour ne garder que celles qui proviennent ou vont vers les Ip selectionnees par
     * l'utilisateur
     * @param l La liste des Ip qui sont concernees par la demande. Donc seuelemt celles cochees par l'utilisateur
     */
    public void tramesConcernees(List<String> l){
        tramesConcernees = new ArrayList<Trame>();
        for (Trame t : trames){
            if (t.isIpv4()){
                if (l.contains(t.getSourceIp())) tramesConcernees.add(t);
                else if (l.contains(t.getDestinationIp())) tramesConcernees.add(t);
            }
        }
    }

    /**
     * @param ListeIpConcernees la liste des Ip qui interragissent, donc même celles non selectionnees mais qui echangent avec les Ip selectionnees
     * @param ListIp la liste des Ip selectionnees par l'utilisateur
     * @return un tableau dans avec les correspondances entre la colonne de l'ip dans l'affichage et les trames (pour avoir les x pour les flèches)
     * Attention, le resultat depend des Ip concernees par la demande 
     */
    public int[][] sourceDest(List<String> ListeIpConcernees, List<String> ListIp){
        if (tramesConcernees == null) tramesConcernees(ListIp);
        int[][] res = new int[tramesConcernees.size()][2];
        for (int i = 0; i < tramesConcernees.size(); i++){
            res[i][0] = tramesConcernees.get(i).indiceSource(ListeIpConcernees);
            res[i][1] = tramesConcernees.get(i).indiceDestination(ListeIpConcernees);
        }
        return res;
    }


    /**
     * @param i l'indice de la trame souhaitee
     * @return le toString de la trame à l'indice i
     */
    public String DataTrameI(int i){
        return tramesConcernees.get(i).toString();
    }

    /**
     * @param i l'indice de la trame souhaitee
     * @return le port source de la trame à l'indice i
     */
    public int getSourcePortI(int i){
        return tramesConcernees.get(i).getSourcePort();
    }

     /**
     * @param i l'indice de la trame souhaitee
     * @return le port destination de la trame à l'indice i
     */
    public int getDestinationPortI(int i){
        return  tramesConcernees.get(i).getDestinationPort();
    }

    /**
     * Itère sur les trames et cree un tableau des ip qui interragissent, sans doublons
     * @return la liste des differentes Ip qui interagissent dans les trames fournies
     */
    public List<String> diffIp(){
        List<String> res = new ArrayList<String>();
        String ipS;
        String ipD;
        for (Trame t : trames){
            if (t.isIpv4()){
                ipS = t.getSourceIp();
                if (!res.contains(ipS)){
                    res.add(ipS);
                }
                ipD = t.getDestinationIp();
                if (!res.contains(ipD)){
                    res.add(ipD);
                }
            }
        }
        return res;
    }

    /**
     * @param s Une Ip dont on veut connaître les correspondants
     * @return une liste d'Ip avec qui correspond l'Ip demandee (qui lui envoie des trames ou qui en reçoit d'elle)
     */
    public List<String> interragitAvec(String s){
        List<String> res = new ArrayList<String>();
        String source;
        String dest;
        for (Trame t : trames){
            if (t.isIpv4()){
                source = t.getSourceIp();
                dest = t.getDestinationIp();
                if (source.equals(s)) res.add(t.getDestinationIp());
                if (dest.equals(s)) res.add(t.getSourceIp());
            }
        }
        return res;
    }

    /**
     * @param l La liste des Ip demandees par l'utilisateur
     * @return le nombre de trames qu'ont echangees les Ip selectionnees par l'utilisateur
     */
    public int nbTramesConcernee(List<String> l){
        tramesConcernees(l);
        return tramesConcernees.size();
    }
}
