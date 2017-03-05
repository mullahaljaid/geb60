package org.billing.geb60.bo;

public class Answer {

	private String answer;
	private int points;
	
	public Answer(String answer, int points) {
		this.answer = answer;
		this.points = points;
	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	public int getPoints() {
		return this.points;
	}
}
