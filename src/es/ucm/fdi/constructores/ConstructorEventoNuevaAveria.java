package es.ucm.fdi.constructores;

import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.eventos.EventoNuevaAveria;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaAveria extends ConstructorEventos {

	
	public ConstructorEventoNuevaAveria() {
		this.etiqueta = "make_vehicle_faulty";
		this.claves = new String[] { "time", "vehicles","duration" };
		this.valoresPorDefecto = new String[] { "", "", ""};
	}
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || section.getValue("type") != null)
			return null;
		else
			return new EventoNuevaAveria(
					// extrae el valor del campo time en la seccion
					// 0 es el valor por defecto en caso de no especificar el tiempo
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					section.getValue("vehicles").split(","),
					// extrae el valor del campo id de la seccion
					Integer.parseInt(section.getValue("duration")));
	}
	
	@Override
	public String toString() {
		return "Nueva Averia";
	}

}
