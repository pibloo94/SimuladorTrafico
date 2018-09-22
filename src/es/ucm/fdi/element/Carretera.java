package es.ucm.fdi.element;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.util.SortedArrayList;

import java.util.Comparator;

/**
 * 
 * @author Manuel Monforte Escobar y Pablo Agudo Brun
 *
 */

public class Carretera extends ObjetoSimulacion {
	protected int longitud; // longitud de la carretera
	protected int velocidadMaxima; // velocidad maxima
	protected CruceGenerico<?> cruceOrigen; // cruce del que parte la carretera
	protected CruceGenerico<?> cruceDestino; // cruce al que llega la carretera
	protected SortedArrayList<Vehiculo> vehiculos; // lista ordenada de vehiculos en la carretera (ordenada por localizaciÃ³n)
	protected Comparator<Vehiculo> comparadorVehiculo; // orden entre vehiculos

	public Carretera(String id, int length, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dest) {
		// se inicializan los atributos de acuerdo con los parametros.
		super(id);
		this.longitud = length;
		this.velocidadMaxima=maxSpeed;
		this.cruceOrigen=src;
		this.cruceDestino=dest;
		comparadorVehiculo  = new Comparator<Vehiculo>() {
			
			@Override
			public int compare(Vehiculo arg0, Vehiculo arg1) {
				return  arg1.localizacion- arg0.localizacion;
			}
		};
		this.vehiculos= new SortedArrayList<>(comparadorVehiculo);
		// se fija el orden entre los vehiculos: (inicia comparadorVehiculo)
		// -la localizacion 0 es la menor
	}
	
	public CruceGenerico<?> getCruceOrigen() {
		return cruceOrigen;
	}

	public CruceGenerico<?> getCruceDestino() {
		return cruceDestino;
	}
	
	public SortedArrayList<Vehiculo> getVehiculos() {
		return vehiculos;
	}

	public int getLongitud(){
		return longitud;
	}
	
	public int getVelocidadMaxima() {
		return velocidadMaxima;
	}

	@Override
	public void avanza() {

		// Para cada vehiculo de la lista vehiculos:
		/*
		 * 1. Si el vehiculo esta averiado se incrementa el numero de obstaculos. 2. Se
		 * fija la velocidad actual del vehiculo 3. Se pide al vehiculo que avance.
		 */
		// ordenar la lista de vehiculos
		int obstaculos = 0;
		for (int i = 0; i < vehiculos.size(); i++) {
			if (vehiculos.get(i).tiempoAveria > 0) {
				obstaculos++;
			} else if (!vehiculos.get(i).estaEnCruce) {
				vehiculos.get(i).setVelocidadActual(calculaVelocidadBase() / calculaFactorReduccion(obstaculos));
			}
			vehiculos.get(i).avanza();
		}
		vehiculos.sort(comparadorVehiculo);
	}

	public void entraVehiculo(Vehiculo vehiculo) {
		// Si el vehiculo no existe en la carretera, se anhade a la lista de vehiculos y
		// se ordena la lista.
		// Si existe no se hace nada.
		if(!vehiculos.contains(vehiculo)) {
			vehiculos.add(vehiculo);
			vehiculos.sort(comparadorVehiculo);
		}
	}

	public void saleVehiculo(Vehiculo vehiculo) {
		vehiculos.remove(vehiculo);
	}

	public void entraVehiculoAlCruce(Vehiculo v) {
		// anhade el vehiculo al cruceDestino de la carretera
		//creo que no hace falta recorrer				
		cruceDestino.entraVehiculoAlCruce(this.id,v);
	}

	protected int calculaVelocidadBase() {
		return Math.min(this.velocidadMaxima,(this.velocidadMaxima/(Math.max(vehiculos.size(),1))+1)); //implementar
	}

	protected int calculaFactorReduccion(int obstaculos) {
		if (obstaculos > 0){
			return 2;
		}else {
			return 1;
		}
	}

	@Override
	protected String getNombreSeccion() {
		return "road_report";
	}

	@Override
	protected void completaDetallesSeccion(IniSection is) {
		String listVC = "";
		for(int i = 0; i < vehiculos.size();i++) {
			if(i==0) {
				listVC = "("+vehiculos.get(i).getId()+","+ vehiculos.get(i).getLocalizacion()+")";
			}
			else {
				listVC += ",("+vehiculos.get(i).getId()+","+ vehiculos.get(i).getLocalizacion()+")";
			}
		}
		is.setValue("state",listVC);
	}

	public String mostrarVeCarretera() {
		String mostrarVe = "[";
		for (int i = 0; i < vehiculos.size(); i++) {
			if (i == 0) {
				mostrarVe += vehiculos.get(i).getId(); 
			}else {
				mostrarVe += ", " + vehiculos.get(i).getId();
			}
		}
		return mostrarVe + "]";
	}
	
	public String toString() {
		return this.id;
	}
}
	
