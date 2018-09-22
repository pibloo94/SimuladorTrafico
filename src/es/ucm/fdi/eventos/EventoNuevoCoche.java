package es.ucm.fdi.eventos;

import java.util.List;
import java.util.Random;
import es.ucm.fdi.element.Coche;
import es.ucm.fdi.element.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.exceptions.VehiculoException;
import es.ucm.fdi.maps.MapaCarreteras;

public class EventoNuevoCoche extends EventoNuevoVehiculo {
	protected int kmUltimaAveria;
	protected int resistenciaKm;
	protected int duracionMaximaAveria;
	protected double probabilidadDeAveria;
	protected Random numAleatorio;
	
	public EventoNuevoCoche(int tiempo, String id, int velocidadMaxima, String[] itinerario,int resistance,double probabilidad,long semilla,int duracionMaximaInfraccion) {
		super(tiempo, id, velocidadMaxima, itinerario);
		this.resistenciaKm=resistance;
		this.probabilidadDeAveria=probabilidad;
		this.numAleatorio = new Random(semilla);
		this.duracionMaximaAveria=duracionMaximaInfraccion;
	}
	public void ejecuta(MapaCarreteras mapa) throws VehiculoException {
		List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this.itinerario,mapa);
		//si iti es null o tiene menos de dos cruces lanzar excepcion
		if(iti==null || iti.size()<2) {
			throw new ErrorDeSimulacion("Itinerario con cruces no validos");
		// en otro caso crear el vehiculo y anhadirlo al mapa.
		}else {
			mapa.addVehiculo(this.id, new Coche(this.id, this.velocidadMaxima, this.resistenciaKm, this.probabilidadDeAveria, numAleatorio, duracionMaximaAveria,iti));
		}
	}
	public String toString() {
		return "Nuevo Coche";
	}
}
