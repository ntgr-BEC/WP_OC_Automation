package webportal.RF_WLAN_Profile.Premium.PRJCBUGEN_T39750;

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
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    Map<String, String> ssidInfo = new HashMap<String, String>();

    @Feature("RF_WLAN_Profile.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case
    @Story("PRJCBUGEN_T39750") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Check status of existing AP when user assigns an RF profile to the device") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T39750") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        //new DevicesDashPage().checkDeviceInAdminAccount();
       
    }

    @Step("Test Step 2: Assign/Unassign RF Profile and verify")
    public void step2() {
        new DevicesDashPage().GoToDevicesDashPage();
        String RFName = "Open Office";
        new DevicesDashPage().AssignRF(WebportalParam.ap1serialNo, RFName);       
        String RF = new DevicesDashPage().devicesRF(WebportalParam.ap1serialNo).getText();
        String RF1 = new DevicesDashPage().devicesRF(WebportalParam.ap2serialNo).getText();
        assertTrue(RF.contains(RFName),"RF is not set properly");
        assertTrue(RF1.contains("N/A"),"RF is not set properly");
        new DevicesDashPage().GoToDevicesDashPage();
        new DevicesDashPage().UNAssignRF(WebportalParam.ap1serialNo);             
        new DevicesDashPage().UNAssignRF(WebportalParam.ap2serialNo);
    }  
       
}
