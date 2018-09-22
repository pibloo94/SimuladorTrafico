package es.ucm.fdi.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

public class ListModel<T> extends DefaultListModel<T> {

	private static final long serialVersionUID = 1L;
	
	private List<T> lista;

	ListModel() {
		this.lista = new ArrayList<T>();
	}

  public void setList(List<T> lista) {
    this.lista = lista;
    fireContentsChanged(this, 0, this.lista.size());
  }

  @Override
    public T getElementAt(int index) {
	  return lista.get(index);
  }

  @Override
  public int getSize() {
    return this.lista == null ? 0 : this.lista.size();
  }
}