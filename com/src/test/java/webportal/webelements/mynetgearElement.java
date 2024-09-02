/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author zheli
 */
public class mynetgearElement extends MyCommonAPIs {

    public SelenideElement      EmailIDField                      = $x("//input[@id='_ipEmlLgn']");
    public SelenideElement      PasswordField                     = $x("//input[@id='searchinput']");
    public SelenideElement      MyLoginButton                     = $x("//button[@id='Login-btn']");
    public SelenideElement      NetgearLogo                       = $x("//div[@id='netgear_logo']");
    public SelenideElement      NoProductRegPopUp                 = $x("//a[contains(text(),'x')]");
    public SelenideElement      MyProfileMenuButton               = $x("//a[contains(text(),'My Profile')]");
    public SelenideElement      EmailVerifyCancelButton           = $x("//md-dialog[@aria-label=\"Email Verification Reminder\"][1]/md-dialog-content[1]/div[3]/button[1]");
    public SelenideElement      EditProfileButton                 = $x("//span[contains(text(),'Edit Profile')]");
    public SelenideElement      EditProfileLabel                  = $x("//h3[contains(text(),'Edit Profile')]");
    public SelenideElement      ProfileBackButton                 = $x("//a[@class=\"back-button buttonPosition btnPosAccount\"]");
    public SelenideElement      ProfileEditBackButton             = $x("//a[@class=\"back-button buttonPosition btnPos\"]");
    
    public SelenideElement      TermsConditionLink                = $x("//a[contains(text(),'Terms of Use')]");
    public SelenideElement      PrivacyPolicy                     = $x("//a[contains(text(),'Privacy Policy')]");
    public SelenideElement      Community                         = $x("//a[contains(text(),'Community')]");
    public SelenideElement      SearchInput                       = $x("//input[@id='input-text']");
    public SelenideElement      SearchButton                      = $x("//div[@class=\"input-wrap\"][1]/button[1]");
    public SelenideElement      Searchthecommunity                = $x("//h2[contains(text(),'Search the Community')]");
    
    public SelenideElement      EditProfileCountry                = $x("//div[@class=\"md-block  loginField country\"][1]/select[1]");
    public SelenideElement      EditProfileSaveButton             = $x("//div[@class=\"buttonBlock margin-top-2\"][1]/button[1]");

    
    
    
    
    
    
}