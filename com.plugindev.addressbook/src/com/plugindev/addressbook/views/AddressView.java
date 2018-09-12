package com.plugindev.addressbook.views;

import java.util.Comparator;
import java.util.Iterator;

import javax.inject.Inject;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import com.plugindev.addressbook.Activator;
import com.plugindev.addressbook.actions.AddAddressAction;
import com.plugindev.addressbook.actions.AddressViewerFilterAction;
import com.plugindev.addressbook.actions.DeleteAddressAction;
import com.plugindev.addressbook.models.AddressItem;
import com.plugindev.addressbook.models.AddressManager;
import com.plugindev.addressbook.util.ImageKeys;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class AddressView extends ViewPart implements ISelectionListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.plugindev.addressbook.views.AddressView";

	@Inject
	IWorkbench workbench;

	private TableViewer viewer;
	/*
	 * private Action action1; private Action action2;
	 */
	private Action doubleClickAction;

	// ɾ������
	private DeleteAddressAction deleteAction;
	// ��Ӳ���
	private AddAddressAction addAction;
	// ���˲���
	private AddressViewerFilterAction filterAction;

	private AddressViewerSorter sorter;

	private IMemento memento;

	private TableColumn nameColumn;

	private TableColumn categoryColumn;

	@Override
	public void createPartControl(Composite parent) {

		// ����TableViewer
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

		// ���ñ��Ľ���
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		nameColumn = new TableColumn(table, SWT.LEFT);
		nameColumn.setText("����");
		nameColumn.setWidth(100);
		nameColumn.setImage(Activator.getImageDescriptor(ImageKeys.IMAGE_PEOPLE).createImage());
		categoryColumn = new TableColumn(table, SWT.LEFT);
		categoryColumn.setText("���");
		categoryColumn.setWidth(100);
		categoryColumn.setImage(Activator.getImageDescriptor(ImageKeys.IMAGE_CATEGORY).createImage());

		// ��ʼ��TableViewer
		viewer.setContentProvider(new AddressViewContentProvider());
		viewer.setLabelProvider(new AddressViewLabelProvider());
		viewer.setInput(AddressManager.getManager());

		// ��ӵײ�״̬��֧��
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = event.getStructuredSelection(); // ��õ�ǰվ���״̬��������

				IStatusLineManager statusLine = getViewSite().getActionBars().getStatusLineManager();
				Object obj = sel.getFirstElement();
				if (obj == null)
					return;

				if (obj instanceof AddressItem) {
					AddressItem item = (AddressItem) obj;
					statusLine.setMessage(item.getCategory().getImage(),
							item.getName() + " : " + item.getCategory().getCategoryName());
				} else {
					statusLine.setMessage(obj.toString());
				}
			}
		});

		// ���������
		createTableSorter();

		// Create the help context id for the viewer's control
		workbench.getHelpSystem().setHelp(viewer.getControl(), "com.plugindev.addressbook.viewer");

		// Ϊ��ͼ�����Ϊ
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		hookKeyboardActions();

		// �����ַ����ͼ�鿴���е����ݸ�������ͼ(������˼�����)
		getViewSite().setSelectionProvider(viewer);

		// ��������Workbench���ֱ�ѡ�е�����(�Ҽ�������)
		getSite().getPage().addSelectionListener(this);
	}

	private void createTableSorter() {
		Comparator nameComparator = new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((AddressItem) o1).getName().compareTo(((AddressItem) o2).getName());
			}
		};
		Comparator categoryComparator = new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((AddressItem) o1).getCategory().compareTo(((AddressItem) o2).getCategory());
			}
		};
		sorter = new AddressViewerSorter(viewer, new TableColumn[] { nameColumn, categoryColumn },
				new Comparator[] { nameComparator, categoryComparator });
		if (memento != null) {
			sorter.init(memento);
		}
		viewer.setSorter(sorter);
	}

	private void hookKeyboardActions() {
		viewer.getControl().addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				handleKeyReleased(e);
			}
		});
	}

	protected void handleKeyReleased(KeyEvent e) {
		if (e.character == SWT.DEL && e.stateMask == 0) {
			deleteAction.run();
		}
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				AddressView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);

		// ��ս����ע�������Ĳ˵�
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		/*
		 * manager.add(action1); manager.add(new Separator()); manager.add(action2);
		 */

		// Ϊ�־û�UI״̬�ṩ֧��
		if (memento != null)
			filterAction.init(memento);

		manager.add(filterAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		/*
		 * manager.add(action1); manager.add(action2);
		 */
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

		// ��deleteAction��䵽�����Ĳ˵���
		manager.add(deleteAction);
		manager.add(addAction);
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		/*
		 * manager.add(action1); manager.add(action2);
		 */

		// ��deleteAction��䵽��ͼ��������
		manager.add(deleteAction);

		deleteAction.setEnabled(false);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				// �ж����������޵�ַԪ����ʱ����
				deleteAction.setEnabled(!event.getSelection().isEmpty());
			}
		});

		manager.add(addAction);
	}

	private void makeActions() {
		/*
		 * action1 = new Action() { public void run() {
		 * showMessage("Action 1 executed"); } }; action1.setText("Action 1");
		 * action1.setToolTipText("Action 1 tooltip");
		 * action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
		 * getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		 * 
		 * action2 = new Action() { public void run() {
		 * showMessage("Action 2 executed"); } }; action2.setText("Action 2");
		 * action2.setToolTipText("Action 2 tooltip");
		 * action2.setImageDescriptor(workbench.getSharedImages().
		 * getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		 */
		doubleClickAction = new Action() {
			public void run() {
				IStructuredSelection selection = viewer.getStructuredSelection();
				Object obj = selection.getFirstElement();
				showMessage("Double-click detected on " + obj.toString());
			}
		};

		// ���ӵ�ɾ������
		ImageDescriptor deleteImage = ImageKeys.getImageDescriptor(ImageKeys.IMG_TOOL_DELETE);
		deleteAction = new DeleteAddressAction(this, "ɾ��", deleteImage);
		deleteAction.setDisabledImageDescriptor(ImageKeys.getImageDescriptor(ImageKeys.IMG_TOOL_DISABLEDELETE));

		// ������Ӳ���
		ImageDescriptor addImage = ImageKeys.getImageDescriptor(ImageKeys.IMG_TOOL_ADD);
		addAction = new AddAddressAction(this, "���", addImage);

		// ������Ӳ���
		ImageDescriptor filterImage = ImageKeys.getImageDescriptor(ImageKeys.IMG_TOOL_FILTER);
		filterAction = new AddressViewerFilterAction(viewer, "����...", filterImage);
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "��ַ����ͼ", message);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	// ��ͼѡ��
	public AddressItem[] getSelectedAddresses() {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();

		AddressItem[] items = new AddressItem[selection.size()];
		Iterator<?> iter = selection.iterator();
		int index = 0;
		while (iter.hasNext()) {
			items[index++] = (AddressItem) iter.next();
		}

		return items;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		this.memento = memento;
	}

	@Override
	public void saveState(IMemento memento) {
		super.saveState(memento);
		this.sorter.saveState(memento);
	}
}
