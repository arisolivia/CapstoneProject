package pageObjects;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	private WebDriver driver;
	Logger logger = Logger.getLogger("LoginPage.class");
	By username = By.id("body_txtUserID");
	By password = By.id("body_txtPassword");
	By login = By.xpath("//*[@id=\"body_btnLogin\"]");
	By signUpLink = By.xpath("//*[@id=\"body_lbtSignUp\"]");
	By forgotLink = By.xpath("//*[@id=\"body_lbtForgotPassword\"]");
	By invalidMessage = By.xpath("/html/body/form/div[3]/div[3]/table/tbody/tr[3]/td[2]/div/table/tbody/tr[9]/td[3]/span");
	By logout = By.xpath("//*[@id=\"lbLoginOut\"]");
	String registerUrl = "http://10.82.180.36/Common/CustomerRegisterPage.aspx";
	String unlockUrl = "http://10.82.180.36/Common/CustomerUnlockAccount.aspx";
	String loginUrl = "http://10.82.180.36/Common/Login.aspx";
	String homeUrl = "http://10.82.180.36/Common/HomePage.aspx";
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean signUp() {
		//Sign up new user
		driver.findElement(signUpLink).click();
		//Verify Register page is current page
		if(driver.getCurrentUrl().equals(registerUrl)) {
			logger.info("Browser navigated to registration page successfully");
			return true;
		}
		return false;	
	}
	
	public boolean clickForgotPassword() {
		//Click Forgot Password link
		driver.findElement(forgotLink).click();
		//Verify Unlock Account page is current page
		if(driver.getCurrentUrl().equals(unlockUrl)) {
			return true;
		}
		return false;
	}
	
	public boolean login(String user, String pass) {
		//Log in
		driver.findElement(username).sendKeys(user);
		driver.findElement(password).sendKeys(pass);
		driver.findElement(login).click();
		//Check if logout is present
		if(driver.findElement(logout).isDisplayed()) {
			return true;
		} 
		return false;
	}
	
	public boolean invalidUserMessage(String user, String pass) {
		//Login
		driver.findElement(username).sendKeys(user);
		driver.findElement(password).sendKeys(pass);
		driver.findElement(login).click();
		//Check if invalid user message is displayed
		if (driver.findElements(invalidMessage).size() != 0) {
			if (driver.findElement(invalidMessage).getText().equals("User Id does not exist")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean logout() throws InterruptedException {
		driver.findElement(logout).click();
		if (driver.getCurrentUrl().equals(loginUrl)) {
			driver.navigate().back();
			if (driver.getCurrentUrl().equals(homeUrl) || driver.getCurrentUrl().equals(loginUrl)) {
				return true;
			}
		}
		return false;
	}

}
