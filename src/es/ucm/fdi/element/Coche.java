package es.ucm.fdi.element;

import java.util.List;
import java.util.Random;
import es.ucm.fdi.exceptions.VehiculoException;
import es.ucm.fdi.ini.IniSection;

public class Coche extends Vehiculo {
	protected int kmUltimaAveria;
	protected int resistenciaKm;
	protected int duracionMaximaAveria;
	protected double probabilidadDeAveria;
	protected Random numAleatorio;
	
	public Coche(String id, int velocidadMaxima,int resistencia,double probabilidad,Random aleatorio,int duracionMaximaInfraccion, List<CruceGenerico<?>> iti) throws VehiculoException {
		super(id, velocidadMaxima, iti);
		this.resistenciaKm=resistencia;
		this.probabilidadDeAveria=probabilidad;
		this.numAleatorio = aleatorio;
		this.duracionMaximaAveria=duracionMaximaInfraccion;
	}
	
	public void avanza() {
		if(this.tiempoAveria>0) {
			this.kmUltimaAveria=this.kilometraje;
		}else if(kilometraje -this.kmUltimaAveria>resistenciaKm && numAleatorio.nextDouble() <= probabilidadDeAveria) {
				super.setTiempoAveria(numAleatorio.nextInt(duracionMaximaAveria)+1);
		}
		super.avanza();
	}
	protected void completaDetallesSeccion(IniSection is) {
		super.completaDetallesSeccion(is);
		is.setValue("type", "car");
	}
}
