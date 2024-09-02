package switches.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

public class SwitchesLoginPageElements {
	public  SelenideElement userName=$(Selectors.byName("uname"));
	public  SelenideElement passWord= $(Selectors.byName("pwd"));
	public  SelenideElement loginButtong= $("#login_button");
	public  SelenideElement notice=$("[aid=\"AlertMsgButton_1\"]");

}
