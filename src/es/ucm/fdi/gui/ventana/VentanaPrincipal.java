package es.ucm.fdi.gui.ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.element.Carretera;
import es.ucm.fdi.element.CruceGenerico;
import es.ucm.fdi.element.Vehiculo;
import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.exceptions.VehiculoException;
import es.ucm.fdi.gui.frames.VentanaAveriados;
import es.ucm.fdi.gui.mapa.ComponenteMapa;
import es.ucm.fdi.gui.menu.BarraMenu;
import es.ucm.fdi.gui.observador.ObservadorSimuladorTrafico;
import es.ucm.fdi.gui.panelesDialogos.DialogoInformes;
import es.ucm.fdi.gui.panelesDialogos.PanelBarraEstado;
import es.ucm.fdi.gui.panelesTexto.PanelEditorEventos;
import es.ucm.fdi.gui.panelesTexto.PanelInformes;
import es.ucm.fdi.gui.tablas.ModeloTablaCarreteras;
import es.ucm.fdi.gui.tablas.ModeloTablaCruces;
import es.ucm.fdi.gui.tablas.ModeloTablaEventos;
import es.ucm.fdi.gui.tablas.ModeloTablaVehiculos;
import es.ucm.fdi.gui.tablas.PanelTabla;
import es.ucm.fdi.gui.toolBar.ToolBar;
import es.ucm.fdi.maps.MapaCarreteras;

public class VentanaPrincipal extends JFrame implements ObservadorSimuladorTrafico {
	
	private static final long serialVersionUID = 1L;

	public static Border bordePorDefecto = BorderFactory.createLineBorder(Color.black, 2);
	
	//SUPERIOR PANEL
	static private final String[] columnIdEventos = { "#", "Tiempo", "Tipo" };
	private PanelEditorEventos panelEditorEventos;
	private PanelInformes panelInformes;
	private PanelTabla<Evento> panelColaEventos;

	// MENU AND TOOL BAR
	private JFileChooser fc;
	private ToolBar toolbar;
	private BarraMenu barraMenu;
	
	// GRAPHIC PANEL
	private ComponenteMapa componenteMapa;
	
	// STATUS BAR (INFO AT THE BOTTOM OF THE WINDOW)
	private PanelBarraEstado panelBarraEstado;

	// INFERIOR PANEL
	static private final String[] columnIdVehiculo = { "ID", "Carretera", "Localizacion", "Vel.", "Km", "Tiempo. Ave.", "Itinerario" };
	static private final String[] columnIdCarretera = { "ID", "Origen", "Destino", "Longitud", "Vel. Max", "Vehiculos" };
	static private final String[] columnIdCruce = { "ID", "Verde", "Rojo" };
	private PanelTabla<Vehiculo> panelVehiculos;
	private PanelTabla<Carretera> panelCarreteras;
	private PanelTabla<CruceGenerico<?>> panelCruces;

	// REPORT DIALOG
	private DialogoInformes dialogoInformes;

	// MODEL PART - VIEW CONTROLLER MODEL
	private File ficheroActual;
	private Controlador controlador;
	private int tiempo;
	private int pasos;
	private int delay;
	
	private Thread hiloejecucion;
	private Thread hiloInformes; //nuevo
	private Thread hiloReloj; // nuevo
	
	//FRAME VEHICULOS AVERIADOS
	private VentanaAveriados ventanaAveriados;
	
	public VentanaPrincipal(String ficheroEntrada, Controlador ctrl) throws FileNotFoundException {
		super("Simulador de Trafico");
		this.controlador = ctrl;
		this.ficheroActual = ficheroEntrada != null ? new File(ficheroEntrada) : null;
		this.initGUI();
		// anhadimos la ventana principal como observadora
		ctrl.addObserver(this);
		this.pasos = 1;
	}
		
	public File getFicheroActual() {
		return ficheroActual;
	}
	
	public String getTextoEditorEventos() {
		return panelEditorEventos.getTexto();
	}
	
