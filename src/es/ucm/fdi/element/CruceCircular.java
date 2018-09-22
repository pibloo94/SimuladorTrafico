package es.ucm.fdi.element;

import es.ucm.fdi.ini.IniSection;

public class CruceCircular extends  CruceGenerico<CarreteraEntranteConIntervalo> {

	protected int minValorIntervalo;
	protected int maxValorIntervalo;

    public CruceCircular(String id, Integer maxValorIntervalo, Integer minValorIntervalo) {
        super(id);
        this.minValorIntervalo = minValorIntervalo;
        this.maxValorIntervalo = maxValorIntervalo;
    }

    @Override
    protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
        return new CarreteraEntranteConIntervalo(carretera, maxValorIntervalo);
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
    	is.setValue("type", "rr");
    }


    @Override
    protected void actualizaSemaforos() {
        /*
         - Si no hay carretera con semaforo en verde (indiceSemaforoVerde == -1) entonces se
         selecciona la primera carretera entrante (la de la posicion 0) y se pone su
         semaforo en verde.
         - Si hay carretera entrante "ri" con su semaforo en verde, (indiceSemaforoVerde !=-1)
         entonces:
         1. Si ha consumido su intervalo de tiempo en verde ("ri.tiempoConsumido()"):
         1.1. Se pone el semaforo de "ri" a rojo.
         1.2. Si ha sido usada en todos los pasos (ri.usoCompleto()), se fija
         el intervalo de tiempo a ... Sino, si no ha sido usada
         (ri.usada()) se fija el intervalo de tiempo a ...
         1.3. Se coge como nueva carretera con semaforo a verde la inmediatamente
         Posterior a ri.
         */
    	if (indiceSemaforoVerde == -1) {
    		indiceSemaforoVerde=0;
    		carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
    	}
    	else {
            if (carreterasEntrantes.get(indiceSemaforoVerde).tiempoConsumido()){
                carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(false);
                if(carreterasEntrantes.get(indiceSemaforoVerde).usoCompleto()){
                	carreterasEntrantes.get(indiceSemaforoVerde).setIntervaloDeTiempo( Math.min((carreterasEntrantes.get(indiceSemaforoVerde).getIntervaloDeTiempo() + 1), maxValorIntervalo));;
                }else if (!carreterasEntrantes.get(indiceSemaforoVerde).getUsadaPorUnVehiculo()){
                	carreterasEntrantes.get(indiceSemaforoVerde).setIntervaloDeTiempo(Math.max((carreterasEntrantes.get(indiceSemaforoVerde).getIntervaloDeTiempo() - 1), minValorIntervalo));;
                }
                //actualizamos ri
                carreterasEntrantes.get(indiceSemaforoVerde).setUsoCompleto(true);
                carreterasEntrantes.get(indiceSemaforoVerde).setUsadaPorUnVehiculo(false);
                carreterasEntrantes.get(indiceSemaforoVerde).setUnidadesDeTiempoUsadas(0);
                indiceSemaforoVerde++;
                indiceSemaforoVerde %= carreterasEntrantes.size();
                carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
            }
        }
        
    }
    
    private int hallaPasos(int buscado) {
    	return carreterasEntrantes.get(buscado).getIntervaloDeTiempo()-carreterasEntrantes.get(buscado).getUnidadesDeTiempoUsadas();
    }
}
