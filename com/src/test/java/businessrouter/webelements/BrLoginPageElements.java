package businessrouter.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

/**
 * @author Sujuan.Li
 *
 */

public class BrLoginPageElements {
	
	public SelenideElement homeLogin   = $(Selectors.byId("ancNinHm"));
    public String          sloginEmail = "loginEmail";
    public SelenideElement loginEmail  = $(Selectors.byName(sloginEmail));
    public SelenideElement loginPwd    = $(Selectors.byId("passwordField"));
    public SelenideElement loginButton = $(".loginBtnSection");

    public SelenideElement loginEmailNew  = $x("//*[@id=\"input_0\"]");
    public SelenideElement loginPwdNew    = $x("//*[@id=\"input_1\"]");
    public SelenideElement loginButtonNew = $("form[name*=loginForm] button");
    public SelenideElement logoutButton = $x("//i[@class=\"anticon anticon-logout\"]");
    /*dialog */
    public SelenideElement modifypasswordokbutton = $x("//span[text()=\"OK\"]/..");
    public SelenideElement modifypasswordokbutton1 = $x("/html/body/div[3]/div/div[2]/div/div[1]/div/div/div[2]/button");
    public SelenideElement modifypasswordokbutton2 = $x("/html/body/div[5]/div/div[2]/div/div[1]/div/div/div[2]/button");
    
    /*For br100*/
    public SelenideElement loginUsernamebr100 = $("#userName");
    public SelenideElement loginPasswordbr100 = $("#password");
    public SelenideElement loginButton100 = $("#signIn_btn");
}

