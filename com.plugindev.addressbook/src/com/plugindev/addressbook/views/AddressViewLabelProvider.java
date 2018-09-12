package com.plugindev.addressbook.views;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.plugindev.addressbook.models.AddressItem;

public class AddressViewLabelProvider extends LabelProvider implements ITableLabelProvider{

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		switch(columnIndex) {
		case 0: //name
			return null;
		case 1: //category
			return ((AddressItem)element).getCategory().getImage();
		default:
			return null;
		}
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		switch(columnIndex) {
		case 0:
			if (element != null) {
				return ((AddressItem)element).getName();
			}
			return "";
		case 1:
			if (element != null) {
				return ((AddressItem)element).getCategory().getCategoryName();
			}
			return "";
		default:
			return "";
		}
	}

}
