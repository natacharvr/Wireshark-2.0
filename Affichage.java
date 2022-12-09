import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class Affichage {
    public static final Border blackline = BorderFactory.createLineBorder(Color.black);
    private static boolean ipDefinies = false;

    public static JFrame fenetre(){
        JFrame fenetre = new JFrame("FlowGuard");
        fenetre.setLayout(new BorderLayout());
        fenetre.setSize(1000, 600);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return fenetre;
    }

    public static String recupFichier(JFrame fenetre) {
    	//Récupérer le nom du fichier :
    	
    	// Panel pour afficher les boutons et le nom du fichier sélectionné
        JPanel recup = new JPanel();
        recup.setLayout(new BoxLayout(recup, BoxLayout.Y_AXIS));
        
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.X_AXIS));
        JLabel logo = new JLabel(new ImageIcon("/Users/mathis/git/Wireshark-2.0/ImageAcceuil.png"));
        logoPanel.add(Box.createHorizontalGlue());
        logoPanel.add(logo);
        logoPanel.add(Box.createHorizontalGlue());
        recup.add(logoPanel);
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        JLabel text = new JLabel("Veuillez selectionner le fichier contenant les traces :");
        textPanel.add(Box.createHorizontalGlue());
        textPanel.add(text);
        textPanel.add(Box.createHorizontalGlue());
        recup.add(textPanel);
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		// Ouvre l'explorateur du fichier
		JButton button1 = new JButton("Parcourir");
		// Ouvre le dossier test
		JButton button2 = new JButton("Fichiers fournis");
		// Valide le fichier selectionné
		JButton button3 = new JButton("Valider");
		// Objet qui permet l'interaction avec l'explorateur de fichier
		SelecteurFichier selecteurfichier = new SelecteurFichier();
		// les boutons sont mis à l'écoute des actions utilisateurs
		button1.addActionListener(selecteurfichier);
		button2.addActionListener(selecteurfichier);
		button3.addActionListener(selecteurfichier);
		// Ajout des boutons sur le panel
		buttons.add(Box.createHorizontalGlue());
		buttons.add(button1);
		buttons.add(button2);
		buttons.add(button3);
		buttons.add(Box.createHorizontalGlue());
        recup.add(new JLabel("  "));
        recup.add(buttons);
        recup.add(new JLabel("  "));
        recup.add(new JLabel("  "));

		// ajout du panel à la fenetre
        selecteurfichier.nomfichier.setMaximumSize(new Dimension(500, 20));
        selecteurfichier.nomfichier.setSize(100, 10);
		recup.add(selecteurfichier.nomfichier);
		fenetre.add(recup);

		fenetre.setVisible(true);
		
        while (!selecteurfichier.isSelectionDone()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        fenetre.remove(recup);
        fenetre.setVisible(false);		
		return selecteurfichier.nomfichier.getText();
    }

    //N'est plus utilisé, ancienne version pour inserer le path soi-même
    /*
    public static String recupNom(JFrame fenetre){
        //Récupérer le nom du fichier :
        JPanel recup = new JPanel();
        recup.setLayout(new BoxLayout(recup, BoxLayout.Y_AXIS));
        recup.add(new JLabel("Entrez le chemin absolu du fichier contenant les traces"));
        JTextField nomFichier = new JTextField(".txt", 100);
        nomFichier.setMaximumSize(new Dimension(fenetre.getWidth(), 20));
        nomFichier.setSize(100, 10);
        recup.add(nomFichier);
        JButton envoyerNom = new JButton("Soumettre");
        recup.add(envoyerNom);
        envoyerNom.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                nom = nomFichier.getText();
                fenetre.remove(recup);
                fenetre.setVisible(false);
                nomAcquis = true;
            }
        });

        fenetre.add(recup);
        fenetre.setVisible(true);
        while (!nomAcquis){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        return nom;
    }
    */

    public static void selectIp(JFrame fenetre, List<String> ListIp){
        JPanel checkPanel = new JPanel();
        checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.Y_AXIS));
        checkPanel.add(new JLabel("Selectionnez les Ip dont vous voulez visualiser les trames (entrantes et sortantes)"));
        JCheckBox  checkBox;
        List<JCheckBox> listCheck = new ArrayList<JCheckBox>();

        for (String s : ListIp) {
            checkBox = new JCheckBox(s, true);
            checkPanel.add(checkBox);
            listCheck.add(checkBox);
        }

        JButton btn1 = new JButton("Soumettre");

        checkPanel.add(btn1);
        checkPanel.add(new JLabel("Attention, le traitement peut être long. Patientez quelques secondes après avoir pressé le bouton Soumettre"));
        checkPanel.add(new JLabel("Veillez à bien selectionner au moins une IP, un ecran blanc n'est pas très passionant"));

        JScrollPane scrollCheck = new JScrollPane(checkPanel);
        scrollCheck.getVerticalScrollBar().setUnitIncrement(16);

        fenetre.add(scrollCheck);
        fenetre.setVisible(true);

        //update de la fenetre quand on clique sur le bouton
        btn1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //recuperation des Ip selectionnees et retirer de la liste des Ip celles qui ne le sont pas
                for (JCheckBox c : listCheck){
                    if (!c.isSelected()){
                        String text = c.getText();
                        // System.out.println(text);
                        ListIp.remove(text);
                    }
                }

                //Affichage off puis on pour refresh
                fenetre.setVisible(false);

                //Après le traitement des donnees fournies par l'utilisateur, on retire le sondage et on va afficher ce qu'il demande
                fenetre.remove(scrollCheck);
                ipDefinies = true;
            }
        } );


        while (!ipDefinies){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static List<String> getIpConcernees(List<String> ListIp, Analyseur a){
        //Utilisation d'un set pour eliminer les doublons
        HashSet<String> temp = new HashSet<String>(); 
        for (String s : ListIp){
            for (String s1 : a.interragitAvec(s)){
                if (!ListIp.contains(s1)){
                    temp.add(s1);
                }
            }
        }
        //ListIpConcernees contient toutes les Ip dont on devra afficher une colonne (celles qui nous interressent + celles avec qui elles interragissent)
        ArrayList<String> ListeIpConcernees = new ArrayList<String>(ListIp);
        ListeIpConcernees.addAll(temp);
        return ListeIpConcernees;
    }

    public static void export(JPanel total, String nom){
        BufferedImage image = new BufferedImage(total.getSize().width, total.getSize().height, BufferedImage.TYPE_3BYTE_BGR); 
        
        Graphics g = image.createGraphics();
        total.paint(g);
        g.dispose();

        //Récuperation du nom du fichier sans le path et sans le .txt
        String nomImage = nom.split((".txt"))[0];
        nomImage = nomImage.replace("\\", "/");
        String[] splitPath = nomImage.split("/");
        nomImage = splitPath[splitPath.length - 1];
        try{
            ImageIO.write(image,"jpg",new File(nomImage + ".jpg"));
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static JPanel descriptionTrames(Analyseur a, List<String> ListIp){
        JPanel descrTrames = new JPanel();
        descrTrames.setLayout(new BoxLayout(descrTrames, BoxLayout.Y_AXIS));
        descrTrames.setAlignmentY(JPanel.TOP_ALIGNMENT);
        //Sauts de lignes pour aligner avec les flèches
        descrTrames.add(new JLabel(" "));
        descrTrames.add(new JLabel(" "));
        descrTrames.add(new JLabel(" "));
        descrTrames.add(new JLabel(" "));
        
        for (int i = 0; i < a.nbTramesConcernee(ListIp); i ++){
            descrTrames.add(new JLabel("<html>Trame n°" + i + " " + a.DataTrameI(i) + "</html>"));
            descrTrames.add(new JLabel(" "));
            descrTrames.add(new JLabel(" "));
        }
        return descrTrames;
    }

    public static JPanel legende(List<String> ListeIpConcernees, int width){
    	//La légende (encadré en haut avec les IP)
        JPanel legende = new JPanel();
        legende.setLayout(new BoxLayout(legende, BoxLayout.X_AXIS));
        legende.setAlignmentX(Component.LEFT_ALIGNMENT);
        legende.setSize(width, 50);
        legende.add(new JLabel("                            ")); //Décalage initial pour centrer l'IP avec la ligne verticale
        for (String s : ListeIpConcernees) {
            legende.add(new JLabel(s));
            legende.add(new JLabel("              "));
        } 
        legende.add(new JLabel("                               "));  //Décalage pour que la bordure droite s'aligne avec l'encadré
        legende.setBorder(blackline);
        return legende;
    }

    public static JPanel total(){
        JPanel total = new JPanel();
        total.setLayout(new BoxLayout(total, BoxLayout.X_AXIS));
        total.setAlignmentY(JPanel.TOP_ALIGNMENT);
        return total;
    }

    public static JPanel graphique(){
        JPanel graphique = new JPanel();
        graphique.setLayout(new BoxLayout(graphique, BoxLayout.Y_AXIS));
        graphique.setAlignmentY(JPanel.TOP_ALIGNMENT);
        graphique.setBorder(blackline);
        graphique.setSize(graphique.getPreferredSize());
        return graphique;
    }
}
