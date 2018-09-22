package es.ucm.fdi.gui.tablas;

import java.util.List;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.element.Carretera;
import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.maps.MapaCarreteras;

public class ModeloTablaCarreteras extends ModeloTabla<Carretera> {
	
	private static final long serialVersionUID = 1L;

	public ModeloTablaCarreteras(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
	}
	
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		
		switch (indiceCol) {
		case 0:
			s = this.lista.get(indiceFil).getId();
			break;
		case 1:
			s = this.lista.get(indiceFil).getCruceOrigen().getId();
			break;
		case 2:
			s = this.lista.get(indiceFil).getCruceDestino().getId();
			break;
		case 3:
			s = this.lista.get(indiceFil).getLongitud();
			break;
		case 4:
			s = this.lista.get(indiceFil).getVelocidadMaxima();
			break;
		case 5:
			s = this.lista.get(indiceFil).mostrarVeCarretera();
			break;
		default:
			assert (false);
		}
		return s;
	}

	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getCarreteras();
		this.fireTableStructureChanged();
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getCarreteras();
		this.fireTableStructureChanged();
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getCarreteras();
		this.fireTableStructureChanged();		
	}

	
}
