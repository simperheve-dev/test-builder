package com.ashley.testbuilder.testRunner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ashley.testbuilder.data.entity.Step;
import com.ashley.testbuilder.data.entity.Test;
import com.ashley.testbuilder.data.entity.TestResult;
import com.ashley.testbuilder.webdriver.WebDriverInit;

public class TestRunner {

	private WebDriver driver;

	private TestRun run;

	public TestRunner(TestRun testRun) {
		driver = WebDriverInit.initDriver(testRun.getOptions());
		this.run = testRun;
	}

	public TestResult runTest() {
		Test test = run.getTest();

		TestResult result = new TestResult(test);

		try {
			for (Step step : test.getSteps()) {

				switch (step.getAction()) {
				case CLICK:
					driver.findElement(By.cssSelector(step.getLocator())).click();
					break;
				case INPUT:
					driver.findElement(By.cssSelector(step.getLocator())).sendKeys(step.getInput());
					break;
				case NAVIGATE:
					driver.get(step.getDestination());
					break;
				default:
					break;
				}
			}
			
			WebDriverInit.exitDriver(driver);
			
			result.setPassed(true);
			result.setMessage("Passed");
			
			return result;
			
		} catch (Exception e) {

			WebDriverInit.exitDriver(driver);
			
			result.setPassed(false);
			result.setMessage(e.getMessage());
			
			return result;
		}
	}
}
