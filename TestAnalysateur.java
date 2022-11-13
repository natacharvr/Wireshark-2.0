// import java.util.ArrayList;
import java.util.List;
// import java.awt.GridLayout;
import javax.swing.*;

import java.awt.*;

// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

public class TestAnalysateur {
    public static void main(String[] args) {
        Analyseur a = new Analyseur("test.txt");
        // a.afficherTrames();
        List<String> ListIp = a.diffIp();
    
    }
}
