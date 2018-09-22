package es.ucm.fdi.eventos;


import es.ucm.fdi.element.CruceCircular;
import es.ucm.fdi.element.CruceGenerico;
import es.ucm.fdi.maps.MapaCarreteras;

public class EventoNuevoCruceCircular extends EventoNuevoCruce{

    protected Integer maxValorIntervalo;
    protected Integer minValorIntervalo;

    public EventoNuevoCruceCircular(int time, String id, int minValorIntervalo, int maxValorIntervalo) {
        super(time, id);
        this.maxValorIntervalo = maxValorIntervalo;
        this.minValorIntervalo = minValorIntervalo;
    }

    @Override
    protected CruceGenerico<?> creaCruce() {
        return new CruceCircular(this.id, this.minValorIntervalo, this.maxValorIntervalo);
    }
    public void ejecuta(MapaCarreteras mapa) {
		mapa.addCruce(this.id, creaCruce());
	}
    public String toString() {
		return "Nuevo Cruce Circular";
	}
}
