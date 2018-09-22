package es.ucm.fdi.gui.tablas;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class PanelTabla<T> extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ModeloTabla<T> modelo;

	public PanelTabla(String bordeId, ModeloTabla<T> modelo) {
		this.setLayout(new GridLayout(1, 1));
		this.setBorder(bordeId);
		this.modelo = modelo;
		JTable tabla = new JTable(this.modelo);
		tabla.setEnabled(false);
		this.add(new JScrollPane(tabla));
	}
	
	public void setBorder(String titulo) {
		Border black =BorderFactory.createLineBorder(Color.BLACK,2);
		this.setBorder(new TitledBorder(black,titulo));
	}

}