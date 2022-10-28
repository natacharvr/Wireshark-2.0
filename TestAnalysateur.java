import java.util.ArrayList;
import java.util.List;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.*;

// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

public class TestAnalysateur {
    public static void main(String[] args) {
        Analyseur a = new Analyseur("test.txt");
        // a.afficherTrames();
        List<String> ListIp = a.diffIp();
        
        JFrame window = new JFrame("Wireshark 2.0");
        window.setSize(500,500);
        window.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        int deltaX = window.getWidth() / (ListIp.size() + 1);
        //panel.setBounds(0, 0, window.getWidth() - deltaX, 50);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(1, (deltaX - 20), 1, (deltaX - 20));
        
        //ArrayList<JTextArea> legendeIp = new ArrayList<JTextArea>(); //une liste de zeones de texte à placer au dessus des lignes verticales représentant les ip
        for (String s : ListIp) {
            JTextField txt = new JTextField(s);
            txt.setHorizontalAlignment(JTextField.CENTER);
            c.gridx = ListIp.indexOf(s);
            c.gridy = 0;
            panel.add(txt, c);
            //txt.setBounds(deltaX * (ListIp.indexOf(s) + 1), 100, 15, 6);
            //legendeIp.add(txt);
        }     

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Graph g = new Graph(ListIp.size(), a.nbTrames(), a.sourceDest());
        window.add(panel, BorderLayout.NORTH);
        window.getContentPane().add(g);
        window.setVisible(true);
    }
}
