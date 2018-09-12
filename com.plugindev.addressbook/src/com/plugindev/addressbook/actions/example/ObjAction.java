package com.plugindev.addressbook.actions.example;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class ObjAction implements IObjectActionDelegate {

	private IWorkbenchPart targetPart;
	
	@Override
	public void run(IAction action) {
		MessageDialog.openInformation(targetPart.getSite().getShell(), "消息", "对象示例操作被执行");

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;

	}

}
