package org.billing.geb60.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {

	private List<Question> unusedQuestions;
	
	private int[] points = new int[2];
	
	public Game(List<Question> list) {
		this.unusedQuestions = new ArrayList<Question>(list);
		
		Arrays.fill(points, 0);
	}
	
	public Question getNextRandomQuestion() {
		if (unusedQuestions.isEmpty()) {
			return null;
		}
		Random rand = new Random();
		int next = rand.nextInt(unusedQuestions.size());
		Question q = unusedQuestions.get(next);
		unusedQuestions.remove(q);
		return q;
	}
	
	public Question getNextQuestion(String quest) {
		if (unusedQuestions.isEmpty()) {
			return null;
		}
		for (int i = 0; i < unusedQuestions.size(); i++) {
			if (unusedQuestions.get(i).isQuestion(quest)) {
				Question q = unusedQuestions.get(i);
				unusedQuestions.remove(q);
				return q;
			}
		}
		return null;
	}
	
	public void addPoint(int who, int pointsToAdd) {
		this.points[who] += pointsToAdd;
	}
	
	public int[] getPoints() {
		return this.points;
	}
	
	public List<Question> getUnusedQuestions() {
		return this.unusedQuestions;
	}
}
