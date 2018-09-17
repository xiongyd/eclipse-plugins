package com.plugindev.addressbook.editors.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.plugindev.addressbook.util.Messages;

public class AreaAddressList extends AddressList {

	// �洢�ص�������ƣ�Ϊ��ͬ�ĵ�ַ�AddressItem���̶�����Ĳ���
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
		// TODO �Զ����ɷ������
		return new HashMap();
	}

	@Override
	public List getStringKeys() {
		// TODO �Զ����ɷ������
		return stringKeys;
	}

	@Override
	public String getDescription() {
		// TODO �Զ����ɷ������
		return Messages.ADD_LIST_AREA_DESC;
	}

}
