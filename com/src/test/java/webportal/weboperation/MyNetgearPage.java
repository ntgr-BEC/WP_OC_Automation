/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.SelenideTargetLocator;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.AccountPageElement;
import webportal.webelements.HamburgerMenuElement;
import webportal.webelements.mynetgearElement;

/**
 * @author Shoib
 */
public class MyNetgearPage extends mynetgearElement {
 
    public boolean LogintomynetgearaccountandChangeCountry(Map<String, String> map) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        boolean result = false;
        String oldTitle = Selenide.title();
        System.out.println(oldTitle);
        System.out.println("Starting for Mynetgear UI");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        open("https://tridiontest.netgear.com/mynetgear/register/register.aspx");
        String NewTitle = Selenide.title();
        System.out.println(NewTitle);
        MyCommonAPIs.waitElement(NetgearLogo);
        if (EmailIDField.isDisplayed() & PasswordField.isDisplayed())
        {
   //         EmailIDField.sendKeys(map.get("Email Address"));
            EmailIDField.sendKeys("shoibinsight9@yopmail.com");
            PasswordField.sendKeys("Netgear1@");
            EmailIDField.sendKeys(map.get("Email Address"));
   //         EmailIDField.sendKeys("shoibinsight@yopmail.com");
            PasswordField.sendKeys("Netgear#123");
            EmailIDField.sendKeys(map.get("Email Address"));
   //         EmailIDField.sendKeys("shoibinsight9@yopmail.com");
            PasswordField.sendKeys("Netgear1@");
            MyCommonAPIs.waitElement(MyLoginButton);
            MyLoginButton.click();
//            MyCommonAPIs.waitElement(NoProductRegPopUp);
//            NoProductRegPopUp.click();
            MyCommonAPIs.waitElement(MyProfileMenuButton);
            MyProfileMenuButton.click();
            MyCommonAPIs.waitElement(EmailVerifyCancelButton);
            EmailVerifyCancelButton.click();
            MyCommonAPIs.waitElement(EditProfileButton);
            EditProfileButton.click();
            MyCommonAPIs.waitElement(EditProfileLabel);
            MyCommonAPIs.sleepi(5);
            EditProfileCountry.selectOptionContainingText("Canada");
            EditProfileSaveButton.click();
            MyCommonAPIs.waitElement(EmailVerifyCancelButton);
            EmailVerifyCancelButton.click();
            
        
            ProfileEditBackButton.click();
            MyCommonAPIs.waitElement(ProfileBackButton);
            ProfileBackButton.click();
            MyCommonAPIs.waitElement(NoProductRegPopUp);
            NoProductRegPopUp.click();
        
            MyCommonAPIs.sleepi(5);
            EditProfileCountry.selectOptionContainingText("Canada");
            EditProfileSaveButton.click();
            MyCommonAPIs.waitElement(EmailVerifyCancelButton);
            EmailVerifyCancelButton.click();        }
          MyCommonAPIs.sleepi(5);

        return result;
    }
    
    public boolean BrowserWindowCheck(Map<String, String> accountInfo) {
        // TODO Auto-generated method stub
        boolean result = false;
        TermsConditionLink.scrollIntoView(true);
        MyCommonAPIs.sleepi(3);
        TermsConditionLink.click();
        MyCommonAPIs.sleepi(5);
        PrivacyPolicy.click();
        MyCommonAPIs.sleepi(5);
        Community.click();
        Selenide.switchTo().window("Insight Network Management System - NETGEAR Communities");
//        Selenide.switchTo().window(3);
        MyCommonAPIs.waitElement(SearchInput);
        SearchInput.sendKeys("Shoib");
        SearchButton.click();
        MyCommonAPIs.sleepi(5);
  //      MyCommonAPIs.waitElement(Searchthecommunity);
        Selenide.back();
        System.out.println("Switching back to Default Page ----->");
        Selenide.switchTo().window("Insight Cloud Portal");
  //      Selenide.switchTo().defaultContent();l
        result = true;
        return result;
    }

    public boolean VerifytoBuySubscription(Map<String, String> accountInfo) {
        // TODO Auto-generated method stub
        boolean result = false;
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        open(WebportalParam.serverUrlLogin);
        MyCommonAPIs.sleepi(5);
        return result;
    }


    public boolean LogintoCreatedAccount(Map<String, String> map) {
        // TODO Auto-generated method stub
        boolean result = false;
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        open(WebportalParam.serverUrlLogin);
        MyCommonAPIs.waitElement(NetgearLogo);
        if (EmailIDField.isDisplayed() & PasswordField.isDisplayed())
        {
            EmailIDField.sendKeys(map.get("Email Address"));
   //         EmailIDField.sendKeys("shoibinsight9@yopmail.com");
            PasswordField.sendKeys("Netgear1@");
            MyCommonAPIs.waitElement(MyLoginButton);
            MyLoginButton.click();
            MyCommonAPIs.sleepi(5);
            MyCommonAPIs.waitElement(sMyDevicesLink);
            if ($(sMyDevicesLink).exists() || $(sCurLocationElement).exists()) {
                result = true;
            }
            else
                throw new RuntimeException("Cannot open login page");
            }
        return result;

    }
}
