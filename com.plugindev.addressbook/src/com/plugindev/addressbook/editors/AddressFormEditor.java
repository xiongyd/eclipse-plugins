package com.plugindev.addressbook.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import com.plugindev.addressbook.editors.models.IManagerListener;
import com.plugindev.addressbook.editors.models.SimpleFormEditorInput;

public class AddressFormEditor extends FormEditor implements IManagerListener{

	//13.8.1 已修改的编辑器 BEGIN
	private boolean isPageModified; //用户是否修改了编辑器的内容
	//13.8.1 已修改的编辑器 END
	
	/*
	 * 在13.7节实现了managerChanged()方法
	 * @see com.plugindev.addressbook.editors.models.IManagerListener#managerChanged(java.lang.Object[], java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void managerChanged(Object object, String type, String itemType, String key) {
		if(isPageModified == false){
			isPageModified = true;
			editorDirtyStateChanged(); //更改编辑器状态，变“已保存”状态为“脏”状态
		}
	}
	
	@Override
	protected void pageChange(int newPageIndex) {
		switch(newPageIndex) {
		case 0:
			break;
		case 1:
			if (isPageModified) {
				PageWithSubPages page = (PageWithSubPages) getSelectedPage();
				page.updateSelection(); //13.8.2 切换页面
			}
			break;
		}
		
		super.pageChange(newPageIndex);
		
		IEditorActionBarContributor contributor = getEditorSite().getActionBarContributor();
		if (contributor instanceof AddressFormEditorContributor) {
			((AddressFormEditorContributor)contributor).setActivePage(this, newPageIndex);
		}
	}
	
	@Override
	protected void addPages() {
		
		try {
			/*
			 * 为编辑器添加三个编辑器页面
			 */
			addPage(new MasterDetailsPage(this));
			addPage(new PageWithSubPages(this));
			addPage(new SourcePage(this));
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
		updateTitle();
		
		////13.8.1 已修改的编辑器 - 向AddressListManager注册自己
		((SimpleFormEditorInput)getEditorInput()).getManager().addManagerListener(this);
	}

	
	//13.8.1 已修改的编辑器 - 编辑器内容已修改
	@Override
	public boolean isDirty() {
		return isPageModified||super.isDirty();
	}
	/*
	 * 更新编辑器标题：其中包括标题名称，标题浮动提示名称等
	 */
	private void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void dispose() {
		((SimpleFormEditorInput)getEditorInput()).getManager().saveDescriptions();
	}
}
