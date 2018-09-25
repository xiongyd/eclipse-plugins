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
			new ComboBoxPropertyDescriptor(PROP_VISIBLE, "是否可见", new String[] {"可见", "不可见"}),
			new TextPropertyDescriptor(PROP_NAME, "名称")
	};
	
	public NodeModel() {
		this.name = "节点";
	}
}
