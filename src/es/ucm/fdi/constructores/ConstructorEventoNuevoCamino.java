package es.ucm.fdi.constructores;

import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.eventos.EventoNuevoCamino;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCamino extends ConstructorEventoNuevaCarretera {

	public ConstructorEventoNuevoCamino() {
		this.etiqueta = "new_road";
		this.claves = new String[] { "time", "id", "type", "src", "dest", "max_speed", "legnth" };
		this.valoresPorDefecto = new String[] { "", "", "dirt", "", "", "", "" };
	}
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("dirt")) {
			return null;	
		}
		else {
			return new EventoNuevoCamino(
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					ConstructorEventos.identificadorValido(section, "id"), 
					section.getValue("src"), section.getValue("dest"),
					ConstructorEventos.parseaInt(section, "max_speed"),
					ConstructorEventos.parseaInt(section, "length"));
		}
	}
	
	public String toString() {
		return "Nuevo Camino";
	}
}
