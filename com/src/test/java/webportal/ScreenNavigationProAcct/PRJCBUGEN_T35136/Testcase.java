package webportal.ScreenNavigationProAcct.PRJCBUGEN_T35136;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DeviceBulkOnboardingPage;
import webportal.weboperation.DeviceScreenNavigationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Anusha H
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("ScreenNavigationProAcct") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35136") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Account Management page Navigation in the pro account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35136") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
  

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {

      WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
      webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
      
      handle.gotoLoction();
    }

    @Step("Test Step 2: Verify Purchase Order History page")
    public void step2() {
           
            assertTrue(new  DeviceScreenNavigationPage().verifyPurchaseOrderHistory(WebportalParam.ap1serialNo)," Purchase Order History is not complete");
            MyCommonAPIs.sleepi(15); 
     }
        
       
    @Step("Test Step 3: Verify Credit Allocation page")
    public void step3() {
           
            assertTrue(new  DeviceScreenNavigationPage().verifyCreditAllocation(WebportalParam.Organizations)," Credit Allocation is not complete");
            MyCommonAPIs.sleepi(15); 
    }
    
    @Step("Test Step 4: Verify Subscription page")
    public void step4() {
           
            assertTrue(new  DeviceScreenNavigationPage().verifySubscriptionPage(),"Subscription page  is not complete");
            MyCommonAPIs.sleepi(15); 
    
    }
    
    @Step("Test Step 5: Verify VPN services page")
    public void step5() {
           
            assertTrue(new  DeviceScreenNavigationPage().verifyVPNServices()," VPN services page  is not complete");
            MyCommonAPIs.sleepi(15); 
    
    }
    
    @Step("Test Step 6: Verify ICP Pro page")
    public void step6() {
           
            assertTrue(new  DeviceScreenNavigationPage().verifyICPProServices()," ICP Pro  page  is not complete");
            MyCommonAPIs.sleepi(15); 
    
    }
    
    @Step("Test Step 7: Verify MUB page")
    public void step7() {
           
            assertTrue(new  DeviceScreenNavigationPage().verifyMUBPage(),"MUB page  is not complete");
            MyCommonAPIs.sleepi(15); 
    
    }
    
    @Step("Test Step 8: Verify Manage Notifications page")
    public void step8() {
           
            assertTrue(new  DeviceScreenNavigationPage().verifyManageNotifications(),"Manage Notifications page  is not complete");
            MyCommonAPIs.sleepi(15); 
    
    }
    
}

