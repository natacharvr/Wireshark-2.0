public class TestAnalysateur {
    public static void main(String[] args) {
        Analyseur a = new Analyseur("trameTest.txt");
        String dest = a.destination();
        System.out.println("dest : " + dest);
        String source = a.source();
        System.out.println("source : " + source);
    }
}
