package com.plugindev.addressbook.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.plugindev.addressbook.editors.models.AddressList;
import com.plugindev.addressbook.editors.models.AddressListManager;
import com.plugindev.addressbook.editors.models.SimpleFormEditorInput;
import com.plugindev.addressbook.models.AddressItem;
import com.plugindev.addressbook.models.AddressManager;

public class NewAddressItemWizard extends Wizard implements INewWizard {

	private NewAddressItemWizardPage newAddressItemPage;
	private EditListsConfigWizardPage editListsConfigPage;

	public NewAddressItemWizard() {
		setWindowTitle("新建地址项");
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPages() {
		newAddressItemPage = new NewAddressItemWizardPage();
		addPage(newAddressItemPage);

		editListsConfigPage = new EditListsConfigWizardPage();
		addPage(editListsConfigPage);
	}

	@Override
	public boolean performFinish() {
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					doFinish(monitor);
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			return false;
		}

		return true;
	}

	private void doFinish(IProgressMonitor monitor) {

		/* AddressList[] lists = editListsConfigPage.getSelection(); */

		// 下面做相应的增加地址项和地址信息列表的操作

		Display display = null;
		Shell shell = getShell();
		if (shell == null)
			display = PlatformUI.getWorkbench().getDisplay();
		else
			display = shell.getDisplay();
		display.asyncExec(new Runnable() {
			public void run() {
				AddressItem item = null;
				SimpleFormEditorInput input = null;
				
				AddressManager manager = AddressManager.getManager();
				if (getContainer().getCurrentPage() == newAddressItemPage) {
					input = new SimpleFormEditorInput(newAddressItemPage.getSelectedName());
					item = new AddressItem(newAddressItemPage.getSelectedName(),
							newAddressItemPage.getSelectedAddressCategory());

				} else if (getContainer().getCurrentPage() == editListsConfigPage) {
					input = editListsConfigPage.getEditorInput();
					AddressList[] lists = editListsConfigPage.getSelection();
					item = editListsConfigPage.getAddressItem();

					AddressListManager listManager = input.getManager();
					listManager.removeAll();
					for (int i = 0; i < lists.length; i++) {
						listManager.add(lists[i], false);
					}
				}
				
				manager.addAddresses(new AddressItem[] { item });
				try {
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					page.openEditor(input, "com.plugindev.addressbook.tableEditor");
					input.getManager().saveDescriptions();
				} catch (PartInitException e) {
					System.out.println(e);
				}
			}

		});

	}

}
