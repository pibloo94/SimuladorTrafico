package es.ucm.fdi.gui.tablas;

import java.util.List;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.element.Vehiculo;
import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.maps.MapaCarreteras;

public class ModeloTablaVehiculos extends ModeloTabla<Vehiculo> {

	private static final long serialVersionUID = 1L;

	public ModeloTablaVehiculos(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
	}
	
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		
		switch (indiceCol) {
		case 0:
			s = this.lista.get(indiceFil).getId();
			break;
		case 1:
			if (this.lista.get(indiceFil).getCarretera() != null) {
				s = this.lista.get(indiceFil).getCarretera().getId();
			}
			break;
		case 2:
			s = this.lista.get(indiceFil).getLocalizacion();
			break;
		case 3:
			s = this.lista.get(indiceFil).getVelocidadActual();
			break;
		case 4:
			s = this.lista.get(indiceFil).getKilometraje();
			break;
		case 5:
			s = this.lista.get(indiceFil).getTiempoAveria();
			break;
		case 6:
			s = this.lista.get(indiceFil).mostrarItinerario();
			break;
		default:
			assert (false);
		}
		return s;
	}

	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getVehiculos();
		this.fireTableStructureChanged();
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getVehiculos();
		this.fireTableStructureChanged();
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getVehiculos();
		this.fireTableStructureChanged();		
	}

}
