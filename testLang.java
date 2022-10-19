import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testLang {
    public static void main(String[] args) {
        // String a = "0000  44 f3 7f ce ea 48 42 2c a2                        D....HB,.";
        // Pattern p = Pattern.compile("^([\\w]{4})\s{1,2}(([\\w]{2}\s){0,15}([\\w]{2}){0,1}).*");
        // Matcher m = p.matcher(a);
        String b = "a";
        Pattern p1 = Pattern.compile("[a-z]");
        Matcher m1 = p1.matcher(b);
        System.out.println(m1.group(1));
    }
}
