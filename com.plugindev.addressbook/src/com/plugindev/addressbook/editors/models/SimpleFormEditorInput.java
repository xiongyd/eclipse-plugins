package com.plugindev.addressbook.editors.models;

public class SimpleFormEditorInput extends FormEditorInput {

	private AddressListManager manager;
	
	public SimpleFormEditorInput(String name) {
		super(name);
		manager = new AddressListManager(name);
	}
	
	public AddressListManager getManager() {
		return manager;
	}

}
