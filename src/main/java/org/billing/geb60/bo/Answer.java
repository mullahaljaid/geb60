package org.billing.geb60.bo;

public class Answer {

	private String answer;
	private int points;
	private boolean alreadyGiven;
	private boolean alreadyPoints;
	
	public Answer(String answer, int points) {
		this.answer = answer;
		this.points = points;
		this.alreadyGiven = false;
		this.alreadyPoints = false;
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
	
	public boolean wasAlreadyPoints() {
		return alreadyPoints;
	}
	
	public void give() {
		if (!this.alreadyGiven) {
			this.alreadyGiven = true;
		}
	}
	
	public int givePoints() {
		if (this.alreadyPoints) {
			return 0;
		} else {
			this.alreadyPoints = true;
			return this.points;
		}
	}
}
