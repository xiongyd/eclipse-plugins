package com.plugindev.addressbook.actions.example;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

public class EditorExampleAction implements IEditorActionDelegate {

	private IEditorPart targetEditor;
	
	@Override
	public void run(IAction action) {
		MessageDialog.openInformation(targetEditor.getSite().getShell(), "消息", "顶层编辑器操作被执行");

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.targetEditor = targetEditor;

	}

}
