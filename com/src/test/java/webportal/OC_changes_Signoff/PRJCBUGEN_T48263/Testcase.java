package webportal.OC_changes_Signoff.PRJCBUGEN_T48263;


import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;

public class Testcase extends TestCaseBase {
    final static Logger logger = Logger.getLogger("PRJCBUGEN_T00010");
    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String email = mailname + "@yopmail.com";
    

    @Feature("OC_changes_Signoff") 
    @Story("PRJCBUGEN_T48263") 
    @Description("Change Password") 
    @TmsLink("PRJCBUGEN_T48263") 

    @Test(alwaysRun = true, groups = "p2") 
    public void test() throws Exception {
        runTest(this);
    }
   
 // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create Netgear Account, select any supported country;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T15514");
        accountInfo.put("Email Address", email);
        accountInfo.put("Confirm Email", email);
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Canada");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    
    @Step("Test Step 2: Change login PWD , check the user should be able to login successfully;")
    public void step2() {
        
        new HamburgerMenuPage().changeAccountPwd("Netgear#123", WebportalParam.loginPassword);

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(email, WebportalParam.loginPassword);

        assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful());
    }
    

}
