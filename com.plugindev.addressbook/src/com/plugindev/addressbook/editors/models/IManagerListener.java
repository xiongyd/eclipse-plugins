package com.plugindev.addressbook.editors.models;

public interface IManagerListener {

	String ADDED="__added";
	String REMOVED="__removed";
	String CHANGED="__changed";
	
	void managerChanged(Object obj, String type, String itemType, String key);
}
