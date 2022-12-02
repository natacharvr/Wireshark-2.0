import java.util.List;

public class Test{
    public static void main(String[] args) {
        //Les datas des trames
        Analyseur a = new Analyseur("test.txt");
        List<String> ListIp = a.diffIp();
        
        for (String s : a.interragitAvec(ListIp.get(0))) {
            System.out.println(s);
            ListIp.add(s);
        }
        System.out.println(a.interragitAvec(ListIp.get(0)).size());
    }
}