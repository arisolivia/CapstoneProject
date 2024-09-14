package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CloseAccountPage {
	WebDriver driver;
	By depositNavLink = By.xpath("//*[@id=\"GeneralTabMenu_Deposit_li_Cust\"]");
	By closeAccountLink = By.xpath("//*[@id=\"body_pnlCustomer_SubMenu\"]/div/div/ul/li[3]/a");
	By fixedDepositRB = By.id("body_cph_Deposit_rbtnDepositType_0");
	By recurDepositRB = By.id("body_cph_Deposit_rbtnDepositType_1");
	By myDepositDD = By.xpath("//*[@id=\"body_cph_Deposit_ddlMyDeposits\"]");
	By resetBtn = By.xpath("//*[@id=\"body_cph_Deposit_btnReset\"]");
	By changeModeCkbx = By.id("body_cph_Deposit_chkNewTransfer");
	By closeDepositBtn = By.xpath("//*[@id=\"body_cph_Deposit_btnClose\"]");
	By myDepositMessage = By.xpath("//*[@id=\"body_cph_Deposit_lblStatusMessage\"]");
	By cashRB = By.id("body_cph_Deposit_rbtnTransferType_0");
	By iebiRB = By.id("body_cph_Deposit_rbtnTransferType_1");
	By otherBankRB = By.id("body_cph_Deposit_rbtnTransferType_2");
	By savingsRB = By.id("body_cph_Deposit_rbtnAccountType_0");
	By currentRB = By.id("body_cph_Deposit_rbtnAccountType_1");
	By mapAccountDD = By.xpath("//*[@id=\"body_cph_Deposit_ddlMapToAccount\"]");
	By otherBankTxt = By.xpath("//*[@id=\"body_cph_Deposit_txtOtherBankAccountNumber\"]");
	By closeDepositMessage = By.xpath("//*[@id=\"body_cph_Deposit_lblMessages\"]");
	String closeAccountUrl = "http://10.82.180.36/Deposits/Customer/CloseFDRDAccount.aspx";
	
	public CloseAccountPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean goToCloseAccountPage() {
		driver.findElement(depositNavLink).click();
		driver.findElement(closeAccountLink).click();
		if (driver.getCurrentUrl().equals(closeAccountUrl)) {
			return true;
		}
		return false;
	}
	
	public boolean verifyPageLoadElements() {
		if (goToCloseAccountPage()) {
			if (driver.findElement(fixedDepositRB).isDisplayed() &&
					driver.findElement(recurDepositRB).isDisplayed() &&
					driver.findElement(myDepositDD).isDisplayed() &&
					!(driver.findElement(changeModeCkbx).isEnabled()) &&
					!(driver.findElement(closeDepositBtn).isEnabled())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean verifyMyDepositDD() {
		driver.findElement(fixedDepositRB).click();
		WebElement fixedDepositDD = driver.findElement(myDepositDD);
		Select fixedDepositDDSelect = new Select(fixedDepositDD);
		List<WebElement> fixedDepositDDopts = fixedDepositDDSelect.getOptions();
		
		driver.findElement(recurDepositRB).click();
		WebElement recurDepositDD = driver.findElement(myDepositDD);
		Select recurDepositDDSelect = new Select(recurDepositDD);
		List<WebElement> recurDepositDDopts = recurDepositDDSelect.getOptions();
		
		if(fixedDepositDDopts.size()>1 && recurDepositDDopts.size()>1) {
			return true;
		}
		return false;
	}
	
	public boolean verifyValidDDSelection() throws InterruptedException {
		driver.findElement(fixedDepositRB).click();
		Thread.sleep(2000);
		WebElement fixedDepositDD = driver.findElement(myDepositDD);
		Select fixedDepositDDSelect = new Select(fixedDepositDD);
		fixedDepositDDSelect.selectByIndex(2);
		if (driver.findElement(myDepositMessage).isDisplayed() && 
				driver.findElement(changeModeCkbx).isEnabled() &&
				driver.findElement(closeDepositBtn).isEnabled()) {
			return true;
		}
		return false;
	}
	
	public boolean verifyTransModeDisablingBtn() throws InterruptedException {
		if (verifyValidDDSelection()) {
			driver.findElement(changeModeCkbx).click();
			Thread.sleep(2000);
			if (!(driver.findElement(closeDepositBtn).isEnabled())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean verifyCashRBtnDisablingBtn() throws InterruptedException {
		if (verifyTransModeDisablingBtn()) {
			driver.findElement(cashRB).click();
			Thread.sleep(2000);
			if (driver.findElement(closeDepositBtn).isEnabled()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean verifyIebiRBtnDisplays() throws InterruptedException {
		if (verifyTransModeDisablingBtn()) {
			driver.findElement(iebiRB).click();
			Thread.sleep(2000);
			if (!(driver.findElement(closeDepositBtn).isEnabled()) &&
					driver.findElement(savingsRB).isDisplayed() &&
					driver.findElement(currentRB).isDisplayed() &&
					driver.findElement(mapAccountDD).isDisplayed()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean verifyOtherBankRBtnDisplays() throws InterruptedException {
		if (verifyTransModeDisablingBtn()) {
			driver.findElement(otherBankRB).click();
			Thread.sleep(2000);
			if (driver.findElement(otherBankTxt).isDisplayed() &&
					driver.findElement(closeDepositBtn).isEnabled()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean verifyCloseDepositMessage() throws InterruptedException {
		driver.findElement(fixedDepositRB).click();
		WebElement fixedDepositDD = driver.findElement(myDepositDD);
		Select fixedDepositDDSelect = new Select(fixedDepositDD);
		fixedDepositDDSelect.selectByIndex(3);
		Thread.sleep(2000);
		driver.findElement(changeModeCkbx).click();
		driver.findElement(cashRB).click();
		driver.findElement(closeDepositBtn).click();	
		Thread.sleep(2000);
		if (driver.findElement(closeDepositMessage).isDisplayed()) {
			return true;
		}
		return false;
	}
	
	public boolean verifyResetBtn() throws InterruptedException {
		driver.findElement(fixedDepositRB).click();
		WebElement fixedDepositDD = driver.findElement(myDepositDD);
		Select fixedDepositDDSelect = new Select(fixedDepositDD);
		fixedDepositDDSelect.selectByIndex(1);
		Thread.sleep(2000);
		driver.findElement(changeModeCkbx).click();
		driver.findElement(cashRB).click();
		driver.findElement(resetBtn).click();
		Thread.sleep(2000);
		WebElement fixedDepositDDReset = driver.findElement(myDepositDD);
		Select fixedDepositDDSelectReset = new Select(fixedDepositDDReset);
		if (!(driver.findElement(fixedDepositRB).isSelected()) &&
				fixedDepositDDSelectReset.getOptions().size()==1 &&
				!(driver.findElement(changeModeCkbx).isEnabled())) {
			return true;
		}
		return false;
	}

}
