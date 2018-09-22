package es.ucm.fdi.gui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import es.ucm.fdi.gui.panelesDialogos.DialogoInformes;

public class PanelObjSim<T> extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ListModel<T> listModel;
	private JList<T> objList;
	
	public PanelObjSim(String titulo) {
		// ...
		this.listModel = new ListModel<T>();
		this.objList = new JList<T>(this.listModel);
		addCleanSelectionListner(objList);
		this.add(new JScrollPane(objList, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), 
				BorderLayout.CENTER);
		
		this.objList.setVisibleRowCount(10);
		this.objList.setFixedCellHeight(20);
		this.objList.setFixedCellWidth(140);
	}

	private void addCleanSelectionListner(JList<?> list) {
		list.addKeyListener(new KeyListener() {
			// limpiar la seleccion de items pulsando �c�
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == DialogoInformes.TECLALIMPIAR)
					list.clearSelection();
			}

			@Override
			public void keyPressed(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}
		});
	}
	
	public List<T> getSelectedItems() {
		List<T> l = new ArrayList<>();
		for (int i : this.objList.getSelectedIndices()) {
			l.add(listModel.getElementAt(i));
		}
		return l;
	}
		
	public void setList(List<T> lista) { 
		this.listModel.setList(lista);
	}
}