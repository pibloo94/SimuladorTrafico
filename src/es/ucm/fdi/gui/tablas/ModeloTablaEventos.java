package es.ucm.fdi.gui.tablas;

import java.util.List;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.maps.MapaCarreteras;

public class ModeloTablaEventos extends ModeloTabla<Evento>{

	private static final long serialVersionUID = 1L;

	public ModeloTablaEventos(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
	}

	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		
		switch (indiceCol) {
		case 0:
			s = indiceFil;
			break;
		case 1:
			s = this.lista.get(indiceFil).getTiempo();
			break;
		case 2:
			s = this.lista.get(indiceFil).toString();
			break;
		default:
			assert (false);
		}
		return s;
	}

	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = eventos;
		this.fireTableStructureChanged();
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		lista = eventos;
		this.fireTableStructureChanged();
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		lista = eventos;
		this.fireTableStructureChanged();
	}

	
}
