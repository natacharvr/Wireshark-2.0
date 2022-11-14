import javax.swing.*;
import java.awt.GridLayout;
public class Interface{
    //Classe inutile, juste des expérimentations avec l'interface de java swing
    //Voir ici pour les différents composants possibles
   public static void main(String[] args) {
        // Définissez le frame
        JFrame frame = new JFrame("Hello World");
    
        JLabel label = new JLabel("Je suis un JLabel", JLabel.CENTER);
        //frame.add(label);
    
        // Définissez le panel
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        // Définir les boutons
        JButton btn1 = new JButton("Bouton 1");
        JButton btn2 = new JButton("Bouton 2");      
        // Ajouter les boutons au frame
        panel.add(btn1); 
        panel.add(btn2);

        String[] langs = {"PHP", "Java", "Python", "C++", "Ruby"};
        // Créer une liste déroulante
        JComboBox<String> cb = new JComboBox<String>(langs);
        panel.add(cb);

         // Créer des cases à cocher    
         JCheckBox checkBox1 = new JCheckBox("Java", true);
         JCheckBox checkBox2 = new JCheckBox("PHP");
         JCheckBox checkBox3 = new JCheckBox("Python", true);
         // Ajouter les cases à cocher au frame
         
         //boutons radio
         ButtonGroup group = new ButtonGroup();     
         JRadioButton radio1 = new JRadioButton("ON", true);
         JRadioButton radio2 = new JRadioButton("OFF", false);
         // Ajouter les boutons radio au groupe
         group.add(radio1);
         group.add(radio2);
         
         // Ajouter les boutons au frame
         panel.add(radio1); 
         panel.add(radio2);
         panel.add(checkBox1); 
         panel.add(checkBox2);
         panel.add(checkBox3);
         // Définir le menu principal
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("Fichier");
        JMenu edit = new JMenu("Edition");
        JMenu help = new JMenu("Aide");
         
        // Définir le sous-menu pour Fichier
        JMenuItem newf = new JMenuItem("Nouveau");
        JMenuItem quit = new JMenuItem("Ouvrir");
        JMenuItem save = new JMenuItem("Enregistrer");
 
        file.add(newf);
        file.add(save);
        file.add(quit);
         
        menu.add(file);
        menu.add(edit);
        menu.add(help);

        JSlider slider = new JSlider();
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
    
        
        // Ajouter label et panel au frame
        frame.setLayout(new GridLayout(5, 1));
        panel.setLayout(new GridLayout(6,1));
        frame.add(menu);
        frame.add(label);
        frame.add(slider);
        frame.add(panel);
        frame.add(panel1);

        

        String[] column = {"ID", "Nom", "Age", "Adresse"};
    
        //Les lignes du JTable
        String[][] data = {
                {"01", "Thomas", "55", "Paris"}, 
                {"02", "Emily", "45", "Marseille"}, 
                {"03", "Yohan", "12", "Lyon"}, 
                {"04", "Jean", "29", "Toulouse"},
                {"05", "Bob", "30", "Nice"}
        };
        
        // Créer le JTable
        JTable table = new JTable(data, column);
        JScrollPane scroll = new JScrollPane(table); 
        panel1.add(scroll);
         
        frame.pack();
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}