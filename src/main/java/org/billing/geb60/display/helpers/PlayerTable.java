package org.billing.geb60.display.helpers;

import org.billing.geb60.bo.Game;
import org.billing.geb60.util.Constants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

public class PlayerTable {

	public static void refreshPoints(final Game game, final boolean first) {
		game.getMainWindow().getPlayerTable().removeAll();

		TableItem tI = new TableItem(game.getMainWindow().getPlayerTable(), SWT.NONE);
		tI.setText(0, Constants.WERNER);
		tI.setText(1, "" + game.getPoints(Constants.WERNER));

		tI = new TableItem(game.getMainWindow().getPlayerTable(), SWT.NONE);
		tI.setText(0, Constants.GERDA);
		tI.setText(1, "" + game.getPoints(Constants.GERDA));

		game.getGameWindow().getPointsWernerLabel().setText(Constants.WERNER + "\n" + game.getPoints(Constants.WERNER));
		game.getGameWindow().getPointsGerdaLabel().setText(Constants.GERDA + "\n" + game.getPoints(Constants.GERDA));

		if (first) {
			game.getMainWindow().getPlayerTable()
					.setSize(game.getMainWindow().getPlayerTable().computeSize(SWT.DEFAULT, 0));
			game.getMainWindow().getPlayerTable().getShell()
					.setSize(game.getMainWindow().getPlayerTable().getShell().computeSize(SWT.DEFAULT, 200));
		}
	}
}
