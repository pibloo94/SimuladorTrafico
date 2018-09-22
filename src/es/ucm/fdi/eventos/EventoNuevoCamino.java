package es.ucm.fdi.eventos;

import es.ucm.fdi.element.Camino;
import es.ucm.fdi.element.Carretera;
import es.ucm.fdi.element.CruceGenerico;

public class EventoNuevoCamino extends EventoNuevaCarretera {
	
	public EventoNuevoCamino(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);
	}
	
	protected Carretera creaCarretera(CruceGenerico<?> origen, CruceGenerico<?> destino) {
		return new Camino(super.id, super.longitud, super.velocidadMaxima, origen, destino);
	}
	public String toString() {
		return "Nuevo Camino";
	}
}
