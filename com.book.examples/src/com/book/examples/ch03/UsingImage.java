package com.book.examples.ch03;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class UsingImage {
	
	public static void main(String[] args) {
		Display disp = Display.getDefault();
		Shell shell = new Shell(disp);
		shell.setText("UsingImage");
		shell.setBounds(400,300,600, 400);
		
		Image iconImage = disp.getSystemImage(SWT.ICON_QUESTION);
		shell.setImage(iconImage);
		
		Button btn = new Button(shell, SWT.NONE);
		btn.setText("Image Button");
		btn.setBounds(20,15,150,50);
		Image buttonImage = new Image(disp, "icons\\gl.jpg");
		btn.setImage(buttonImage);
		
		shell.open();
		
		while(!shell.isDisposed()) {
			if (!disp.readAndDispatch()) {
				disp.sleep();
			}
		}
		
		buttonImage.dispose();
		disp.dispose();

	}

}
