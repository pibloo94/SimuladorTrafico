package es.ucm.fdi.eventos;

import es.ucm.fdi.element.CruceGenerico;
import es.ucm.fdi.element.Vehiculo;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.exceptions.VehiculoException;
import es.ucm.fdi.maps.MapaCarreteras;

import java.util.List;

public class EventoNuevoVehiculo extends Evento {

	protected String id;
	protected Integer velocidadMaxima;
	protected String[] itinerario;

	public EventoNuevoVehiculo(int tiempo, String id, int velocidadMaxima, String[] itinerario) {
		super(tiempo);
		this.id = id;
		this.velocidadMaxima=velocidadMaxima;
		this.itinerario = itinerario;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this.itinerario,mapa);
		//si iti es null o tiene menos de dos cruces lanzar excepcion
		if(iti==null || iti.size()<2) {
			throw new ErrorDeSimulacion("Itinerario con cruces no validos");
		// en otro caso crear el vehiculo y anhadirlo al mapa.
		}else {
			try {
				mapa.addVehiculo(this.id, new Vehiculo(id, velocidadMaxima, iti));
			} catch (VehiculoException e) {
				throw new ErrorDeSimulacion("Error atributos de vehiculos");
			}
		}
	}
	public String toString() {
		return "Nuevo Vehiculo";
	}
}
