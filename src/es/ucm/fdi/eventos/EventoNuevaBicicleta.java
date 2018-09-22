package es.ucm.fdi.eventos;

import java.util.List;
import es.ucm.fdi.element.Bicicleta;
import es.ucm.fdi.element.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.exceptions.VehiculoException;
import es.ucm.fdi.maps.MapaCarreteras;

public class EventoNuevaBicicleta extends EventoNuevoVehiculo{


	public EventoNuevaBicicleta(int tiempo, String id, int velocidadMaxima, String[] itinerario) {
		super(tiempo, id, velocidadMaxima, itinerario);
	}
	
	public void ejecuta(MapaCarreteras mapa) throws VehiculoException {
		List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this.itinerario,mapa);
		//si iti es null o tiene menos de dos cruces lanzar excepcion
		if(iti==null || iti.size()<2) {
			throw new ErrorDeSimulacion("Itinerario con cruces no validos");
		// en otro caso crear el vehiculo y anhadirlo al mapa.
		}else {
			mapa.addVehiculo(this.id, new Bicicleta(id, velocidadMaxima, iti));
		}
	}
	public String toString() {
		return "Nueva Bicicleta";
	}
}
