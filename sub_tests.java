package com.example.tests;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class sub_tests{
	public WebDriver driver = new FirefoxDriver();

	@Before
	public void setup_user(){
		driver = new FirefoxDriver();
	    baseUrl = "http://www.reddit.com/";
	    driver.get(baseUrl);
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    try
	    {
	    	driver.findElement(By.linkText("logout")).click();
			driver.findElement(By.name("user")).click();
			driver.findElement(By.name("user")).clear();
			driver.findElement(By.name("user")).sendKeys("cs1699testuser");
			driver.findElement(By.name("passwd")).click();
			driver.findElement(By.name("passwd")).clear();
			driver.findElement(By.name("passwd")).sendKeys("cs1699");
			driver.findElement(By.cssSelector("button.btn")).click();
	    }
	    catch(NoSuchElementException e)
	   	{
		   
	   	}
	}

	@After
	public void teardown_user(){
		driver.quit();
	}

	@Test
	public void test_subscribe(){
		//press subsribe button
		driver.get(baseUrl + "/r/cs1699test/");
		driver.findElement(By.linkText("subscribe")).click();
	    driver.get("www.reddit.com/subreddits/");
	    // Warning: assertTextPresent may require manual changes
	    //assert that the subreddit is on the subreddits page
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*cs1699test[\\s\\S]*$"));
	}

	@Test
	public void test_unsubscribe(){
		//press unsubscribe button
		driver.get(baseUrl + "/r/cs1699test/");
	    driver.findElement(By.linkText("unsubscribe")).click();
	    driver.get("www.reddit.com/subreddits");
	    // Warning: assertTextNotPresent may require manual changes
	    //assert that the subreddit isn't on the page
	    assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*cs1699test[\\s\\S]*$"));
	}
}