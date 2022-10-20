// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

public class TestAnalysateur {
    public static void main(String[] args) {
        Analyseur a = new Analyseur("trameTest.txt");
        a.découpage();
        a.afficherTrames();
    }
}
