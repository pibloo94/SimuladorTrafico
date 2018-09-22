package es.ucm.fdi.exceptions;

public class ErrorDeSimulacion extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ErrorDeSimulacion() {
		super();
	}
	
	public ErrorDeSimulacion(String message) {
		super(message);
	}
}
