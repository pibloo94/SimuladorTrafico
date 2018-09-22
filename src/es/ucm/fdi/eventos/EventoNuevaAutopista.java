package es.ucm.fdi.eventos;

import es.ucm.fdi.element.Autopista;
import es.ucm.fdi.element.Carretera;
import es.ucm.fdi.element.CruceGenerico;

public class EventoNuevaAutopista extends EventoNuevaCarretera {

	protected Integer numCarriles;
	
	public EventoNuevaAutopista(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud, int numCarriles) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);
		this.numCarriles= numCarriles;
	}
	
	protected Carretera creaCarretera(CruceGenerico<?> origen, CruceGenerico<?> destino) {
		return new Autopista(super.id, super.longitud, super.velocidadMaxima, origen, destino,this.numCarriles);
	}
	
	public String toString() {
		return "Nueva Autopista";
	}
}
