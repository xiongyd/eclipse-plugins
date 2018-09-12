package com.plugindev.addressbook.models;

import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class AddressItemPropertySource implements IPropertySource {

	protected static final String PROPERTY_CATEGORY = "com.plugindev.addressbook.properties.category";
	protected static final String PROPERTY_NAME = "com.plugindev.addressbook.properties.name";

	private ComboBoxPropertyDescriptor categoryPropertyDescriptor;
	private TextPropertyDescriptor namePropertyDescriptor;

	private String[] categoryNames;
	private int property;
	private String name;

	protected AddressItem addressItem;

	public AddressItemPropertySource(AddressItem item) {
		super();
		this.addressItem = item;
		initProperties();
	}

	private void initProperties() {
		if (categoryNames == null) {
			AddressCategory categories[] = AddressCategory.getTypes();
			categoryNames = new String[categories.length];
			for (int i = 0; i < categories.length; i++) {
				categoryNames[i] = categories[i].getCategoryName();
			}
		}

		for (int i = 0; i < categoryNames.length; i++) {
			if (categoryNames[i].equals(addressItem.getCategory().getCategoryName())) {
				property = i;
			}
		}
		name = addressItem.getName();
	}

	@Override
	public Object getEditableValue() {
		// TODO Auto-generated method stub
		return addressItem;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		categoryPropertyDescriptor = new ComboBoxPropertyDescriptor(PROPERTY_CATEGORY, "类别", categoryNames);
		categoryPropertyDescriptor.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				Integer item = (Integer) element;
				return categoryNames[item.intValue()];
			}
		});
		categoryPropertyDescriptor.setCategory("地址元素信息");

		namePropertyDescriptor = new TextPropertyDescriptor(PROPERTY_NAME, "姓名");
		namePropertyDescriptor.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return (String) element;
			}
		});
		namePropertyDescriptor.setCategory("地址元素信息");

		return new IPropertyDescriptor[] { categoryPropertyDescriptor, namePropertyDescriptor };
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(PROPERTY_CATEGORY))
			return new Integer(property);
		if (id.equals(PROPERTY_NAME))
			return name;
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		hookPropertyChanged(id, value);
		if (id.equals(PROPERTY_CATEGORY))
			property = ((Integer) value).intValue();
		if (id.equals(PROPERTY_NAME))
			name = (String) value;
	}

	private void hookPropertyChanged(Object id, Object newValue) {
		if (id.equals(PROPERTY_CATEGORY)) {
			addressItem
					.setCategory(AddressCategory.getCategoryMap().get(categoryNames[((Integer) newValue).intValue()]));
			AddressManager.getManager()
					.propertyChange(new PropertyChangeEvent(this.addressItem, "Category", property, newValue));
		} else if (id.equals(PROPERTY_NAME)) {
			addressItem.setName((String) newValue);
			AddressManager.getManager()
					.propertyChange(new PropertyChangeEvent(this.addressItem, "Name", name, newValue));
		}
	}
}
