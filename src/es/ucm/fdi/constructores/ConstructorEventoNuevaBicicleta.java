package es.ucm.fdi.constructores;

import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.eventos.EventoNuevaBicicleta;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaBicicleta extends ConstructorEventoNuevoVehiculo{

	public ConstructorEventoNuevaBicicleta() {
		this.etiqueta = "new_vehicle";
		this.claves = new String[] { "time", "id", "type", "max_speed", "itinerary"  };
		this.valoresPorDefecto = new String[] { "", "", "bike", "", "" };
	}
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("bike")) {
			return null;			
		}
		else 
			return new EventoNuevaBicicleta(
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					ConstructorEventos.identificadorValido(section, "id"), Integer.parseInt(section.getValue("max_speed")),section.getValue("itinerary").split(","));
	}
	
	@Override
	public String toString() {
		return "Nuevo Bicicleta";
	}
}
