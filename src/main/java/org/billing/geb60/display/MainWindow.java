package org.billing.geb60.display;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.billing.geb60.bo.Game;
import org.billing.geb60.display.helpers.AnswerTable;
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
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class MainWindow {

	/**
	 * https://www.chrisnewland.com/swt-best-practice-single-display-multiple-shells-111
	 */
	
	private Log _log = LogFactory.getLog(getClass());
	
	public MainWindow(Display display) throws LoadingException {
		_log.info("Creating main window!");
		
		final Shell shell = new Shell(display, SWT.CLOSE);
		shell.setText("Mainwindow");
		shell.open();
		
		Game game = getGame(shell);
		if (game == null) {
			shell.dispose();
			return;
		}
		
		addPlayers(game, shell);
		
		GameWindow gW = new GameWindow(shell, game);
		
		doLayout(shell, game, gW);

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

	private void doLayout(final Shell shell, final Game game, final GameWindow gW) {
		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout);
		shell.forceActive();
		
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		final Table playerTable = new Table(shell, SWT.BORDER);
		playerTable.setLayoutData(gridData);
		TableColumn c = new TableColumn(playerTable, SWT.NONE);
		c.setText("Spieler");
		c.setWidth(300);
		c = new TableColumn(playerTable, SWT.NONE);
		c.setText("Punkte");
		c.setWidth(50);
		playerTable.setHeaderVisible(true);
		playerTable.setLinesVisible(true);
		PlayerTable.refreshPoints(playerTable, game, true);
		
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		new EmptySpace(shell, gridData);
		
		final Text questionLabel = new Text(shell, SWT.WRAP);
		questionLabel.setText(game.getCurrentQuestion().toString());
		questionLabel.setEditable(false);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		gridData.heightHint = 50;
		questionLabel.setLayoutData(gridData);
		
		final Button nextQuestionButton = new Button(shell, SWT.PUSH);
		nextQuestionButton.setText("Nächste Frage");
		
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		final Table answerTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		answerTable.setLayoutData(gridData);
		c = new TableColumn(answerTable, SWT.NONE);
		c.setText("Antwort");
		c.setWidth(200);
		c = new TableColumn(answerTable, SWT.NONE);
		c.setText("Punkte");
		c.setWidth(50);
		answerTable.setHeaderVisible(true);
		answerTable.setLinesVisible(true);
		AnswerTable.refreshAnswers(answerTable, game, true);
		
		// Listeners
		
		Listener nextQuestionListener = new Listener() {
			public void handleEvent(Event arg0) {
				new QuestionSelectionDialog(shell, game, questionLabel, answerTable);
			}
		};
		nextQuestionButton.addListener(SWT.Selection, nextQuestionListener);
		
		Listener answerSelectionListener = new Listener() {
			
			public void handleEvent(Event arg0) {
				new AnswerSelectionDialog(shell, game, answerTable, playerTable);
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
			Game game = new Game(ResourceLoader.getInstance().getQuestions(gameUrl));
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
		// TODO player setup
	}
}
