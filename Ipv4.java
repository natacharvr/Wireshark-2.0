import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ipv4 {
    
    public static int version(String contenu){
        return Integer.parseInt("" + contenu.charAt(0));
    }

    public static int headerLength(String contenu){
        return Integer.parseInt("" + contenu.charAt(1)) * 4;
    }

    public static int size(String contenu){
        String res = contenu.substring(6, 11);
        res = res.replace(" ", "");
        return Integer.parseInt(res, 16);
    }

    public static String sourceIp(String contenu){
        String res = contenu.substring(36, 47);
        Pattern p = Pattern.compile("(..) (..) (..) (..)");
        Matcher m = p.matcher(res);
        if(m.find()){
            res = "";
            for (int i = 1; i < 5; i ++){
                res += Integer.parseInt(m.group(i), 16) + ".";
            }
        }        return res;
    }

    public static String destinationIp(String contenu){
        String res = contenu.substring(48, 59
        );
        Pattern p = Pattern.compile("(..) (..) (..) (..)");
        Matcher m = p.matcher(res);
        if(m.find()){
            res = "";
            for (int i = 1; i < 5; i ++){
                res += Integer.parseInt(m.group(i), 16) + ".";
            }
        }
        return res;
    }

}
