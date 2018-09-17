package com.plugindev.addressbook.editors.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.editor.FormEditor;

import com.plugindev.addressbook.editors.AddressFormEditor;
import com.plugindev.addressbook.editors.models.AddressList;
import com.plugindev.addressbook.editors.models.AddressListManager;
import com.plugindev.addressbook.editors.models.SimpleFormEditorInput;

public class RemoveAddressListAction extends Action {

	private TableViewer viewer;
	private AddressFormEditor editor;
	
	private ISelectionChangedListener listener = new ISelectionChangedListener() {
		
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			setEnabled(!event.getSelection().isEmpty());
		}
	};
	
	public RemoveAddressListAction(FormEditor editor, TableViewer viewer, String text, ImageDescriptor imageDescriptor) {
		super(text, imageDescriptor);
		this.editor = (AddressFormEditor) editor;
		this.viewer = viewer;
		setEnabled(false);
		viewer.addSelectionChangedListener(listener);
	}
	
	@Override
	public void run() {
		AddressListManager manager = ((SimpleFormEditorInput)editor.getEditorInput()).getManager();
		ISelection sel = viewer.getSelection();
		Table table = viewer.getTable();
		table.setRedraw(false);
		
		try {
			AddressList list = (AddressList)((IStructuredSelection)sel).getFirstElement();
			manager.remove(list, true);
		} finally {
			//¸üÐÂÏÔÊ¾
			table.setRedraw(true);
			viewer.refresh();
		}
	}
}
