package switches.weboperation;

import org.openqa.selenium.WebDriver;

import com.codeborne.selenide.WebDriverRunner;

import switches.webelements.CatlogPageElements;

public class CatlogPage extends CatlogPageElements {

	public CatlogPage() {
		WebDriver webDriver = WebDriverRunner.getWebDriver();
		webDriver.switchTo().defaultContent();
	}

	public void logout() {
		logoutButton.click();
	}
	public void changeToMainContent() {
	    WebDriver webDriver = WebDriverRunner.getWebDriver();
        webDriver.switchTo().frame("maincontent");
	}
	public void switchingPortsPortConfigutation() {
		switching.click();
		ports.click();
		portConfiguration.click();
		WebDriver webDriver = WebDriverRunner.getWebDriver();
		webDriver.switchTo().frame("maincontent");
	}
	   public void systemInformation() {
	        system.click();
	        management.click();
	        systemInformation.click();
	        WebDriver webDriver = WebDriverRunner.getWebDriver();
	        webDriver.switchTo().frame("maincontent");
	    }
}
