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
		
		this.currentQuestion = Question.NULL_QUESTION;
	}
	
	public Question getNextRandomQuestion() {
		if (this.unusedQuestions.isEmpty()) {
			return null;
		}
		Random rand = new Random();
		int next = rand.nextInt(this.unusedQuestions.size());
		Question q = this.unusedQuestions.get(next);
		this.unusedQuestions.remove(q);
		this.currentQuestion = q;
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
				this.currentQuestion = q;
				return q;
			}
		}
		return null;
	}
	
	public void addPlayer(String name) {
		Player player = new Player(this.players.size(), name);
		if (this.activePlayer == null) {
			this.activePlayer = player;
		}
		if (this.players.size() < 2) {
			this.players.add(player);
		}
	}
	
	public void addPoint(Player player, int pointsToAdd) {
		this.points[player.getId()] += pointsToAdd;
	}
	
	public int getPoints(String name) {
		Player player = this.getPlayer(name);
		if (player != null) {
			return this.points[player.getId()];
		}
		return 0;
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
	
	public void nextPlayer() {
		for (Player play : this.players) {
			if (play.getId() != this.getActivePlayer().getId()) {
				this.activePlayer = play;
				return;
			}
		}
	}
	
	public Player getPlayer(final String name) {
		for (Player p : this.players) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}
}
