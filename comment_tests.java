package com.example.tests;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class comment_tests{
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
    public void test_Createcomment() throws Exception {
	    driver.get(baseUrl + "/r/cs1699test/");
	    driver.findElement(By.xpath("//div/div/div[2]/ul/li/a")).click();
	    //create comment
	    driver.findElement(By.name("text")).click();
	    driver.findElement(By.name("text")).clear();
	    driver.findElement(By.name("text")).sendKeys("this is my test comment");
	    driver.findElement(By.cssSelector("button.save")).click();
	    WebDriverWait wait = new WebDriverWait(driver, 5); //wait for comment to appear
	    // assert comment exists and delte it
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*this is my test comment[\\s\\S]*$"));
	    driver.findElement(By.xpath("//li[5]/form/span/a")).click();
	    driver.findElement(By.xpath("//li[5]/form/span[2]/a")).click();
	}

	@Test
	public void test_DeleteComment() throws Exception {
	    driver.get(baseUrl + "/r/cs1699test/");
	    //submit comment
    	driver.findElement(By.xpath("(//a[@href='http://www.reddit.com/r/cs1699test/comments/2xqpr7/test_comment_post/'])[3]")).click();
    	driver.findElement(By.xpath("//div[2]/form/div/div/textarea")).click();
    	driver.findElement(By.xpath("//div[2]/form/div/div/textarea")).clear();
   		driver.findElement(By.xpath("//div[2]/form/div/div/textarea")).sendKeys("this comment will be deleted");
   		WebDriverWait wait = new WebDriverWait(driver, 5); //wait for comment to appear
    	driver.findElement(By.xpath("//div[2]/form/div/div[2]/div/button")).click();
    	driver.findElement(By.xpath("(//a[contains(text(),'delete')])[2]")).click();
    	driver.findElement(By.xpath("(//a[contains(text(),'yes')])[5]")).click();
    	WebDriverWait wait2 = new WebDriverWait(driver, 5); //wait for comment to delete
    	//assert comment not present
    	assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*this comment will be deleted[\\s\\S]*$"));
	}

	@Test
	public void testEditcomment() throws Exception {
		driver.get(baseUrl + "/r/cs1699test/");
		driver.findElement(By.xpath("//div/div/div[2]/ul/li/a")).click();
		//submit a comment
		driver.findElement(By.xpath("(//textarea[@name='text'])[2]")).click();
		driver.findElement(By.xpath("(//textarea[@name='text'])[2]")).clear();
		driver.findElement(By.xpath("(//textarea[@name='text'])[2]")).sendKeys("this comment will be edited");
		driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5); //wait for the comment
		//edit the comment
		driver.findElement(By.xpath("(//a[contains(text(),'edit')])[3]")).click();
		driver.findElement(By.xpath("(//textarea[@name='text'])[3]")).click();
		driver.findElement(By.xpath("(//textarea[@name='text'])[3]")).clear();
		driver.findElement(By.xpath("(//textarea[@name='text'])[3]")).sendKeys("this comment will not be edited");
		driver.findElement(By.xpath("(//button[@type='submit'])[3]")).click();
		//assert the comment exists and delete it
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*this comment will not be edited[\\s\\S]*$"));
		driver.findElement(By.xpath("(//a[contains(text(),'delete')])[2]")).click();
		driver.findElement(By.xpath("(//a[contains(text(),'yes')])[5]")).click();
	}

}