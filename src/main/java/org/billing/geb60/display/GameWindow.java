package org.billing.geb60.display;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.billing.geb60.bo.Game;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class GameWindow {

	private Log _log = LogFactory.getLog(getClass());
	
	private final Shell shell;
	
	public GameWindow(final Shell main, final Game game) {
		_log.info("Creating game window!");
		shell = new Shell(main, SWT.MIN | SWT.MAX);
		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout);
		
		
		
		final Label test1 = new Label(shell, SWT.NONE);
		test1.setText("testetstetstetstestetst");
		
		final Button b = new Button(shell, SWT.PUSH);
		b.setText("aaa");
		
		shell.pack();
		shell.open();
	}
	
	public Shell getShell() {
		return shell;
	}
}
