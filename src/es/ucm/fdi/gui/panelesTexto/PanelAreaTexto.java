package es.ucm.fdi.gui.panelesTexto;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public abstract class PanelAreaTexto extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextArea areatexto;
	
	public PanelAreaTexto(String titulo, boolean ediatble) {
		this.setLayout(new GridLayout(1, 1));
		this.areatexto = new JTextArea(48,38);
		this.getAreatexto().setEditable(ediatble);
		this.add(new JScrollPane(getAreatexto()));
		this.setBorde(titulo);
	}

	public void setBorde(String titulo) {
		Border black =BorderFactory.createLineBorder(Color.BLACK,2);
		this.setBorder(BorderFactory.createTitledBorder(black,titulo));
	}
	
	public String getTexto() {
		return this.getAreatexto().getText();
	}
	public void setTexto(String texto) {
		this.getAreatexto().setText(texto);
	}
	public void limpiar() {
		this.getAreatexto().setText("");
	}
	public void inserta (String valor) {
		this.getAreatexto().insert(valor, this.getAreatexto().getCaretPosition());;
	}

	public JTextArea getAreatexto() {
		return areatexto;
	}
}
