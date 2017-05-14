package org.billing.geb60.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Question {

	private String question;
	private List<Answer> answers;
	
	public Question(String question) {
		this.question = question;
		this.answers = new ArrayList<Answer>();
	}
	
	public void addAnswer(Answer answer) {
		answers.add(answer);
		Collections.sort(this.answers, new AnswerComparator());
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public List<Answer> getAnswers() {
		return this.answers;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(this.question);
		for (Answer answer : answers) {
			sb.append("\n").append(answer.getAnswer()).append("=").append(answer.getPoints());
		}
		return sb.toString();
	}
	
	public boolean isQuestion(String quest) {
		return StringUtils.equalsIgnoreCase(this.question, quest);
	}
	
	private class AnswerComparator implements Comparator<Answer> {
		public int compare(Answer ans1, Answer ans2) {
			return ans2.getPoints() - ans1.getPoints();
		}
	}
	
	public int giveAnswer(String answer) {
		for (Answer a : this.answers) {
			if (a.getAnswer().equals(answer)) {
				return a.give();
			}
		}
		return 0;
	}
	
	public static final Question NULL_QUESTION = new Question("Noch keine Frage ausgewählt!");
}
