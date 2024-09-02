/**
 * 
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

/**
 * @author zheli
 *
 */
public class RegisterPageElement {
    public SelenideElement emailAdress     = $(Selectors.byName("signupEmail"));
    public SelenideElement password        = $(Selectors.byName("signupPassword"));
    public SelenideElement confirmPassword = $(Selectors.byName("passwordConfirm"));
    public SelenideElement firstname       = $(Selectors.byName("firstname"));
    public SelenideElement lastName        = $(Selectors.byId("lastName"));
    public SelenideElement countryCode     = $(Selectors.byId("countryCode"));
    public SelenideElement subscription    = $x("//fieldset/section[7]/label/i");
    public SelenideElement termsCondition  = $(Selectors.byXpath("//fieldset/section[8]/label/i"));
    public SelenideElement signUpButton    = $(".loginBtnSection");
    public SelenideElement agreeButton     = $("#i-agree");

}
