package org.billing.geb60.display;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class GameWindowLayoutWindow {
	
	public GameWindowLayoutWindow(Display display, final GameWindow gW) {		
		final Shell gameWindowLayouter = new Shell(display, SWT.CLOSE | SWT.ON_TOP);
		gameWindowLayouter.forceActive();
		gameWindowLayouter.setActive();
		gameWindowLayouter.forceActive();
		gameWindowLayouter.setActive();
		
		GridLayout layout = new GridLayout(1, false);
		gameWindowLayouter.setLayout(layout);
		
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		
		final Text fontText = new Text(gameWindowLayouter, SWT.NONE);
		fontText.setText("" + gW.getCurrentSize());
		fontText.setLayoutData(gridData);
		
		Button fontButton = new Button(gameWindowLayouter, SWT.PUSH);
		fontButton.setText("Schriftgröße ändern!");
		
		Button tableButton = new Button(gameWindowLayouter, SWT.PUSH);
		tableButton.setText("Spalten angleichen!");
		
		Button closeButton = new Button(gameWindowLayouter, SWT.PUSH);
		closeButton.setText("Schließen");
		
		// Listeners
		Listener sizeListener = new Listener() {
			public void handleEvent(Event arg0) {
				gW.setFontSize(Integer.valueOf(fontText.getText()));
			}
		};
		fontButton.addListener(SWT.Selection, sizeListener);
		
		Listener tableListener = new Listener() {
			public void handleEvent(Event arg0) {
				gW.setTableLayout();
			}
		};
		tableButton.addListener(SWT.Selection, tableListener);
		
		Listener closeListener = new Listener() {
			public void handleEvent(Event arg0) {
				gameWindowLayouter.close();
			}
		};
		closeButton.addListener(SWT.Selection, closeListener);
		
		gameWindowLayouter.pack();
		gameWindowLayouter.open();
	}
}
