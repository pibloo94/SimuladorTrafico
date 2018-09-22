package es.ucm.fdi.element;

import java.util.List;


import es.ucm.fdi.exceptions.VehiculoException;
import es.ucm.fdi.ini.IniSection;

public class Bicicleta extends Vehiculo{

	public Bicicleta(String id, int velocidadMaxima, List<CruceGenerico<?>> iti) throws VehiculoException {
		super(id, velocidadMaxima, iti);
	}
	
	public void setTiempoAveria(Integer duracionAveria) {
		if(this.velocidadActual>=this.velocidadMaxima/2) {
			super.setTiempoAveria(duracionAveria);
		}
	}
	protected void completaDetallesSeccion(IniSection is) {
		super.completaDetallesSeccion(is);
		is.setValue("type", "bike");
	}
}
