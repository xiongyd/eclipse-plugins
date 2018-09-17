package com.plugindev.addressbook.editors;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.plugindev.addressbook.util.ImageCache;
import com.plugindev.addressbook.util.ImageKeys;
import com.plugindev.addressbook.util.Messages;

public class MasterDetailsPage extends FormPage {

	private ScrolledPropertiesBlock block;
	
	public MasterDetailsPage(FormEditor editor) {
		
		super(editor, "masterDetail", Messages.PAGE_NAME_MASTERDETAIL);
		block = new ScrolledPropertiesBlock(this);
	}
	
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		
		form.setText(Messages.PAGE_TITLE_MASTERDETAIL);
		form.setBackgroundImage(ImageCache.getInstance().getImage(ImageKeys.getImageDescriptor(ImageKeys.IMG_FORM_BG)));
		block.createContent(managedForm);
	}
	
	public IAction getTableAction(String workbenchActionId) {
		return block.getTableAction(workbenchActionId);
	}
}
