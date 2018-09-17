package com.plugindev.addressbook.editors.models;

import java.util.HashMap;

import org.eclipse.swt.widgets.Composite;

public class PhoneAddressListItemProperties extends AddressListProperties {

	public PhoneAddressListItemProperties() {
		super();
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		createSection(parent, PhoneAddressList.stringKeys, new HashMap<String, Object[]>());
	}
}
