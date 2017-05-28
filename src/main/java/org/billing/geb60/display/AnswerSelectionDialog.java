package org.billing.geb60.display;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.billing.geb60.bo.Game;
import org.billing.geb60.display.helpers.AnswerTable;
import org.billing.geb60.display.helpers.AudioHelper;
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
import org.eclipse.swt.widgets.TableItem;

public class AnswerSelectionDialog {

	private Log _log = LogFactory.getLog(getClass());

	private Game game;
	private Shell dialog;

	public AnswerSelectionDialog(final Shell shell, final Game game) {
		this.game = game;
		final TableItem tI = game.getMainWindow().getAnswerTable().getSelection()[0];
		if (tI.getBackground().getRed() == 255) {
			return;
		}

		dialog = new Shell(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
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

		wernerButton.addListener(SWT.Selection, new ButtonListener(Constants.WERNER, tI.getText(0)));
		gerdaButton.addListener(SWT.Selection, new ButtonListener(Constants.GERDA, tI.getText(0)));
		nobodyButton.addListener(SWT.Selection, new ButtonListener(Constants.NOBODY, tI.getText(0)));

		dialog.pack();
		dialog.open();
	}

	

	class ButtonListener implements Listener {
		private String player;
		private String answerText;

		ButtonListener(String player, String answerText) {
			super();
			this.player = player;
			this.answerText = answerText;
		}

		public void handleEvent(Event arg0) {
			game.getCurrentQuestion().giveAnswer(answerText);
			dialog.close();
			AudioHelper.playWin();
			AnswerTable.refreshAnswers(game, false);
			
			game.getDisplay().timerExec(2000, new Runnable() {
				
				@Override
				public void run() {
					int points = game.getCurrentQuestion().giveAnserPoints(answerText);
					game.addPoint(game.getPlayer(player), points);
					_log.info(player + " recieved " + points + " points for answer '" + answerText + "'!");
					AudioHelper.playPoints();
					AnswerTable.refreshAnswers(game, false);
					PlayerTable.refreshPoints(game, false);
				}
			});
		}
	}
}
