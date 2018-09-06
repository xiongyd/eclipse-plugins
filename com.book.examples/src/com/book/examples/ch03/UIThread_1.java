package com.book.examples.ch03;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class UIThread_1 {

	private static int clickCounts = 0;
	
	public static void main(String[] args) {
		Display disp = Display.getDefault();
		Shell shell = new Shell(disp, SWT.SHELL_TRIM);
		shell.setText("Muti Thread");
		shell.setBounds(400,300,600, 400);
		shell.layout();
		
		Button btn = new Button(shell, SWT.NONE);
		btn.setText("button");
		btn.setBounds(20,15,150,50);
		btn.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				btn.setText("click"+(++clickCounts));
			}
			
		});
		
		
		Button btnDonothing = new Button(shell, SWT.NONE);
		btnDonothing.setText("donothing");
		btnDonothing.setBounds(200,15,150,50);
		
		shell.open();
		
		while(!shell.isDisposed()) {
			if (!disp.readAndDispatch()) {
				disp.sleep();
			}
		}
		
		disp.dispose();

	}

}
