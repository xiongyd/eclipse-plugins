package com.plugindev.addressbook.views;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

import com.plugindev.addressbook.models.AddressManager;
import com.plugindev.addressbook.models.AddressManagerEvent;
import com.plugindev.addressbook.models.AddressManagerListener;

public class AddressViewContentProvider implements IStructuredContentProvider,AddressManagerListener{

	private AddressManager manager;
	private TableViewer viewer;

	@Override
	public Object[] getElements(Object inputElement) {
		return manager.getAddresses();
	}

	@Override
	public void addressesChanged(AddressManagerEvent event) {
		viewer.getTable().setRedraw(false);
		try {
			viewer.remove(event.getItemsRemoved());
			viewer.add(event.getItemsAdded());
			viewer.update(event.getItemsModified(), null);
		} finally {
			viewer.getTable().setRedraw(true);
		}
	}
	
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TableViewer) viewer;
		if (manager != null) {
			manager.removeAddressManagerListener(this);
		}
		manager = (AddressManager) newInput;
		if (manager != null) {
			manager.addAddressManagerListener(this);
		}
	}

}
