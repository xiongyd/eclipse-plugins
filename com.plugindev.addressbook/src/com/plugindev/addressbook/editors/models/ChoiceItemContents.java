package com.plugindev.addressbook.editors.models;

import java.util.ArrayList;
import java.util.List;

public class ChoiceItemContents {

	private String key;
	private int value;
	private List<String> choices;
	
	public ChoiceItemContents(String key,String[] choices,int value) {
		this.key = key;
		this.value = value;
		
		this.choices = new ArrayList<String>();
		for (int i=0;i<choices.length;i++) {
			this.choices.add(choices[i]);
		}
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	public Object[] getChoices(){
		return choices.toArray();
	}
	public void addChoice(String choice){
		choices.add(choice);
	}
	public void removeChoice(String choice){
		choices.remove(choice);
	}
}
