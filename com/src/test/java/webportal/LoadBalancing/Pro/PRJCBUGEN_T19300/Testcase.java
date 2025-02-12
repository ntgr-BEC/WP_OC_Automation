package webportal.LoadBalancing.Pro.PRJCBUGEN_T19300;

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
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("LoadBalancing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T19300") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the user should be able to configure the channel load allowed on 5 GHz low channel") // It's a testcase title from
                                                                                                                      // Jira Test Case.
    @TmsLink("PRJCBUGEN-T19300") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().deleteSsidYes("apwp19300");
        new WirelessQuickViewPage().disableLoadBalancing();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        // new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Enable and config load balancing;")
    public void step2() {
        String msg = "";
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp19300");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        Map<String, String> cfgInfo = new HashMap<String, String>();
        cfgInfo.put("Channel 5 GHz", "50");
        new WirelessQuickViewPage().enableLoadBalancing(cfgInfo);
        MyCommonAPIs.sleepi(60);
        if (new WirelessQuickViewPage().checkLoadBalancingStatus("Channel 5 GHz").equals("Green")) {
            boolean result = false;
            msg = new APUtils(WebportalParam.ap1IPaddress).getLoadBalancingStatus("Channel 5 GHz", WebportalParam.ap1Model);
            if (msg != "") {
                if (msg.indexOf("Status 1") != -1) {
                    result = true;
                }
            }
            assertTrue(result, "<2>Enable load balancing failed");
        } else {
            assertTrue(false, "<1>Enable load balancing failed");
        }
    }

}
