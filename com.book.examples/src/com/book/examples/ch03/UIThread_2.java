package com.book.examples.ch03;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class UIThread_2 {

	private static int clickCounts = 0;
	
	public static void main(String[] args) {
		Display disp = Display.getDefault();
		Shell shell = new Shell(disp, SWT.SHELL_TRIM);
		shell.setText("Muti Thread2");
		shell.setBounds(400,300,600, 400);
		shell.layout();
		
		Button btn = new Button(shell, SWT.NONE);
		btn.setText("button");
		btn.setBounds(20,15,150,50);
		btn.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Thread t = new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						//通知UI线程下次事件循环时执行Runnable的run方法
						disp.asyncExec(new Runnable() {
							@Override
							public void run() {
								if (!btn.isDisposed())
									btn.setText("click"+(++clickCounts));
							}
						});
						
					}
				};
				t.start();
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
