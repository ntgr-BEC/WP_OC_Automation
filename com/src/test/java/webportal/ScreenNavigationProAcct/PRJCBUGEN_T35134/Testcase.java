package webportal.ScreenNavigationProAcct.PRJCBUGEN_T35134;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceBulkOnboardingPage;
import webportal.weboperation.DeviceScreenNavigationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Anusha Hdone
 *
 */
public class Testcase extends TestCaseBase {
    
    String locationName = "office1";
   
    @Feature("ScreenNavigationProAcct") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35134") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("IM-7.0-My devices icon navigation in the account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35134") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
  

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new AccountPage().enterLocation("office1");
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ap2serialNo);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {

      WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
      webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

      handle.gotoLoction();
    }
                      

    @Step("Test Step 2: Add AP to new location")
    public void step2() {
        
        handle.gotoLoction(locationName);
        
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap5macaddress);

        new DevicesDashPage(false).addNewdummyDevice(devInfo);
        
    }
       
    @Step("Test Step 3:Verify the deivces in organization device page;")
    public void step3() {
        
        assertTrue(new  DeviceScreenNavigationPage().checkDevices(WebportalParam.ap1serialNo)," Device is not found");
        MyCommonAPIs.sleepi(10);
        assertTrue(new  DeviceScreenNavigationPage().checkDevices(WebportalParam.ap2serialNo)," Device is not found");
    }
    }

