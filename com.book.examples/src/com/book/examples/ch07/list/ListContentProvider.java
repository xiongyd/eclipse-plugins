package com.book.examples.ch07.list;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;

public class ListContentProvider implements IStructuredContentProvider, PropertyChangeListener {

	private ListModel model;
	private ListViewer viewer;
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (ListModel.ADD_ELEMENT.equals(evt.getPropertyName())) {
			viewer.add(evt.getNewValue());
		} else if (ListModel.REMOVE_ELEMENT.equals(evt.getPropertyName())) {
			viewer.remove(evt.getNewValue());
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return model.elements();
	}
	
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (ListViewer) viewer;
		if (oldInput instanceof ListModel) {
			((ListModel)oldInput).removePropertyChangeListener(this);
		}
		
		if (newInput instanceof ListModel) {
			this.model = (ListModel) newInput;
			((ListModel)newInput).addPropertyChangeListener(this);
		}
	}

}
