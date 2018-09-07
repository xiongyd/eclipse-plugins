package com.book.examples.ch06;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.custom.TableTree;

public class WindowBuilderTest {

	protected Shell shell;
	private Text text;
	private Table table;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			WindowBuilderTest window = new WindowBuilderTest();
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
		shell.setSize(867, 527);
		shell.setText("WindowBuilderTest");
		shell.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Group grpButton = new Group(shell, SWT.NONE);
		grpButton.setText("button");
		grpButton.setLayout(new RowLayout(SWT.HORIZONTAL));
		grpButton.setLayoutData(new RowData(699, 80));
		
		Button btn0 = new Button(grpButton, SWT.BORDER);
		btn0.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btn0.setText("btn1");
		
		Button btn1 = new Button(grpButton, SWT.BORDER | SWT.RADIO);
		btn1.setText("btn2");
		
		Button btnNewButton = new Button(grpButton, SWT.BORDER | SWT.CHECK);
		btnNewButton.setText("New Button");
		
		Button btnNewButton_1 = new Button(grpButton, SWT.BORDER | SWT.TOGGLE);
		btnNewButton_1.setText("New Button");
		
		Button btnNewButton_2 = new Button(grpButton, SWT.BORDER | SWT.ARROW);
		btnNewButton_2.setText("New Button");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setText("New Label");
		
		text = new Text(shell, SWT.BORDER);
		
		Combo combo = new Combo(shell, SWT.NONE);
		
		DateTime dateTime = new DateTime(shell, SWT.BORDER);
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("New Column");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("New Column");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("New Column");
		
		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText("New TableItem");
		
		TableItem tableItem_1 = new TableItem(table, SWT.NONE);
		tableItem_1.setText("New TableItem");
		
		Tree tree_1 = new Tree(shell, SWT.BORDER);
		
		Tree tree = new Tree(shell, SWT.BORDER);
		
		TreeColumn trclmnNewColumn_1 = new TreeColumn(tree, SWT.NONE);
		trclmnNewColumn_1.setWidth(100);
		trclmnNewColumn_1.setText("New Column");
		
		TreeColumn trclmnNewColumn = new TreeColumn(tree, SWT.NONE);
		trclmnNewColumn.setWidth(100);
		trclmnNewColumn.setText("New Column");
		
		List list = new List(shell, SWT.BORDER);
		
		TableTree tableTree = new TableTree(shell, SWT.BORDER | SWT.FULL_SELECTION);

	}
}
