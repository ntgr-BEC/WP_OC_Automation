package webportal.ProPremiumAccOnboardAPandDeleteNotifications.PRJCBUGEN_T50003;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {


    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num) + "@mailinator.com";
    String organizationName = "TEST14342";
    
  

    @Feature("ProPremiumAccOnboardAPandDeleteNotifications") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T50003") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Delete Notifications from Insight Premium and Pro accounts") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T50003") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        
        new AccountPage().deleteOneLocation("OnBoardingTest");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
   
    @Step("Test Step 1: Login to Insight Pro Account WP success")
    public void step1() {
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);        
    }
    
    @Step("Test Step 2: Delete Notifications and delete organization from pro account")
    public void step2() {
        

        assertTrue(new HamburgerMenuPage(false).deleteNotificationsfromProAcount(), "Notifications are not deleted successfully");
     
        new HamburgerMenuPage().proAccLogout();
        
    }
    
    @Step("Test Step 3: Login to Insight Premium Account WP success")
    public void step3() {
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
    }
    
    @Step("Test Step 4: Delete Notifications and delete location from premium account")
    public void step4() {
        
        assertTrue(new HamburgerMenuPage(false).deleteNotificationsfromPremiumAcount(), "Notifications are not deleted successfully");
        
        
    }

}
