// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

public class TestAnalysateur {
    public static void main(String[] args) {
        Analyseur a = new Analyseur("trameTest.txt");
        a.découpage();
        // a.afficherTrames();
        // Pattern p = Pattern.compile("^([0-9]{4})\s((.{2}\s){0,15}(.{2}){0,1}).*");
        // Matcher m = p.matcher("0000 c0 e4 34 af a9 19 da d9 6a 9b 4b 56 08 00 45 00");
        // if (m.find()){
        //     System.out.println(m.group(2));
        // }
    }
}
