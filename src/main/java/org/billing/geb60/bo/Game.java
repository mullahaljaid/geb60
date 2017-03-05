package org.billing.geb60.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {

	private List<Question> allQuestions;
	private List<Question> unusedQuestions;
	
	private List<Player> players;
	private Player activePlayer;
	
	private Question currentQuestion;
	
	private int[] points = new int[2];
	
	public Game(List<Question> list) {
		this.allQuestions = list;
		this.unusedQuestions = new ArrayList<Question>(list);
		
		this.players = new ArrayList<Player>();
		
		Arrays.fill(this.points, 0);
	}
	
	public Question getNextRandomQuestion() {
		if (this.unusedQuestions.isEmpty()) {
			return null;
		}
		Random rand = new Random();
		int next = rand.nextInt(this.unusedQuestions.size());
		Question q = this.unusedQuestions.get(next);
		this.unusedQuestions.remove(q);
		return q;
	}
	
	public Question getNextQuestion(String quest) {
		if (this.unusedQuestions.isEmpty()) {
			return null;
		}
		for (int i = 0; i < this.unusedQuestions.size(); i++) {
			if (this.unusedQuestions.get(i).isQuestion(quest)) {
				Question q = this.unusedQuestions.get(i);
				this.unusedQuestions.remove(q);
				return q;
			}
		}
		return null;
	}
	
	public void addPlayer(Player player) {
		if (this.players.size() < 2) {
			this.players.add(player);
		}
	}
	
	public void addPoint(Player player, int pointsToAdd) {
		this.points[player.getId()] += pointsToAdd;
	}
	
	public int[] getPoints() {
		return this.points;
	}
	
	public List<Question> getUnusedQuestions() {
		return this.unusedQuestions;
	}
	
	public List<Question> getAllQuestions() {
		return this.allQuestions;
	}
	
	public Question getCurrentQuestion() {
		return this.currentQuestion;
	}
	
	public Player getActivePlayer() {
		return this.activePlayer;
	}
}
