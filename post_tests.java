package com.example.tests;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class post_tests{
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
	public void testTextpost() throws Exception {
		driver.get(baseUrl + "/r/cs1699test/");
		driver.findElement(By.xpath("//div[4]/div/div/a")).click();
		//create test post
		driver.findElement(By.name("title")).click();
		driver.findElement(By.name("title")).clear();
		driver.findElement(By.name("title")).sendKeys("Test post");
		driver.findElement(By.name("text")).clear();
		driver.findElement(By.name("text")).sendKeys("this a post");
		// Put a wait here so the user can capcha
		WebDriverWait wait = new WebDriverWait(driver,15);
		driver.findElement(By.name("submit")).click();
		driver.get(baseUrl + "/r/cs1699test/");
		//assert the post title exists
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Test post[\\s\\S]*$"));
		driver.findElement(By.xpath("//li[5]/form/span/a")).click();
		driver.findElement(By.xpath("//span[2]/a")).click();
	}

	@Test
	public void testEdittextpost() throws Exception {
		driver.get(baseUrl + "/r/cs1699test/");
		driver.findElement(By.xpath("//div[4]/div/div/a")).click();
		//create post
		driver.findElement(By.name("title")).click();
		driver.findElement(By.name("title")).clear();
		driver.findElement(By.name("title")).sendKeys("Test post");
		driver.findElement(By.name("text")).clear();
		driver.findElement(By.name("text")).sendKeys("this a post");
		// Put a wait here so the user can capcha
		WebDriverWait wait = new WebDriverWait(driver,15);
		driver.findElement(By.name("submit")).click();
		//edit the post
		driver.findElement(By.linkText("edit")).click();
		driver.findElement(By.name("text")).click();
		driver.findElement(By.name("text")).clear();
		driver.findElement(By.name("text")).sendKeys("I like posts");
		driver.findElement(By.cssSelector("button.save")).click();
		WebDriverWait wait2 = new WebDriverWait(driver,2);
		// assert that the text inside of the post has been changed
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*I like posts[\\s\\S]*$"));
		driver.findElement(By.xpath("//li[6]/form/span/a")).click();
		driver.findElement(By.xpath("//span[2]/a")).click();
	}

	@Test
	public void testLinkpost() throws Exception {
		driver.get(baseUrl + "/r/cs1699test/");
		//click the link post link
		driver.findElement(By.xpath("//div[3]/div/div/a")).click();
		driver.findElement(By.name("title")).click();
		driver.findElement(By.name("title")).clear();
		driver.findElement(By.name("title")).sendKeys("Melting Dog Comic");
		driver.findElement(By.id("url")).click();
		driver.findElement(By.id("url")).clear();
		driver.findElement(By.id("url")).sendKeys("http://gunshowcomic.com/comics/20130109.png");
		// Insert wait here for entering capcha
		WebDriverWait wait = new WebDriverWait(driver,15);
		driver.findElement(By.name("submit")).click();
		driver.get(baseUrl + "/r/cs1699test/");//go back to subreddit main page and assert that the post title exists on the page
		// Warning: assertTextPresent may require manual changes
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Melting Dog Comic[\\s\\S]*$"));
	}

	@Test
	public void testDeletePost() throws Exception {
		//delete the link post created above then assert that the title of that post isn't there
		driver.get(baseUrl + "/r/cs1699test/");
		driver.findElement(By.xpath("//li[5]/form/span/a")).click();
		driver.findElement(By.xpath("//span[2]/a")).click();
		// Warning: assertTextNotPresent may require manual changes
		assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Melting Dog Comic[\\s\\S]*$"));
	}

}