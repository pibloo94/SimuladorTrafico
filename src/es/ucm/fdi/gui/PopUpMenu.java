package es.ucm.fdi.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import es.ucm.fdi.constructores.ConstructorEventos;
import es.ucm.fdi.eventos.ParserEventos;
import es.ucm.fdi.gui.ventana.VentanaPrincipal;

public class PopUpMenu  extends JPopupMenu{

	private static final long serialVersionUID = 1L;

	public PopUpMenu(VentanaPrincipal mainWindow) {
		
		JMenu plantillas = new JMenu("Nueva plantilla");
		this.add(plantillas);
		// anadir las opciones con sus listeners
		for (ConstructorEventos ce : ParserEventos.getEventos()) {
			JMenuItem mi = new JMenuItem(ce.toString());
			mi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mainWindow.inserta(ce.template() + System.lineSeparator());
				}
			});
			plantillas.add(mi);
		}
		
		// String template() es un m�todo p�blico que debe definirse en la
		// clase ConstructorEventos, y que debe generar la plantilla
		// correspondiente en funci�n de los campos, y teniendo en cuenta
		// los posibles valores por defecto.
		
		JMenuItem cargar = new JMenuItem("Carga eventos");
		cargar.setMnemonic(KeyEvent.VK_L);
		cargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		cargar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.cargaFichero();
			}
		});
		
		JMenuItem salvar = new JMenuItem("Guardar Eventos");
		salvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK));
		salvar.setMnemonic(KeyEvent.VK_S);
		salvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.guardaEventos();
			}
		});
		
		this.add(cargar);
		this.add(salvar);
	}
}
