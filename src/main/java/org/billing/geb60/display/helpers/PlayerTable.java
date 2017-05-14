package org.billing.geb60.display.helpers;

import org.billing.geb60.bo.Game;
import org.billing.geb60.display.GameWindow;
import org.billing.geb60.util.Constants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class PlayerTable {
	
	public static void refreshPoints(final Table playerTable, final Game game, final boolean first, final GameWindow gW) {
		playerTable.removeAll();
		
		TableItem tI = new TableItem(playerTable, SWT.NONE);
		tI.setText(0, Constants.WERNER);
		tI.setText(1, "" + game.getPoints(Constants.WERNER));
		
		tI = new TableItem(playerTable, SWT.NONE);
		tI.setText(0, Constants.GERDA);
		tI.setText(1, "" + game.getPoints(Constants.GERDA));
		
		gW.getPointsWernerLabel().setText("" + game.getPoints(Constants.WERNER));
		gW.getPointsGerdaLabel().setText("" + game.getPoints(Constants.GERDA));
		gW.resize();
		
		if (first) {
			playerTable.setSize(playerTable.computeSize(SWT.DEFAULT, 0));
			playerTable.getShell().setSize(playerTable.getShell().computeSize(SWT.DEFAULT, 200));
		}
	}
}
