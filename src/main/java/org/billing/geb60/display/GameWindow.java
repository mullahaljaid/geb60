package org.billing.geb60.display;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.billing.geb60.bo.Game;
import org.billing.geb60.display.helpers.EmptySpace;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public class GameWindow {

	private Log _log = LogFactory.getLog(getClass());
	
	private final Shell shell;
	
	private final Label questionLabel;
	private final Table answerTable;
	private final Label pointsWernerLabel;
	private final Label pointsGerdaLabel;
	
	private boolean closable = false;
	
	private Font font;
	
	public GameWindow(final Display main, final Game game, final Shell mainShell) {
		_log.info("Creating game window!");
		shell = new Shell(main, SWT.MIN | SWT.MAX | SWT.RESIZE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 5;
		shell.setLayout(gridLayout);
		
		font = shell.getFont();
		
		questionLabel = new Label(shell, SWT.WRAP | SWT.CENTER);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 5;
		questionLabel.setLayoutData(gridData);
		questionLabel.setText("\n\n ");
		
		// TODO Answertable
		answerTable = new Table(shell, SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 5;
		gridData.verticalSpan = 5;
		answerTable.setLayoutData(gridData);
		
		pointsWernerLabel = new Label(shell, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, false, false);
		gridData.horizontalSpan = 1;
		pointsWernerLabel.setLayoutData(gridData);
		pointsWernerLabel.setText("0     ");
		
		new EmptySpace(shell, 3);
		
		pointsGerdaLabel = new Label(shell, SWT.SINGLE | SWT.BORDER | SWT.RIGHT);
		gridData = new GridData(SWT.FILL, SWT.FILL, false, false);
		gridData.horizontalSpan = 1;
		pointsGerdaLabel.setLayoutData(gridData);
		pointsGerdaLabel.setText("     0");
		
		
		// Listeners
		Listener closeListener = new Listener() {
			public void handleEvent(Event event) {
		        event.doit = closable;
			}
		};
		shell.addListener(SWT.Close, closeListener);
		
		setFontSize(35);
		
		shell.pack();
		shell.open();
	}
	
	public Shell getShell() {
		return shell;
	}
	
	public Table getAnswerTable() {
		return answerTable;
	}
	
	public Label getQuestionLabel() {
		return questionLabel;
	}
	
	public Label getPointsWernerLabel() {
		return pointsWernerLabel;
	}
	
	public Label getPointsGerdaLabel() {
		return pointsGerdaLabel;
	}
	
	public void resize() {
		int y = shell.getSize().y;
		setFontSize(y / 20);
		shell.pack();
	}
	
	private void setFontSize(int size) {
		FontData fd = font.getFontData()[0];
		fd.setHeight(size);
		font = new Font(Display.getCurrent(), fd);
		questionLabel.setFont(font);
		answerTable.setFont(font);
		pointsGerdaLabel.setFont(font);
		pointsWernerLabel.setFont(font);
	}
	
	public void closeable() {
		this.closable = true;
	}
}
