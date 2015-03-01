package com.example.tests;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;


public class GuestTest {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before //Makes sure that the user is not logged in for these guest tests.
  public void setUp() throws Exception {
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
    	
  }
  
  @Test
  public void testSeePosts()
  {
	  driver.get(baseUrl + "/r/cs1699test/"); //Directs to our testing subreddit
	  WebElement W=driver.findElement(By.className("rank"));
	  //Every post has a rank; it's a core piece to reddit. If there is any post on the page,
	  //then it will have a span with a class=rank in it.
	  assertNotNull(W);//Asserting that it found a rank.
		  
  }
  
  @Test
  public void testSeeComments()
  {
	  driver.get(baseUrl + "/r/cs1699test/"); //Directs to our testing subreddit
	  WebElement W2=driver.findElement(By.xpath(".//*[@id='siteTable']/div[1]/div[2]/ul/li[1]/a"));//Generated using FirePath, a FireBug plugin.
	  W2.click();//This clicks the comment button of the rank-1 link on the subreddit.
	  String s=driver.findElement(By.xpath("html/body/div[3]/div[2]/div[1]/span")).getText();
	  boolean b1=s.equals("no comments (yet)"); //This is the default message, which appears if there have been no comments made so far.
	  if (!b1) //If that message isn't found, then someon had to have commented.
	  {
		  String s2=s.substring(0, 3)+s.substring(s.length()-9); //This removes the number from between the two words in "all n comments"
		  b1=s2.equals("all comments");
	  }
	  assertTrue(b1); //If either of those messages checked to be true, then you must be on a  comments page.
  }
  
  @Test
  public void testSearchFunction()
  {
	 driver.get(baseUrl + "/r/cs1699test/");
	 WebElement W=driver.findElement(By.xpath(".//*[@id='search']/input[1]")) ;
	 W.click();
	 W.clear();
	 W.sendKeys("skate butt");//Childish, I know. But it was a funny image that would be left up for us to refer to, and chances are would never be mentioned otherwise
	 driver.findElement(By.xpath(".//*[@id='searchexpando']/label/input")).click();//This sets the search to only our testing subreddit
	 driver.findElement(By.xpath(".//*[@id='search']/input[2]")).click();//This is the actual search button.
	 assertEquals("Cheer up with Skate Butt", driver.findElement(By.xpath("//div[2]/p/a")).getText()); //The title of the post is "Cheer up with Skate Butt".
	 
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
