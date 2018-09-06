package com.book.examples.ch03;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

public class UsingSystemTray {
	
	public static void main(String[] args) {
		Display disp = Display.getDefault();
		Shell shell = new Shell(disp);
		shell.setText("UsingSystemTray");
		shell.setBounds(400,300,600, 400);
		shell.open();
		
		Tray systemTray = disp.getSystemTray();
		TrayItem ti = new TrayItem(systemTray, SWT.NONE);
		ti.setImage(disp.getSystemImage(SWT.ICON_ERROR));
		ti.setToolTipText("Test Tray!");

		
		Menu menu = new Menu(shell, SWT.POP_UP);
		MenuItem mi1 = new MenuItem(menu, SWT.PUSH);
		mi1.setText("Menu Item 1");
		MenuItem mi2 = new MenuItem(menu, SWT.PUSH);
		mi2.setText("Menu Item 2");
		
		ti.addListener(SWT.MenuDetect, new Listener() {

			@Override
			public void handleEvent(Event event) {
				menu.setVisible(true);
			}
			
		});
		while(!shell.isDisposed()) {
			if (!disp.readAndDispatch()) {
				disp.sleep();
			}
		}
		
		ti.dispose();
		disp.dispose();

	}

}
