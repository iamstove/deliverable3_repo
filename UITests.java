package com.example.tests;

import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;


public class UITests {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before //Makes sure that the user is not logged in for these guest tests.
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://www.reddit.com/r/cs1699test";
    driver.get(baseUrl);
    driver.manage().timeouts().implicitlyWait(12, TimeUnit.SECONDS);
   try
    {
    	driver.findElement(By.linkText("logout")).click(); //Logs out of a different user
    	driver.findElement(By.name("user")).click();
        driver.findElement(By.name("user")).clear();
        driver.findElement(By.name("user")).sendKeys("cs1699testuser"); //User:cs1699testuser
        driver.findElement(By.name("passwd")).clear();
        driver.findElement(By.name("passwd")).sendKeys("cs1699"); //Pass:cs1699
        driver.findElement(By.cssSelector("button.btn")).click(); //Clicks the login button
    }
   catch(NoSuchElementException e)//No one was logged in already
   	{
	   driver.findElement(By.name("user")).click();
       driver.findElement(By.name("user")).clear();
       driver.findElement(By.name("user")).sendKeys("cs1699testuser"); //User:cs1699testuser
       driver.findElement(By.name("passwd")).clear();
       driver.findElement(By.name("passwd")).sendKeys("cs1699"); //Pass:cs1699
       driver.findElement(By.cssSelector("button.btn")).click(); //Clicks the login button
   	}
    	
  }
  
  @Test
  public void testUpvotePost() throws InterruptedException
  {
	  Thread.sleep(1000);
	//  String prev=driver.findElement(By.xpath(".//*[@id='siteTable']/div[1]/div[1]/div[3]")).getText(); //This gets the number of upvotes the first post has.
	  driver.findElement(By.xpath(".//*[@id='siteTable']/div[1]/div[1]/div[1]")).click(); //This clicks the upvote button for the first post.
	  //String next=driver.findElement(By.xpath(".//*[@id='siteTable']/div[1]/div[1]/div[4]")).getText();
	  WebElement q=driver.findElement(By.xpath(".//*[@id='siteTable']/div[1]/div[1]/div[4]"));
	  //System.out.println(prev+" "+next);
	  assertTrue(q.isDisplayed());
	  
  }
  
  @Test
  public void testDownvotePost() throws InterruptedException
  {
	  Thread.sleep(1000);
	  //  prev=driver.findElement(By.xpath(".//*[@id='siteTable']/div[1]/div[1]/div[4]")).getText();
	  driver.findElement(By.xpath(".//*[@id='siteTable']/div[1]/div[1]/div[5]")).click(); //This clicks the downvote button for the first post.
	  //String next=driver.findElement(By.xpath(".//*[@id='siteTable']/div[1]/div[1]/div[2]")).getText();
	  WebElement q=driver.findElement(By.xpath(".//*[@id='siteTable']/div[1]/div[1]/div[2]"));
	  // System.out.println(prev+" "+next);
	 
	  assertTrue(q.isDisplayed());
  }
  
  @Test
  public void testCommentUpvote() throws InterruptedException
  {
	  Thread.sleep(1000);
	  driver.get(baseUrl+"/comments/2xkpr0/cheer_up_with_skate_butt/");//This post stays up, and has a comment that stays up with it.
	  driver.findElement(By.xpath(".//*[@id='siteTable_t3_2xkpr0']/div[1]/div[1]/div[1]")).click();//Clicks to upvote a post.
	  WebElement q=driver.findElement(By.xpath(".//*[@id='siteTable_t3_2xkpr0']/div[1]/div[2]/p/span[4]"));
	  //The way reddit keeps track and instantly updates the counters is by having three spans, with n-1, n, and n+1 as the counts. Span 4 is the n+1 counter, and is only displayed after upvoting.
	  assertTrue(q.isDisplayed());
	  
  }
  @Test
  public void testCommentDownvote() throws InterruptedException
  {
	  Thread.sleep(1000);
	  driver.get(baseUrl+"/comments/2xkpr0/cheer_up_with_skate_butt/");//This post stays up, and has a comment that stays up with it.
	  driver.findElement(By.xpath(".//*[@id='siteTable_t3_2xkpr0']/div[1]/div[1]/div[2]")).click();//Clicks to downvote a post.
	  WebElement q=driver.findElement(By.xpath(".//*[@id='siteTable_t3_2xkpr0']/div[1]/div[2]/p/span[2]"));
	  //The way reddit keeps track and instantly updates the counters is by having three spans, with n-1, n, and n+1 as the counts. Span 2 contains the n-1, and is only displayed after downvotes.
	  assertTrue(q.isDisplayed());
	  
  }
  
  
  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
 
  
}
