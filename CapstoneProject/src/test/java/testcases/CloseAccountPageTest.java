package testcases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.CloseAccountPage;
import pageObjects.LoginPage;
import setup.BasePage;

public class CloseAccountPageTest {
	WebDriver driver;
	LoginPage loginPage;
	CloseAccountPage closeAccountPage;
	SoftAssert soft;
	
	@BeforeClass
	@Parameters({"browser","url"})
	public void setup(String browser, String url) {
		BasePage base = new BasePage(browser,url);
		driver = base.getDriver();
		loginPage = new LoginPage(driver);
		closeAccountPage = new CloseAccountPage(driver);
		soft = new SoftAssert();
	}
	
	@Test(priority = -1)
	public void goToPageTest() throws IOException {
		loginPage.login("donhere", "don@123");
		boolean verify = closeAccountPage.goToCloseAccountPage();
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("C:\\Users\\aris.carroll\\eclipse-workspace\\CapstoneProject\\ScreenShots\\image.png"));
		soft.assertTrue(verify, "Browser did not load Close Account page.");
	}
	
	@Test(priority = 4)
	public void loadingPageElementsTest() {
		boolean verify = closeAccountPage.verifyPageLoadElements();
		soft.assertTrue(verify, "Page elements did not load correctly.");
	}
	
	@Test
	public void populateDropDownTest() {
		boolean verify = closeAccountPage.verifyMyDepositDD();
		soft.assertTrue(verify, "Dropdowns were not populated with options.");
	}
	
	@Test(priority = 1)
	public void dropDownSelectionTest() throws InterruptedException {
		boolean verify = closeAccountPage.verifyValidDDSelection();
		soft.assertTrue(verify, "Dropdown did not make a correct selection.");
	}
	
	@Test(priority = 2)
	public void transferModeTest() throws InterruptedException {
		boolean verify = closeAccountPage.verifyTransModeDisablingBtn();
		soft.assertTrue(verify, "Page did not load proper elements.");
	}
	
	@Test
	public void cashRadioBtnTest() throws InterruptedException {
		boolean verify = closeAccountPage.verifyCashRBtnDisablingBtn();
		soft.assertTrue(verify, "Clicking cash radio button did not disable close button.");
	}
	
	@Test
	public void iebiRadioBtnTest() throws InterruptedException {
		boolean verify = closeAccountPage.verifyIebiRBtnDisplays();
		soft.assertTrue(verify, "Clicking IEBI radio button did not display correct elements.");
	}
	
	@Test
	public void otherBankRadioBtnTest() throws InterruptedException {
		boolean verify = closeAccountPage.verifyOtherBankRBtnDisplays();
		soft.assertTrue(verify, "Clicking other radio button did not display correct elements.");
	}
	
	@Test(priority = 3)
	public void closeDepositBtnTest() throws InterruptedException {
		boolean verify = closeAccountPage.verifyCloseDepositMessage();
		soft.assertTrue(verify, "Page did not display message verifying request submitted.");
	}
	
	@Test
	public void resetBtnTest() throws InterruptedException {
		boolean verify = closeAccountPage.verifyResetBtn();
		soft.assertTrue(verify, "Fields did not reset upon click.");
	}
	
	@AfterClass
	public void teardown() {
		driver.quit();
		soft.assertAll();
	}
}
