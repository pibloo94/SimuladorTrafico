package es.ucm.fdi.eventos;

import es.ucm.fdi.exceptions.VehiculoException;
import es.ucm.fdi.maps.MapaCarreteras;

public abstract class Evento {
    protected Integer tiempo;

    public Evento(int tiempo) {
    	this.tiempo=tiempo;
    }

    public int getTiempo() {
        return this.tiempo; //implementar
    }

    // cada clase que hereda implementa su método ejecuta, que creará
    // el correspondiente objeto de la simulación.

    public abstract void ejecuta(MapaCarreteras mapa) throws VehiculoException;
}