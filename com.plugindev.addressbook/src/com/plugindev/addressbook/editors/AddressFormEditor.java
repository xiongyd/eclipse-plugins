package com.plugindev.addressbook.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import com.plugindev.addressbook.editors.models.IManagerListener;
import com.plugindev.addressbook.editors.models.SimpleFormEditorInput;

public class AddressFormEditor extends FormEditor implements IManagerListener{

	//13.8.1 ���޸ĵı༭�� BEGIN
	private boolean isPageModified; //�û��Ƿ��޸��˱༭��������
	//13.8.1 ���޸ĵı༭�� END
	
	/*
	 * ��13.7��ʵ����managerChanged()����
	 * @see com.plugindev.addressbook.editors.models.IManagerListener#managerChanged(java.lang.Object[], java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void managerChanged(Object object, String type, String itemType, String key) {
		if(isPageModified == false){
			isPageModified = true;
			editorDirtyStateChanged(); //���ı༭��״̬���䡰�ѱ��桱״̬Ϊ���ࡱ״̬
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
				page.updateSelection(); //13.8.2 �л�ҳ��
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
			 * Ϊ�༭����������༭��ҳ��
			 */
			addPage(new MasterDetailsPage(this));
			addPage(new PageWithSubPages(this));
			addPage(new SourcePage(this));
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
		updateTitle();
		
		////13.8.1 ���޸ĵı༭�� - ��AddressListManagerע���Լ�
		((SimpleFormEditorInput)getEditorInput()).getManager().addManagerListener(this);
	}

	
	//13.8.1 ���޸ĵı༭�� - �༭���������޸�
	@Override
	public boolean isDirty() {
		return isPageModified||super.isDirty();
	}
	/*
	 * ���±༭�����⣺���а����������ƣ����⸡����ʾ���Ƶ�
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
