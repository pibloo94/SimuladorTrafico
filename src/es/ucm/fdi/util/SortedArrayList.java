package es.ucm.fdi.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;


public class SortedArrayList<E> extends ArrayList<E> {
	
	private static final long serialVersionUID = 1L;
	
	private Comparator<E> cmp;

    public SortedArrayList(Comparator<E> cmp) {
    	this.cmp = cmp;
    }

    @Override
    public boolean add(E e) {
    	boolean encontrado = false;
    	int i = 0;
    	
    	while(i<this.size() && !encontrado) {//enconntramos el elemento en i
    		int rdo = cmp.compare(e,this.get(i));
    		if(rdo<0)encontrado=true;
    		i++;
    	}
    	int pos = i;
    	super.add(pos, e);

    	return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
		for (E e: c) {
			this.add(e);
		}
		return false;
    }

    // sobreescribir los métodos que realizan operaciones de
    // inserción basados en un índice para que lancen excepcion.
    // Ten en cuenta que esta operación rompería la ordenación.
    // estos métodos son add(int index, E element),
    // addAll(int index, Colection<? Extends E>) y  public E set(int index, E element)

	
	public void add(int index, E element){
    	throw  new ErrorDeSimulacion("Fallo SortedArrayList");
	}

	public boolean addAll(int index, Collection<? extends E> c){
		throw  new ErrorDeSimulacion("Fallo SortedArrayList");
	}

	public E set(int index, E element) {
		throw  new ErrorDeSimulacion("Fallo SortedArrayList");
	}
	
}
