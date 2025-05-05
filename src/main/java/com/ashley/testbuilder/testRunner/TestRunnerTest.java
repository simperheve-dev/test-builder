package com.ashley.testbuilder.testRunner;

import com.ashley.testbuilder.data.entity.Step;
import com.ashley.testbuilder.data.entity.Test;
import com.ashley.testbuilder.data.entity.TestResult;

public class TestRunnerTest {
	
	public static void main(String[] args) {
		
		Test test = new Test("Test");
		
		test.addStep(new Step(1).navigateStep("https://www.selenium.dev/selenium/web/web-form.html"));
		test.addStep(new Step(2).inputStep("#my-text-id", "Test"));
		
		TestRun run = new TestRun(test);
		run.addOption("--remote-allow-origins=*");
		
		TestResult result = new TestRunner(run).runTest();
		
		System.out.println(result.getMessage());
	}
}
