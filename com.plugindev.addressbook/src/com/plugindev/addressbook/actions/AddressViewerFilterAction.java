package com.plugindev.addressbook.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;

import com.plugindev.addressbook.views.AddressViewerCategoryFilter;

public class AddressViewerFilterAction extends Action {

	private Shell shell;
	private AddressViewerCategoryFilter filter;

	public AddressViewerFilterAction(StructuredViewer viewer, String text, ImageDescriptor imageDescriptor) {
		super(text, imageDescriptor);
		shell = viewer.getControl().getShell();
		filter = new AddressViewerCategoryFilter(viewer);
	}

	@Override
	public void run() {
		InputDialog dialog = new InputDialog(shell,
				"������", 
				"����һ��������� " + System.getProperty("line.separator") + "�����ÿ�", 
				filter.getPattern(),
				null);
		if (dialog.open() == InputDialog.OK) {
			filter.setPattern(dialog.getValue().trim());
		}
	}
	
	//�������״̬
	public void saveState(IMemento memento){
		filter.saveState(memento);
	}
	
	//��ñ���Ĺ���״̬
	public void init(IMemento memento){
		filter.init(memento);
	}
}
