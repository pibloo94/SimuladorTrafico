package es.ucm.fdi.constructores;

import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.eventos.EventoNuevaCarreteraBaches;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaCarreteraBaches extends ConstructorEventos{
	
	public ConstructorEventoNuevaCarreteraBaches() {
		this.etiqueta = "new_road";
		this.claves = new String[] { "time", "id", "type", "src", "dest", "max_speed", "length", "initRut", "endRut", "penalization" };
		this.valoresPorDefecto = new String[] { "", "", "rut", "", "", "", "", "", "", "" };
	}
	
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("rut")) {
			return null;	
		}
		else {
			return new EventoNuevaCarreteraBaches(
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					ConstructorEventos.identificadorValido(section, "id"), 
					section.getValue("src"), 
					section.getValue("dest"),
					ConstructorEventos.parseaInt(section, "max_speed"),
					ConstructorEventos.parseaInt(section, "length"),
					ConstructorEventos.parseaIntNoNegativo(section, "initRut", 0),
					ConstructorEventos.parseaIntNoNegativo(section, "endRut", 0),
					ConstructorEventos.parseaIntNoNegativo(section, "penalization", 0));
		}
	}
	
	public String toString() {
		return "Nueva CarreteraBaches";
	}
}
