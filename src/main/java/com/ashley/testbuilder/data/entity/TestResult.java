package com.ashley.testbuilder.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class TestResult extends AbstractEntity{

	@OneToOne
	private Test test;
	private boolean passed;
	private String message;
	
	public TestResult() {
	}
	
	public TestResult(Test test) {
		this.test = test;
	}
	
	public Test getTest() {
		return test;
	}
	
	public void setTest(Test test) {
		this.test = test;
	}
	
	public boolean isPassed() {
		return passed;
	}
	
	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
