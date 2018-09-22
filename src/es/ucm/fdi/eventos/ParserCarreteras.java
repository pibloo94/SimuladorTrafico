package es.ucm.fdi.eventos;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.element.CruceGenerico;
import es.ucm.fdi.element.Vehiculo;
import es.ucm.fdi.maps.MapaCarreteras;

public class ParserCarreteras {
	
	public static List<CruceGenerico<?>> parseaListaCruces(String [] itinerario, MapaCarreteras mapa) {
		List<CruceGenerico<?>> iti = new ArrayList<>();

		for (String j : itinerario) {
			iti.add(mapa.getCruce(j));
		}

		return iti;
	}

	public static List<Vehiculo> parseaListaVehiculos(String vehicle[], MapaCarreteras mapa) {
		String[] listaAveriados = vehicle;
		ArrayList<Vehiculo> vehiculos = new ArrayList<>(); // lista ordenada de vehiculos en la carretera (ordenada por localizacion)

		for (String v : listaAveriados) {
			vehiculos.add(mapa.getVehiculo(v));
		}

		return vehiculos;
	}
}
