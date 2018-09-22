package es.ucm.fdi.constructores;

import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.eventos.EventoNuevoCruce;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCruce extends ConstructorEventos {


	public ConstructorEventoNuevoCruce() {
		this.etiqueta = "new_junction";
		this.claves = new String[] { "time", "id" };
		this.valoresPorDefecto = new String[] { "", "","juction" };
	}

	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || section.getValue("type") != null)
			return null;
		else
			return new EventoNuevoCruce(
					// extrae el valor del campo time en la seccion
					// 0 es el valor por defecto en caso de no especificar el tiempo
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					// extrae el valor del campo id de la seccion
					ConstructorEventos.identificadorValido(section, "id"));
	}

	public String toString() {
		return "Nuevo Cruce";
	}
}
