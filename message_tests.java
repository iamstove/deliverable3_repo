package com.example.tests;

import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class message_tests{
	public WebDriver driver;
	public String baseUrl;

	@Before
	public void setup_user(){
		driver = new FirefoxDriver();
		baseUrl = "http://www.reddit.com/";
		driver.get(baseUrl);
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
	public void testSendmessage() throws Exception {
		Thread.sleep(1000);
		driver.get(baseUrl + "/r/cs1699test/");
		driver.findElement(By.linkText("cs1699testuser2")).click();
		driver.findElement(By.xpath("html/body/div[2]/div[1]/div/div[3]/a")).click();
		//send mail
		driver.findElement(By.xpath("//div[2]/div/div/input")).clear();
		driver.findElement(By.xpath("//div[2]/div/div/input")).sendKeys("Hello");
		driver.findElement(By.xpath("//div/div/div/textarea")).clear();
		driver.findElement(By.xpath("//div/div/div/textarea")).sendKeys("I'm sending a message!");
		// wait for captcha
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if ("your message has been delivered".equals(driver.findElement(By.xpath(".//*[@id='compose-message']/span")).getText())) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}
		// wait for send
		Thread.sleep(2000);
		// Warning: assertTextPresent may require manual changes
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*your message has been delivered[\\s\\S]*$"));
	}

	@Test
	public void testGetmessage() throws Exception {
		Thread.sleep(1000);
		driver.get(baseUrl + "/r/cs1699test/");
		//log in as the user that just got a message
		driver.findElement(By.linkText("logout")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("user")).click();
		driver.findElement(By.name("user")).clear();
		driver.findElement(By.name("user")).sendKeys("cs1699testuser2");
		driver.findElement(By.name("passwd")).click();
		driver.findElement(By.name("passwd")).sendKeys("cs1699");
		driver.findElement(By.xpath("//form[@id='login_login-main']/div[3]/button")).click();
		Thread.sleep(1000);
		//click on mail and make sure that the message just sent is there
		driver.findElement(By.id("mail")).click();
		Thread.sleep(1000);
		// Warning: assertTextPresent may require manual changes
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*I'm sending a message[\\s\\S]*$"));
	}
}