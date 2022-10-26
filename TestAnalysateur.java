import java.util.List;
import java.awt.GridLayout;
import javax.swing.*;

// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

public class TestAnalysateur {
    public static void main(String[] args) {
        Analyseur a = new Analyseur("test.txt");
        // a.afficherTrames();
        List<String> ListIp = a.diffIp();
        
        JFrame window = new JFrame("Wireshark 2.0");
        window.setSize(500,500);
        window.setLayout(new GridLayout(1, 1));


        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Graph g = new Graph(ListIp.size(), a.nbTrames());
        window.getContentPane().add(g);
        window.setVisible(true);
    }
}
