package pageObjects;

import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class CalculatorPage {
	WebDriver driver;
	Logger logger = Logger.getLogger("CalculatorPage.class");
	PropertyConfigurator.configure("log4j.properties");
	By DepositNavLink = By.xpath("//*[@id=\"GeneralTabMenu_Deposit_li_Cust\"]");
	By calculatorLink = By.xpath("//*[@id=\"body_pnlCustomer_SubMenu\"]/div/div/ul/li[2]/a");
	By radioBtnFixed = By.id("body_cph_Deposit_rbtnDepositType_0");
	By radioBtnRecurring = By.xpath("//*[@id=\"body_cph_Deposit_rbtnDepositType_1\"]");
	By depositAmtTxt = By.id("body_cph_Deposit_txtDepositAmount");
	By termsTxt = By.id("body_cph_Deposit_txtTerms");
	By calculateBtn = By.xpath("//*[@id=\"body_cph_Deposit_btnCalculate\"]");
	By resetBtn = By.xpath("//*[@id=\"body_cph_Deposit_btnReset\"]");
	By noRadioBtnPopup = By.xpath("//*[@id=\"body_cph_Deposit_rfvDepositType_VCE_popupTable\"]/tbody/tr/td[3]");
	By emptyDepositAmtPopup = By.xpath("//*[@id=\"body_cph_Deposit_rfvActualDeposite_VCE_popupTable\"]/tbody/tr/td[3]");
	By invalidFixedDepositAmtPopup = By.xpath("//*[@id=\"body_cph_Deposit_cmpvDepositAmountFD_VCE_popupTable\"]/tbody/tr/td[3]");
	By invalidRecurDepositAmtPopup = By.xpath("//*[@id=\"body_cph_Deposit_cmpvDepositAmountRD_VCE_popupTable\"]/tbody/tr/td[3]");
	By emptyTermsPopup = By.xpath("//*[@id=\"body_cph_Deposit_txtTerms_VCE_popupTable\"]/tbody/tr/td[3]");
	By invalidTermsPopup = By.xpath("//*[@id=\"body_cph_Deposit_rngTerms_VCE_popupTable\"]/tbody/tr/td[3]");
	By calculatedAmt = By.id("body_cph_Deposit_lblMaturityAmountValue");
	String calculatorUrl = "http://10.82.180.36/Deposits/Customer/MaturityAmountCalculator.aspx";
	
	
	public CalculatorPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean goToCalculatorPage() {
		driver.findElement(DepositNavLink).click();
		driver.findElement(calculatorLink).click();
		if (driver.getCurrentUrl().equals(calculatorUrl)) {
			logger.info("Page navigated successfully");
			return true;
		}
		return false;
	}
	
	public boolean verifyRadioPopup(long amount, int term) {
		driver.findElement(depositAmtTxt).sendKeys(""+amount);
		driver.findElement(termsTxt).sendKeys(""+term);
		driver.findElement(calculateBtn).click();
		if (driver.findElement(noRadioBtnPopup).isDisplayed()) {
			return true;
		}
		return false;
	}
	
	public boolean verifyEmptyDepositAmtPopup(int term) {
		driver.findElement(radioBtnFixed).click();
		driver.findElement(termsTxt).sendKeys(""+term);
		driver.findElement(calculateBtn).click();
		if (driver.findElement(emptyDepositAmtPopup).isDisplayed()) {
			return true;
		}
		return false;
	}
	
	public boolean verifyInvalidFixedDepositAmtPopup(long amount, int term) {
		driver.findElement(radioBtnFixed).click();
		driver.findElement(depositAmtTxt).sendKeys(""+amount);
		driver.findElement(termsTxt).sendKeys(""+term);
		driver.findElement(calculateBtn).click();
		if (driver.findElement(invalidFixedDepositAmtPopup).isDisplayed()) {
			return true;
		}
		return false;
	}
	
	public boolean verifyInvalidRecurDepositAmtPopup(long amount, int term) {
		driver.findElement(radioBtnRecurring).click();
		driver.findElement(depositAmtTxt).sendKeys(""+amount);
		driver.findElement(termsTxt).sendKeys(""+term);
		driver.findElement(calculateBtn).click();
		if (driver.findElements(invalidRecurDepositAmtPopup).size() != 0) {
			return true;
		}
		return false;
	}
	
	public boolean verifyEmptyTermsTxt(long amount) {
		driver.findElement(radioBtnFixed).click();
		driver.findElement(depositAmtTxt).sendKeys(""+amount);
		driver.findElement(calculateBtn).click();
		if (driver.findElement(emptyTermsPopup).isDisplayed()) {
			return true;
		}
		return false;
	}
	
	public boolean verifyInvalidTermsAmt(long amount, int term) {
		driver.findElement(radioBtnFixed).click();
		driver.findElement(depositAmtTxt).sendKeys(""+amount);
		driver.findElement(termsTxt).sendKeys(""+term);
		driver.findElement(calculateBtn).click();
		if (driver.findElements(invalidTermsPopup).size() != 0) {
			return true;
		}
		return false;
	}
	
	public boolean verifyCalculatedAmt(long amount, int term) throws InterruptedException {
		driver.findElement(radioBtnFixed).click();
		driver.findElement(depositAmtTxt).sendKeys(""+amount);
		driver.findElement(termsTxt).sendKeys(""+term);
		driver.findElement(calculateBtn).click();
		Thread.sleep(2000);
		if (driver.findElement(calculatedAmt).isDisplayed()) {
			return true;
		}
		return false;
	}
	
	public boolean verifyResetBtn() throws InterruptedException {
		driver.findElement(radioBtnFixed).click();
		driver.findElement(depositAmtTxt).sendKeys("10000");
		driver.findElement(termsTxt).sendKeys("20");
		reset();
		if (driver.findElement(termsTxt).getText().isBlank() && 
				driver.findElement(depositAmtTxt).getText().isBlank() && 
				!(driver.findElement(radioBtnFixed).isSelected()) && 
				!(driver.findElement(radioBtnRecurring).isSelected())) {
			return true;
		}
		return false;
	}
	
	public void reset() throws InterruptedException {
		driver.findElement(resetBtn).click();
		Thread.sleep(2000);
	}

}
