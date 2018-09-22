package es.ucm.fdi.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.ini.IniSection;

abstract public class CruceGenerico<T extends CarreteraEntrante> extends ObjetoSimulacion {

	protected int indiceSemaforoVerde;
	protected List<T> carreterasEntrantes;
	protected Map<String, T> mapaCarreterasEntrantes;
	protected Map<CruceGenerico<?>, Carretera> carreterasSalientes;

	public CruceGenerico(String id) {
		super(id);
		indiceSemaforoVerde = -1;
		carreterasEntrantes = new ArrayList<>();
		mapaCarreterasEntrantes = new HashMap<>();
		carreterasSalientes = new HashMap<>();
	}

	public List<T> getCarreteras() {
		return carreterasEntrantes;
	}

	public Carretera carreteraHaciaCruce(CruceGenerico<?> cruce) {
		// devuelve la carretera que llega a ese cruce desde this
		return carreterasSalientes.get(cruce);
	}

	public void addCarreteraEntranteAlCruce(String idCarretera, Carretera carretera) {
		// anhade una carretera entrante al mapaCarreterasEntrantes y a las
		// carreterasEntrantes
		mapaCarreterasEntrantes.put(idCarretera, creaCarreteraEntrante(carretera));
		carreterasEntrantes.add(creaCarreteraEntrante(carretera));
	}

	abstract protected T creaCarreteraEntrante(Carretera carretera);

	public void addCarreteraSalienteAlCruce(CruceGenerico<?> destino, Carretera road) {
		// anhade una carretera saliente
		carreterasSalientes.put(destino, road);
	}

	public void entraVehiculoAlCruce(String idCarretera, Vehiculo v) {
		for (int i = 0; i < carreterasEntrantes.size(); i++) {
			if (carreterasEntrantes.get(i).carretera.id.equals(idCarretera)) {
				carreterasEntrantes.get(i).colaVehiculos.add(v);
			}
		}
	}

	@Override
	public void avanza() {
		if (!carreterasEntrantes.isEmpty()) {
			if (indiceSemaforoVerde != -1) {
				carreterasEntrantes.get(indiceSemaforoVerde).avanzaPrimerVehiculo();
			}
			actualizaSemaforos();
		}
	}

	abstract protected void actualizaSemaforos();

	abstract protected void completaDetallesSeccion(IniSection is);

	public String pintarCola(CarreteraEntrante ce) {
		String result = "";
		for (int i = 0; i < ce.colaVehiculos.size(); i++) {
			if (i == 0) {
				result = ce.colaVehiculos.get(i).getId();
			} else {
				result += "," + ce.colaVehiculos.get(i).getId();
			}
		}
		return result;
	}

	protected String getNombreSeccion() {
		return "junction_report";
	}

	public String mostrarSemV() {
		String semV = "[";
		for (int i = 0; i < carreterasEntrantes.size(); i++) {
			if (i == 0) {
				if (carreterasEntrantes.get(i).semaforo) {
					semV += "(" + carreterasEntrantes.get(i).carretera.getId() + ", verde" + "["+pintarCola(carreterasEntrantes.get(i))+"])";
				}
			} else {
				if (carreterasEntrantes.get(i).semaforo) {
					semV += "(" + carreterasEntrantes.get(i).carretera.getId() + ", verde" + "["+pintarCola(carreterasEntrantes.get(i))+"])";
				}
			}
		}
		return semV + "]";
	}

	public String mostrarSemR() {
		String semR = "[";
		for (int i = 0; i < carreterasEntrantes.size(); i++) {
			if (i == 0) {
				if (!carreterasEntrantes.get(i).semaforo) {
					semR += "(" + carreterasEntrantes.get(i).carretera.getId() + ", rojo" + "["+pintarCola(carreterasEntrantes.get(i))+"])";
				}
			} else {
				if (!carreterasEntrantes.get(i).semaforo) {
					semR += "(" + carreterasEntrantes.get(i).carretera.getId() + ", rojo" + "["+pintarCola(carreterasEntrantes.get(i))+"])";
				}
			}
		}
		return semR + "]";
	}
	
	public String toString() {
		return this.id;
	}
}
