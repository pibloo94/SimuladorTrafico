package es.ucm.fdi.gui.frames;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.element.Vehiculo;
import es.ucm.fdi.gui.tablas.ModeloTablaAveriados;
import es.ucm.fdi.gui.tablas.PanelTabla;

public class VentanaAveriados extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private PanelTabla<Vehiculo> panelVehiculosAveriados;
	static private final String[] columnIdVehiculo = { "ID", "Carretera", "Localizacion", "Km", "Tiempo. Ave."};

	public VentanaAveriados(Controlador controlador){
		super();		
		this.initGUI(controlador);
	}
	
	public void initGUI(Controlador controlador) {
		//inicializamos el panel averiados
		panelVehiculosAveriados = new PanelTabla<>("Averias", new ModeloTablaAveriados(columnIdVehiculo, controlador));
		JPanel panelAverias = new JPanel(new GridLayout(1, 1)); 
		panelAverias.add(panelVehiculosAveriados);
		this.setContentPane(panelAverias);
		
		this.pack();
		this.setVisible(true);
	}
}
