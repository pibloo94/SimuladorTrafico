package es.ucm.fdi.gui.menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.gui.panelesTexto.PanelInformes;
import es.ucm.fdi.gui.ventana.VentanaPrincipal;

public class BarraMenu extends JMenuBar{

	private static final long serialVersionUID = 1L;
	private PanelInformes panelInformes;

	private JMenu menuFicheros;
	private JMenu menuSimulador;
	private JMenu menuReport;
	
	public BarraMenu(VentanaPrincipal mainWindow, Controlador controlador, PanelInformes panelInformes) {
		super();
		this.panelInformes = panelInformes;
		this.setBackground(new Color(255, 255, 255));
		// MANEJO DE FICHEROS
		menuFicheros = new JMenu("Ficheros");
		this.add(menuFicheros);
		this.creaMenuFicheros(menuFicheros, mainWindow);
		
		// SIMULADOR
		menuSimulador = new JMenu("Simulador");
		this.add(menuSimulador);
		this.creaMenuSimulador(menuSimulador, controlador, mainWindow);
		
		// INFORMES
		menuReport = new JMenu("Informes");
		this.add(menuReport);
		this.creaMenuInformes(menuReport, mainWindow);
	}
	
	private void creaMenuFicheros(JMenu menu,VentanaPrincipal mainWindow) {
		JMenuItem cargar = new JMenuItem("Carga eventos");
		cargar.setMnemonic(KeyEvent.VK_L);
		cargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		cargar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.cargaFichero();
			}
		});
		
		JMenuItem salvar = new JMenuItem("Guardar Eventos");
		salvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK));
		salvar.setMnemonic(KeyEvent.VK_S);
		salvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.guardaEventos();
			}
		});
		
		JMenuItem salvarInformes = new JMenuItem("Guardar Informes");
		salvarInformes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.SHIFT_MASK));
		salvarInformes.setMnemonic(KeyEvent.VK_R);
		salvarInformes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.guardaInforme();
			}
		});
		
		JMenuItem salir = new JMenuItem("Exit");
		salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.SHIFT_MASK));
		salir.setMnemonic(KeyEvent.VK_E);
		salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int confirmacion = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", JOptionPane.YES_NO_OPTION);
				if (confirmacion == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		
		menu.add(cargar);
		menu.add(salvar);
		menu.addSeparator();
		menu.add(salvarInformes);
		menu.addSeparator();
		menu.add(salir);
	}

	private void creaMenuSimulador(JMenu menuSimulador, Controlador controlador, VentanaPrincipal mainWindow) {
		JMenuItem ejecuta = new JMenuItem("Ejecuta");
		ejecuta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						mainWindow.ejecutaSimulador();
					}
				});
			}
		});
		JMenuItem reinicia = new JMenuItem("Reinicia");
		reinicia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.reinicia();
				} catch (ErrorDeSimulacion err) {
					err.printStackTrace();
				}
			}
		});
		
		JCheckBox output = new JCheckBox("Redirigir Output");
		output.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(output.isSelected()) {
					controlador.cambiarOutput(panelInformes.getAreatexto());
				}
				else {
					controlador.cambiarOutput(panelInformes.getAreatexto());
				}
			}
		});
		
		JMenuItem eliminarVehiculo = new JMenuItem("Eliminar Vehiculo");
		eliminarVehiculo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = JOptionPane.showInputDialog("Introduzca ID vehiculo a eliminar");
				mainWindow.eliminarVehiculo(id);
			}
		});
		
		
		menuSimulador.add(ejecuta);
		menuSimulador.add(reinicia);
		menuSimulador.add(output);
		menuSimulador.add(eliminarVehiculo);
	}
	
	private void creaMenuInformes(JMenu menuReport, VentanaPrincipal mainWindow) {
		JMenuItem generaInformes = new JMenuItem("Generar");
		generaInformes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {//genera informes del paso de la simulacion
				mainWindow.createDialogInf();
			}
		});
		
		menuReport.add(generaInformes);
		
		JMenuItem limpiaAreaInformes = new JMenuItem("Clear");
		limpiaAreaInformes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.limpiaInformes();
			}
		});
		
		menuReport.add(limpiaAreaInformes);
		
	}
	
	public JMenu getMenuFicheros() {
		return menuFicheros;
	}

	public JMenu getMenuSimulador() {
		return menuSimulador;
	}

	public JMenu getMenuReport() {
		return menuReport;
	}
}

