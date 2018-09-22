package es.ucm.fdi.element;

import es.ucm.fdi.ini.IniSection;


/**
 * 
 * @author Manuel Monforte Escobar y Pablo Agudo Brun
 *
 */

//abstract es para la segunta parte
public class Cruce extends CruceGenerico<CarreteraEntrante> {

	public Cruce(String id) {
		super(id);
	}
	
	protected void actualizaSemaforos(){
		// pone el semaforo de la carretera actual a rojo, y busca la siguiente
		// carretera entrante para ponerlo a verde
		if(indiceSemaforoVerde==-1) {
			carreterasEntrantes.get(0).ponSemaforo(true);
			indiceSemaforoVerde++;
		}else {
			carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(false);
			indiceSemaforoVerde++;
			indiceSemaforoVerde%=carreterasEntrantes.size();
			carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
		}
	}
	
	@Override
	protected CarreteraEntrante creaCarreteraEntrante(Carretera carretera) {
		return new CarreteraEntrante(carretera);
	}

	@Override
	protected void completaDetallesSeccion(IniSection is) {
		String listCr = "";
		for(int i = 0; i < carreterasEntrantes.size();i++) {
			if(i==0) {
				if (carreterasEntrantes.get(i).semaforo) {
					listCr = "(" + carreterasEntrantes.get(i).carretera.getId() + ",green,["+pintarCola(carreterasEntrantes.get(i))+"])";
				} else if (!carreterasEntrantes.get(i).semaforo) {
					listCr = "(" + carreterasEntrantes.get(i).carretera.getId() + ",red,["+pintarCola(carreterasEntrantes.get(i))+"])";
				}
			} else {
				if (carreterasEntrantes.get(i).semaforo) {
					listCr += ",(" + carreterasEntrantes.get(i).carretera.getId() + ",green,["+pintarCola(carreterasEntrantes.get(i))+"])";
				} else if (!carreterasEntrantes.get(i).semaforo) {
					listCr += ",(" + carreterasEntrantes.get(i).carretera.getId() + ",red,["+pintarCola(carreterasEntrantes.get(i))+"])";
				}
			}
		}
		is.setValue("queues", listCr);
	}
	
	
}
