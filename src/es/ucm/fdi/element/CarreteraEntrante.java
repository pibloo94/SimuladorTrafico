package es.ucm.fdi.element;


import es.ucm.fdi.util.SortedArrayList;

import java.util.Comparator;
import java.util.List;

public class CarreteraEntrante {

    protected Carretera carretera;
    protected List<Vehiculo> colaVehiculos;
    protected boolean semaforo; // true=verde, false=rojo

    public CarreteraEntrante(Carretera carretera){
        // inicia los atributos.
        // el semáforo a rojo
        this.semaforo = false;
        this.carretera = carretera;

        Comparator<Vehiculo> cmpV = new Comparator<Vehiculo>() {

            @Override
            public int compare(Vehiculo v1, Vehiculo v2) {
                if(v1.getLocalizacion()<v2.getLocalizacion()) return -1;
                else if(v1.getLocalizacion()>v2.getLocalizacion())return 1;
                else return 0;
            }
        };
        this.colaVehiculos = new SortedArrayList<>(cmpV);
    }

	public Carretera getCarretera() {
		return carretera;
	}
	
    void ponSemaforo(boolean color) {
        this.semaforo = color;
    }

    public boolean isSemaforo() {
		return semaforo;
	}

	protected void avanzaPrimerVehiculo(){
    	if(!colaVehiculos.isEmpty()) {
    		Vehiculo auxVehiculo = colaVehiculos.remove(0);
        // coge el primer vehiculo de la cola, lo elimina,
        // y le manda que se mueva a su siguiente carretera.
    		auxVehiculo.moverASiguienteCarretera();
    	}
    }
}
