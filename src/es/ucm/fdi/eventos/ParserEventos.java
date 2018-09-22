package es.ucm.fdi.eventos;

import es.ucm.fdi.constructores.ConstructorEventoNuevaAutopista;
import es.ucm.fdi.constructores.ConstructorEventoNuevaAveria;
import es.ucm.fdi.constructores.ConstructorEventoNuevaBicicleta;
import es.ucm.fdi.constructores.ConstructorEventoNuevaCarretera;
import es.ucm.fdi.constructores.ConstructorEventoNuevaCarreteraBaches;
import es.ucm.fdi.constructores.ConstructorEventoNuevoBus;
import es.ucm.fdi.constructores.ConstructorEventoNuevoCamino;
import es.ucm.fdi.constructores.ConstructorEventoNuevoCoche;
import es.ucm.fdi.constructores.ConstructorEventoNuevoCruce;
import es.ucm.fdi.constructores.ConstructorEventoNuevoCruceCircular;
import es.ucm.fdi.constructores.ConstructorEventoNuevoCruceCongestionado;
import es.ucm.fdi.constructores.ConstructorEventoNuevoVehiculo;
import es.ucm.fdi.constructores.ConstructorEventos;
import es.ucm.fdi.ini.IniSection;

public class ParserEventos{


	private static ConstructorEventos[] eventos = {
			new ConstructorEventoNuevoCruce(),
			new ConstructorEventoNuevoCruceCircular(),
			new ConstructorEventoNuevoCruceCongestionado(),
			new ConstructorEventoNuevaCarretera(),
			new ConstructorEventoNuevoCamino(),
			new ConstructorEventoNuevaAutopista(),
			new ConstructorEventoNuevoVehiculo(),
			new ConstructorEventoNuevaBicicleta(),
			new ConstructorEventoNuevoCoche(),
			new ConstructorEventoNuevaAveria(),
			new ConstructorEventoNuevoBus(),
			new ConstructorEventoNuevaCarreteraBaches()
			};

	public static ConstructorEventos[] getEventos() {
		return eventos;
	}

	// bucle de prueba y error
	public static Evento parseaEvento(IniSection sec) {
		int i = 0;
		boolean seguir = true;
		Evento e = null;

		while (i < ParserEventos.eventos.length && seguir) {
			e = ParserEventos.eventos[i].parser(sec);
			if (e != null) {
				seguir = false;
			} else {
				i++;
			}
		}
		return e;
	}
}
