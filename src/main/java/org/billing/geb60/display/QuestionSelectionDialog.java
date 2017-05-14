package org.billing.geb60.display;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.billing.geb60.bo.Game;
import org.billing.geb60.bo.Question;
import org.billing.geb60.display.helpers.AnswerTable;
import org.billing.geb60.display.helpers.CancelButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

public class QuestionSelectionDialog {
	
	private Log _log = LogFactory.getLog(getClass());

	public QuestionSelectionDialog(final Shell shell, final Game game, final Text questionLabel, final Table answerTable) {
		final Shell dialog = new Shell(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		dialog.setText("Nächste Frage?");
		
		GridLayout layout = new GridLayout(1, false);
		dialog.setLayout(layout);
		
		final List questionList = new List(dialog, SWT.BORDER | SWT.V_SCROLL);
		for (Question q : game.getUnusedQuestions()) {
			questionList.add(q.getQuestion());
		}
	    
		questionList.addListener(SWT.Selection, new Listener() {
			
			public void handleEvent(Event arg0) {
				int[] selection = questionList.getSelectionIndices();
				String questionString = questionList.getItem(selection[0]);
				game.getNextQuestion(questionString);
				questionLabel.setText(questionString);
				AnswerTable.refreshAnswers(answerTable, game, false);
				_log.info("Next question: " + questionString);
				dialog.close();
			}
		});
		
		new CancelButton(dialog);
		dialog.pack();
		dialog.open();
	}
}
