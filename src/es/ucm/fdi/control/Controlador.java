package es.ucm.fdi.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JTextArea;

import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.eventos.ParserEventos;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.exceptions.VehiculoException;
import es.ucm.fdi.gui.observador.ObservadorSimuladorTrafico;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simulador.SimuladorTrafico;

public class Controlador {
	  private SimuladorTrafico simulador;
	  private OutputStream ficheroSalida;
	  private InputStream ficheroEntrada;
	  private int pasosSimulacion;
	 
	  
	public Controlador(SimuladorTrafico sim, Integer limiteTiempo, InputStream is, OutputStream os) {
		simulador = sim;
		ficheroSalida= os;
		ficheroEntrada = is;
		pasosSimulacion = limiteTiempo;
	}

	public void ejecuta() throws ErrorDeSimulacion, VehiculoException {
		this.cargaEventos(this.ficheroEntrada);
		this.simulador.ejecuta(pasosSimulacion, this.ficheroSalida);
	}

	public void cargaEventos(InputStream inStream) throws ErrorDeSimulacion {
		Ini ini;
		try {
			ini = new Ini(inStream);
		} catch (IOException e) {
			throw new ErrorDeSimulacion("Error en la lectura de Eventos: " + e);
		}
		for (IniSection sec : ini.getSections()) {
			Evento e = ParserEventos.parseaEvento(sec);
			if (e != null) {
				this.simulador.insertaEvento(e);
			} else {
				throw new ErrorDeSimulacion("Error en la lectura de Eventos: " + sec.getTag());
			}
		}
	}

	public void ejecuta(int pasos){
		this.simulador.ejecuta(pasos, this.ficheroSalida);
	}


	public void reinicia() {
		this.simulador.reinicia();
	}
	
	public void cambiarOutput(JTextArea areaInformes) {
		simulador.cambiarOutput(areaInformes);
	}

	public void addObserver(ObservadorSimuladorTrafico o) {
		this.simulador.addObservador(o);
	}
	
	public String generateReport() {
		return simulador.generateReport();
	}
	
	public void eliminarVehiculo(String id) {
		simulador.eliminarVehiculoSimulacion(id);
	}
}
