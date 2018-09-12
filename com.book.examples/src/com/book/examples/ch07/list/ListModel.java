package com.book.examples.ch07.list;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;

public class ListModel {

	public static final String ADD_ELEMENT = "addElement";
	public static final String REMOVE_ELEMENT = "removeElement";
	
	private Vector content;
	private PropertyChangeSupport delegate;
	
	public ListModel() {
		this.content = new Vector();
		this.delegate = new PropertyChangeSupport(this);
	}
	
	public void add(Object element) {
		if (content.add(element)) {
			firePropertyChange(new PropertyChangeEvent(this, ADD_ELEMENT, null, element));
		}
	}
	
	public void remove(Object element) {
		if (content.remove(element)) {
			firePropertyChange(new PropertyChangeEvent(this, REMOVE_ELEMENT, null, element));
		}
	}
	
	public void firePropertyChange(PropertyChangeEvent evt) {
		delegate.firePropertyChange(evt);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		delegate.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		delegate.removePropertyChangeListener(listener);
	}
	
	public Object[] elements() {
		return content.toArray();
	}
}
