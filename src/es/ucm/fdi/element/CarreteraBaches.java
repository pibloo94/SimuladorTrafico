package es.ucm.fdi.element;

import es.ucm.fdi.ini.IniSection;

public class CarreteraBaches extends Carretera{

	private int inicioBaches;
	private int finalBaches;
	private int penalizacion;
	
	public CarreteraBaches(String id, int length, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dest, int inicioBaches, int finalBaches, int penalizacion) {
		super(id, length, maxSpeed, src, dest);
		this.inicioBaches = inicioBaches;
		this.finalBaches = finalBaches;
		this.penalizacion = penalizacion;
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
	
	protected void calculaVelocidadBase(Vehiculo vehiculo) {
		if (vehiculo.getLocalizacion() >= inicioBaches && vehiculo.getLocalizacion() <= finalBaches) {
			if (this.velocidadMaxima - penalizacion > 0) {
				vehiculo.setVelocidadActual(this.velocidadMaxima - penalizacion);
			}
//		} else {
//			vehiculo.setVelocidadActual(velocidadMaxima);
		}
	}
	
	protected int calculaFactorReduccion(int obstaculos) {
		return obstaculos+1;
	}
	
	protected void completaDetallesSeccion(IniSection is) {
		super.completaDetallesSeccion(is);
		is.setValue("type", "rut");
	}
}
