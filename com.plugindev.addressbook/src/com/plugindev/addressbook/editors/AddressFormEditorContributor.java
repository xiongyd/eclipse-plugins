package com.plugindev.addressbook.editors;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.LabelRetargetAction;
import org.eclipse.ui.part.EditorActionBarContributor;

public class AddressFormEditorContributor extends EditorActionBarContributor {

	private static final String[] WORKBENCH_ACTION_IDS = {ActionFactory.DELETE.getId()};
	private LabelRetargetAction retargetRemoveAction;
	
	public AddressFormEditorContributor() {
		retargetRemoveAction = new LabelRetargetAction(ActionFactory.DELETE.getId(), "删除");
	}
	
	//创建重定向操作
	@Override
	public void init(IActionBars bars, IWorkbenchPage page) {
		super.init(bars, page);
		page.addPartListener(retargetRemoveAction);
	}
	
	//添加到顶层菜单
	@Override
	public void contributeToMenu(IMenuManager menuManager) {
		IMenuManager menuMgr = new MenuManager("地址编辑器");
		menuManager.prependToGroup(IWorkbenchActionConstants.MB_ADDITIONS, menuMgr);
		menuMgr.add(retargetRemoveAction);
	}
	
	//添加到工具栏
	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		toolBarManager.add(new Separator());
		toolBarManager.add(retargetRemoveAction);
	}
	
	@Override
	public void setActiveEditor(IEditorPart targetEditor) {
		AddressFormEditor  editor = (AddressFormEditor)targetEditor;
		setActivePage(editor, editor.getActivePage());
	}

	public void setActivePage(AddressFormEditor editor, int pageIndex) {
		IActionBars actionBars = getActionBars();
		if (actionBars != null) {
			switch(pageIndex) {
			case 0:
				MasterDetailsPage page = (MasterDetailsPage) editor.getSelectedPage();
				if (page != null) {
					hookGlobalTableActions(page, actionBars);
				}
				break;
			}
		}
		actionBars.updateActionBars();
	}

	private void hookGlobalTableActions(MasterDetailsPage page, IActionBars actionBars) {
		for (int i=0;i<WORKBENCH_ACTION_IDS.length;i++) {
			actionBars.setGlobalActionHandler(WORKBENCH_ACTION_IDS[i], page.getTableAction(WORKBENCH_ACTION_IDS[i]));
		}
	}		
}
