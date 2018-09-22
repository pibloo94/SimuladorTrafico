package es.ucm.fdi.element;

import java.util.List;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.exceptions.VehiculoException;
import es.ucm.fdi.ini.IniSection;

/**
 * 
 * @author Manuel Monforte Escobar y Pablo Agudo Brun
 *
 */

public class Vehiculo extends ObjetoSimulacion{

	protected Carretera carretera; // carretera en la que esta el vehiculo
	protected int velocidadMaxima; // velocidad maxima
	protected int velocidadActual; // velocidad actual
	protected int kilometraje; // distancia recorrida
	protected int localizacion; // localizacion en la carretera
	protected int tiempoAveria; // tiempo que estara averiado
	protected int cruceActual;

	protected List<CruceGenerico<?>> itinerario; // itinerario a recorrer (minimo 2)

	private boolean haLlegado;
	protected boolean estaEnCruce;
	private static final String dataError = "Datos incorrectos";

	public Vehiculo(String id, int velocidadMaxima, List<CruceGenerico<?>> iti) throws VehiculoException {
		// comprobar que la velocidadMaxima es mayor o igual que 0, y
		// que el itinerario tiene al menos dos cruces.
		// En caso de no cumplirse lo anterior, lanzar una excepcion.
		// inicializar los atributos teniendo en cuenta los parametros.
		// al crear un vehiculo su carretera sera inicalmene null.
		super(id);
		if (velocidadMaxima < 0 && itinerario.size() < 2){
			throw new VehiculoException(dataError);
		}
		this.velocidadActual = 0;
		this.cruceActual=0;
		this.velocidadMaxima = velocidadMaxima;
		this.carretera =null;
		this.localizacion = 0;
		this.kilometraje = 0;
		this.tiempoAveria = 0;
		this.estaEnCruce = false;
		this.haLlegado = false;
		this.itinerario = iti;
	}

	public Carretera getCarretera() {
		return carretera;
	}

	public int getVelocidadActual() {
		return velocidadActual;
	}

	public int getKilometraje() {
		return kilometraje;
	}
	
	public int getLocalizacion() {
		return  localizacion; 
	}

	public int getTiempoAveria() {
		return  tiempoAveria;
	}
	public boolean isHaLlegado() {
		return haLlegado;
	}

	public void setVelocidadActual(int velocidad) {
		// Si velocidad es negativa, entonces la velocidadActual es 0.
		// Si velocidad excede a velocidadMaxima, entonces la velocidadActual es velocidadMaxima
		// En otro caso, velocidadActual es velocidad
		if (velocidad < 0){
			velocidadActual = 0;
		}else if (velocidad > velocidadMaxima){
			velocidadActual = velocidadMaxima;
		}else{
			velocidadActual = velocidad;
		}
	}

	public void setTiempoAveria(Integer duracionAveria) {
		// Comprobar que carretera no es null.
		// Se fija el tiempo de averia de acuerdo con el enunciado.
		// Si el tiempo de averia es finalmente positivo, entonces
		// la velocidadActual se pone a 0
		if (carretera != null){
			tiempoAveria += duracionAveria;
			if (tiempoAveria > 0){
				velocidadActual = 0;
			}
		}
	}

	@Override
	protected void completaDetallesSeccion(IniSection is) {
		is.setValue("speed", velocidadActual);
		is.setValue("kilometrage", kilometraje);
		is.setValue("faulty", tiempoAveria);
		is.setValue("location", this.haLlegado ? "arrived" : "("+carretera.getId() + "," + localizacion+")");
	}

	@Override
	public void avanza() {
		// si el coche esta averiado, decrementar tiempoAveria
		// si el coche esta esperando en un cruce, no se hace nada.
		// en otro caso:
		/*
		1. Actualizar su localizacion
		2. Actualizar su kilometraje
		3. Si el coche ha llegado a un cruce (localizacion >= carretera.getLength())
		3.1. Poner la localizacion igual a la longitud de la carretera.
		3.2. Corregir el kilometraje.
		3.3. Indicar a la carretera que el vehiculo entra al cruce.
		3.4. Marcar que este vehiculo esta en un cruce (this.estEnCruce = true)
		 */

		if (tiempoAveria > 0) {
			tiempoAveria--;
		} else if (estaEnCruce) {
			this.velocidadActual = 0;
		} else {
			localizacion += velocidadActual;
			kilometraje += velocidadActual;
			if (localizacion >= carretera.getLongitud()) {
				kilometraje = kilometraje - localizacion + carretera.getLongitud();
				localizacion = carretera.getLongitud();
				carretera.entraVehiculoAlCruce(this);
				estaEnCruce = true;
				this.velocidadActual = 0;
			}
		}
	}

	public void moverASiguienteCarretera() {
		// Si la carretera no es null, sacar el vehiculo de la carretera.
		// Si hemos llegado al ultimo cruce del itinerario, entonces:
		/*
		 * 1. Se marca que el vehiculo ha llegado (this.haLlegado = true). 2. Se pone su
		 * carretera a null. 3. Se pone su velocidadActual y localizacion a 0.
		 */
		// y se marca que el vehiculo esta en un cruce (this.estaEnCruce = true).
		// En otro caso:
		/*
		 * 1. Se calcula la siguiente carretera a la que tiene que ir. -->
		 * indiceDelCruceActual = this.itinerario.indexOf(this.carreteraActual.destino)
		 * --> this.carreteraActual.destino.carreteraAlCruce(this.itinerario.get(
		 * indiceDelCruceActual +1 ) 2. Si dicha carretera no existe, se lanza
		 * excepcion. 3. En otro caso, se introduce el vehiculo en la carretera. 4. Se
		 * inicializa su localizacion.
		 */

		if (carretera != null) {
			carretera.saleVehiculo(this);
		}
		if (cruceActual +1 == itinerario.size()) {
			haLlegado = true;
			carretera = null;
			localizacion = 0;
			velocidadActual = 0;
		} else {
			CruceGenerico<?> actual = this.itinerario.get(this.cruceActual);
			CruceGenerico<?> sig = this.itinerario.get(cruceActual + 1);
			Carretera auxCarretera = actual.carreteraHaciaCruce(sig);
			
			if (auxCarretera != null) {
				cruceActual++;
				carretera = auxCarretera;
				localizacion = 0;
				carretera.entraVehiculo(this);
				estaEnCruce = false;
				
			} else {
				throw new ErrorDeSimulacion("Carretera Inexistente");
			}
		}
	}
	@Override
	protected String getNombreSeccion() {
		return "vehicle_report";
	}
	public void moverAprimeraCarretera() {
		CruceGenerico<?> actual = this.itinerario.get(this.cruceActual);
		CruceGenerico<?> sig = this.itinerario.get(cruceActual + 1);
		Carretera auxCarretera = actual.carreteraHaciaCruce(sig);
		cruceActual++;
		if (auxCarretera != null) {
			carretera = auxCarretera;
			carretera.entraVehiculo(this);
			localizacion = 0;
		} else {
			throw new ErrorDeSimulacion("Carretera Inexistente");
		}
	}
	
	public String mostrarItinerario() {
		String mostrarIt = "[";
		for (int i = 0; i < itinerario.size(); i++) {
			if (i == 0) {
				mostrarIt += itinerario.get(i).getId(); 
			}else {
				mostrarIt += ", " + itinerario.get(i).getId();
			}
		}
		return mostrarIt + "]";
	}
	
	public String toString() {
		return this.id;
	}
}
