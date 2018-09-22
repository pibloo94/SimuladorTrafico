package es.ucm.fdi.gui.panelesDialogos;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.gui.observador.ObservadorSimuladorTrafico;
import es.ucm.fdi.maps.MapaCarreteras;

public class PanelBarraEstado extends JPanel implements ObservadorSimuladorTrafico{

	private static final long serialVersionUID = 1L;
	
	private JLabel infoEjecucion;
	
	public PanelBarraEstado(String mensaje,Controlador controlador) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.infoEjecucion = new JLabel(mensaje);
		this.add(this.infoEjecucion);
		this.setBorder(BorderFactory.createBevelBorder(1));
		controlador.addObserver(this);
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		this.infoEjecucion.setText("Error en la aplicacion");
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.infoEjecucion.setText("Paso: " + tiempo + " del Simulador");
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.infoEjecucion.setText("Evento/s a�adido al simulador");
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.infoEjecucion.setText("Reinicio");
	}
}
