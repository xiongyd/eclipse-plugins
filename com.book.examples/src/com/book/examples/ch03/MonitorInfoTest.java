package com.book.examples.ch03;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;

public class MonitorInfoTest {

	public static void main(String[] args) {
		Display disp = Display.getDefault();
		Monitor monitor = disp.getPrimaryMonitor();

		System.out.println("client area: " + monitor.getClientArea());
		System.out.println("bounds: " + monitor.getBounds());
	}

}
