package es.ucm.fdi.constructores;

import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.eventos.EventoNuevoCruceCongestionado;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCruceCongestionado extends ConstructorEventos {
	
	public ConstructorEventoNuevoCruceCongestionado() {
		this.etiqueta = "new_junction";
		this.claves = new String[] { "time", "id" };
		this.valoresPorDefecto = new String[] { "", "", "mc" };
	}
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("mc")) {
			return null;	
		}
		else {
			return new EventoNuevoCruceCongestionado(
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					ConstructorEventos.identificadorValido(section, "id")
			);
		}
	}
	
	@Override
	public String toString() {
		return "Nuevo Cruce Congestionado";
	}

}
