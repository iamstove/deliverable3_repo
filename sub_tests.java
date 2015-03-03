package com.example.tests;

import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class sub_tests{
	public WebDriver driver;
	String baseUrl;
	
	@Before
	public void setup_user(){
		driver = new FirefoxDriver();
	    baseUrl = "http://www.reddit.com/";
	    driver.get(baseUrl+"r/cs1699test/");
	    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	    try
	    {
	    	driver.findElement(By.linkText("logout")).click();
	    }
	    catch(NoSuchElementException e)
	   	{
	   	}
	   	finally{ //whether or not the try catch fails, were here.
		   	driver.findElement(By.name("user")).click();
			driver.findElement(By.name("user")).clear();
			driver.findElement(By.name("user")).sendKeys("cs1699testuser");
			driver.findElement(By.name("passwd")).click();
			driver.findElement(By.name("passwd")).clear();
			driver.findElement(By.name("passwd")).sendKeys("cs1699");
			driver.findElement(By.cssSelector("button.btn")).click();
	   	}
	}

	@After
	public void teardown_user(){
		driver.quit();
	}

	@Test
	public void test_subscribe() throws InterruptedException{
		//press subsribe button
		driver.findElement(By.xpath("//div[5]/div/span/a")).click();
		Thread.sleep(1000);
	    driver.get(baseUrl + "subreddits/");
	    // Warning: assertTextPresent may require manual changes
	    //assert that the subreddit is on the subreddits page
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*cs1699test[\\s\\S]*$"));
	}

	@Test
	public void test_unsubscribe() throws InterruptedException{
		//press unsubscribe button
	    driver.findElement(By.xpath("//div[5]/div/span/a")).click();
	    Thread.sleep(1000);
	    driver.get(baseUrl + "subreddits/");
	    // Warning: assertTextNotPresent may require manual changes
	    //assert that the subreddit isn't on the page
	    assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*cs1699test[\\s\\S]*$"));
	}
}