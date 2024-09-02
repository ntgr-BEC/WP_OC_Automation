package webportal.ScreenNavigationProAcct.PRJCBUGEN_T34368;

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
import webportal.webelements.DeviceScreenNavigationElement;
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
    @Story("PRJCBUGEN_T34368") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Summary page Navigation in the pro account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T34368") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: Verify Summary page;")
    public void step2() {
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        assertTrue(new  DeviceScreenNavigationPage().verifyDeviceSummary()," Summary Screen is not complete");
        MyCommonAPIs.sleepi(15); 
    }
    
   
    @Step("Test Step 3: Verify device Details page")
    public void step3() {
        
        Map<String, String> deviceDetails = new HashMap<String, String>();
        deviceDetails.put("DeviceName",WebportalParam.ap1deveiceName );
        deviceDetails.put("SerialNo",WebportalParam.ap1deveiceName);
        deviceDetails.put("MacAddress",WebportalParam.ap1macaddress);
        deviceDetails.put("Model", WebportalParam.ap1Model);
        deviceDetails.put("IPAddress",WebportalParam.ap1IPaddress);
        
        assertTrue(new DeviceScreenNavigationPage().verifyDeviceDetails(deviceDetails),"Device Details Screen is not complete");
        
    }
    }
