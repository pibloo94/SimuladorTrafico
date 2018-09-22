package es.ucm.fdi.gui.observador;

import java.util.List;

import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.maps.MapaCarreteras;

public interface ObservadorSimuladorTrafico {
	// notifica errores
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e);

	// notifica el avance de los objetos de simulacion
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos);

	// notifica que se ha generado un nuevo evento
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos);
	
	// notifica que la simulacion se ha reiniciado
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos);

}
