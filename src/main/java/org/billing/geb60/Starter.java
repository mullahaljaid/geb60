package org.billing.geb60;

import org.billing.geb60.display.MainWindow;
import org.billing.geb60.exceptions.LoadingException;
import org.eclipse.swt.widgets.Display;

public class Starter {

	private static MainWindow mW = null;
	
	public static void main(String[] args) throws LoadingException {
		final Display display = Display.getDefault();
		mW = new MainWindow(display);
	}
	
	public static MainWindow getMainWindow() {
		return mW;
	}
}
