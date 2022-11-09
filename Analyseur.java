import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyseur {
    private File fichier;
    private List<Trame> trames;

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
        Pattern p = Pattern.compile("^([\\w]{4})\s{1,3}(([\\w]{2}\s)*([\\w]{2})).*$");

        try {
            scan = new Scanner(fichier);
        }
        catch(FileNotFoundException e){
            System.out.println("Le fichier " + fichier + " n'existe pas");
            return;
        }
        
        while (scan.hasNextLine()){
            String line;
            String trame = "";
            while (scan.hasNextLine()){
                line = scan.nextLine();
                Matcher m = p.matcher(line);
                if (m.find()){
                    if (!(trame.equals("")) && (m.group(1).compareTo("0000") == 0)){
                        trames.add(new Trame(trame));
                        trame = "";
                    }

                    trame += " " + m.group(2);
                }
                
            }     
            trames.add(new Trame(trame));       
        }
        scan.close();
    }

    public void afficherTrames(){
        String res = "";
        int i = 0;
        for (Trame t : trames){
            res += i + " : " + t.toString() + "\n";
            i ++;
        }
        System.out.println(res);
    }

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
}
