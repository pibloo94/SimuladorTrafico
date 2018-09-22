package es.ucm.fdi.eventos;


import es.ucm.fdi.element.CruceCongestionado;
import es.ucm.fdi.element.CruceGenerico;
import es.ucm.fdi.maps.MapaCarreteras;

public class EventoNuevoCruceCongestionado  extends EventoNuevoCruce{

    public EventoNuevoCruceCongestionado(int time, String id) {
        super(time, id);
    }

    @Override
    protected CruceGenerico<?> creaCruce(){
        return new CruceCongestionado(this.id);
    }
    
    public void ejecuta(MapaCarreteras mapa) {
		mapa.addCruce(this.id, creaCruce());
	}
    public String toString() {
		return "Nuevo Cruce Congestionado";
	}
}
