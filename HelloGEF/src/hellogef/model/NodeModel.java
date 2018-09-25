package hellogef.model;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class NodeModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	final public static String PROP_LOCATION = "LOCATION";
	final public static String PROP_NAME = "NAME";
	final public static String PROP_VISIBLE = "VISIBLE";
	final public static String PROP_INPUTS = "INPUTS";
	final public static String PROP_OUTPUTS = "OUTPUTS";


	protected IPropertyDescriptor[] propertyDescriptos = new IPropertyDescriptor[] {
			new ComboBoxPropertyDescriptor(PROP_VISIBLE, "�Ƿ�ɼ�", new String[] {"�ɼ�", "���ɼ�"}),
			new TextPropertyDescriptor(PROP_NAME, "����")
	};
	
	public NodeModel() {
		this.name = "�ڵ�";
	}
}
