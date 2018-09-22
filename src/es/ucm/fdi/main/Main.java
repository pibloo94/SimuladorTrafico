package es.ucm.fdi.main;


/**
 * 
 * @author Manuel Monforte Escobar y Pablo Agudo Brun
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import es.ucm.fdi.exceptions.VehiculoException;
import es.ucm.fdi.gui.ventana.VentanaPrincipal;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.simulador.SimuladorTrafico;


public class Main {

	private final static Integer limiteTiempoPorDefecto = 10;
	private static Integer limiteTiempo = 10;
	private static String ficheroEntrada = null;
	private static String ficheroSalida = null;
	private static boolean gui = false;
	
	private static void ParseaArgumentos(String[] args) throws ErrorDeSimulacion, InvocationTargetException, IOException, VehiculoException, InterruptedException {

		// define the valid command line options
		Options opcionesLineaComandos = Main.construyeOpciones();

		// parse the command line as provided in args
		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine linea = parser.parse(opcionesLineaComandos, args); // crea linea comando y la parsea
			parseaOpcionHELP(linea, opcionesLineaComandos); // muestra la ayuda
			parseaOpcionModo(linea);
			if(!gui) {
				parseaOpcionFicheroIN(linea); // rellena variable FicheroEntrada
				parseaOpcionFicheroOUT(linea); // rellena variable FicheroSalida
				parseaOpcionSTEPS(linea); // rellena variable LimiteDeTiempo
			}
			// if there are some remaining arguments, then something wrong is provided in the command line!
			String[] resto = linea.getArgs();
			if (resto.length > 0) {
				String error = "Illegal arguments:";
				for (String o : resto)
					error += (" " + o);
				throw new ParseException(error);
			}
		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
	}

	private static Options construyeOpciones() {
		Options opcionesLineacomandos = new Options();

		opcionesLineacomandos.addOption(Option.builder("h").longOpt("help").desc("Muestra la ayuda.").build());
		opcionesLineacomandos.addOption(Option.builder("i").longOpt("input").hasArg().desc("Fichero de entrada de eventos.").build());
		opcionesLineacomandos.addOption(Option.builder("o").longOpt("output").hasArg().desc("Fichero de salida, donde se escriben los informes.").build());
		opcionesLineacomandos.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Pasos que ejecuta el es.ucm.fdi.es.ucm.fdi.es.ucm.fdi.simulador en su bucle principal (el valor por defecto es "+ Main.limiteTiempoPorDefecto + ").").build());
		opcionesLineacomandos.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Modo de uso de la aplicacion (el valor por defecto es batch .").build());
		return opcionesLineacomandos;
	}

	private static void parseaOpcionHELP(CommandLine linea, Options opcionesLineaComandos) {
		if (linea.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), opcionesLineaComandos, true);
			System.exit(0);
		}
	}

	private static void parseaOpcionFicheroIN(CommandLine linea) throws ParseException {
		Main.ficheroEntrada = linea.getOptionValue("i");
		if (Main.ficheroEntrada == null) {
			throw new ParseException("El fichero de eventos no existe");
		}
	}

	private static void parseaOpcionFicheroOUT(CommandLine linea) throws ParseException {
		Main.ficheroSalida = linea.getOptionValue("o");
	}

	private static void parseaOpcionSTEPS(CommandLine linea) throws ParseException {
		String t = linea.getOptionValue("t", Main.limiteTiempoPorDefecto.toString());
		try {
			Main.limiteTiempo = Integer.parseInt(t);
			assert (Main.limiteTiempo < 0);
		} catch (Exception e) {
			throw new ParseException("Valor invalido para el limite de tiempo: " + t);
		}
	}

	
	private static void parseaOpcionModo(CommandLine linea) throws ParseException, ErrorDeSimulacion, IOException, VehiculoException, InvocationTargetException, InterruptedException {
		if(linea.getOptionValue("m")==null ||linea.getOptionValue("m").equals("batch")) gui = false;
		else if(linea.getOptionValue("m").equals("gui")) gui = true;
		else throw new ParseException("Illegal mode");

	}
	
	// generar is, os, es.ucm.fdi.es.ucm.fdi.simulador y llama a ejecuta de Controlador
	private static void iniciaModoEstandar() throws IOException, ErrorDeSimulacion, VehiculoException {
		File file = new File(Main.ficheroEntrada);
		InputStream is = new FileInputStream(file);
		OutputStream os = Main.ficheroSalida == null ? System.out : new FileOutputStream(new File(Main.ficheroSalida));
		SimuladorTrafico sim = new SimuladorTrafico();
		Controlador ctrl = new Controlador(sim, Main.limiteTiempo, is, os);
		ctrl.ejecuta();
		is.close();
		System.out.println("Done!");
	}
	
	private static void ejecutaFicheros(String path) throws IOException {

		File dir = new File(path);

		if (!dir.exists()) {
			throw new FileNotFoundException(path);
		}

		File[] files = dir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ini");
			}
		});

		for (File file : files) {
			Main.ficheroEntrada = file.getAbsolutePath();
			Main.ficheroSalida = file.getAbsolutePath() + ".out";
			Main.limiteTiempo = 20;
			try {
				Main.iniciaModoEstandar();
			} catch (ErrorDeSimulacion e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	private static void iniciaModoGrafico() throws FileNotFoundException, InvocationTargetException, InterruptedException {
		SimuladorTrafico sim = new SimuladorTrafico();
		OutputStream os = Main.ficheroSalida == null ? System.out : new FileOutputStream(new File(Main.ficheroSalida));
		Controlador ctrl = new Controlador(sim, Main.limiteTiempo,null , os);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new VentanaPrincipal(Main.ficheroEntrada, ctrl);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void main(String[] args) throws IOException  {

		// example command lines
		// -i resources/examples/events/basic/ex1.ini
		// -i resources/examples/events/advanced/ex1.ini
		// --help
		// -i resources\examples\events\basic\02_twoRoads.ini -o resources\examples\events\basic\02_twoRoads.ini -t 10
		
		//ejecutaFicheros("resources\\examples\\events\\basic");//inicia la aplicacion con E/S estandar
		
		try {
			Main.ParseaArgumentos(args);
			if(gui) 
				iniciaModoGrafico();
			else
				Main.iniciaModoEstandar();
		} catch (ErrorDeSimulacion | InvocationTargetException  | InterruptedException e1) {
			e1.printStackTrace();
		}//realiza el parseo de los argumentos
		
	}

}