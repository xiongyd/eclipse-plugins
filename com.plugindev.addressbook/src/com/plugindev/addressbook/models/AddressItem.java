package com.plugindev.addressbook.models;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.views.properties.IPropertySource;

public class AddressItem implements IAdaptable{

	private String name;
	private String messageInfo;
	private AddressCategory category;
	
	public static final AddressItem[] NONE = new AddressItem[] {};
	
	public AddressItem(String name, AddressCategory category) {
		setName(name);
		setCategory(category);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessageInfo() {
		return messageInfo;
	}
	public void setMessageInfo(String messageInfo) {
		this.messageInfo = messageInfo;
	}
	public AddressCategory getCategory() {
		return category;
	}
	public void setCategory(AddressCategory category) {
		this.category = category;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		//为属性视图提供支持
		if (adapter == IPropertySource.class) {
			return (T) new AddressItemPropertySource(this);
		}
		return null;
	}
}
