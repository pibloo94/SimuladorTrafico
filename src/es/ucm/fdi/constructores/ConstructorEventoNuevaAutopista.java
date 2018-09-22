package es.ucm.fdi.constructores;

import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.eventos.EventoNuevaAutopista;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaAutopista extends ConstructorEventos {

	public ConstructorEventoNuevaAutopista() {
		this.etiqueta = "new_road";
		this.claves = new String[] { "time", "id", "type", "src", "dest", "max_speed", "length" };
		this.valoresPorDefecto = new String[] { "", "", "lanes", "", "", "", ""};
	}
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("lanes")) {
			return null;			
		}
		else {
			return new EventoNuevaAutopista(
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					ConstructorEventos.identificadorValido(section, "id"), 
					section.getValue("src"),
					section.getValue("dest"),
					ConstructorEventos.parseaInt(section, "max_speed"),
					ConstructorEventos.parseaInt(section, "length"),
					ConstructorEventos.parseaInt(section, "lanes"));
		}
	}
	
	public String toString() {
		return "Nueva Autopista";
	}
}
