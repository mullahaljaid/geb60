package org.billing.geb60.display.helpers;

import org.billing.geb60.bo.Answer;
import org.billing.geb60.bo.Game;
import org.billing.geb60.display.GameWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class AnswerTable {
	
	public static void refreshAnswers(final Table answerTable, final Game game, final boolean first, final GameWindow gW) {
		answerTable.removeAll();
		gW.getAnswerTable().removeAll();
		int i = 0;
		for (; i < game.getCurrentQuestion().getAnswers().size(); i++) {
			//Main Window
			Answer a = game.getCurrentQuestion().getAnswers().get(i);
			TableItem tI = new TableItem(answerTable, SWT.NONE);
			if (a.wasAlreadyGiven()) {
				tI.setBackground(new Color(Display.getCurrent(), 255, 0, 0));
			} else {
				tI.setBackground(new Color(Display.getCurrent(), 0, 255, 0));
			}
			tI.setText(0, a.getAnswer());
			tI.setText(1, "" + a.getPoints());
			
			// GameWindow
			TableItem ti2 = new TableItem(gW.getAnswerTable(), SWT.NONE);
			if (a.wasAlreadyGiven()) {
				ti2.setText(0, a.getAnswer());
				ti2.setText(1, "" + a.getPoints());
			} else {
				ti2.setText(0, "........................................");
				ti2.setText(1, "???");
			}
		}
		for (; i < 8; i++) {
			TableItem tI = new TableItem(answerTable, SWT.NONE);
			tI.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
			tI.setText(0, "");
			tI.setText(1, "");
		}
		if (first) {
			answerTable.setSize(answerTable.computeSize(SWT.DEFAULT, 200));
			answerTable.getShell().setSize(answerTable.getShell().computeSize(SWT.DEFAULT, 200));
		}
	}
}
