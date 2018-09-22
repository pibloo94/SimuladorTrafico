package es.ucm.fdi.gui.toolBar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.gui.frames.VentanaAveriados;
import es.ucm.fdi.gui.observador.ObservadorSimuladorTrafico;
import es.ucm.fdi.gui.ventana.VentanaPrincipal;
import es.ucm.fdi.maps.MapaCarreteras;

public class ToolBar extends JToolBar implements ObservadorSimuladorTrafico {

	private static final long serialVersionUID = 1L;
	
	private JSpinner steps;
	private JSpinner delay;
	private JTextField time;
	
	private List<JButton> listaBotones;
	
	private JLabel reloj; //nuevo

	public JLabel getReloj() { //nuevo
		return reloj;
	}

	public void setReloj(JLabel reloj) { //nuevo
		this.reloj = reloj;
	}

	public ToolBar(VentanaPrincipal mainWindow, Controlador controlador) {
		super();
		controlador.addObserver(this);
		this.setBackground(new Color(255, 255, 255));
		listaBotones = new ArrayList<>();
		Border emptyBorder = BorderFactory.createEmptyBorder();

		// cargar
		JButton botonCargar = new JButton(new ImageIcon("images/load.png"));
		botonCargar.setToolTipText("Carga un fichero de ventos");
		botonCargar.setBorder(emptyBorder);
		botonCargar.setBackground(new Color(255, 255, 255));
		botonCargar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.cargaFichero();
			}
		});

		// Reinciar
		JButton botonReinicia = new JButton(new ImageIcon("images/cargareventos.png"));
		botonReinicia.setToolTipText("Reinicia el simulador");
		botonReinicia.setBorder(emptyBorder);
		botonReinicia.setBackground(new Color(255, 255, 255));
		botonReinicia.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.reinicia();
				} catch (ErrorDeSimulacion err) {
					err.printStackTrace();
				}
			}
		});

		// guardar
		JButton botonGuardar = new JButton(new ImageIcon("images/save.png"));
		botonGuardar.setToolTipText("Guarda el evento cargado");
		botonGuardar.setBorder(emptyBorder);
		botonGuardar.setBackground(new Color(255, 255, 255));
		botonGuardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow.guardaEventos();
				} catch (ErrorDeSimulacion err) {
					err.printStackTrace();
				}
			}
		});

		// limpiar
		JButton botonLimpiar = new JButton(new ImageIcon("images/clean.png"));
		botonLimpiar.setToolTipText("Limpia TextArea Eventos");
		botonLimpiar.setBorder(emptyBorder);
		botonLimpiar.setBackground(new Color(255, 255, 255));
		botonLimpiar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow.limpiarAreaEventos();
				} catch (ErrorDeSimulacion err) {
					err.printStackTrace();
				}
			}
		});
		// Ejecutar
		JButton botonIniciar = new JButton(new ImageIcon("images/play.png"));
		botonIniciar.setToolTipText("Iniciar ejecucion");
		botonIniciar.setBorder(emptyBorder);
		botonIniciar.setBackground(new Color(255, 255, 255));
		botonIniciar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.ejecutaSimulador();
			}
		});

		// CargarEventos
		JButton botonCheckIn = new JButton(new ImageIcon("images/events.png"));
		botonCheckIn.setToolTipText("Cargar Eventos");
		botonCheckIn.setBorder(emptyBorder);
		botonCheckIn.setBackground(new Color(255, 255, 255));
		botonCheckIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] contenido = mainWindow.getTextoEditorEventos().getBytes();
				try {
				controlador.reinicia();
				controlador.cargaEventos(new ByteArrayInputStream(contenido));
				}catch(ErrorDeSimulacion err){
					System.out.println(err.getMessage());
				}
			}
		});
		
		JButton botonDetener = new JButton(new ImageIcon("images/stop.png"));
		botonDetener.setToolTipText("Detiene la Simulacion");
		botonDetener.setBorder(emptyBorder);
		botonDetener.setBackground(new Color(255, 255, 255));
		botonDetener.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow.detenerEjecucion();
				}catch(ErrorDeSimulacion err){
					System.out.println(err.getMessage());
				}
			}
		});

		// OPCIONAL
		// genera informes
		JButton botonGeneraReports = new JButton(new ImageIcon("images/report.png"));
		botonGeneraReports.setToolTipText("Genera informes");
		botonGeneraReports.setBorder(emptyBorder);
		botonGeneraReports.setBackground(new Color(255, 255, 255));
		botonGeneraReports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.generaInformes(controlador.generateReport());
			}
		});

		// elimina informes
		JButton botonEliminarReport = new JButton(new ImageIcon("images/delete.png"));
		botonEliminarReport.setToolTipText("Elimina informes");
		botonEliminarReport.setBorder(emptyBorder);
		botonEliminarReport.setBackground(new Color(255, 255, 255));
		botonEliminarReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.limpiaInformes();
			}
		});

		JButton botonGuardarInforme = new JButton(new ImageIcon("images/save_report.png"));
		botonGuardarInforme.setToolTipText("Guarda informes");
		botonGuardarInforme.setBorder(emptyBorder);
		botonGuardarInforme.setBackground(new Color(255, 255, 255));
		botonGuardarInforme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.guardaInforme();
			}
		});

		// salir
		JButton botonSalir = new JButton(new ImageIcon("images/shutdown.png"));
		botonSalir.setToolTipText("Salir de la aplicacion");
		botonSalir.setBorder(emptyBorder);
		botonSalir.setBackground(new Color(255, 255, 255));
		botonSalir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int confirmacion = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", JOptionPane.YES_NO_OPTION);
					if (confirmacion == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				} catch (ErrorDeSimulacion err) {
					err.printStackTrace();
				}
			}
		});

		//nuevo frame mostrar averiados
		JButton botonAveriados = new JButton(new ImageIcon("images/break.png"));
		botonAveriados.setToolTipText("Muestra vehiculos averiados");
		botonAveriados.setBorder(emptyBorder);
		botonAveriados.setBackground(new Color(255, 255, 255));
		botonAveriados.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaAveriados(controlador).setVisible(true);
			}
		});
		
		this.add(botonCargar);
		addSeparator();
		this.add(botonGuardar);
		addSeparator();
		this.add(botonCheckIn);
		addSeparator();
		this.add(botonLimpiar);
		addSeparator();
		this.add(botonIniciar);
		addSeparator();
		this.add(botonDetener);
		addSeparator();
		this.add(botonReinicia);
		addSeparator();
		this.add(botonAveriados);
		addSeparator();
		
		listaBotones.add(botonCargar);
		listaBotones.add(botonGuardar);
		listaBotones.add(botonCheckIn);
		listaBotones.add(botonLimpiar);
		listaBotones.add(botonIniciar);
		listaBotones.add(botonReinicia);
		listaBotones.add(botonAveriados);
		
		this.add(new JLabel(" Delay: "));
		this.delay = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
		this.delay.setToolTipText("Espera en cada paso: 1-10");
		this.delay.setMaximumSize(new Dimension(70, 70));
		this.delay.setMinimumSize(new Dimension(70, 70));
		delay.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int value = (int) delay.getValue();
				mainWindow.setDelay(value);
			}
		});
		
		this.add(delay);
		addSeparator();

		
		this.add(new JLabel(" Pasos: "));
		this.steps = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
		this.steps.setToolTipText("pasos a ejecutar: 1-1000");
		this.steps.setMaximumSize(new Dimension(70, 70));
		this.steps.setMinimumSize(new Dimension(70, 70));
		steps.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int value = (int) steps.getValue();
				mainWindow.setPasos(value);
			}
		});
		
		this.add(steps);
		addSeparator();

		this.add(new JLabel(" Tiempo: "));
		this.time = new JTextField("1", 5);
		this.time.setToolTipText("Tiempo actual");
		this.time.setMaximumSize(new Dimension(70, 70));
		this.time.setMinimumSize(new Dimension(70, 70));
		this.time.setEditable(false);

		this.add(this.time);
		addSeparator();
		
		//nuevo reloj del programa
		reloj = new JLabel("0");
		reloj.setToolTipText("muestra el tiempo que lleva encendida la aplicacion");
		
		this.add(botonGeneraReports);
		addSeparator();
		this.add(botonEliminarReport);
		addSeparator();
		this.add(botonGuardarInforme);
		addSeparator();
		this.add(botonSalir);
		addSeparator();

		this.add(reloj); //nuevo
		addSeparator(); //nuevo

		listaBotones.add(botonGeneraReports);
		listaBotones.add(botonEliminarReport);
		listaBotones.add(botonGuardarInforme);
		listaBotones.add(botonSalir);
	}

	public void desactivaToolBar() {
		for (JButton jButton : listaBotones) {
			jButton.setEnabled(false);
		}
		steps.setEnabled(false);
		delay.setEnabled(false);
	}
	
	public void reactivarToolBar() {
		for (JButton jButton : listaBotones) {
			jButton.setEnabled(true);
		}
		steps.setEnabled(true);
		delay.setEnabled(true);
	}
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.time.setText("" + tiempo);
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {

	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.steps.setValue(1);
		this.time.setText("0");
	}
}