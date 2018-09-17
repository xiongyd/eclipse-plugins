package com.plugindev.addressbook.editors.models;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.plugindev.addressbook.util.ImageCache;
import com.plugindev.addressbook.util.ImageKeys;

public class MasterLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (element instanceof BasicAddressList) {
			return ImageCache.getInstance().getImage(ImageKeys.getImageDescriptor(ImageKeys.IMG_SCROL_BASIC));
		}
		if (element instanceof AreaAddressList) {
			return ImageCache.getInstance().getImage(ImageKeys.getImageDescriptor(ImageKeys.IMG_SCROL_AREA));
		}
		if (element instanceof PhoneAddressList) {
			return ImageCache.getInstance().getImage(ImageKeys.getImageDescriptor(ImageKeys.IMG_SCROL_PHONE));
		}
		return ImageCache.getInstance().getImage(ImageKeys.getImageDescriptor(ImageKeys.IMG_CAT_UNKNOWN));
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		return element.toString();
	}

}
