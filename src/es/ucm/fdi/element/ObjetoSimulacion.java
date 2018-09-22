package es.ucm.fdi.element;

import es.ucm.fdi.ini.IniSection;

public abstract class ObjetoSimulacion {
    protected String id;

    public ObjetoSimulacion(String id) {
    	this.id = id;
    }

    public String getId() {
        return this.id;
    }

    protected abstract void completaDetallesSeccion(IniSection is);

    protected abstract String getNombreSeccion();

    public abstract void avanza();

    public String generaInforme(int tiempo) {
        IniSection is = new IniSection(this.getNombreSeccion());
        is.setValue("id", this.id);
        is.setValue("time", tiempo);
        this.completaDetallesSeccion(is);
        return is.toString();
    }
}