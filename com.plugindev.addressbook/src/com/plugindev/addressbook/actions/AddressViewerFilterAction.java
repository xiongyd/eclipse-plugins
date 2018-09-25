package com.plugindev.addressbook.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;

import com.plugindev.addressbook.dialogs.AddressViewFilterDialog;
import com.plugindev.addressbook.views.AddressViewerCategoryFilter;
import com.plugindev.addressbook.views.AddressViewerNameFilter;

public class AddressViewerFilterAction extends Action {

	private Shell shell;
	private AddressViewerCategoryFilter filter;
	private AddressViewerNameFilter nameFilter;

	public AddressViewerFilterAction(StructuredViewer viewer, String text, ImageDescriptor imageDescriptor) {
		super(text, imageDescriptor);
		shell = viewer.getControl().getShell();
		filter = new AddressViewerCategoryFilter(viewer);
		nameFilter = new AddressViewerNameFilter(viewer);
	}

	@Override
	public void run() {
//		InputDialog dialog = new InputDialog(shell,
//				"������", 
//				"����һ��������� " + System.getProperty("line.separator") + "�����ÿ�", 
//				filter.getPattern(),
//				null);
//		if (dialog.open() == InputDialog.OK) {
//			filter.setPattern(dialog.getValue().trim());
//		}
		AddressViewFilterDialog dialog = new AddressViewFilterDialog(shell, nameFilter.getPattern(), filter.getCategories());
		if (dialog.open() != AddressViewFilterDialog.OK) {
			return;
		}
		
		nameFilter.setPattern(dialog.getNamePattern());
		filter.setCategories(dialog.getSelectedCategories());
	}
	
	//�������״̬
	public void saveState(IMemento memento){
		filter.saveState(memento);
		nameFilter.saveState(memento);
	}
	
	//��ñ���Ĺ���״̬
	public void init(IMemento memento){
		filter.init(memento);
		nameFilter.init(memento);
	}
}
