package com.plugindev.addressbook.editors.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.plugindev.addressbook.util.Messages;

public abstract class AddressList {

	private String name; //列表名称
	private int sequence; //列表优先级
	private String type;
	private AddressListManager manager;
	
	private Map<String, TextItemContents> textContentMap;
	private Map<String, ChoiceItemContents> choiceContentMap;
	
	protected AddressList(String name,List stringItems,List chiceItems, int sequence) {
		this.name = name;
		this.sequence = sequence;
		
		if (textContentMap == null) {
			textContentMap = new HashMap<String, TextItemContents>();
			for (int i=0;i<stringItems.size();i++) {
				TextItemContents item = (TextItemContents) stringItems.get(i);
				textContentMap.put(item.getKey(), item);
			}
		}
		
		if (choiceContentMap == null) {
			choiceContentMap = new HashMap<String, ChoiceItemContents>();
			for (int i=0;i<chiceItems.size();i++) {
				ChoiceItemContents item = (ChoiceItemContents) chiceItems.get(i);
				choiceContentMap.put(item.getKey(), item);
			}
		}
	}
	
	public String getStringValue(String key) {
		TextItemContents item = textContentMap.get(key);
		if (item != null) {
			return (String)item.getValue();
		}
		return "";
	}
	
	public void setStringValue(String key,String value) {
		TextItemContents item = textContentMap.get(key);
		item.setValue(value);
		manager.fireManagerChanged(this, IManagerListener.CHANGED, Messages.ITEM_TYPE_TEXT, key);
	}
	
	public int getIntValue(String key) {
		// TODO 自动生成方法存根
		ChoiceItemContents item = choiceContentMap.get(key);
		if(item != null)
			return item.getValue();
		return 0;
	}

	public void setIntValue(String key, int value) {
		// TODO 自动生成方法存根
		ChoiceItemContents item = choiceContentMap.get(key);
		item.setValue(value);
		manager.fireManagerChanged(this, 
				IManagerListener.CHANGED, Messages.ITEM_TYPE_BUTTON, key);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		manager.fireManagerChanged(this, IManagerListener.CHANGED,"","");
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AddressListManager getManager() {
		return manager;
	}

	public void setManager(AddressListManager manager) {
		this.manager = manager;
	}

	public void setTextContentMap(Map<String, TextItemContents> textContentMap) {
		this.textContentMap = textContentMap;
	}

	public void setChoiceContentMap(Map<String, ChoiceItemContents> choiceContentMap) {
		this.choiceContentMap = choiceContentMap;
	}

	public Map<String, TextItemContents> getTextContentMap() {
		return textContentMap;
	}

	public Map<String, ChoiceItemContents> getChoiceContentMap() {
		return choiceContentMap;
	}


	public abstract List getStringKeys();
	public abstract Map getChoiceKeysMap();
	public abstract String getDescription();
	
	@Override
	public String toString() {
		return getName();
	}
}
