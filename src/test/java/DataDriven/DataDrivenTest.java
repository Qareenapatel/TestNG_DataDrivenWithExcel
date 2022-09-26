package DataDriven;


import java.time.Duration;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class DataDrivenTest {

			WebDriver driver;
			
			Logger log = Logger.getLogger( DataDrivenTest.class);

	@BeforeClass
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\patel\\Webdriver\\chromedriver.exe");
	}

	@BeforeMethod
	public void initialization() {
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
	}

	@Test(dataProvider ="LoginTest")
	public void login(String username, String password) {
		driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@id='login-button']")).click();
		
		
		if (driver.getPageSource().contains("Epic sadface")) {
			Assert.assertTrue(false);
		}else {
			Assert.assertTrue(true);
		}
		System.out.println(username + password);
		log.debug("Hello this is a debug message");   
	}
		
	@DataProvider(name="LoginTest")
	public Object[][] getdata(){
		Object[][] testObjectArray=null;
		try {
			testObjectArray = ExcelUtility.getTableArray("C:\\Users\\patel\\OneDrive\\Desktop\\PracticeforQA.xlsx", "Sheet1");
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		return testObjectArray;
	}

	@AfterMethod
	public void closeBrowser() {
		driver.quit();
	}
}
