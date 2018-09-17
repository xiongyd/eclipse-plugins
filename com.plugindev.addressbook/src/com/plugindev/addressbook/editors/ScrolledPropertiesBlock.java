package com.plugindev.addressbook.editors;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.plugindev.addressbook.editors.actions.RemoveAddressListAction;
import com.plugindev.addressbook.editors.models.AreaAddressList;
import com.plugindev.addressbook.editors.models.AreaAddressListProperties;
import com.plugindev.addressbook.editors.models.BasicAddressList;
import com.plugindev.addressbook.editors.models.BasicAddressListProperties;
import com.plugindev.addressbook.editors.models.MasterContentProvider;
import com.plugindev.addressbook.editors.models.MasterLabelProvider;
import com.plugindev.addressbook.editors.models.PhoneAddressList;
import com.plugindev.addressbook.editors.models.PhoneAddressListItemProperties;
import com.plugindev.addressbook.util.ImageKeys;
import com.plugindev.addressbook.util.Messages;

public class ScrolledPropertiesBlock extends MasterDetailsBlock {

	private FormPage page;
	private TableViewer viewer;
	private Action haction;
	private Action vaction;

	private RemoveAddressListAction removeAction;
	
	public ScrolledPropertiesBlock(FormPage page) {
		this.page = page;
	}

	@Override
	protected void createMasterPart(IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		
		//描述信息列表(段)
		Section section = toolkit.createSection(parent, Section.DESCRIPTION|Section.TITLE_BAR);
		section.setText(Messages.SCROL_BLOC_NAME);
		section.setDescription(Messages.SCROL_BLOC_DESCRIP);
		section.marginWidth = 10;
		section.marginHeight = 5;
		
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		Table table = toolkit.createTable(client, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		gd.verticalSpan = 2;
		table.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		
		section.setClient(client);
		SectionPart spart =new SectionPart(section);
		managedForm.addPart(spart);
		viewer = new TableViewer(table);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				managedForm.fireSelectionChanged(spart, event.getSelection());
			}
		});
		viewer.setContentProvider(new MasterContentProvider());
		viewer.setLabelProvider(new MasterLabelProvider());
		viewer.setInput(page.getEditor().getEditorInput());
		
		createActions();
		createContextMenu();
		
		//添加 按钮
		Button addButton = toolkit.createButton(client, Messages.SCROL_BLOC_ADD, SWT.PUSH);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING);
		addButton.setLayoutData(gd);
		
		//删除 按钮
		Button deleteButton = toolkit.createButton(client, Messages.SCROL_BLOC_DELETE, SWT.PUSH);
		deleteButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeAction.run();
			}
		});
		
		viewer.getTable().addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.DEL) {
					removeAction.run();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL|GridData.VERTICAL_ALIGN_BEGINNING);
		deleteButton.setLayoutData(gd);
	}


	private void createContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {

			@Override
			public void menuAboutToShow(IMenuManager manager) {
				ScrolledPropertiesBlock.this.fillContextMenu(manager);
			}
			
		});
		
		Table table = viewer.getTable();
		Menu menu = menuMgr.createContextMenu(table);
		table.setMenu(menu);
		
		//向编辑菜单注册，以便其他插件可以在该菜单中添加操作
		page.getEditor().getSite().registerContextMenu(menuMgr, viewer);
	}

	protected void fillContextMenu(IMenuManager manager) {
		boolean isEmpty = viewer.getSelection().isEmpty();
		removeAction.setEnabled(!isEmpty);
		manager.add(removeAction);
		//指示上下文操作出现的位置
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void createActions() {
		ImageDescriptor removeImage = PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE);
		ImageDescriptor disableRemoveImage = PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED);
		
		removeAction = new RemoveAddressListAction(page.getEditor(), viewer, "删除", removeImage);
		removeAction.setDisabledImageDescriptor(disableRemoveImage);
	}

	@Override
	protected void registerPages(DetailsPart detailsPart) {
		detailsPart.registerPage(BasicAddressList.class, new BasicAddressListProperties());
		detailsPart.registerPage(AreaAddressList.class, new AreaAddressListProperties());
		detailsPart.registerPage(PhoneAddressList.class, new PhoneAddressListItemProperties());
	}

	@Override
	protected void createToolBarActions(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		haction = new Action("hor", Action.AS_RADIO_BUTTON) {
			public void run() {
				sashForm.setOrientation(SWT.HORIZONTAL);
				form.reflow(true);
			}
		};
		//默认为真，表示默认为水平布局
		haction.setChecked(true);
		haction.setToolTipText(Messages.SCROL_BLOC_HORIZONTAL);
		haction.setImageDescriptor(ImageKeys.getImageDescriptor(ImageKeys.IMG_HORIZONTAL));
		
		vaction = new Action("ver", Action.AS_RADIO_BUTTON) {
			public void run() {
				sashForm.setOrientation(SWT.VERTICAL);
				form.reflow(true);
			}
		};
		vaction.setChecked(false);
		vaction.setToolTipText(Messages.SCROL_BLOC_VERTICAL);
		vaction.setImageDescriptor(ImageKeys.getImageDescriptor(ImageKeys.IMG_VERTICAL));
	
		form.getToolBarManager().add(haction);
		form.getToolBarManager().add(vaction);
	}

	public IAction getTableAction(String workbenchActionId) {
		if ((ActionFactory.DELETE.getId().equals(workbenchActionId)))
			return removeAction;
		return null;
	}

}
