package org.billing.geb60;

import org.billing.geb60.display.MainWindow;
import org.billing.geb60.exceptions.LoadingException;
import org.eclipse.swt.widgets.Display;

public class Starter {

	public static void main(String[] args) throws LoadingException {
		final Display display = Display.getDefault();
		new MainWindow(display);
	}
}
