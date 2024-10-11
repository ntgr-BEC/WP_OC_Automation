package webportal.NightlyBuild.PRJCBUGEN_T14272;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("NightlyBuild") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14272") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify create ssid functionally") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14272") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp14272");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

//        handle.gotoLoction();
    }

//    @Step("Test Step 2: Add WIFI ssid and check ssid config successful;")
//    public void step2() {
//        new WirelessQuickViewPage().deleteALLSSID();
//        Map<String, String> ssidInfo = new HashMap<String, String>();
//        ssidInfo.put("SSID", "apwp14272");
//        ssidInfo.put("Security", "WPA2 Personal Mixed");
//        ssidInfo.put("Password", "123456798");
//        new WirelessQuickViewPage().addSsid1(ssidInfo);
//
//    }
}
