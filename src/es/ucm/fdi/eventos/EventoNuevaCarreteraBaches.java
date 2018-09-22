package es.ucm.fdi.eventos;

import es.ucm.fdi.element.Carretera;
import es.ucm.fdi.element.CarreteraBaches;
import es.ucm.fdi.element.CruceGenerico;

public class EventoNuevaCarreteraBaches extends EventoNuevaCarretera{

	protected int inicioBaches;
	protected int finalBaches;
	protected int penalizacion;
	
	public EventoNuevaCarreteraBaches(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud, int inicioBaches, int finalBaches, int penalizacion) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);
		this.inicioBaches = inicioBaches;
		this.finalBaches = finalBaches;
		this.penalizacion = penalizacion;
	}
	
	protected Carretera creaCarretera(CruceGenerico<?> origen, CruceGenerico<?> destino) {
		return new CarreteraBaches(super.id, super.longitud, super.velocidadMaxima, origen, destino, finalBaches, finalBaches, penalizacion);
	}
	
	public String toString() {
		return "Nueva CarreteraBaches";
	}
}
