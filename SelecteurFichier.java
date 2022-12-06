import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

@SuppressWarnings("serial")
public class SelecteurFichier extends JFrame implements ActionListener {
	// Jlabel to show the files user selects
	JLabel nomfichier;
	private boolean selectionOK = false;

	// a default constructor
	SelecteurFichier()
	{
		// set the label to its initial value
		nomfichier = new JLabel("no file selected");
	}
	
	public boolean isSelectionDone() {
		return selectionOK;
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		// on récupère les boutons disponibles
		String com = evt.getActionCommand();

		if (com.equals("Valider")) {
			selectionOK = true;
		}
		// if the user presses the open dialog show the open dialog

		else {
	    	String path = System.getProperty("user.dir");
	    	File dir = new File(path);
			
			// create an object of JFileChooser class
			JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getChild(dir,"tramesTest"));

			// restrict the user to select files of all types
			j.setAcceptAllFileFilterUsed(false);

			// set a title for the dialog
			j.setDialogTitle("Select a .txt file");

			// only allow files of .txt extension
			FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
			j.addChoosableFileFilter(restrict);

			// invoke the showsOpenDialog function to show the save dialog
			int r = j.showOpenDialog(null);

			// if the user selects a file
			if (r == JFileChooser.APPROVE_OPTION) {
				// set the label to the path of the selected file
				nomfichier.setText(j.getSelectedFile().getAbsolutePath());
			}
			// if the user cancelled the operation
			//else
			//	nomfichier.setText("Vous avez interrompu la sélection");
		}
	}
}
