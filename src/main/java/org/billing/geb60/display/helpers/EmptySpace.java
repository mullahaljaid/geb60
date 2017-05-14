package org.billing.geb60.display.helpers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class EmptySpace {

	public EmptySpace(final Shell shell) {
		final Label emptyLabel = new Label(shell, SWT.NONE);
		emptyLabel.setText("");
	}
	
	public EmptySpace(final Shell shell, final GridData gridData) {
		final Label emptyLabel = new Label(shell, SWT.NONE);
		emptyLabel.setText("");
		emptyLabel.setLayoutData(gridData);
	}
}
