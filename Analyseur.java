import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Analyseur {
    private File trame;
    private Scanner scan;

    public Analyseur(String path){
        trame = new File(path);
        try{
            scan = new Scanner(trame);
        }
        catch(FileNotFoundException e){
            System.out.println("Le fichier n'existe pas");
        }
    }

    public String destination(){
        String res = "";
        int IdLigne = Integer.parseInt(scan.next(Pattern.compile("[0-9]{4}")));
        System.out.println(IdLigne);
        if (scan.hasNext()){
            if (IdLigne == 0){
                res += scan.next(Pattern.compile(".{2}"));
                for (int i = 0; i < 5; i ++)
                res += ":" + scan.next(Pattern.compile(".{2}"));
            }
        }
        return res;
    }

    public String source(){
        String res = "";
        if (scan.hasNext()){
            res += scan.next(Pattern.compile(".{2}"));
                for (int i = 0; i < 5; i ++)
                res += ":" + scan.next(Pattern.compile(".{2}"));
        }
        return res;
    }
}