package org.billing.geb60.display;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.billing.geb60.bo.Game;
import org.billing.geb60.display.helpers.EmptySpace;
import org.billing.geb60.util.Constants;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
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
import org.eclipse.swt.widgets.TableItem;

public class GameWindow {

	private Log _log = LogFactory.getLog(getClass());
	
	private final Shell shell;
	
	private final Label questionLabel;
	private final Table answerHeaderTable;
	private final Table answerTable;
	private final Label pointsWernerLabel;
	private final Label pointsGerdaLabel;
	private final Composite tableHeaderComposite;
	private final Composite tableComposite;
	private final TableColumn ch1;
	private final TableColumn ch2;
	private final TableColumn ch3;
	private final TableColumn c1;
	private final TableColumn c2;
	private final TableColumn c3;
	
	private int size;
	
	private boolean closable = false;
	private Font font;
	
	public GameWindow(final Display main, final Game game) {
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
		
		tableHeaderComposite = new Composite(shell, SWT.NONE);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 5;
		gridData.verticalSpan = 1;
		tableHeaderComposite.setLayoutData(gridData);
		answerHeaderTable = new Table(tableHeaderComposite, SWT.BORDER);
		answerHeaderTable.setHeaderVisible(false);
		ch1 = new TableColumn(answerHeaderTable, SWT.NONE);
		ch2 = new TableColumn(answerHeaderTable, SWT.NONE);
		ch3 = new TableColumn(answerHeaderTable, SWT.NONE);
		ch3.setAlignment(SWT.RIGHT);
		TableItem item = new TableItem(answerHeaderTable, SWT.NONE);
		item.setText(1, "Antworten");
		item.setText(2, "Punkte");		
		
		tableComposite = new Composite(shell, SWT.NONE);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 5;
		gridData.verticalSpan = 5;
		tableComposite.setLayoutData(gridData);
		answerTable = new Table(tableComposite, SWT.NONE);
		answerTable.setHeaderVisible(false);
		answerTable.setLinesVisible(false);
		c1 = new TableColumn(answerTable, SWT.NONE);
		c2 = new TableColumn(answerTable, SWT.NONE);
		c3 = new TableColumn(answerTable, SWT.NONE);
		c3.setAlignment(SWT.RIGHT);
		
		setTableLayout();
		
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
	
	public void setFontSize(int size) {
		this.size = size;
		FontData fd = font.getFontData()[0];
		fd.setHeight(size);
		font = new Font(Display.getCurrent(), fd);
		questionLabel.setFont(font);
		String questionText = questionLabel.getText();
		questionLabel.setText("\n\n ");
		answerHeaderTable.setFont(font);
		answerTable.setFont(font);
		pointsGerdaLabel.setFont(font);
		pointsWernerLabel.setFont(font);
		shell.pack();
		shell.setMaximized(true);
		questionLabel.setText(questionText);
	}
	
	public void setTableLayout() {
		String questionText = questionLabel.getText();
		questionLabel.setText("\n\n ");
		TableColumnLayout layout = new TableColumnLayout();
		int x = shell.getSize().x;
		layout.setColumnData(ch1, new ColumnPixelData((int)((x - 30) * 0.035)));
		layout.setColumnData(ch2, new ColumnPixelData((int)((x - 30) * 0.82)));
		layout.setColumnData(ch3, new ColumnPixelData((int)((x - 30) * 0.14)));
		layout.setColumnData(c1, new ColumnPixelData((int)((x - 30) * 0.035)));
		layout.setColumnData(c2, new ColumnPixelData((int)((x - 30) * 0.82)));
		layout.setColumnData(c3, new ColumnPixelData((int)((x - 30) * 0.14)));
		tableHeaderComposite.setLayout(layout);
		tableComposite.setLayout(layout);
		shell.pack();
		shell.setMaximized(true);
		questionLabel.setText(questionText);
	}
	
	public void closeable() {
		this.closable = true;
	}
	
	public int getCurrentSize() {
		return this.size;
	}
}
