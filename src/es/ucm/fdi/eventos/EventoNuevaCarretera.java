package es.ucm.fdi.eventos;

import es.ucm.fdi.element.Carretera;
import es.ucm.fdi.element.CruceGenerico;
import es.ucm.fdi.maps.MapaCarreteras;

public class EventoNuevaCarretera extends Evento {

	protected String id;
	protected Integer velocidadMaxima;
	protected Integer longitud;
	protected String cruceOrigenId;
	protected String cruceDestinoId;
	
	public EventoNuevaCarretera(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud) {
		super(tiempo);
		this.id = id;
		this.velocidadMaxima=velocidadMaxima;
		this.cruceOrigenId=origen;
		this.cruceDestinoId= destino;
		this.longitud=longitud;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) {
		//obten cruce origen y cruce destino utilizando el mapa
		// crea la carretera
		CruceGenerico<?> origen = mapa.getCruce(this.cruceOrigenId);
		CruceGenerico<?> destino = mapa.getCruce(this.cruceDestinoId);
		Carretera c = creaCarretera(origen, destino);
		// anhade al mapa la carretera
		mapa.addCarretera(this.id, origen,c,destino);
	}
	
	protected Carretera creaCarretera(CruceGenerico<?> origen, CruceGenerico<?> destino) {
		return new Carretera(this.id, this.longitud, this.velocidadMaxima, origen, destino);
	}
	
	public String toString() {
		return "Nueva Carretera";
	}
}
