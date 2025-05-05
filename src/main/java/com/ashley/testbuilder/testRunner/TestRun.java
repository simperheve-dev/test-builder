package com.ashley.testbuilder.testRunner;

import java.util.ArrayList;
import java.util.List;

import com.ashley.testbuilder.data.entity.Test;

public class TestRun {

	private Test test;
	private List<String> options;

	public TestRun(Test test) {
		this.test = test;
		options = new ArrayList<>();
	}

	public Test getTest() {
		return test;
	}

	public void addOption(String option) {
		options.add(option);
	}
	
	public List<String> getOptions() {
		return options;
	}
}
