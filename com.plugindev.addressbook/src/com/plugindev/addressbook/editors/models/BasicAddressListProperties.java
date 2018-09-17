package com.plugindev.addressbook.editors.models;

import org.eclipse.swt.widgets.Composite;

public class BasicAddressListProperties extends AddressListProperties {

	public BasicAddressListProperties() {
		super();
	}
	
	@Override
	public void createContents(Composite parent) {
		// TODO Auto-generated method stub
		super.createContents(parent);
		createSection(parent, BasicAddressList.stringKeys, BasicAddressList.choiceKeysMap);
	}
}
