import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	static JLabel l;

	// a default constructor
	SelecteurFichier()
	{
	}

	public static void main(String args[])
	{
		// frame to contains GUI elements
		JFrame f = new JFrame("Selectionnez le fichier .txt à analyser");

		// set the size of the frame
		f.setSize(400, 400);

		// set the frame's visibility
		f.setVisible(true);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// button to open save dialog
		JButton button1 = new JButton("Valider");

		// button to open open dialog
		JButton button2 = new JButton("Ouvrir");

		// make an object of the class filechooser
		SelecteurFichier f1 = new SelecteurFichier();

		// add action listener to the button to capture user
		// response on buttons
		button1.addActionListener(f1);
		button2.addActionListener(f1);

		// make a panel to add the buttons and labels
		JPanel p = new JPanel();

		// add buttons to the frame
		p.add(button1);
		p.add(button2);

		// set the label to its initial value
		l = new JLabel("no file selected");

		// add panel to the frame
		p.add(l);
		f.add(p);

		f.setVisible(true);
	}
	public void actionPerformed(ActionEvent evt)
	{
		// if the user presses the save button show the save dialog
		String com = evt.getActionCommand();

		if (com.equals("Valider")) {
			// create an object of JFileChooser class
			JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

			// restrict the user to select files of all types
			j.setAcceptAllFileFilterUsed(false);

			// set a title for the dialog
			j.setDialogTitle("Select a .txt file");

			// only allow files of .txt extension
			FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
			j.addChoosableFileFilter(restrict);

			// invoke the showsSaveDialog function to show the save dialog
			int r = j.showSaveDialog(null);

			// if the user selects a file
			if (r == JFileChooser.APPROVE_OPTION)

			{
				// set the label to the path of the selected file
				l.setText(j.getSelectedFile().getAbsolutePath());
			}
			// if the user cancelled the operation
			else
				l.setText("Vous avez interrompu la sélection");
		}
		// if the user presses the open dialog show the open dialog

		else {
			// create an object of JFileChooser class
			JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

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
				l.setText(j.getSelectedFile().getAbsolutePath());
			}
			// if the user cancelled the operation
			else
				l.setText("Vous avez interrompu la sélection");
		}
	}
}
