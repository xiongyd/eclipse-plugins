package com.plugindev.addressbook.editors.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.plugindev.addressbook.util.Messages;

public class PhoneAddressList extends AddressList {

	//存储电话项的名称，为不同的地址项（AddressItem）固定不变的部分
	public static List<String> stringKeys;
	public static final String TYPE_PHONE = "phoneList";
	
	protected PhoneAddressList(String name, List stringItems,int sequence) {
		super(name, stringItems, new ArrayList(), sequence);
		if (stringKeys == null)  {
			stringKeys = new ArrayList<String>();
			for (int i=0;i<stringItems.size();i++) {
				TextItemContents item = (TextItemContents) stringItems.get(i);
				stringKeys.add(item.getKey());
			}
		}
		setType(TYPE_PHONE);
	}

	@Override
	public List getStringKeys() {
		// TODO Auto-generated method stub
		return stringKeys;
	}

	@Override
	public Map getChoiceKeysMap() {
		// TODO Auto-generated method stub
		return new HashMap();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return Messages.ADDR_LIST_PHONE_DESC;
	}

}
