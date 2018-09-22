package es.ucm.fdi.gui.observador;

public interface Observador<T> {
	
	public void addObservador(T o);

	public void removeObservador(T o);
}
