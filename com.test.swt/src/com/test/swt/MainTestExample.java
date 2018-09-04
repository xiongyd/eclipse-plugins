package com.test.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class MainTestExample {

	public static void main(String[] args) {
		Display display = new Display();
		Color color = new Color(display,255,0,0);
		
		Shell sh1 = new Shell(display);
		sh1.setText("shell 1标题");
		sh1.setBounds(100,100,400,200);
		sh1.setLayout(new FillLayout());
		
		Label lbl1 = new Label(sh1, SWT.CENTER);
		lbl1.setText("This is the text of a lable1");
		lbl1.setForeground(color);
		
		sh1.open();
		
		Shell sh2 = new Shell(display);
		sh2.setText("shell 2标题");
		sh2.setBounds(250,250,400,200);
		sh2.setLayout(new FillLayout());
		
		Label lbl2 = new Label(sh2, SWT.CENTER);
		lbl2.setText("This is the text of a lable2");
		lbl2.setForeground(color);
		sh2.open();
		
		while(!sh1.isDisposed() || !sh2.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		display.beep();
		color.dispose();
		display.dispose();
	}
}
