package org.billing.geb60.bo;

public class Player {

	private int id = 0;
	private String name;
	
	public Player(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return this.getName();
	}
}
