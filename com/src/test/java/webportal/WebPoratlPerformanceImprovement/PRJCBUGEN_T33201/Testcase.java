package webportal.WebPoratlPerformanceImprovement.PRJCBUGEN_T33201;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import com.codeborne.selenide.Selenide;

/**
*
* @ Tejeshwini K V
*
*/
public class Testcase extends TestCaseBase {
    
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String organizationName = "PRJCBUGEN_T33692";

    @Feature("Web Portal Performance Improvements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33201") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user should able to login with premium and pro account with valid creds.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T33201") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage().deleteOrganizationNew(organizationName);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        
        Selenide.clearBrowserCookies();
        Selenide.refresh();
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T18721");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear1@");
        accountInfo.put("Confirm Password", "Netgear1@");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }
    
    @Step("Test Step 2: Create new location;")
    public void step2() {
        
        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);
        
    }

    @Step("Test Step 3: Navigate to Account Management, check upgrade to pro option and click on it;")
    public void step3() {
        System.out.println("starting with setup 2");
        assertTrue(new HamburgerMenuPage().insightPritoinsightPro(),"Failed navigate to Account Management");
        
    }
    
    @Step("Test Step 4: Fill the tset data into the form and click on upgrade button;")
    public void step4() {
        
        Map<String, String> businessInfo = new HashMap<String, String>();
        businessInfo.put("Business Name", "Netgear");
        businessInfo.put("Primary Address of Business", "test 1st");
        businessInfo.put("City", "NewYork");
        businessInfo.put("State", "test");
        businessInfo.put("Zip Code", "12345");
        businessInfo.put("Country", "United States of America");
        businessInfo.put("Business Phone Number", "1234567890");
        new HamburgerMenuPage(false).inputBusinessInfo(businessInfo);
        new HamburgerMenuPage(false).clickBusinessInfoPageButton();
        assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create pro account unsuccess."); 
        assertTrue(new HamburgerMenuPage(false).addLocationsToOrg(organizationName), "Location is not Successfully added to new created orgnizqation");
    }
    
    @Step("Test Step 5: logout and login agagin;")
    public void step5() {
        
        UserManage userManage = new UserManage();
        userManage.logout();
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(mailname + "@mailinator.com","Netgear1@");
        
    }
    
}
