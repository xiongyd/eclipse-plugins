package com.book.examples.ch06;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class WindowBuilderTest {

	protected Shell shell;

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
		
		Button btn0 = new Button(shell, SWT.BORDER);
		btn0.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btn0.setText("btn1");
		
		Button btn1 = new Button(shell, SWT.BORDER | SWT.RADIO);
		btn1.setText("btn2");
		
		Button btnNewButton = new Button(shell, SWT.BORDER | SWT.CHECK);
		btnNewButton.setText("New Button");
		
		Button btnNewButton_1 = new Button(shell, SWT.BORDER | SWT.TOGGLE);
		btnNewButton_1.setText("New Button");
		
		Button btnNewButton_2 = new Button(shell, SWT.BORDER | SWT.ARROW);
		btnNewButton_2.setText("New Button");

	}
}
