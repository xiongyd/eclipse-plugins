package com.test.swt;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractExample {
	public void run() {
		Display display = new Display();
		Shell sh1 = new Shell(display);
		sh1.setText("shell example");
		sh1.setBounds(600,300,600,400);
		sh1.setLayout(new FillLayout());

		todo(sh1);
		
		sh1.open();
		
		while(!sh1.isDisposed()){
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		display.beep();
		display.dispose();
	}

	public abstract void todo(Shell shell);
}
