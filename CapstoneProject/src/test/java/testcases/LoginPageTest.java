package testcases;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.LoginPage;
import setup.BasePage;

public class LoginPageTest {
	WebDriver driver;
	LoginPage loginPage;
	SoftAssert soft;
	
	@BeforeClass
	@Parameters({"browser","url"})
	public void setup(String browser, String url) {
		BasePage base = new BasePage(browser,url);
		driver = base.getDriver();
		loginPage = new LoginPage(driver);
		soft = new SoftAssert();
	}
	
	@BeforeMethod
	@Parameters({"url"})
	public void goToLoginPage(String url) throws IOException {
		driver.navigate().to(url);
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("C:\\Users\\aris.carroll\\eclipse-workspace\\CapstoneProject\\ScreenShots\\image1.png"));
	}
	
	@DataProvider
	public Object[][] loginCreds() {
		//Two sets of data with two parameters each
		Object[][] data = new Object[2][2];
		data[0][0] = "donhere";
		data[0][1] = "don@123";
		data[1][0] = "aris";
		data[1][1] = "aris@123";
		return data;
	}
	
	@Test(priority = 2)
	public void signUpTest() throws InterruptedException {
		boolean verify = loginPage.signUp();
		soft.assertTrue(verify, "Browser did not navigate to Register page.");	
	}
	
	@Test(priority = 1)
	public void forgotPasswordTest() throws InterruptedException {
		boolean verify = loginPage.clickForgotPassword();
		soft.assertTrue(verify, "Browser did not navigate to Unlock Account page.");
	}
	
	@Test(priority = 4, dataProvider = "loginCreds")
	public void loginTest(String username, String password) throws InterruptedException {
		boolean verify = loginPage.login(username, password);
		soft.assertTrue(verify, "User did not get logged in.");
	}
	
	@Test(priority = 3, dataProvider = "loginCreds")
	public void invalidUserMessageTest(String username, String password) throws InterruptedException {
		boolean verify = loginPage.invalidUserMessage(username, password);
		soft.assertTrue(verify, "Invalid user message was not displayed.");
	}
	
	@Test(priority = 5)
	public void logoutTest() throws InterruptedException {
		boolean verify = loginPage.logout();
		soft.assertTrue(verify, "Browser did not logout or browser did not return to home page.");
	}
	
	@AfterClass
	public void teardown() {
		driver.quit();
		soft.assertAll();
	}
}
