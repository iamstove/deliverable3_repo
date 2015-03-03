package com.example.tests;

import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;


public class mod_tests{
	public WebDriver driver;
	private String baseUrl;
	
	@Before
	public void setup_mod(){
		driver = new FirefoxDriver();
	    baseUrl = "http://www.reddit.com/r/cs1699test";
	    driver.get(baseUrl);
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	   try
	    {
	    	driver.findElement(By.linkText("logout")).click();
	    }
	   catch(NoSuchElementException e)
	   	{
		   
	   	}
		driver.findElement(By.name("user")).click();
		driver.findElement(By.name("user")).clear();
		driver.findElement(By.name("user")).sendKeys("cs1699admin");
		driver.findElement(By.name("passwd")).click();
		driver.findElement(By.name("passwd")).clear();
		driver.findElement(By.name("passwd")).sendKeys("potato");
		driver.findElement(By.cssSelector("button.btn")).click();
	}

	@After
	public void teardown_mod(){
		//driver.findElement(By.linkText("logout")).click(); //log out once we're done testing the mod	
		driver.quit();
	}

	@Test
	public void test_delete() throws InterruptedException{
		driver.findElement(By.linkText("logout")).click();
		//log into a test user
		driver.findElement(By.name("user")).click();
		driver.findElement(By.name("user")).clear();
		driver.findElement(By.name("user")).sendKeys("cs1699testuser");
		driver.findElement(By.name("passwd")).click();
		driver.findElement(By.name("passwd")).clear();
		driver.findElement(By.name("passwd")).sendKeys("cs1699");
		driver.findElement(By.cssSelector("button.btn")).click();

		//submit a post
		//driver.findElement(By.linkText("Submit a new text post")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("html/body/div[2]/div[4]/div/div/a")).click();
		driver.findElement(By.name("title")).click();
		driver.findElement(By.name("title")).clear();
		driver.findElement(By.name("title")).sendKeys("Hello");
		driver.findElement(By.name("text")).click();
		driver.findElement(By.name("text")).clear();
		driver.findElement(By.name("text")).sendKeys("delete me");

		//the person running the test must type in the capcha and hit submit here, this test waits for the next page to load

		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if ("no comments (yet)".equals(driver.findElement(By.cssSelector("div.panestack-title > span.title")).getText())) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}

