package com.ashley.testbuilder.webdriver;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public final class WebDriverInit {
	
	public static WebDriver initDriver(List<String> options)
	{
		ChromeOptions chromeOptions = new ChromeOptions();
		
		chromeOptions.addArguments(options);
    	
        WebDriver driver = new ChromeDriver(chromeOptions);
		
		return driver;
	}
	
	public static void exitDriver(WebDriver driver)
	{
		driver.close();
	}
}
