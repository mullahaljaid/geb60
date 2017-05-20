package org.billing.geb60.display.helpers;

import org.billing.geb60.bo.Answer;
import org.billing.geb60.bo.Game;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;

public class AnswerTable {

	private static final String NO_POINTS = "--";
	private static final String NO_ANSWER = ".............................................................................................................";

	public static void refreshAnswers(final Game game, final boolean first) {
		game.getMainWindow().getAnswerTable().removeAll();
		game.getGameWindow().getAnswerTable().removeAll();
		int i = 0;
		for (; i < game.getCurrentQuestion().getAnswers().size(); i++) {
			// Main Window
			Answer a = game.getCurrentQuestion().getAnswers().get(i);
			TableItem tI = new TableItem(game.getMainWindow().getAnswerTable(), SWT.NONE);
			if (a.wasAlreadyGiven()) {
				tI.setBackground(new Color(Display.getCurrent(), 255, 0, 0));
			} else {
				tI.setBackground(new Color(Display.getCurrent(), 0, 255, 0));
			}
			tI.setText(0, a.getAnswer());
			tI.setText(1, "" + a.getPoints());

			// GameWindow
			TableItem ti2 = new TableItem(game.getGameWindow().getAnswerTable(), SWT.NONE);
			if (a.wasAlreadyPoints()) {
				ti2.setText(0, i+1 + ".");
				ti2.setText(1, a.getAnswer());
				ti2.setText(2, "" + a.getPoints());
			} else if (a.wasAlreadyGiven()) {
				ti2.setText(0, i+1 + ".");
				ti2.setText(1, a.getAnswer());
				ti2.setText(2, NO_POINTS);
			} else {
				ti2.setText(0, i+1 + ".");
				ti2.setText(1, NO_ANSWER);
				ti2.setText(2, NO_POINTS);
			}
		}
		for (; i < 8; i++) {
			TableItem tI = new TableItem(game.getMainWindow().getAnswerTable(), SWT.NONE);
			tI.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
			tI.setText(0, "");
			tI.setText(1, "");
		}
		if (first) {
			game.getMainWindow().getAnswerTable()
					.setSize(game.getMainWindow().getAnswerTable().computeSize(SWT.DEFAULT, 200));
			game.getMainWindow().getAnswerTable().getShell()
					.setSize(game.getMainWindow().getAnswerTable().getShell().computeSize(SWT.DEFAULT, 200));
		}
	}
}
