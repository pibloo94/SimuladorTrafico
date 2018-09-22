package es.ucm.fdi.element;


import es.ucm.fdi.ini.IniSection;

public class Camino extends Carretera{
	
	public Camino(String id, int length, int maxSpeed, CruceGenerico<?> origen, CruceGenerico<?> destino) {
		super(id, length, maxSpeed, origen, destino);
	}

	protected int calculaVelocidadBase() {
		return this.velocidadMaxima;
	}
	
	protected int calculaFactorReduccion(int obstaculos) {
		return obstaculos +1;
	}

	protected void completaDetallesSeccion(IniSection is) {
		super.completaDetallesSeccion(is);
		is.setValue("type", "dirt");
	}
}
