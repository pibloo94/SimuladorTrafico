package es.ucm.fdi.element;


public class CarreteraEntranteConIntervalo extends CarreteraEntrante{

    private int intervaloDeTiempo; // Tiempo que ha de transcurrir para poner el semÃ¡foro de la carretera en rojo
	private int unidadesDeTiempoUsadas; // Se incrementa cada vez que avanza un vehÃ­culo
	private boolean usoCompleto; // Controla que en cada paso con el semÃ¡foro en verde, ha pasado un vehÃ­culo
    private boolean usadaPorUnVehiculo; // Controla que al menos ha pasado un vehÃ­culo mientras el semÃ¡foro estaba en verde.

	public CarreteraEntranteConIntervalo(Carretera carretera,  int intervalTiempo) {
        super(carretera);
        this.intervaloDeTiempo = intervalTiempo;
        this.unidadesDeTiempoUsadas = 0;
        this.usoCompleto = true;
        this.usadaPorUnVehiculo = false;
    }


	public void setUsoCompleto(boolean usoCompleto) {
		this.usoCompleto = usoCompleto;
	}

    public boolean usoCompleto() {
        return  usoCompleto;
    }
	public int getIntervaloDeTiempo() {
		return intervaloDeTiempo;
	}

    public void setUsadaPorUnVehiculo(boolean usadaPorUnVehiculo) {
		this.usadaPorUnVehiculo = usadaPorUnVehiculo;
	}

	public void setIntervaloDeTiempo(int intervaloDeTiempo) {
		this.intervaloDeTiempo = intervaloDeTiempo;
	}
	
    public int getUnidadesDeTiempoUsadas() {
		return unidadesDeTiempoUsadas;
	}

	public void setUnidadesDeTiempoUsadas(int unidadesDeTiempoUsadas) {
		this.unidadesDeTiempoUsadas = unidadesDeTiempoUsadas;
	}


    public boolean getUsadaPorUnVehiculo() {
		return usadaPorUnVehiculo;
	}

    @Override
    //NOTA: he tenido que cambiar la visibilidad del metodo, de protected a public
    protected void avanzaPrimerVehiculo() {
        // Incrementa unidadesDeTiempoUsadas
        // Actualiza usoCompleto:
        // - Si â€œcolaVehiculosâ€� es vacÃ­a, entonces â€œusoCompleto=falseâ€�
        // - En otro caso saca el primer vehÃ­culo â€œvâ€� de la â€œcolaVehiculosâ€�,
        // y le mueve a la siguiente carretera (â€œv.moverASiguienteCarretera()â€�)
        // Pone usadaPorUnVehiculoâ€� a true.
        unidadesDeTiempoUsadas++;
        if (colaVehiculos.isEmpty()){
            usoCompleto = false;
        }else {
            Vehiculo v = colaVehiculos.get(0);
            colaVehiculos.remove(0);
            v.moverASiguienteCarretera();
            usadaPorUnVehiculo = true;
        }
    }

    public boolean tiempoConsumido() {
        // comprueba si se ha agotado el intervalo de tiempo.
        // â€œunidadesDeTiempoUsadas >= â€œintervaloDeTiempoâ€�
        if (unidadesDeTiempoUsadas >= intervaloDeTiempo){
            return true;
        }else {
            return false;
        }
    }
}
