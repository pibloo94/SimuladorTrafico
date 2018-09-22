package es.ucm.fdi.gui.panelesDialogos;


import javax.swing.JLabel;
import javax.swing.JPanel;

public class InformationPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private JLabel jLabel;
	private String  textArea = "Select items for which you want to generate reports."
			+ "\nUse 'c' to deselect all."
			+ "\nUse 'Ctrl+A to select all.";
	
	public InformationPanel() {
		jLabel = new JLabel(textArea);
		jLabel.setVisible(true);
		this.add(jLabel);
	}
	
}
