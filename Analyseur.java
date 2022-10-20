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

    public Analyseur(String path){
        fichier = new File(path);  
        trames = new ArrayList<Trame>();
    }

    /**
     * Découpe le fichier texte reçu en une liste de Trames
     */
    public void découpage(){
        Scanner scan;
        Pattern p = Pattern.compile("^([\\w]{4})\s{1,2}(([\\w]{2}\s){0,15}([\\w]{2}){0,1}).*");
        try {
            scan = new Scanner(fichier);
        }
        catch(FileNotFoundException e){
            System.out.println("Le fichier " + fichier + " n'existe pas");
            return;
        }
        while (scan.hasNextLine()){
            String line = scan.nextLine();
            String trame = "";
            while (line != ""){
                System.out.println(line);
                Matcher m = p.matcher(line);
                if (m.find()){
                    trame += "\n" + m.group(2);
                    //System.out.println(m.group(2));
                }
                if (!scan.hasNextLine()){
                    break;
                }
                line = scan.nextLine();
            }
            if (trame != "")
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
    /*
    

    public String source(){
        String res = "";
        if (scan.hasNext()){
            res += scan.next(Pattern.compile(".{2}"));
                for (int i = 0; i < 5; i ++)
                res += ":" + scan.next(Pattern.compile(".{2}"));
        }
        return res;
    }*/
}
