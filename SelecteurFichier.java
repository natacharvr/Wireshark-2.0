import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class SelecteurFichier extends JFrame implements ActionListener {
	//JLabel utilisé pour afficher le chemin du fichier sélectionné
	JTextField nomfichier;
	private boolean selectionOK = false;

	// constructeur par défaut
	SelecteurFichier()
	{
		// indique à l'utilisateur qu'il n'a pas encore selectionné de fichier
		nomfichier = new JTextField("aucun fichier sélectionné");
	}
	
	public boolean isSelectionDone() {
		return selectionOK;
	}
	
	public void actionPerformed(ActionEvent evt) {
		// on récupère les boutons disponibles
		String com = evt.getActionCommand();

		if (com.equals("Valider")) {
			selectionOK = true;
		}
		
		// Si l'utilisateur utilise
		else {

			// objet permettant d'ouvrir un explorateur de fichier au dossier spécifié
			JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			
			//Decommenter les 3 lignes dessous et commenter la ligne dessus pour que l'explorateur ouvre
			//directement le dossier contenant les trames test
			/*
	    	String path = System.getProperty("user.dir");
	    	File dir = new File(path);
			JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getChild(dir,"tramesTest"));
			*/

			// restreint l'utilisateur quant aux types de fichier qu'il peut sélectionner
			j.setAcceptAllFileFilterUsed(false);

			// titre dans l'explorateur de fichier
			j.setDialogTitle("Select a .txt file");

			// n'autorise que les extensions .txt
			FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
			j.addChoosableFileFilter(restrict);

			// ouvre l'explorateur de fichier
			int r = j.showOpenDialog(null);

			// si l'utilisateur selectionne un fichier
			if (r == JFileChooser.APPROVE_OPTION) {
				// ajoute le chemin du fichier au label
				nomfichier.setText(j.getSelectedFile().getAbsolutePath());
			}
		}
	}
}
