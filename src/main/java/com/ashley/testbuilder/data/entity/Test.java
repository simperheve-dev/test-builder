package com.ashley.testbuilder.data.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Entity
public class Test extends AbstractEntity{

	private String name;
	
	@OneToMany(mappedBy = "test", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Step> steps;

	public Test() {
		steps = new ArrayList<>();
	}
	
	public Test(String name) {
		this.name = name;
		steps = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void addStep(Step step) {
		steps.add(step);
		step.setTest(this);
	}
	
	public void removeStep(Step step)
	{
		steps.remove(step);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(getSteps().size());
	}
}
