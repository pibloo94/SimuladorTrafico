
package es.ucm.fdi.element;

import es.ucm.fdi.ini.IniSection;

public class CruceCongestionado extends  CruceGenerico<CarreteraEntranteConIntervalo> {

    // no tiene atributos

    public CruceCongestionado(String id) {
        super(id);
    }

    @Override
    protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
        return new CarreteraEntranteConIntervalo(carretera, 0 );
    }

    @Override
    protected void completaDetallesSeccion(IniSection is) {
    	String listCr = "";
		for(int i = 0; i < carreterasEntrantes.size();i++) {
			if(i==0) {
				if (carreterasEntrantes.get(i).semaforo) {
					listCr = "(" + carreterasEntrantes.get(i).carretera.getId() + ",green:"+hallaPasos(i)+",["+pintarCola(carreterasEntrantes.get(i))+"])";
				} else if (!carreterasEntrantes.get(i).semaforo) {
					listCr = "(" + carreterasEntrantes.get(i).carretera.getId() + ",red,["+pintarCola(carreterasEntrantes.get(i))+"])";
				}
			} else {
				if (carreterasEntrantes.get(i).semaforo) {
					listCr += ",(" + carreterasEntrantes.get(i).carretera.getId() +",green:"+hallaPasos(i)+",["+pintarCola(carreterasEntrantes.get(i))+"])";
				} else if (!carreterasEntrantes.get(i).semaforo) {
					listCr += ",(" + carreterasEntrantes.get(i).carretera.getId() + ",red,["+pintarCola(carreterasEntrantes.get(i))+"])";
				}
			}
		}
		is.setValue("queues", listCr);
    	is.setValue("type", "mc");
    }



    @Override
    protected void actualizaSemaforos() {
        /*
        - Si no hay carretera con semáforo en verde (indiceSemaforoVerde == -1) entonces se
         selecciona la carretera que tenga más vehículos en su cola de vehiculos.
        - Si hay carretera entrante "ri" con su semáforo en verde, (indiceSemaforoVerde !=-1)
         entonces:
         1. Si ha consumido su intervalo de tiempo en verde ("ri.tiempoConsumido()"):
         1.1. Se pone el semáforo de "ri" a rojo.
         1.2. Se inicializan los atributos de "ri".
         1.3. Se busca la posición "max" que ocupa la primera carretera entrante
         distinta de "ri" con el mayor número de vehículos en su cola.
         1.4. "indiceSemaforoVerde" se pone a "max".
         1.5. Se pone el semáforo de la carretera entrante en la posición "max" ("rj")
         a verde y se inicializan los atributos de "rj", entre ellos el
         "intervaloTiempo" a Math.max(rj.numVehiculosEnCola()/2,1).
         */
        if (indiceSemaforoVerde == -1){
        	indiceSemaforoVerde = buscaMaxCarretera();
        	this.carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
        	this.carreterasEntrantes.get(indiceSemaforoVerde).setIntervaloDeTiempo(Math.max(this.carreterasEntrantes.get(indiceSemaforoVerde).colaVehiculos.size()/2, 1));
        }else {
            if(this.carreterasEntrantes.get(indiceSemaforoVerde).tiempoConsumido()) {
            	this.carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(false);
                // Se inicializan los atributos de "ri".
            	this.carreterasEntrantes.get(indiceSemaforoVerde).setUnidadesDeTiempoUsadas(0);
            	this.carreterasEntrantes.get(indiceSemaforoVerde).setUsoCompleto(false);
            	indiceSemaforoVerde = buscaMaxCarretera();
            	this.carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
            	this.carreterasEntrantes.get(indiceSemaforoVerde).setIntervaloDeTiempo(Math.max(this.carreterasEntrantes.get(indiceSemaforoVerde).colaVehiculos.size()/2, 1));
            	this.carreterasEntrantes.get(indiceSemaforoVerde).setUnidadesDeTiempoUsadas(0);
            }
        }
    }

    private int buscaMaxCarretera() {
    	int noRepetir = indiceSemaforoVerde;
    	int maximo =-1;
    	int posMax=indiceSemaforoVerde;
    	for(int i = 0; i < this.carreterasEntrantes.size();i++) {
    		if(maximo < this.carreterasEntrantes.get(i).colaVehiculos.size() && i !=noRepetir) {
    			maximo = this.carreterasEntrantes.get(i).colaVehiculos.size();
    			posMax = i;
    		}
    	}
    	return posMax;
    }
    
    private int hallaPasos(int buscado) {
    	return carreterasEntrantes.get(buscado).getIntervaloDeTiempo()-carreterasEntrantes.get(buscado).getUnidadesDeTiempoUsadas();
    }
    
}

