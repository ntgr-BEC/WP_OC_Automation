package orbi.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

/**
 * @author Sujuan.Li
 *
 */

public class OrbiLoginPageElements {
    
    public SelenideElement homeLogin   = $(Selectors.byId("ancNinHm"));
    public String          sloginEmail = "loginEmail";
    public SelenideElement loginEmail  = $(Selectors.byName(sloginEmail));
    public SelenideElement loginPwd    = $(Selectors.byId("passwordField"));
    public SelenideElement loginButton = $(".loginBtnSection");

    public SelenideElement loginEmailNew  = $x("//*[@id=\"input_0\"]");
    public SelenideElement loginPwdNew    = $x("//*[@id=\"input_1\"]");
    public SelenideElement loginButtonNew = $("form[name*=loginForm] button");
    public SelenideElement logoutButton = $("#logout");
    public SelenideElement basicmanue  = $x("//*[@id=\"basic_label\"]/div[2]/b/span");
    /*dialog */
    public SelenideElement modifypasswordokbutton = $x("//span[text()=\"OK\"]/..");
    public SelenideElement modifypasswordokbutton1 = $x("/html/body/div[3]/div/div[2]/div/div[1]/div/div/div[2]/button");
    public SelenideElement modifypasswordokbutton2 = $x("/html/body/div[5]/div/div[2]/div/div[1]/div/div/div[2]/button");
    
    /*For br100*/
    public SelenideElement loginUsernamebr100 = $("#userName");
    public SelenideElement loginPasswordbr100 = $("#password");
    public SelenideElement loginButton100 = $("#signIn_btn");
    
    public SelenideElement multiloginconfirmyes = $x("//*[@id='yes' or text()='Yes']");
    public SelenideElement logindetailbutton = $x("//*[@id=\"details-button\"]");
    public SelenideElement loginproceeslink = $("#proceed-link");
    
    /*For new SXK50*/
    public SelenideElement loginUsernamesxk50 = $("#login_username");
    public SelenideElement loginPasswordsxk50 = $("#login_password");
    public SelenideElement loginButtonsxk50 = $("#login");
    public SelenideElement cancelButtonsxk50 = $("#cancel");
    
}

