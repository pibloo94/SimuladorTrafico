package es.ucm.fdi.constructores;

import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.eventos.EventoNuevoCruceCircular;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCruceCircular extends ConstructorEventos {

	public ConstructorEventoNuevoCruceCircular() {
		this.etiqueta = "new_junction";
		this.claves = new String[] { "time", "id", "type", "max_time_slice", "min_time_slice" };
		this.valoresPorDefecto = new String[] { "", "", "rr", "", "" };
	}
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("rr")) {
			return null;	
		}
		else {
			return new EventoNuevoCruceCircular(
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					ConstructorEventos.identificadorValido(section, "id"),
					ConstructorEventos.parseaInt(section, "max_time_slice"),
					ConstructorEventos.parseaInt(section, "min_time_slice")
					);
		}
	
	}
	
	@Override
	public String toString() {
		return "Nuevo Cruce Circular";
	}

}
