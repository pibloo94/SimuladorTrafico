package es.ucm.fdi.gui.panelesTexto;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import es.ucm.fdi.gui.PopUpMenu;
import es.ucm.fdi.gui.ventana.VentanaPrincipal;

public class PanelEditorEventos extends PanelAreaTexto {

	private static final long serialVersionUID = 1L;

	public PanelEditorEventos(String titulo,String texto, boolean ediatble, VentanaPrincipal mainWindow) {
		super(titulo, ediatble);
		this.setTexto(texto);
		
		//OPCIONAL
		PopUpMenu popUp = new PopUpMenu(mainWindow);
		this.getAreatexto().add(popUp);
		this.getAreatexto().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger() && getAreatexto().isEnabled()) {
					popUp.show(e.getComponent(), e.getX(), e.getY());
					}				
				}
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.isPopupTrigger() && getAreatexto().isEnabled()) {
					popUp.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.isPopupTrigger() && getAreatexto().isEnabled()) {
					popUp.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		
	}
	public void setText(String texto) {
		this.setTexto(texto);
	}
	
	public void setBorde(String nombreFichero) {
		Border black =BorderFactory.createLineBorder(Color.BLACK,2);
		this.setBorder(new TitledBorder(black,nombreFichero));
	}
}
