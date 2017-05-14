package org.billing.geb60.bo;

public class Answer {

	private String answer;
	private int points;
	private boolean alreadyGiven;
	
	public Answer(String answer, int points) {
		this.answer = answer;
		this.points = points;
		this.alreadyGiven = false;
	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public boolean wasAlreadyGiven() {
		return alreadyGiven;
	}
	
	public int give() {
		if (this.alreadyGiven) {
			return 0;
		} else {
			this.alreadyGiven = true;
			return this.points;
		}
	}
}
