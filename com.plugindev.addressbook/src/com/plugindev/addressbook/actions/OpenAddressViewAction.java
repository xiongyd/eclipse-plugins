package com.plugindev.addressbook.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;

import com.plugindev.addressbook.views.AddressView;

public class OpenAddressViewAction implements IWorkbenchWindowActionDelegate{

	private IWorkbenchWindow window=null;
	
	@Override
	public void run(IAction action) {
		if (this.window == null)
			return;
		
		IWorkbenchPage page = window.getActivePage();
		try {
			page.showView(AddressView.ID);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MessageDialog.openError(window.getShell(), "��ַ������", "�򲻿���ַ����ͼ!");
		}
//		Display display=Display.getCurrent();
//        Shell shell=new Shell(display);
//        MessageDialog.openInformation(
//                shell,
//                "PlungInClient",
//                "����ActionSetģʽʵ�ֵġ�");
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

}
