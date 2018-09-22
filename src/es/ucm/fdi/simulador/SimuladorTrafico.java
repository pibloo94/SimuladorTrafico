package es.ucm.fdi.simulador;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.gui.observador.Observador;
import es.ucm.fdi.gui.observador.ObservadorSimuladorTrafico;
import es.ucm.fdi.maps.MapaCarreteras;
import es.ucm.fdi.util.SortedArrayList;

public class SimuladorTrafico implements Observador<ObservadorSimuladorTrafico> {

	private MapaCarreteras mapa;
	private List<Evento> eventos;
	private int contadorTiempo;
	private OutputStream salida;

	//Atributos GUI
	private List<ObservadorSimuladorTrafico> observadores;
	
	public SimuladorTrafico() {
		this.mapa = new MapaCarreteras();
		this.contadorTiempo = 0;
		Comparator<Evento> cmp = new Comparator<Evento>() {

			@Override
			public int compare(Evento o1, Evento o2) {
				if(o1.getTiempo()<o2.getTiempo()) return -1;
				else if(o1.getTiempo()>o2.getTiempo())return 1;
				else return 0;
			}
		};
		this.eventos = new SortedArrayList<Evento>(cmp); // estructura ordenada por tiempo
		this.observadores= new ArrayList<>();
		salida = null;
	}

	public void ejecuta(int pasosSimulacion, OutputStream ficheroSalida) throws ErrorDeSimulacion {
		int limiteTiempo = this.contadorTiempo + pasosSimulacion - 1;
		while (this.contadorTiempo <= limiteTiempo) {
			int i = 0;
			try {
			while(i < eventos.size() && eventos.get(i) !=null) {
				Evento actual = eventos.get(i);
				if(actual.getTiempo()==contadorTiempo) {
					actual.ejecuta(mapa);
					eventos.remove(i);
					i--;
				}
				i++;
			}
			// actualizar mapa
			mapa.actualizar();
			}catch(ErrorDeSimulacion e) {
				this.notificaError(e);
				throw new ErrorDeSimulacion("Error de Simulacion");
			}
			// escribir el informe en ficheroSalida, controlando que no sea null.
			if (salida == null) {
				System.out.println(mapa.generateReport(contadorTiempo));
			} else {
				String informe = mapa.generateReport(contadorTiempo);
				try {
					salida.write(informe.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			contadorTiempo++;
			notificaAvanza();
		}
	}

	public void insertaEvento(Evento e) {
		if( e!=null) {
			if(e.getTiempo() < this.contadorTiempo) {
				ErrorDeSimulacion err = new ErrorDeSimulacion("Tiempo Invalido");
				this.notificaError(err);
				throw err;
			}else {
				this.eventos.add(e);
				this.notificaNuevoEvento();
			}
		}else {
			ErrorDeSimulacion err = new ErrorDeSimulacion();
			this.notificaError(err);
			throw err;
		}
	}
	
	public void cambiarOutput(JTextArea areaInformes) {
		if (salida == null) {
			this.salida = new OutputStream() {

				@Override
				public void write(int b) throws IOException {
					String aux = Integer.toString(b);
					areaInformes.setText(areaInformes.getText() + aux);
				}

				@Override
				public void write(byte[] b) {
					areaInformes.setText(areaInformes.getText() + new String(b));
				}
			};
		} else
			salida = null;
	}

	// FUNCIONES GUI
	@Override
	public void addObservador(ObservadorSimuladorTrafico o) {
		if (o != null && !this.observadores.contains(o)) {
			this.observadores.add(o);
		}
	}

	@Override
	public void removeObservador(ObservadorSimuladorTrafico o) {
		if (o != null && this.observadores.contains(o)) {
			this.observadores.remove(o);
		}
	}
	

	public void reinicia() {
		// this.mapa.reinicia();
		this.mapa = new MapaCarreteras();
		this.contadorTiempo = 0;
		
		this.eventos.clear();
		this.notificaReinicia();
	}

	private void notificaNuevoEvento() {

		for (ObservadorSimuladorTrafico o : observadores) {
			o.addEvento(contadorTiempo, mapa, eventos);
		}
	}

	private void notificaReinicia() {
		for (ObservadorSimuladorTrafico o : observadores) {
			o.reinicia(contadorTiempo, mapa, eventos);
		}
	}

	private void notificaAvanza() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				for (ObservadorSimuladorTrafico o : observadores) {
					o.avanza(contadorTiempo, mapa, eventos);
				}
			}
		});

	}

	private void notificaError(ErrorDeSimulacion err) {
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.errorSimulador(this.contadorTiempo, this.mapa, this.eventos, err);
		}
	}

	public String generateReport() {
		return mapa.generateReport(contadorTiempo);
	}
	
	public void eliminarVehiculoSimulacion(String id) {
		mapa.eliminarVehiculo(id);
	}
}
