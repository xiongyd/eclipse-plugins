package com.book.examples.ch03;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ColoredLabel {
	
	public static void main(String[] args) {
		Display disp = Display.getDefault();
		Shell shell = new Shell(disp);
		shell.setText("ColoredLabel");
		shell.setBounds(400,300,600, 400);
		
		Button btn = new Button(shell, SWT.NONE);
		btn.setText("ColoredLabel");
		btn.setBounds(20,15,150,50);
		
		Color createdWhite = new Color(disp, 255,255,255);
		Color systemBlack = disp.getSystemColor(SWT.COLOR_BLACK);
		
		btn.setForeground(createdWhite);
		btn.setBackground(systemBlack);
		
		shell.open();
		
		while(!shell.isDisposed()) {
			if (!disp.readAndDispatch()) {
				disp.sleep();
			}
		}
		
		createdWhite.dispose();
		disp.dispose();

	}

}
