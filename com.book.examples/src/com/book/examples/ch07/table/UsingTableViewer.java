package com.book.examples.ch07.table;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.book.examples.ch07.list.ListModel;
import com.book.examples.ch07.list.User;
import org.eclipse.wb.swt.SWTResourceManager;

public class UsingTableViewer {

	protected Shell shell;
	private Table table;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UsingTableViewer window = new UsingTableViewer();
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
		shell.setSize(939, 546);
		shell.setText("Table Viewer");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		table.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn colId = new TableColumn(table, SWT.NONE);
		colId.setWidth(100);
		colId.setText("ID");
		
		TableColumn colName = new TableColumn(table, SWT.NONE);
		colName.setWidth(100);
		colName.setText("NAME");
		
		TableViewer viewer = new TableViewer(table);
		viewer.setContentProvider(new TableContentProvider());
		viewer.setLabelProvider(new TableLabelProvider());

		ListModel input = new ListModel();
		viewer.setInput(input);
		
		input.add(new User("01", "–°–‹"));
		input.add(new User("02", "–°ÃÔ"));
		input.add(new User("03", "…¡µÁ–‹"));
	}
}
