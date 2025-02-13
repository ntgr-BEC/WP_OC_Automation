package webportal.LoadBalancing.Pro.PRJCBUGEN_T19284;

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
    @Story("PRJCBUGEN_T19284") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, if anyone of the options (Max client, Client RX RRSI and channel utilization) or in any combination of an option can be configured") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T19284") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
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
        Map<String, String> cfgInfo = new HashMap<String, String>();
        cfgInfo.put("Radio 2.4 GHz", "5");
        cfgInfo.put("Radio 5 GHz", "5");
        cfgInfo.put("Radio 5 GHz High", "5");
        cfgInfo.put("Client 2.4 GHz", "20");
        cfgInfo.put("Client 5 GHz", "20");
        cfgInfo.put("Client 5 GHz High", "20");
        new WirelessQuickViewPage().enableLoadBalancing(cfgInfo);
        MyCommonAPIs.sleepi(60);
        
        boolean result = true;
        outer:for (Entry<String, String> entry : cfgInfo.entrySet()) {
            msg = new APUtils(WebportalParam.ap1IPaddress).getLoadBalancingStatus(entry.getKey(), WebportalParam.ap1Model);
            if (msg != "") {
                if (entry.getKey().indexOf("Radio") != -1) {
                    if (msg.indexOf("Status 1") == -1) {
                        result = false;
                        break outer;
                    }
                } else {
                    String nowNum = new WirelessQuickViewPage(false).getLoadBalancingTypeNowNum(entry.getKey());
                    if (msg.indexOf("Threshold " + nowNum) == -1 && msg.indexOf("Status 1") == -1) {
                        result = false;
                        break outer;
                    }
                }
            } else {
                result = false;
                break outer;
            }
        }
        assertTrue(result, "<1>Enable load balancing failed");
    }

    @Step("Test Step 3: Enable and config load balancing;")
    public void step3() {
        String msg = "";
        Map<String, String> cfgInfo = new HashMap<String, String>();
        cfgInfo.put("Client 2.4 GHz", "20");
        cfgInfo.put("Client 5 GHz", "20");
        cfgInfo.put("Client 5 GHz High", "20");
        cfgInfo.put("Channel 2.4 GHz", "70");
        cfgInfo.put("Channel 5 GHz", "70");
        cfgInfo.put("Channel 5 GHz High", "70");
        new WirelessQuickViewPage().enableLoadBalancing(cfgInfo);
        MyCommonAPIs.sleepi(60);

        boolean result = true;
        outer:for (Entry<String, String> entry : cfgInfo.entrySet()) {
            msg = new APUtils(WebportalParam.ap1IPaddress).getLoadBalancingStatus(entry.getKey(), WebportalParam.ap1Model);
            if (msg != "") {
                if (entry.getKey().indexOf("Channel") != -1) {
                    if (msg.indexOf("Status 1") == -1) {
                        result = false;
                        break outer;
                    }
                } else {
                    String nowNum = new WirelessQuickViewPage(false).getLoadBalancingTypeNowNum(entry.getKey());
                    if (msg.indexOf("Threshold " + nowNum) == -1 && msg.indexOf("Status 1") == -1) {
                        result = false;
                        break outer;
                    }
                }
            } else {
                result = false;
                break outer;
            }
        }
        assertTrue(result, "<2>Enable load balancing failed");
    }

    @Step("Test Step 4: Enable and config load balancing;")
    public void step4() {
        String msg = "";
        Map<String, String> cfgInfo = new HashMap<String, String>();
        cfgInfo.put("Radio 2.4 GHz", "5");
        cfgInfo.put("Radio 5 GHz", "5");
        cfgInfo.put("Radio 5 GHz High", "5");
        cfgInfo.put("Channel 2.4 GHz", "70");
        cfgInfo.put("Channel 5 GHz", "70");
        cfgInfo.put("Channel 5 GHz High", "70");
        new WirelessQuickViewPage().enableLoadBalancing(cfgInfo);
        MyCommonAPIs.sleepi(60);

        boolean result = true;
        outer:for (Entry<String, String> entry : cfgInfo.entrySet()) {
            msg = new APUtils(WebportalParam.ap1IPaddress).getLoadBalancingStatus(entry.getKey(), WebportalParam.ap1Model);
            if (msg != "") {
                if (msg.indexOf("Status 1") == -1) {
                    result = false;
                    break outer;
                }
            } else {
                result = false;
                break outer;
            }
        }
        assertTrue(result, "<3>Enable load balancing failed");
    }

}