	public void setPasos(int pasos) {
		this.pasos = pasos;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}

	
	private void initGUI() {
//	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				int confirmacion = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", JOptionPane.YES_NO_OPTION);
				if (confirmacion == JOptionPane.YES_OPTION) {
					System.exit(0);
				}					
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {

				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						relojPrograma();			
					}
				});				
			}
			// al salir pide confirmacion
		});
		
		
		JPanel panelPrincipal = this.creaPanelPrincipal();
		this.setContentPane(panelPrincipal);
		
		// BARRA DE ESTADO INFERIOR
		// (contiene una JLabel para mostrar el estado delsimulador)
		this.addBarraEstado(panelPrincipal);
		
		//PANEL CENTRAL
		JPanel panelCentral = this.createPanelCentral();
		
		   // PANEL SUPERIOR
		this.createPanelSuperior(panelCentral);
		
		   // MENU
		barraMenu= new BarraMenu(this,this.controlador,this.panelInformes);
		this.setJMenuBar(barraMenu);
		
		   // PANEL INFERIOR
		this.createPanelInferior(panelCentral);
		
		   // BARRA DE HERRAMIENTAS
	    this.addToolBar(panelPrincipal);
	    
		   // FILE CHOOSER
		this.fc = new JFileChooser(System.getProperty("user.dir"));
		
		   // REPORT DIALOG (OPCIONAL)
		this.dialogoInformes = new DialogoInformes(this,this.controlador);
		this.dialogoInformes.setSize(new Dimension(550, 300));
		
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
	}
	
	public void createDialogInf() {
		this.dialogoInformes.setLocationRelativeTo(null);
		dialogoInformes.mostrar();
	}

	private void createPanelSuperior(JPanel panelCentral) {
		JPanel panelSuperior = new JPanel(new GridLayout(1,3));
		panelEditorEventos = new PanelEditorEventos("Editor Eventos", "", true, this);
		panelColaEventos= new PanelTabla<Evento>("Cola Eventos", new ModeloTablaEventos(columnIdEventos, controlador));
		panelInformes = new PanelInformes("Informes", false, controlador);
		panelSuperior.add(panelEditorEventos);
		panelSuperior.add(panelColaEventos);
		panelSuperior.add(panelInformes);
		panelCentral.add(panelSuperior);
	}

	private void createPanelInferior(JPanel panelCentral) {
		JPanel panelInferior = new JPanel(new GridLayout(1, 2));
		JPanel tablas = new JPanel(new GridLayout(3, 1));
		//Inicializar paneles y componenteMapa
		panelVehiculos= new PanelTabla<Vehiculo>("Vehiculos", new ModeloTablaVehiculos(columnIdVehiculo, controlador));
		panelCarreteras = new PanelTabla<Carretera>("Carreteras", new ModeloTablaCarreteras(columnIdCarretera, controlador));
		panelCruces = new PanelTabla<CruceGenerico<?>>("Cruces", new ModeloTablaCruces(columnIdCruce, controlador));
		
		//Incorporamos elementos
		tablas.add(panelVehiculos);
		tablas.add(panelCarreteras);
		tablas.add(panelCruces);
		panelInferior.add(tablas);
		componenteMapa = new ComponenteMapa(controlador);
		panelInferior.add(componenteMapa);
		panelCentral.add(panelInferior);
	}
	
	private void addToolBar(JPanel panelPrincipal) {
		toolbar = new ToolBar(this, this.controlador);
		panelPrincipal.add(toolbar,BorderLayout.PAGE_START);
	}

	private void addBarraEstado(JPanel panelPrincipal) {
		this.panelBarraEstado = new PanelBarraEstado("Bienvenido al" +  " simulador!",this.controlador);
		panelPrincipal.add(this.panelBarraEstado,BorderLayout.PAGE_END);
	}

	private JPanel creaPanelPrincipal() {
		return new JPanel(new BorderLayout());
	}
	
	private JPanel createPanelCentral() {
		JPanel panelCentral = new JPanel();
		// para colocar el panel superior e inferior
		panelCentral.setLayout(new GridLayout(2, 1));
		return panelCentral;
	}

	
	// opcional
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		JOptionPane.showMessageDialog(new JFrame(),e.getMessage());
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub

	}
	
	public void generaInformes(String text) {
		panelInformes.getAreatexto().setText(text);
	}
	
	public void limpiaInformes() {
		panelInformes.getAreatexto().setText("");
	}
	
	public void limpiarAreaEventos() {
		panelEditorEventos.getAreatexto().setText("");
	}
	

	public void inserta(String string) {
		String actualTetx = panelEditorEventos.getTexto();
		panelEditorEventos.setText(actualTetx+string);
	}
	
	public void generarInformesConcretos(){
		  String informe = "";

		  for(CruceGenerico<?> j : this.dialogoInformes.getCrucesSeleccionados()){
			  informe += j.generaInforme(this.tiempo);
			  informe+="\n";
		  }
		  for(Carretera r : this.dialogoInformes.getCarreterasSeleccionadas()){
			  informe += r.generaInforme(this.tiempo);
			  informe+="\n";
		  }
		  for(Vehiculo v : this.dialogoInformes.getVehiculosSeleccionados()){
			  informe += v.generaInforme(this.tiempo);
			  informe+="\n";
		  }

		  this.panelInformes.setTexto(informe);
		 }

	//FUNCIONES PARA FICHEROS
	public void cargaFichero() {
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String s = leeFichero(file);
			this.ficheroActual = file;
			panelEditorEventos.getAreatexto().setText(s);
		}
	}
	
	@SuppressWarnings("resource")
	public static String leeFichero(File file){
		String s = "";
		try {
			s = new Scanner(file).useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	public void guardaEventos() {
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			escribeFichero(file, panelEditorEventos.getAreatexto().getText());
		}
	}
	
	public void guardaInforme() {
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			escribeFichero(file, controlador.generateReport());
		}
	}
	
	private static void escribeFichero(File file, String content) {
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.print(content);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ejecutaSimulador() {
		try {
			if (hiloejecucion == null) {
				hiloejecucion = new Thread(new Runnable() {
					public void run() {
						int i = 0;
						while (!hiloejecucion.isInterrupted() && i < pasos) {
							controlador.ejecuta(1);
							//panelInformes.setTexto(panelInformes.getTexto() + "\n" + controlador.generateReport());
							mostrarInformesPasos();
							// desactivamos gui
							desactivarGUI();
							try {
								Thread.sleep(delay * 1000);
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
							}
							i++;
						}
						reactivarGUI();
						hiloejecucion = null;
					}
				});
				this.hiloejecucion.start();
			}
		} catch (VehiculoException vex) {
			vex.printStackTrace();
		}
	}
	
	public void mostrarInformesPasos() {
		try {
			if (hiloInformes == null) {
				hiloInformes = new Thread(new Runnable() {
				
					@Override
					public void run() {
						int i= 0;
						while (!hiloInformes.isInterrupted() && i < pasos) {
							panelInformes.setTexto(panelInformes.getTexto() + controlador.generateReport());
							try {
								Thread.sleep(delay * 1000);
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
							}
							i++;
						}
						hiloInformes = null;
					}
				});
				this.hiloInformes.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void relojPrograma() {
		if (hiloReloj == null) {
			hiloReloj = new Thread(new Runnable() {

				@Override
				public void run() {
					int cont = 0;
					while (!hiloReloj.isInterrupted()) {
						int time = ++cont;
						String s = time + "";
						toolbar.getReloj().setText(s);

						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						}
					}
					hiloReloj = null;
				}
			});
			hiloReloj.start();
		}
	}
	
	public void detenerEjecucion() {
		if (hiloejecucion != null && hiloejecucion.isAlive()) {
			hiloejecucion.interrupt();
			hiloejecucion = null;
		}
	}
	
	public void desactivarGUI() {
		barraMenu.getMenuFicheros().setEnabled(false);
		barraMenu.getMenuReport().setEnabled(false);
		barraMenu.getMenuSimulador().setEnabled(false);
		toolbar.desactivaToolBar();
		panelEditorEventos.setEnabled(false);
	}
	
	public void reactivarGUI() {
		toolbar.reactivarToolBar();
		barraMenu.getMenuFicheros().setEnabled(true);
		barraMenu.getMenuReport().setEnabled(true);
		barraMenu.getMenuSimulador().setEnabled(true);
		panelEditorEventos.setEnabled(true);
	}

	public VentanaAveriados getVentanaAveriados() {
		return ventanaAveriados;
	}	
	
	public void eliminarVehiculo(String id) {
		controlador.eliminarVehiculo(id);
	}
}
