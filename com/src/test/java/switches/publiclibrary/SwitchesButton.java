package switches.publiclibrary;

import org.openqa.selenium.WebDriver;

import com.codeborne.selenide.WebDriverRunner;

import switches.webelements.ButtonElements;

public class SwitchesButton {

	public SwitchesButton() {
		// TODO Auto-generated constructor stub
	}

	public static void apply() {
		WebDriver webDriver = WebDriverRunner.getWebDriver();
		webDriver.switchTo().defaultContent();
		ButtonElements.APPLY.click();
	}
}
