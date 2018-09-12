package com.book.examples.ch07.list;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class UsingListViewer {

	protected Shell shlListViewer;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UsingListViewer window = new UsingListViewer();
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
		shlListViewer.open();
		shlListViewer.layout();
		while (!shlListViewer.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlListViewer = new Shell();
		shlListViewer.setSize(867, 521);
		shlListViewer.setText("List Viewer");
		shlListViewer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		ListViewer viewer = new ListViewer(shlListViewer, SWT.BORDER | SWT.V_SCROLL);
		viewer.setContentProvider(new ListContentProvider());
		viewer.setLabelProvider(new ListLabelProvider());
		
		ListModel input = new ListModel();
		viewer.setInput(input);

		input.add(new User("01", "–°–‹"));
		input.add(new User("02", "–°ÃÔ"));
		input.add(new User("03", "…¡µÁ–‹"));
	}
}
