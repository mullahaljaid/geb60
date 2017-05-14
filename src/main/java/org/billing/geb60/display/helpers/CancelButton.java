package org.billing.geb60.display.helpers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class CancelButton {

	public CancelButton(final Shell shell) {
		final Button cancelButton = new Button(shell, SWT.PUSH);
		cancelButton.setText("Abbrechen");
		cancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event arg0) {
				shell.close();
			}
		});
	}
}
