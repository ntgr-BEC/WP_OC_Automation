/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author zheli
 */
public class WebportalLoginPageElement extends MyCommonAPIs {
    public SelenideElement homeLogin   = $(Selectors.byId("ancNinHm"));
    public String          sloginEmail = "loginEmail";
    public SelenideElement loginEmail  = $(Selectors.byName(sloginEmail));
    public SelenideElement loginPwd    = $(Selectors.byId("passwordField"));
    public SelenideElement loginButton = $(".loginBtnSection");

    public SelenideElement loginEmailNew1 = $x("//md-input-container[contains(@class,'md-block mbl5')]/input[@id='_ipEmlLgn']");
    public SelenideElement loginEmailNew  = $x("//*[@id=\"_ipEmlLgn\"]");
    public SelenideElement loginPwdNew1   = $x("//*[@id='input_1']");
    public SelenideElement loginPwdNew    = $("#searchinput");
    public SelenideElement loginButtonNew = $("form[name*=loginForm] button");
   

    public SelenideElement CheckWhatsNew = $(".WhatsNewModal.in button");

    public SelenideElement loginProgressBar = $(".loginOverlay");
    public SelenideElement loginErrorMsg    = $x("(//div[@class='AccountTopBlock']//div[contains(@class,'TostMsgBlock')])[1]");
    
    public SelenideElement settingsButton    = $("#darkThemeSideBarButton");
    public SelenideElement darkthemeInput    = $("#enableDarkMode");
    public SelenideElement darkthemeSpan    = $x("//*[@id='enableDarkMode']/../span");
    public SelenideElement sidebarCloseIcon    = $x("//*[contains(@class,'sideBarCloseIcon')]");
    public SelenideElement loginNowButton   = $x("//*[@id=\"button_premium\"]");
    public SelenideElement goToInsightAcc   = $x("//p/a[@data-target='.goToMyAccModal']");
    public SelenideElement YesButton        = $x("//button[@class='btn btn-primary' and text()='Yes']");
    public SelenideElement loginButton1 = $x("//button[@id='Login-btn']");
}
