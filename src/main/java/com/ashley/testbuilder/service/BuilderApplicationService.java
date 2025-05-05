package com.ashley.testbuilder.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ashley.testbuilder.data.entity.Step;
import com.ashley.testbuilder.data.entity.Test;
import com.ashley.testbuilder.data.entity.TestResult;
import com.ashley.testbuilder.repository.StepRepository;
import com.ashley.testbuilder.repository.TestRepository;
import com.ashley.testbuilder.repository.TestResultRepository;

@Service
public class BuilderApplicationService {

	private final TestRepository testRepository;
	private final StepRepository stepRepository;
	private final TestResultRepository testResultRepository;

	public BuilderApplicationService(TestRepository testRepository, StepRepository stepRepository,
			TestResultRepository testResultRepository) {
		this.testRepository = testRepository;
		this.stepRepository = stepRepository;
		this.testResultRepository = testResultRepository;
	}

	public void saveTest(Test test) {
		testRepository.save(test);
	}

	public void saveStep(Step step) {
		stepRepository.save(step);
	}

	public void saveTestResult(TestResult result) {
		testResultRepository.save(result);
	}

	public List<Test> findTests() {
		return testRepository.findAll();
	}
	
	public Test findTestById(Long id)
	{
		return testRepository.findById(id).get();
	}
	
	public Step findStepById(Long id)
	{
		return stepRepository.findById(id).get();
	}
	
	public List<Step> findStepsByTestId(String id)
	{
		return stepRepository.findStepsByTestId(id);
	}
	
	public TestResult findTestResultById(Long id)
	{
		return testResultRepository.findById(id).get();
	}
}
