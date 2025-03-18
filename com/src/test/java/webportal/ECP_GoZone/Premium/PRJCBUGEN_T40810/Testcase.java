package webportal.ECP_GoZone.Premium.PRJCBUGEN_T40810;

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
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author RaviShankar S
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("ECP_GoZone") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40810") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Error message is shown or not ,When security is selected as WPA2/3 enterprise then we should not allow ECP to enabled.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T40810") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteALLSSID();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();

    }

    @Step("Test Step 2: Add WIFI ssid with WPA2 Enterprise security and enable ECP;")
    public void step2() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp40810");
        locationInfo.put("Security", "WPA2 Enterprise");
        locationInfo.put("ECP", "true");
        assertTrue(new WirelessQuickViewPage().addECP(locationInfo), "POP not appered");       

    }

    @Step("Test Step 3: Add WIFI ssid with WPA2 Enterprise security and enable ECP;")
    public void step3() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp40810a");
        locationInfo.put("Security", "WPA3 Enterprise");
        locationInfo.put("ECP", "true");
        assertTrue(new WirelessQuickViewPage().addECP(locationInfo), "POP not appered");

    }

}
