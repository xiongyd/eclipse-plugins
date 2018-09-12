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

	// 删除操作
	private DeleteAddressAction deleteAction;
	// 添加操作
	private AddAddressAction addAction;
	// 过滤操作
	private AddressViewerFilterAction filterAction;

	private AddressViewerSorter sorter;

	private IMemento memento;

	private TableColumn nameColumn;

	private TableColumn categoryColumn;

	@Override
	public void createPartControl(Composite parent) {

		// 创建TableViewer
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

		// 设置表格的界面
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		nameColumn = new TableColumn(table, SWT.LEFT);
		nameColumn.setText("姓名");
		nameColumn.setWidth(100);
		nameColumn.setImage(Activator.getImageDescriptor(ImageKeys.IMAGE_PEOPLE).createImage());
		categoryColumn = new TableColumn(table, SWT.LEFT);
		categoryColumn.setText("类别");
		categoryColumn.setWidth(100);
		categoryColumn.setImage(Activator.getImageDescriptor(ImageKeys.IMAGE_CATEGORY).createImage());

		// 初始化TableViewer
		viewer.setContentProvider(new AddressViewContentProvider());
		viewer.setLabelProvider(new AddressViewLabelProvider());
		viewer.setInput(AddressManager.getManager());

		// 添加底部状态栏支持
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = event.getStructuredSelection(); // 获得当前站点的状态栏管理器

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

		// 添加排序器
		createTableSorter();

		// Create the help context id for the viewer's control
		workbench.getHelpSystem().setHelp(viewer.getControl(), "com.plugindev.addressbook.viewer");

		// 为视图添加行为
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		hookKeyboardActions();

		// 共享地址本视图查看器中的内容给其它视图(允许别人监听我)
		getViewSite().setSelectionProvider(viewer);

		// 监听其它Workbench部分被选中的内容(我监听别人)
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

		// 在战点中注册上下文菜单
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

		// 为持久化UI状态提供支持
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

		// 将deleteAction填充到上下文菜单中
		manager.add(deleteAction);
		manager.add(addAction);
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		/*
		 * manager.add(action1); manager.add(action2);
		 */

		// 将deleteAction填充到视图工具栏中
		manager.add(deleteAction);

		deleteAction.setEnabled(false);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				// 判断条件，当无地址元素项时禁用
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

		// 增加的删除操作
		ImageDescriptor deleteImage = ImageKeys.getImageDescriptor(ImageKeys.IMG_TOOL_DELETE);
		deleteAction = new DeleteAddressAction(this, "删除", deleteImage);
		deleteAction.setDisabledImageDescriptor(ImageKeys.getImageDescriptor(ImageKeys.IMG_TOOL_DISABLEDELETE));

		// 增加添加操作
		ImageDescriptor addImage = ImageKeys.getImageDescriptor(ImageKeys.IMG_TOOL_ADD);
		addAction = new AddAddressAction(this, "添加", addImage);

		// 增加添加操作
		ImageDescriptor filterImage = ImageKeys.getImageDescriptor(ImageKeys.IMG_TOOL_FILTER);
		filterAction = new AddressViewerFilterAction(viewer, "过滤...", filterImage);
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "地址本视图", message);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	// 视图选择
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
