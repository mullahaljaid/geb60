package org.billing.geb60.display;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.billing.geb60.bo.Game;
import org.billing.geb60.exceptions.LoadingException;
import org.billing.geb60.util.ResourceLoader;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

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
				
		// TODO window layout

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
}
