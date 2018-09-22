package es.ucm.fdi.constructores;

import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.eventos.EventoNuevaCarretera;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaCarretera extends ConstructorEventos {


	public ConstructorEventoNuevaCarretera() {
		this.etiqueta = "new_road";
		this.claves = new String[] { "time", "id", "src", "dest", "max_speed", "length" };
		this.valoresPorDefecto = new String[] { "", "", "", "", "", "" };
	}

	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || section.getValue("type") != null)
			return null;
		else
			return new EventoNuevaCarretera(
					// extrae el valor del campo time en la seccion
					// 0 es el valor por defecto en caso de no especificar el tiempo
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					// extrae el valor del campo id de la seccion
					//FALTA COMPLETAR CONSTRUCTOR
					ConstructorEventos.identificadorValido(section, "id"), section.getValue("src"), section.getValue("dest"),
					ConstructorEventos.parseaInt(section, "max_speed"),ConstructorEventos.parseaInt(section, "length"));
	}
	public String toString() {
		return "Nueva Carretera";
	}
}
