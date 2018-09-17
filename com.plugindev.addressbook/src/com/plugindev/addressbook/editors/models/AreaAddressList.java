package com.plugindev.addressbook.editors.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.plugindev.addressbook.util.Messages;

public class AreaAddressList extends AddressList {

	// 存储地点项的名称，为不同的地址项（AddressItem）固定不变的部分
	public static List<String> stringKeys;
	public static final String TYPE_AREA = "positionList";

	protected AreaAddressList(String name, List stringItems, int sequence) {
		super(name, stringItems, new ArrayList(), sequence);
		if (stringKeys == null) {
			stringKeys = new ArrayList<String>();
			for (int i = 0; i < stringItems.size(); i++) {
				TextItemContents item = (TextItemContents) stringItems.get(i);
				stringKeys.add(item.getKey());
			}
		}
		setType(TYPE_AREA);
	}

	@Override
	public Map getChoiceKeysMap() {
		// TODO 自动生成方法存根
		return new HashMap();
	}

	@Override
	public List getStringKeys() {
		// TODO 自动生成方法存根
		return stringKeys;
	}

	@Override
	public String getDescription() {
		// TODO 自动生成方法存根
		return Messages.ADD_LIST_AREA_DESC;
	}

}
