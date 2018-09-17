package com.plugindev.addressbook.editors.models;

import org.eclipse.jface.viewers.IStructuredContentProvider;

public class MasterContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof SimpleFormEditorInput) {
			SimpleFormEditorInput input = (SimpleFormEditorInput) inputElement;
			return input.getManager().getContents();
		}
		return new Object[0];
	}

}
