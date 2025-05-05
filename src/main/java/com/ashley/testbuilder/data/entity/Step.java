package com.ashley.testbuilder.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Step extends AbstractEntity {
	private int position;
	private Action action;
	private String locator;
	private String input;
	private String destination;
	
	@ManyToOne
	@JoinColumn(name = "test_id")
	private Test test;
	
	public Step() {
	}
	
	public Step(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

	public String getLocator() {
		return locator;
	}
	
	public String getInput() {
		return input;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public Step navigateStep(String destination) {
		this.destination = destination;
		this.action = Action.NAVIGATE;
		
		return this;
	}
	
	public Step clickStep(String locator) {
		this.locator = locator;
		this.action = Action.CLICK;
		
		return this;
	}
	
	public Step inputStep(String locator, String input) {
		this.locator = locator;
		this.input = input;
		this.action = Action.INPUT;
		return this;
	}
	
	public void setTest(Test test) {
		
			this.test = test;
	}
}
