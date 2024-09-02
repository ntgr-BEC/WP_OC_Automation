package webportal.ClientIsolationWithDLAN.IncreaseClientIsolationFrom5to16.PRJCBUGEN_T40704;

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
 * @author  Pratik
 *
 */
public class Testcase extends TestCaseBase {
    

    

    @Feature("IncreaseClientIsolationFrom5to16") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40704") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that if MPSK is enabled then Client Isolation should be hidden.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40704") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        System.out.println("start to do tearDown");
        handle.gotoLoction();
        new WirelessQuickViewPage().deleteSsidYes("apwp40704");
        new WirelessQuickViewPage().deleteMPSKKey();

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Add MPSK Key.")
    public void step2() {
        
        assertTrue(new WirelessQuickViewPage().addMPSKKey1(), "MPSK key is not added successfully");
        
    }
    
    @Step("Test Step 3: Test to verify that Under SSID check box should be displayed for client isolation.")
    public void step3() {
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp40704");
        locationInfo.put("Band", "Both");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addMPSKSsid1(locationInfo);
        
        assertTrue (new WirelessQuickViewPage().verifyEnableMPSKClientIsolationHide(locationInfo.get("SSID")), "Under SSID check box is not displayed for client isolation.");
    }
}
