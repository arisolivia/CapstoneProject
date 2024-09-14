package testcases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.CalculatorPage;
import pageObjects.LoginPage;
import setup.BasePage;

public class CalculatorPageTest {
	WebDriver driver;
	LoginPage loginPage;
	CalculatorPage calculatorPage;
	SoftAssert soft;
	XSSFSheet wBookSheet;
	
	@BeforeClass
	@Parameters({"browser","url"})
	public void setup(String browser, String url) throws IOException {
		BasePage base = new BasePage(browser,url);
		driver = base.getDriver();
		loginPage = new LoginPage(driver);
		calculatorPage = new CalculatorPage(driver);
		soft = new SoftAssert();
		XSSFWorkbook wBook = new XSSFWorkbook("C:\\Users\\aris.carroll\\eclipse-workspace\\CapstoneProject\\CapstoneWorkbook.xlsx");
		wBookSheet = wBook.getSheet("Sheet1");
	}
	
	@DataProvider
	public Object[][] termParameters() {
		Object[][] data = new Object[3][2];
		data[0][0] = 10000;
		data[0][1] = 15;	
		data[1][0] = 10000;
		data[1][1] = 0;	
		data[2][0] = 10000;
		data[2][1] = 36;
		return data; 
	}
	
	@Test(priority = -1)
	private void goToCalculatorPageTest() throws Exception {
		loginPage.login("donhere", "don@123");
		Thread.sleep(2000);
		boolean verify = calculatorPage.goToCalculatorPage();
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File("C:\\Users\\aris.carroll\\eclipse-workspace\\CapstoneProject\\ScreenShots\\image2.png"));
		Assert.assertTrue(verify, "Browser did not navigate to Calculator page.");
	}
	
	@Test
	public void radioMessageTest() throws InterruptedException {
		//Get values from excel
		Row row = wBookSheet.getRow(0);
		long amount = (long)row.getCell(0).getNumericCellValue();
		int term = (int)row.getCell(1).getNumericCellValue();
		//Check mandatory fields
		boolean verify = calculatorPage.verifyRadioPopup(amount, term);
		soft.assertTrue(verify, "Page did not display no radio button selected message.");
		calculatorPage.reset();
	}
	
	@Test
	public void emptyDepositAmtTest() throws InterruptedException {
		//Get values from excel
		Row row = wBookSheet.getRow(0);
		int term = (int)row.getCell(1).getNumericCellValue();
		
		boolean verify = calculatorPage.verifyEmptyDepositAmtPopup(term);
		soft.assertTrue(verify, "Page did not display empty deposit amount box message.");
		calculatorPage.reset();
	}
	
	@Test
	public void emptyTermsTest() throws InterruptedException {
		//Get values from excel
		Row row = wBookSheet.getRow(0);
		long amount = (long)row.getCell(0).getNumericCellValue();
		
		boolean verify = calculatorPage.verifyEmptyTermsTxt(amount);
		soft.assertTrue(verify, "Page did not display empty terms box message.");
		calculatorPage.reset();
	}
	
	@Test
	public void invalidFixedDepositAmtTest() throws InterruptedException {
		//Get values from excel
		Row row = wBookSheet.getRow(1);
		long amount = (long)row.getCell(0).getNumericCellValue();
		int term = (int)row.getCell(1).getNumericCellValue();
		
		boolean verify = calculatorPage.verifyInvalidFixedDepositAmtPopup(amount, term);
		soft.assertTrue(verify, "Page did not display invalid fixed deposit amount message.");
		Thread.sleep(2000);
		calculatorPage.reset();
	}
	
	@Test
	public void invalidRecurringDepositAmtTest() throws InterruptedException {
		//Get values from excel
		Row row = wBookSheet.getRow(1);
		long amount = (long)row.getCell(0).getNumericCellValue();
		int term = (int)row.getCell(1).getNumericCellValue();
		
		boolean verify = calculatorPage.verifyInvalidRecurDepositAmtPopup(amount, term);
		soft.assertTrue(verify, "Page did not display invalid recurring deposit amount message.");
		calculatorPage.reset();
	}
	
	@Test(dataProvider = "termParameters")
	public void invalidTermsAmtTest(long amount, int term) throws InterruptedException {
		boolean verify = calculatorPage.verifyInvalidTermsAmt(amount, term);
		soft.assertTrue(verify, "Page did not display invalid terms amount message.");
		calculatorPage.reset();
	}
	
	@Test
	public void resultsTest() throws InterruptedException {
		//Get values from excel
		Row row = wBookSheet.getRow(2);
		long amount = (long)row.getCell(0).getNumericCellValue();
		int term = (int)row.getCell(1).getNumericCellValue();
		
		boolean verify = calculatorPage.verifyCalculatedAmt(amount, term);
		soft.assertTrue(verify, "Page did not display calculated amount.");
		calculatorPage.reset();
	}
	
	@Test(priority = 1)
	public void resetBtnTest() throws InterruptedException {
		boolean verify = calculatorPage.verifyResetBtn();
		soft.assertTrue(verify, "Page did not clear fields upon reset.");
		
	}
	
	@AfterClass
	public void teardown() {
		driver.quit();
		soft.assertAll();
	}
}
