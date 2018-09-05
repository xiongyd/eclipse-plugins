package com.test.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class TabTreeTableTest extends AbstractExample {

	private Table table;
	private Tree tree;
	
	@Override
	public void todo(Shell shell) {
		TabFolder tab = new TabFolder(shell, SWT.BORDER);
		
		TabItem ti1 = new TabItem(tab, SWT.NONE);
		ti1.setText("第一页");
		
		Composite c1 = new Composite(tab, SWT.NONE);
		ti1.setControl(c1);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		c1.setLayout(layout);
		
		Group treeGroup = new Group(c1, SWT.NONE);
		treeGroup.setText("Tree");
		
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 50;
		treeGroup.setLayoutData(gridData);
		treeGroup.setLayout(new GridLayout(1,false));
		
		{
			tree = new Tree(treeGroup, SWT.SINGLE);
			tree.setLayoutData(new GridData(GridData.FILL_BOTH));
			
			TreeItem stu1 = new TreeItem(tree,SWT.NONE);
			stu1.setText("闪电熊");
			{
				TreeItem info1 = new TreeItem(stu1,SWT.NONE);
				info1.setText("age:25");
				
				TreeItem info2 = new TreeItem(stu1,SWT.NONE);
				info2.setText("tel:12345");
			}
			
			TreeItem stu2 = new TreeItem(tree,SWT.NONE);
			stu2.setText("田壮壮");
			{
				TreeItem info1 = new TreeItem(stu2,SWT.NONE);
				info1.setText("age:18");
				
				TreeItem info2 = new TreeItem(stu2,SWT.NONE);
				info2.setText("tel:123321");
			}
			
			tree.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					//table.removeAll();
					TableItem item = new TableItem(table,SWT.NONE);
					item.setText(
							new String[] {
								tree.getSelection()[0].toString(),
								tree.getSelection()[0].getText()
							}
						);
				}
			});
		}
		
		Group tableGroup = new Group(c1, SWT.NONE);
		tableGroup.setText("Table");
		
		GridData gridData2 = new GridData(GridData.FILL_BOTH);
		gridData2.heightHint = 20;
		tableGroup.setLayoutData(gridData2);
		tableGroup.setLayout(new GridLayout(1,false));
		{
			table = new Table(tableGroup, SWT.SINGLE|SWT.BORDER|SWT.FULL_SELECTION);
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			table.setLayoutData(new GridData(GridData.FILL_BOTH));
			
			TableColumn col1 = new TableColumn(table, SWT.NULL);
			col1.setText("Tree Item");
			col1.pack();
			col1.setWidth(200);
			
			TableColumn col2 = new TableColumn(table, SWT.NULL);
			col2.setText("Parent");
			col2.pack();
			col2.setWidth(200);
		}
		
		TabItem ti2 = new TabItem(tab, SWT.NONE);
		ti2.setText("第二页");
		
		TabItem ti3 = new TabItem(tab, SWT.NONE);
		ti3.setText("第三页");

	}

	public static void main(String[] args) {
		new TabTreeTableTest().run();
	}

}
