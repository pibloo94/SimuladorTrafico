package es.ucm.fdi.gui.tablas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.gui.observador.ObservadorSimuladorTrafico;

public abstract class ModeloTabla<T> extends DefaultTableModel implements ObservadorSimuladorTrafico{

	private static final long serialVersionUID = 1L;
	
	protected String[] columnIds;
	protected List<T> lista;
	
	public ModeloTabla(String[] columnIdEventos, Controlador ctrl) {
		this.lista= new ArrayList<T>();
		columnIds=columnIdEventos;
		ctrl.addObserver(this);
	}
	
	@Override
	public String getColumnName(int col) {
		return this.columnIds[col];
	}

	@Override
	public int getColumnCount() {
		return this.columnIds.length;
	}

	@Override
	public int getRowCount() {
		return this.lista == null ? 0 : this.lista.size();
	}
}
