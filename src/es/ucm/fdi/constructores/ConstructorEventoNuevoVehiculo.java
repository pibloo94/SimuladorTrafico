package es.ucm.fdi.constructores;

import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.eventos.EventoNuevoVehiculo;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoVehiculo extends ConstructorEventos {

	public ConstructorEventoNuevoVehiculo() {
		this.etiqueta = "new_vehicle";
		this.claves = new String[] { "time", "id", "max_speed", "itinerary" };
		this.valoresPorDefecto = new String[] { "", "","vehicle","","" };
	}
	
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || section.getValue("type")!=null)
			return null;
		else
			return new EventoNuevoVehiculo(
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					ConstructorEventos.identificadorValido(section, "id"), ConstructorEventos.parseaInt(section, "max_speed"),section.getValue("itinerary").split(","));
	}
	
	public String toString() {
		return "Nuevo Vehiculo";
	}
}
