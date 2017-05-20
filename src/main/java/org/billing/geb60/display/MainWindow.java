package org.billing.geb60.display;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.billing.geb60.bo.Game;
import org.billing.geb60.display.helpers.AnswerTable;
import org.billing.geb60.display.helpers.AudioHelper;
import org.billing.geb60.display.helpers.EmptySpace;
import org.billing.geb60.display.helpers.PlayerTable;
import org.billing.geb60.exceptions.LoadingException;
import org.billing.geb60.util.Constants;
import org.billing.geb60.util.ResourceLoader;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class MainWindow {

	/**
	 * https://www.chrisnewland.com/swt-best-practice-single-display-multiple-shells-111
	 */
	
	private Log _log = LogFactory.getLog(getClass());
	
	private Table answerTable;
	private Table playerTable;
	
	private Label questionLabel;
	
	public MainWindow(Display display) throws LoadingException {
		_log.info("Creating main window!");
		
		final Shell shell = new Shell(display, SWT.CLOSE | SWT.ON_TOP);
		shell.setText("Mainwindow");
		shell.open();
		
		Game game = getGame(shell);
		if (game == null) {
			shell.dispose();
			return;
		}
		
		addPlayers(game, shell);
		
		GameWindow gW = new GameWindow(display, game);
		game.setGameWindow(gW);
		
		doLayout(display, shell, game, gW);
		
		gW.setFontSize(55);
		gW.setTableLayout();

		while (!display.isDisposed()) {
			try {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Table getAnswerTable() {
		return this.answerTable;
	}
	
	public Table getPlayerTable() {
		return this.playerTable;
	}
	
	public Label getQuestionLabel() {
		return this.questionLabel;
	}

	private void doLayout(final Display display, final Shell shell, final Game game, final GameWindow gW) {
		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout);
		shell.forceActive();
		shell.setActive();
		shell.forceActive();
		shell.setActive();
		
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		playerTable = new Table(shell, SWT.BORDER);
		playerTable.setLayoutData(gridData);
		TableColumn c = new TableColumn(playerTable, SWT.NONE);
		c.setText("Spieler");
		c.setWidth(300);
		c = new TableColumn(playerTable, SWT.NONE);
		c.setText("Punkte");
		c.setWidth(50);
		playerTable.setHeaderVisible(true);
		playerTable.setLinesVisible(true);
		PlayerTable.refreshPoints(game, true);
		
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		
		final Button sizeButton = new Button(shell, SWT.PUSH);
		sizeButton.setText("Größe des Spieldialogs anpassen!");
		sizeButton.setLayoutData(gridData);
		
		new EmptySpace(shell, gridData);
		
		questionLabel = new Label(shell, SWT.WRAP);
		questionLabel.setText(game.getCurrentQuestion().toString());
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		gridData.heightHint = 50;
		questionLabel.setLayoutData(gridData);
		
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		
		final Button nextQuestionButton = new Button(shell, SWT.PUSH);
		nextQuestionButton.setText("Nächste Frage");
		nextQuestionButton.setLayoutData(gridData);
		
		final Button wrongAnswer = new Button(shell, SWT.PUSH);
		wrongAnswer.setText("Falsche Antwort!");
		wrongAnswer.setLayoutData(gridData);
		
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		answerTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		answerTable.setLayoutData(gridData);
		c = new TableColumn(answerTable, SWT.NONE);
		c.setText("Antwort");
		c.setWidth(200);
		c = new TableColumn(answerTable, SWT.NONE);
		c.setText("Punkte");
		c.setWidth(50);
		answerTable.setHeaderVisible(true);
		answerTable.setLinesVisible(true);
		AnswerTable.refreshAnswers(game, true);
		
		// Listeners
		Listener closeListener = new Listener() {
			public void handleEvent(Event event) {
				gW.closeable();
				gW.getShell().close();
				Display.getCurrent().dispose();
		        event.doit = true;
			}
		};
		shell.addListener(SWT.Close, closeListener);
		
		Listener gWSizeListener = new Listener() {
			public void handleEvent(Event arg0) {
				new GameWindowLayoutWindow(display, gW);
			}
		};
		sizeButton.addListener(SWT.Selection, gWSizeListener);
		
		Listener nextQuestionListener = new Listener() {
			public void handleEvent(Event arg0) {
				new QuestionSelectionDialog(shell, game);
			}
		};
		nextQuestionButton.addListener(SWT.Selection, nextQuestionListener);
		
		Listener wrongAnserListener = new Listener() {
			public void handleEvent(Event event) {
				AudioHelper.playFail();
			}
		};
		wrongAnswer.addListener(SWT.Selection, wrongAnserListener);
		
		Listener answerSelectionListener = new Listener() {
			
			public void handleEvent(Event arg0) {
				new AnswerSelectionDialog(shell, game);
			}
		};
		answerTable.addListener(SWT.Selection, answerSelectionListener);
		
		shell.pack();
	}

	private Game getGame(Shell shell) throws LoadingException {
		String fileName = null;
		int max = 3;
		while (fileName == null && max > 0) {
			fileName = openDialog(shell);
			max--;
		}
        
		if (fileName == null) {
			throw new LoadingException("No file specified!");
		}
		
        // Load game
		try {
			URL gameUrl = new URL("file:\\" + fileName);
			Game game = new Game(ResourceLoader.getInstance().getQuestions(gameUrl), this);
	        _log.info("Game file loaded: " + gameUrl.toString());
	        return game;
		} catch (MalformedURLException e1) {
			throw new LoadingException("Problems while loading game file!", e1);
		}
	}
	
	private String openDialog(Shell shell) {
		// Load file
		FileDialog fd = new FileDialog(shell, SWT.OPEN);
		fd.setText("Save");
		fd.setFilterPath("C:/");
		String[] filterExt = { "*.properties" };
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();
		_log.info("Selected game file: " + selected);
		return selected;
	}
	
	private void addPlayers(Game game, Shell shell) {
		game.addPlayer(Constants.WERNER);
		game.addPlayer(Constants.GERDA);
	}
}
