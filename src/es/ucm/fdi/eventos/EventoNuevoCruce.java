package es.ucm.fdi.eventos;


import es.ucm.fdi.element.Cruce;
import es.ucm.fdi.element.CruceGenerico;
import es.ucm.fdi.maps.MapaCarreteras;

public class EventoNuevoCruce extends Evento {

		protected String id;

		public EventoNuevoCruce(int time, String id) {
			super(time);
			this.id = id;
		}

		@Override
		public void ejecuta(MapaCarreteras mapa) {
			mapa.addCruce(this.id, creaCruce());
		}
		
		protected CruceGenerico<?> creaCruce(){
			return new Cruce(this.id);
		}
		
		@Override
		public String toString() {
			return "Nuevo Cruce";
		}
}