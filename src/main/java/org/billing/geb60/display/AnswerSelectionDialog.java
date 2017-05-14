package org.billing.geb60.display;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.billing.geb60.bo.Game;
import org.billing.geb60.display.helpers.AnswerTable;
import org.billing.geb60.display.helpers.CancelButton;
import org.billing.geb60.display.helpers.EmptySpace;
import org.billing.geb60.display.helpers.PlayerTable;
import org.billing.geb60.util.Constants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class AnswerSelectionDialog {

	private Log _log = LogFactory.getLog(getClass());
	
	public AnswerSelectionDialog(final Shell shell, final Game game, final Table answerTable, final Table playerTable, final GameWindow gW) {
		final TableItem tI = answerTable.getSelection()[0];
		if (tI.getBackground().getRed() == 255) {
			return;
		}
		
		final Shell dialog = new Shell(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		dialog.setText("Wer hat geantwortet?");
		GridLayout layout = new GridLayout(1, false);
		dialog.setLayout(layout);
		
		final Button wernerButton = new Button(dialog, SWT.PUSH);
		wernerButton.setText(Constants.WERNER);
		final Button gerdaButton = new Button(dialog, SWT.PUSH);
		gerdaButton.setText(Constants.GERDA);
		
		new EmptySpace(dialog);
		
		final Button nobodyButton = new Button(dialog, SWT.PUSH);
		nobodyButton.setText("Auflösung!!");
		
		new CancelButton(dialog);
		
		Listener wernerListener = new Listener() {
			public void handleEvent(Event arg0) {
				int points = game.getCurrentQuestion().giveAnswer(tI.getText(0));
				game.addPoint(game.getPlayer(Constants.WERNER), points);
				_log.info(Constants.WERNER + " recieved " + points + " points for answer '" + tI.getText(0) + "'!");
				dialog.close();
				AnswerTable.refreshAnswers(answerTable, game, false, gW);
				PlayerTable.refreshPoints(playerTable, game, false, gW);
			}
		};
		wernerButton.addListener(SWT.Selection, wernerListener);
		
		Listener gerdaListener = new Listener() {
			
			public void handleEvent(Event arg0) {
				int points = game.getCurrentQuestion().giveAnswer(tI.getText(0));
				game.addPoint(game.getPlayer(Constants.GERDA), points);
				_log.info(Constants.GERDA + " recieved " + points + " points for answer '" + tI.getText(0) + "'!");
				dialog.close();
				AnswerTable.refreshAnswers(answerTable, game, false, gW);
				PlayerTable.refreshPoints(playerTable, game, false, gW);
			}
		};
		gerdaButton.addListener(SWT.Selection, gerdaListener);
		
		Listener nobodyListener = new Listener() {
			
			public void handleEvent(Event arg0) {
				int points = game.getCurrentQuestion().giveAnswer(tI.getText(0));
				_log.info("Nobody recieved " + points + " points for answer '" + tI.getText(0) + "'!");
				dialog.close();
				AnswerTable.refreshAnswers(answerTable, game, false, gW);
			}
		};
		nobodyButton.addListener(SWT.Selection, nobodyListener);
		
		dialog.pack();
		dialog.open();
	}
}
