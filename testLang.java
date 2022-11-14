import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testLang {
    //Classe faite pour des tests annexes si doutes sur le fonctionnement de java
    public static void main(String[] args) {
        String a = "0000  44 f3 7f ce ea 48 42 2c a2                        D....HB,.";
        Pattern p = Pattern.compile("^([\\w]{4})\s{1,2}(([\\w]{2}\s){0,15}([\\w]{2}){0,1}).*");
        Matcher m = p.matcher(a);
        if (m.find()){
            System.out.println(m.group(2));
        }
        else{
            System.out.println("Pas de match");
        }
    }
}
