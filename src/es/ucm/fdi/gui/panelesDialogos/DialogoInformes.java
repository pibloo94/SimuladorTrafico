package es.ucm.fdi.gui.panelesDialogos;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.element.Carretera;
import es.ucm.fdi.element.CruceGenerico;
import es.ucm.fdi.element.Vehiculo;
import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.gui.PanelBotones;
import es.ucm.fdi.gui.PanelObjSim;
import es.ucm.fdi.gui.observador.ObservadorSimuladorTrafico;
import es.ucm.fdi.gui.ventana.VentanaPrincipal;
import es.ucm.fdi.maps.MapaCarreteras;

public class DialogoInformes extends JDialog implements ObservadorSimuladorTrafico {
	
	private static final long serialVersionUID = 1L;
	
	private PanelBotones panelBotones;
	private PanelObjSim<Vehiculo> panelVehiculos;
	private PanelObjSim<Carretera> panelCarreteras;
	private PanelObjSim<CruceGenerico<?>> panelCruces;
	private JPanel panelPrincipal;
	
	public static char TECLALIMPIAR = 'c';
	
	public DialogoInformes(VentanaPrincipal ventanaPrincipal, Controlador controlador) {
		super();		
		initGUI(ventanaPrincipal);
		controlador.addObserver(this);
	}

	private void initGUI(VentanaPrincipal ventanaPrincipal) {
		this.panelVehiculos = new PanelObjSim<Vehiculo>("Vehiculos");
		this.panelCarreteras = new PanelObjSim<Carretera>("Carreteras");
		this.panelCruces = new PanelObjSim<CruceGenerico<?>>("Cruces");
		
		this.panelBotones = new PanelBotones(this);
		
		JButton botoNCancelar = new JButton("Cancelar");
		botoNCancelar.setToolTipText("Cancelar dialogo informes");
		botoNCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ocultar();
			}
		});
		
		JButton botonGenerar = new JButton("Generar");
		botonGenerar.setToolTipText("Generar dialogo informes");
		botonGenerar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaPrincipal.generarInformesConcretos();
			}
		});
		panelBotones.add(botoNCancelar);
		panelBotones.add(botonGenerar);
		
		this.panelPrincipal = new JPanel(new BorderLayout());
		InformationPanel panelInfo = new InformationPanel();
		panelPrincipal.add(panelInfo, BorderLayout.PAGE_START);
		panelPrincipal.add(panelBotones, BorderLayout.PAGE_END);
		panelPrincipal.add(panelVehiculos,BorderLayout.EAST);
		panelPrincipal.add(panelCarreteras,BorderLayout.CENTER);
		panelPrincipal.add(panelCruces,BorderLayout.WEST);
		this.setContentPane(panelPrincipal);
	}

	public void mostrar() {
		this.setVisible(true);
	}
	
	public void ocultar() {
		this.setVisible(false);
	}

	private void setMapa(MapaCarreteras mapa) {
		this.panelVehiculos.setList(mapa.getVehiculos());
		this.panelCarreteras.setList(mapa.getCarreteras());
		this.panelCruces.setList(mapa.getCruces());
	}

	public List<Vehiculo> getVehiculosSeleccionados() {
		return this.panelVehiculos.getSelectedItems();
	}

	public List<Carretera> getCarreterasSeleccionadas() {
		return this.panelCarreteras.getSelectedItems();
	}

	public List<CruceGenerico<?>> getCrucesSeleccionados() {
		return this.panelCruces.getSelectedItems();
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.setMapa(mapa);
	}
	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.setMapa(mapa);
		
	}
	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.setMapa(mapa);
	}

}
