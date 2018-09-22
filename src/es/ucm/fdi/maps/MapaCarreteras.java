package es.ucm.fdi.maps;

import es.ucm.fdi.element.Carretera;
import es.ucm.fdi.element.CruceGenerico;
import es.ucm.fdi.element.Vehiculo;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MapaCarreteras {
    private List<Carretera> carreteras;
	private List<CruceGenerico<?>> cruces;
	private List<Vehiculo> vehiculos;

	private static String lineSeparator = System.getProperty("line.separator");

    // estructuras para agilizar la busqueda (id,valor)
    private Map<String, Carretera> mapaDeCarreteras;
    private Map<String, CruceGenerico<?>> mapaDeCruces;
    private Map<String, Vehiculo> mapaDeVehiculos;

    public MapaCarreteras() {
        // inicializa los atributos a sus constructoras por defecto.

        // Para carreteras, cruces y vehiculos puede usarse ArrayList.
    	this.carreteras = new ArrayList<>();
    	this.cruces = new ArrayList<>();
    	this.vehiculos = new ArrayList<>();

        // Para los mapas puede usarse HashMap
    	mapaDeCarreteras = new HashMap<String, Carretera>();
    	mapaDeCruces = new HashMap<String, CruceGenerico<?>>();
    	mapaDeVehiculos = new HashMap<String, Vehiculo>();

    }
    
    public List<CruceGenerico<?>> getCruces() {
		return cruces;
	}
    
    public List<Carretera> getCarreteras() {
		return carreteras;
	}
    
    public List<Vehiculo> getVehiculos() {
		return vehiculos;
	}

    public void addCruce(String idCruce, CruceGenerico<?> cruce) throws ErrorDeSimulacion {
        // comprueba que idCruce no existe en el mapa.
        // Si no existe, lo anade a cruces y a mapaDeCruces.
    	if(!mapaDeCruces.containsKey(idCruce)) {
            cruces.add(cruce);
    		mapaDeCruces.put(idCruce, cruce);
    	}else {// Si existe lanza una excepcion.
    		throw new ErrorDeSimulacion("Elemento repetido");
    	}
    }

    public void addVehiculo(String idVehiculo,Vehiculo vehiculo) throws ErrorDeSimulacion{
        // comprueba que idVehiculo no existe en el mapa.
        // Si no existe, lo anhade a vehiculos y a mapaDeVehiculos,
        // y posteriormente solicita al vehiculo que se mueva a la siguiente
        // carretera de su itinerario (moverASiguienteCarretera).
        // Si existe lanza una excepcion.
        if(!mapaDeVehiculos.containsKey(idVehiculo)) {
            vehiculos.add(vehiculo);
            mapaDeVehiculos.put(idVehiculo, vehiculo);
            vehiculo.moverAprimeraCarretera(); //primera vez que entra vehiculo en carretera
        }else {// Si existe lanza una excepcion.
            throw new ErrorDeSimulacion("Elemento repetido");
        }
    }

    public void addCarretera(String idCarretera, CruceGenerico<?> origen, Carretera carretera, CruceGenerico<?> destino) throws ErrorDeSimulacion{
        // comprueba que idCarretera no existe en el mapa.
        // Si no existe, lo anhade a carreteras y a mapaDeCarreteras,
        // y posteriormente actualiza los cruces origen y destino como sigue:
        // - anhade al cruce origen la carretera, como carretera saliente
        // - anhade al crude destino la carretera, como carretera entrante
        // Si existe lanza una excepcion.
        if (!mapaDeCarreteras.containsKey(idCarretera)){
            carreteras.add(carretera);
            mapaDeCarreteras.put(idCarretera,carretera);
            origen.addCarreteraSalienteAlCruce(destino, carretera);
            destino.addCarreteraEntranteAlCruce(carretera.getId(), carretera);
        }else {
            throw new ErrorDeSimulacion("Elemento repetido");
        }
    }

    public String generateReport(int time) {
        String report = "";
        // genera informe para cruces
        for(int i = 0; i < cruces.size();i++) {
        	report+=cruces.get(i).generaInforme(time);
        	report+=lineSeparator;
        }
        // genera informe para carreteras
        for(int i = 0; i < carreteras.size();i++) {
        	report+=carreteras.get(i).generaInforme(time);
        	report+=lineSeparator;

        }
        // genera informe para vehiculos
        for(int i = 0; i < vehiculos.size();i++) {
        	report+=vehiculos.get(i).generaInforme(time);
        	report+=lineSeparator;

        if(i!=vehiculos.size()-1)report+=lineSeparator;
        }
        return "******STEP " + time + "******\n"+ report;
    }

    public void actualizar() throws ErrorDeSimulacion {
    	 // llama al metodo avanza de cada carretera
        for (int i = 0; i < carreteras.size(); i++) {
        	carreteras.get(i).avanza();
        }
    	// llama al metodo avanza de cada cruce
    	 for(int i = 0; i < cruces.size();i++) {
         	cruces.get(i).avanza();
         }
       
    }

    public CruceGenerico<?> getCruce(String id) throws ErrorDeSimulacion {
        // devuelve el cruce con ese id utilizando el mapaDeCruces.
        // sino existe el cruce lanza excepcion.
        int i = 0;
        while (!cruces.get(i).getId().equals(id) && i < cruces.size()){
            i++;
        }
        if (i == cruces.size()){
            throw  new ErrorDeSimulacion("Cruce no encontrado");
        }
        return  cruces.get(i); 
    }

    public Vehiculo getVehiculo(String id) throws ErrorDeSimulacion{
        // devuelve el vehiculo con ese id utilizando el mapaDeVehiculos.
        // sino existe el vehiculo lanza excepcion.

        int i = 0;
        while (!vehiculos.get(i).getId().equals(id) && i < vehiculos.size()){
            i++;
        }
        if (i == vehiculos.size()){
            throw  new ErrorDeSimulacion("Vehiculo no encontrado");
        }
        return  vehiculos.get(i); //implementar
    }

    public Carretera getCarretera(String id) throws ErrorDeSimulacion{
        // devuelve la carretera con ese id utilizando el mapaDeCarreteras.
        // sino existe la carretra lanza excepcion.
        int i = 0;
        while (!carreteras.get(i).getId().equals(id) && i < carreteras.size()){
            i++;
        }
        if (i == carreteras.size()){
            throw  new ErrorDeSimulacion("Carretera no encontrado");
        }
        return  carreteras.get(i);
    }
    
	public void eliminarVehiculo(String id) {
		Vehiculo auxVehiculo = null;
		Carretera carretera = null;
		
		for (Vehiculo v : vehiculos) {
			if (v.getId().equals(id)) {
				auxVehiculo = v;
				vehiculos.remove(v);
				mapaDeVehiculos.remove(id);
			}
		}
		carretera = auxVehiculo.getCarretera();
		carretera.getVehiculos().remove(auxVehiculo);
	}
}
