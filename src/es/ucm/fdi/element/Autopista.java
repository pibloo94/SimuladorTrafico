package es.ucm.fdi.element;

import es.ucm.fdi.ini.IniSection;

public class Autopista extends Carretera {
	
	private int numCarriles;
	
	public Autopista(String id, int length, int maxSpeed, CruceGenerico<?> origen, CruceGenerico<?> destino,int numCarriles) {
		super(id, length, maxSpeed, origen, destino);
		this.numCarriles = numCarriles;
	}

	protected int calculaVelocidadBase() {
		return Math.min(this.velocidadMaxima,((this.velocidadMaxima *numCarriles)/(Math.max(vehiculos.size(),1))+1));
		}
	
	protected int calculaFactorReduccion(int obstaculos) {
		return obstaculos <this.numCarriles? 1:2;
	}
	
	protected void completaDetallesSeccion(IniSection is) {
		super.completaDetallesSeccion(is);
		is.setValue("type", "lanes");
	}
}
