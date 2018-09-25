package hellogef.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public abstract class AbstractModel implements Cloneable, Serializable,IPropertySource {

	private static final long serialVersionUID = 1L;
	
	final public static String PROP_NAME="NAME";
	
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	protected String name="";


	protected IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[] {
	};
	
	
	@Override
	public Object getEditableValue() {
		return this;
	}
	
	@Override
	public Object getPropertyValue(Object id) {
		if (PROP_NAME.equals(id)) {
			return getName();
		}
		return null;
	}
	
	@Override
	public void setPropertyValue(Object id, Object value) {
		if (PROP_NAME.equals(id)) {
			setName((String)value);
		}
	}
	
	@Override
	public boolean isPropertySet(Object id) {
		return true;
	}
	
	@Override
	public void resetPropertyValue(Object id) {

	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if (this.name.equals(name)) {
			return;
		}
		this.name = name;
		firePropertyChange(PROP_NAME, null, name);
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return propertyDescriptors;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}
	
	protected void firePropertyChange(String prop, Object oldValue, Object newValue) {
		listeners.firePropertyChange(prop, oldValue, newValue);
	}
	
	protected void fireStructureChange(String prop, Object child) {
		listeners.firePropertyChange(prop, null, child);
	}
}
