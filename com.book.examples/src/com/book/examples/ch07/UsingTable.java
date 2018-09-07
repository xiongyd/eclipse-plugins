package com.book.examples.ch07;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class UsingTable {

	protected Shell shell;
	private Table table;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UsingTable window = new UsingTable();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(761, 536);
		shell.setText("Table");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		table = new Table(shell, SWT.BORDER | SWT.CHECK);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(220);
		tblclmnNewColumn.setText("col1");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("col2");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("col3");
		
		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tableItem.setBackground(0,SWTResourceManager.getColor(SWT.COLOR_GREEN));
		tableItem.setText(new String[] {"a", "b", "c"});
		
		TableItem tableItem_1 = new TableItem(table, SWT.NONE);
		tableItem_1.setText(new String[] {"aaaaa", "bbbb", "ccccc"});
		
		TableItem tableItem_2 = new TableItem(table, SWT.NONE);
		tableItem_2.setText(new String[] {"fsafsd", "", "fasdfads"});
	}

}
