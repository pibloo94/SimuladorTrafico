package es.ucm.fdi.constructores;

import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.eventos.EventoNuevoCoche;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCoche extends ConstructorEventoNuevoVehiculo {

	public ConstructorEventoNuevoCoche() {
		this.etiqueta = "new_vehicle";
		this.claves = new String[] { "time", "id","type", "max_speed", "itinerary","resistance", "fault_probability", "seed", "max_fault_duration" };
		this.valoresPorDefecto = new String[] { "", "", "car", "", "", "","","","" };
	}
	
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("car")) {
			return null;	
		}
		else
			return new EventoNuevoCoche(
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					ConstructorEventos.identificadorValido(section, "id"), 
					Integer.parseInt(section.getValue("max_speed")),
					section.getValue("itinerary").split(","),
					ConstructorEventos.parseaInt(section, "resistance"),
					Double.parseDouble(section.getValue("fault_probability")),
					ConstructorEventos.parseaInt(section, "seed"),
					ConstructorEventos.parseaInt(section, "max_fault_duration"));
	}
	
	@Override
	public String toString() {
		return "Nuevo Coche";
	}
}
