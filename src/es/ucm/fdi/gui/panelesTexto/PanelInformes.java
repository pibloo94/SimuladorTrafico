package es.ucm.fdi.gui.panelesTexto;

import java.util.List;

import javax.swing.SwingUtilities;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.gui.observador.ObservadorSimuladorTrafico;
import es.ucm.fdi.maps.MapaCarreteras;

public class PanelInformes extends PanelAreaTexto implements ObservadorSimuladorTrafico {

	private static final long serialVersionUID = 1L;

	public PanelInformes(String titulo, boolean editable, Controlador ctrl) {
	   super(titulo, editable);
	   ctrl.addObserver(this); // se anade como observador
	 }

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				getAreatexto().setText("");
			}
		});	
	}
}
