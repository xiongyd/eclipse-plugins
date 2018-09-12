package com.plugindev.addressbook.models;

import java.util.HashMap;

import org.eclipse.swt.graphics.Image;

import com.plugindev.addressbook.util.ImageCache;
import com.plugindev.addressbook.util.ImageKeys;

public abstract class AddressCategory implements Comparable<Object>{

	private final String categoryName;
	private final int priority;

	private static final HashMap<String,AddressCategory> categoryMap = new HashMap<String,AddressCategory>();
	
	public final static AddressCategory UNKOWN = new AddressCategory("未分类", 0) {

		@Override
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.getImageDescriptor(ImageKeys.IMG_CAT_UNKNOWN));
		}
		
	};
	
	public final static AddressCategory ORDINARY = new AddressCategory("普通", 1) {

		@Override
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.getImageDescriptor(ImageKeys.IMG_CAT_ORDINARY));
		}
		
	};
	
	public final static AddressCategory MATE = new AddressCategory("同事", 2) {

		@Override
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.getImageDescriptor(ImageKeys.IMG_CAT_MATE));
		}
		
	};
	
	public final static AddressCategory BUSINESS = new AddressCategory("商业伙伴", 3) {

		@Override
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.getImageDescriptor(ImageKeys.IMG_CAT_BUSINESS));
		}
		
	};
	
	public final static AddressCategory FRIENDS = new AddressCategory("朋友", 4) {

		@Override
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.getImageDescriptor(ImageKeys.IMG_CAT_FRIENDS));
		}
		
	};
	
	public final static AddressCategory FAMILY = new AddressCategory("家庭", 5) {

		@Override
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.getImageDescriptor(ImageKeys.IMG_CAT_FAMILY));
		}
		
	};
	
	public final static AddressCategory VIP = new AddressCategory("VIP", 6) {

		@Override
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.getImageDescriptor(ImageKeys.IMG_CAT_VIP));
		}
		
	};
	
	public final static AddressCategory TEACHER = new AddressCategory("师长", 7) {

		@Override
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.getImageDescriptor(ImageKeys.IMG_CAT_TEACHER));
		}
		
	};
	
	public final static AddressCategory LOVER = new AddressCategory("伴侣", 8) {

		@Override
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.getImageDescriptor(ImageKeys.IMG_CAT_LOVER));
		}
		
	};
	
	private static final AddressCategory[] TYPES = {
		UNKOWN,
		ORDINARY,
		MATE,
		BUSINESS,
		FRIENDS,
		FAMILY,
		VIP,
		TEACHER,
		LOVER
	};
	
	public static AddressCategory[] getTypes() {
		return TYPES;
	}
	
	public AddressCategory(String categoryName, int priority) {
		this.categoryName = categoryName;
		this.priority = priority;
	}
	
	public static HashMap<String,AddressCategory> getCategoryMap() {
		if (categoryMap.isEmpty()) {
			for (AddressCategory e : TYPES) {
				categoryMap.put(e.categoryName, e);
			}
		}
		return categoryMap;
	}
	
	public static AddressCategory getCategoryByName(String name) {
		for (int i=0;i<TYPES.length;i++) {
			if (TYPES[i].getCategoryName().equals(name))
				return TYPES[i];
		}
		return UNKOWN;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	@Override
	public int compareTo(Object obj) {
		return this.priority - ((AddressCategory)obj).priority;
	}
	
	public abstract Image getImage();


}
