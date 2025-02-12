package webportal.LoadBalancing.Premium.PRJCBUGEN_T19279;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
    @Story("PRJCBUGEN_T19279") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, Maximum channel utilization has legends > Excellent, Good, Fair, Poor") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T19279") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().deleteSsidYes("apwp19279");
        new WirelessQuickViewPage().disableLoadBalancing();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

        handle.gotoLoction();
        // new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Enable and config load balancing;")
    public void step2() {
        String msg = "";
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp19279");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        Map<String, String> cfgInfo = new HashMap<String, String>();
        cfgInfo.put("Channel 2.4 GHz", "50");
        cfgInfo.put("Channel 5 GHz", "50");
        cfgInfo.put("Channel 5 GHz High", "50");
        new WirelessQuickViewPage().enableLoadBalancing(cfgInfo);

        boolean result = true;
        outer: for (Entry<String, String> entry : cfgInfo.entrySet()) {
            if (!new WirelessQuickViewPage().checkLoadBalancingStatus(entry.getKey()).equals("Green")) {
                result = false;
                break outer;
            }
        }
        assertTrue(result, "<1>Enable load balancing failed");
    }

    @Step("Test Step 3: Enable and config load balancing;")
    public void step3() {
        Map<String, String> cfgInfo = new HashMap<String, String>();
        cfgInfo.put("Channel 2.4 GHz", "52");
        cfgInfo.put("Channel 5 GHz", "52");
        cfgInfo.put("Channel 5 GHz High", "52");
        new WirelessQuickViewPage().enableLoadBalancing(cfgInfo);

        boolean result = true;
        outer: for (Entry<String, String> entry : cfgInfo.entrySet()) {
            if (!new WirelessQuickViewPage().checkLoadBalancingStatus(entry.getKey()).equals("Green")) {
                result = false;
                break outer;
            }
        }
        assertTrue(result, "<2>Enable load balancing failed");
    }

    @Step("Test Step 4: Enable and config load balancing;")
    public void step4() {
        Map<String, String> cfgInfo = new HashMap<String, String>();
        cfgInfo.put("Channel 2.4 GHz", "60");
        cfgInfo.put("Channel 5 GHz", "60");
        cfgInfo.put("Channel 5 GHz High", "60");
        new WirelessQuickViewPage().enableLoadBalancing(cfgInfo);

        boolean result = true;
        outer: for (Entry<String, String> entry : cfgInfo.entrySet()) {
            if (!new WirelessQuickViewPage().checkLoadBalancingStatus(entry.getKey()).equals("Blue")) {
                result = false;
                break outer;
            }
        }
        assertTrue(result, "<3>Enable load balancing failed");
    }
    
    @Step("Test Step 5: Enable and config load balancing;")
    public void step5() {
        Map<String, String> cfgInfo = new HashMap<String, String>();
        cfgInfo.put("Channel 2.4 GHz", "73");
        cfgInfo.put("Channel 5 GHz", "73");
        cfgInfo.put("Channel 5 GHz High", "73");
        new WirelessQuickViewPage().enableLoadBalancing(cfgInfo);

        boolean result = true;
        outer: for (Entry<String, String> entry : cfgInfo.entrySet()) {
            if (!new WirelessQuickViewPage().checkLoadBalancingStatus(entry.getKey()).equals("Orange")) {
                result = false;
                break outer;
            }
        }
        assertTrue(result, "<4>Enable load balancing failed");
    }
    
    @Step("Test Step 6: Enable and config load balancing;")
    public void step6() {
        Map<String, String> cfgInfo = new HashMap<String, String>();
        cfgInfo.put("Channel 2.4 GHz", "80");
        cfgInfo.put("Channel 5 GHz", "80");
        cfgInfo.put("Channel 5 GHz High", "80");
        new WirelessQuickViewPage().enableLoadBalancing(cfgInfo);

        boolean result = true;
        outer: for (Entry<String, String> entry : cfgInfo.entrySet()) {
            if (!new WirelessQuickViewPage().checkLoadBalancingStatus(entry.getKey()).equals("Red")) {
                result = false;
                break outer;
            }
        }
        assertTrue(result, "<5>Enable load balancing failed");
    }

}