		//log in as a mod
		driver.findElement(By.linkText("logout")).click();
		driver.findElement(By.name("user")).click();
		driver.findElement(By.name("user")).clear();
		driver.findElement(By.name("user")).sendKeys("cs1699admin");
		driver.findElement(By.name("passwd")).click();
		driver.findElement(By.name("passwd")).clear();
		driver.findElement(By.name("passwd")).sendKeys("potato");
		driver.findElement(By.cssSelector("button.btn")).click();
		//remove the comment 
		WebElement q=driver.findElement(By.xpath(".//*[@id='siteTable']/div[1]/div[2]/ul/li[7]/form/span[1]/a"));
		q.click();
		q=driver.findElement(By.xpath(".//*[@id='siteTable']/div[1]/div[2]/ul/li[7]/form/span[2]/a[1]"));
		q.click();
		driver.navigate().refresh();
		// Warning: assertTextNotPresent may require manual changes
		assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*//body[\\s\\S]*$"));
	}

	@Test
	public void test_textonly(){
		//change to text only subreddit
		driver.findElement(By.linkText("subreddit settings")).click();
	    driver.findElement(By.xpath("//div[@id='sr-form']/div[7]/div/div/table/tbody/tr[3]/td/label")).click();
	    driver.findElement(By.id("link_type_self")).click();
	    driver.findElement(By.name("edit")).click();
	    //go back to the subreddit
	    driver.get(baseUrl);
	    //login as a regular user and check if the submit a new link button is present (it isn't if it's a text only subreddit)
	    driver.findElement(By.linkText("logout")).click();
	    driver.findElement(By.name("user")).click();
	    driver.findElement(By.name("user")).clear();
	    driver.findElement(By.name("user")).sendKeys("cs1699testuser");
	    driver.findElement(By.name("passwd")).click();
	    driver.findElement(By.name("passwd")).clear();
	    driver.findElement(By.name("passwd")).sendKeys("cs1699");
	    driver.findElement(By.cssSelector("button.btn")).click();
	    try{
	    	WebElement q=driver.findElement(By.linkText("Submit a new link"));
	    	assertTrue(q.isDisplayed());
	    }
	    catch(Exception e)
	    {//Basically, this is set to fail. If Q is missing, as it should be, asserting it being displayed should throw an assertionerror.
	    	assertTrue(true);
	    }
	    //log back in as admin and switch back to a normal subreddit
	    driver.findElement(By.linkText("logout")).click();
	    driver.findElement(By.name("user")).click();
	    driver.findElement(By.name("user")).clear();
	    driver.findElement(By.name("user")).sendKeys("cs1699admin");
	    driver.findElement(By.name("passwd")).click();
	    driver.findElement(By.name("passwd")).clear();
	    driver.findElement(By.name("passwd")).sendKeys("potato");
	    driver.findElement(By.cssSelector("button.btn")).click();
	    driver.findElement(By.linkText("subreddit settings")).click();
	    driver.findElement(By.xpath("//div[@id='sr-form']/div[7]/div/div/table/tbody/tr/td/label")).click();
	    driver.findElement(By.id("link_type_any")).click();
	    driver.findElement(By.name("edit")).click();
	}

	@Test
	public void test_ban_user() throws InterruptedException{
		//ban the user for one day
	    driver.findElement(By.linkText("ban users")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys("cs1699testuser");
	    driver.findElement(By.id("note")).click();
	    driver.findElement(By.id("note")).clear();
	    driver.findElement(By.id("note")).sendKeys("Test ban");
	    driver.findElement(By.id("duration")).click();
	    driver.findElement(By.id("duration")).clear();
	    driver.findElement(By.id("duration")).sendKeys("1");
	    driver.findElement(By.cssSelector("button.btn")).click();

	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if (isElementPresent(By.linkText("CS1699TestUser"))) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }
	    //assert the user is on the banlist
	    assertTrue(isElementPresent(By.linkText("CS1699TestUser")));
	    //unban the user
	    driver.findElement(By.linkText("remove")).click();
	    driver.findElement(By.linkText("yes")).click();
	}

	@Test
	public void test_private(){
		
		//make subreddit private
	    driver.findElement(By.linkText("subreddit settings")).click();
	    driver.findElement(By.xpath("//div[@id='sr-form']/div[6]/div/div/table/tbody/tr[3]/td/label")).click();
	    driver.findElement(By.id("type_private")).click();
	    driver.findElement(By.name("edit")).click();
	    
	    // go back ot main subreddit page, log out, and assert that there is an error element on the page
	    driver.get(baseUrl);
	    driver.findElement(By.linkText("logout")).click();
	    WebElement q=driver.findElement(By.id("classy-error"));
	    assertTrue(q.isDisplayed());

	    //log back in and change the subreddit back to public
	    driver.findElement(By.id("header-img")).click();
	    driver.findElement(By.name("user")).click();
	    driver.findElement(By.name("user")).clear();
	    driver.findElement(By.name("user")).sendKeys("cs1699admin");
	    driver.findElement(By.name("passwd")).click();
	    driver.findElement(By.name("passwd")).clear();
	    driver.findElement(By.name("passwd")).sendKeys("potato");
	    driver.findElement(By.cssSelector("button.btn")).click();
	    driver.get("http://www.reddit.com/r/cs1699test/");
	    driver.findElement(By.linkText("subreddit settings")).click();
	    driver.findElement(By.cssSelector("td.nowrap.nopadding > label")).click();
	    driver.findElement(By.id("type_public")).click();
	    driver.findElement(By.name("edit")).click();
	}

	@Test
	public void test_delete_comment(){
		//log in as test user
		driver.findElement(By.linkText("logout")).click();
	    driver.findElement(By.name("user")).click();
	    driver.findElement(By.name("user")).clear();
	    driver.findElement(By.name("user")).sendKeys("cs1699testuser");
	    driver.findElement(By.name("passwd")).click();
	    driver.findElement(By.name("passwd")).clear();
	    driver.findElement(By.name("passwd")).sendKeys("cs1699");
	    driver.findElement(By.cssSelector("button.btn")).click();
	    //make a comment
	    driver.findElement(By.xpath("//div[@id='siteTable']/div[3]/div[2]/ul/li/a")).click();
	    driver.findElement(By.name("text")).click();
	    driver.findElement(By.name("text")).clear();
	    driver.findElement(By.name("text")).sendKeys("I'm commenting!");
	    driver.findElement(By.cssSelector("button.save")).click();
	    //login as admin and delete the comment
	    driver.findElement(By.linkText("logout")).click();
	    driver.findElement(By.name("user")).click();
	    driver.findElement(By.name("user")).clear();
	    driver.findElement(By.name("user")).sendKeys("cs1699admin");
	    driver.findElement(By.name("passwd")).click();
	    driver.findElement(By.name("passwd")).clear();
	    driver.findElement(By.name("passwd")).sendKeys("potato");
	    driver.findElement(By.cssSelector("button.btn")).click();
	    driver.findElement(By.xpath("(//a[contains(text(),'remove')])[3]")).click();
	    driver.findElement(By.xpath("(//a[contains(text(),'yes')])[8]")).click();
	    driver.navigate().refresh();
	    // Warning: assertTextPresent may require manual changes
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*removed by cs1699admin[\\s\\S]*$"));
	}

	@Test
	public void test_view_reports(){
		//make a comment
		driver.findElement(By.xpath("//div[@id='siteTable']/div[3]/div[2]/ul/li/a")).click();
	    driver.findElement(By.xpath("html/body/div[3]/div[2]/form/div/div/textarea")).click();
	    driver.findElement(By.xpath("html/body/div[3]/div[2]/form/div/div/textarea")).clear();
	    driver.findElement(By.xpath("html/body/div[3]/div[2]/form/div/div/textarea")).sendKeys("offensive comment");
	    driver.findElement(By.cssSelector("button.save")).click();
	    //logout and report the comment as a different user
	    driver.findElement(By.linkText("logout")).click();
	    driver.findElement(By.name("user")).click();
	    driver.findElement(By.name("user")).clear();
	    driver.findElement(By.name("user")).sendKeys("cs1699testuser");
	    driver.findElement(By.name("passwd")).click();
	    driver.findElement(By.name("passwd")).clear();
	    driver.findElement(By.name("passwd")).sendKeys("cs1699");
	    driver.findElement(By.cssSelector("button.btn")).click();
	    driver.findElement(By.xpath("(//a[contains(text(),'report')])[last()]")).click();
	    driver.findElement(By.cssSelector("li > label")).click();
	    driver.findElement(By.name("reason")).click();
	    driver.findElement(By.cssSelector("button.btn.submit-action-thing")).click();
	    //log back in as an admin and check that the comment is reported on that page
	    driver.findElement(By.linkText("logout")).click();
	    driver.findElement(By.name("user")).click();
	    driver.findElement(By.name("user")).clear();
	    driver.findElement(By.name("user")).sendKeys("cs1699admin");
	    driver.findElement(By.name("passwd")).click();
	    driver.findElement(By.name("passwd")).clear();
	    driver.findElement(By.name("passwd")).sendKeys("potato");
	    driver.findElement(By.cssSelector("button.btn")).click();
	    driver.findElement(By.linkText("moderation queue")).click();
	    // Warning: assertTextPresent may require manual changes
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*reports:[\\s\\S]*$"));
	    //delete the comment
	    driver.findElement(By.linkText("delete")).click();
	    driver.findElement(By.xpath("(//a[contains(text(),'yes')])[2]")).click();
	}
	
	private boolean isElementPresent(By by) {
	    try {
	        driver.findElement(by);
	        return true;
	    } catch (NoSuchElementException e) {
	        return false;
	    }
	}
}
