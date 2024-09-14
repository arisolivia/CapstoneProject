package setup;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BasePage {
	private static WebDriver driver;
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public BasePage(String browser, String url) {
		intialize(browser, url);
	}
	
	public void intialize(String browser, String url) {
		
		switch(browser) {
		case "chrome": 
			System.setProperty("webdriver.chrome.driver","D:\\SeleniumJava_3.6.0\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.get(url);
			break;
		case "edge":
			System.setProperty("webdriver.edge.driver", "D:\\SeleniumJava_3.6.0\\edgedriver_win64\\msedgedriver.exe");
			driver = new EdgeDriver();
			driver.get(url);
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", "D:\\SeleniumJava_3.6.0\\IEDriverServer_x64_3.150.1\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.get(url);
			break;
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

}
