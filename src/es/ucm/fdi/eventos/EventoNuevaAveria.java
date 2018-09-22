package es.ucm.fdi.eventos;

import es.ucm.fdi.element.Vehiculo;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.maps.MapaCarreteras;

import java.util.List;

public class EventoNuevaAveria extends Evento {
	private int duration;
	private String[] vehicle;
	
	public EventoNuevaAveria(int tiempo, String[] vehicle, int duration) {
		super(tiempo);
		this.vehicle=vehicle;
		this.duration=duration;
	}


	@Override
	public void ejecuta(MapaCarreteras mapa) {
		List<Vehiculo> vehiculos = ParserCarreteras.parseaListaVehiculos(this.vehicle, mapa);

		if (vehiculos.isEmpty()){
			throw  new ErrorDeSimulacion("Error en EventoNuevAveria");
		}

		for (Vehiculo v : vehiculos) {
			v.setTiempoAveria(this.duration);
		}
	}
	public String toString() {
		return "Nueva Averia";
	}
}