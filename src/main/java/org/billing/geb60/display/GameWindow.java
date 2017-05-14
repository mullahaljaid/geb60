package org.billing.geb60.display;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.billing.geb60.bo.Game;
import org.billing.geb60.display.helpers.EmptySpace;
import org.billing.geb60.util.Constants;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

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
		shell.setMaximized(true);
		
		font = shell.getFont();
		
		questionLabel = new Label(shell, SWT.WRAP | SWT.CENTER);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 5;
		questionLabel.setLayoutData(gridData);
		questionLabel.setText("\n\n ");
		
		// TODO Answertable
		Composite tableComposite = new Composite(shell, SWT.NONE);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 5;
		gridData.verticalSpan = 5;
		tableComposite.setLayoutData(gridData);
		answerTable = new Table(tableComposite, SWT.BORDER);
		answerTable.setHeaderVisible(true);
		answerTable.setLinesVisible(true);
		TableColumn c1 = new TableColumn(answerTable, SWT.NONE);
		c1.setText("Antwort");
		TableColumn c2 = new TableColumn(answerTable, SWT.NONE);
		c2.setText("Punkte");
		c2.setAlignment(SWT.RIGHT);
		
		TableColumnLayout layout = new TableColumnLayout();
		layout.setColumnData(c1, new ColumnWeightData(200, 500, true));
		layout.setColumnData(c2, new ColumnWeightData(20, 50, true));
		tableComposite.setLayout(layout);
		
		pointsWernerLabel = new Label(shell, SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, false, false);
		gridData.horizontalSpan = 1;
		pointsWernerLabel.setLayoutData(gridData);
		pointsWernerLabel.setText(Constants.WERNER + "\n0     ");
		
		new EmptySpace(shell, 3);
		
		pointsGerdaLabel = new Label(shell, SWT.BORDER | SWT.RIGHT);
		gridData = new GridData(SWT.FILL, SWT.FILL, false, false);
		gridData.horizontalSpan = 1;
		pointsGerdaLabel.setLayoutData(gridData);
		pointsGerdaLabel.setText(Constants.GERDA + "\n     0");
		
		
		// Listeners
		Listener closeListener = new Listener() {
			public void handleEvent(Event event) {
		        event.doit = closable;
			}
		};
		shell.addListener(SWT.Close, closeListener);
		
		setFontSize(40);
		c1.pack();
		c2.pack();
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
		setFontSize(y / 30);
//		shell.pack();
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
