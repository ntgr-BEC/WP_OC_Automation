package webportal.LoadBalancing.Premium.PRJCBUGEN_T19277;

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
    @Story("PRJCBUGEN_T19277") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, Max number of clients can be set to each band 2.4GHz, 5GHz and 5GHz high individually") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T19277") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().deleteSsidYes("apwp19277");
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
        ssidInfo.put("SSID", "apwp19277");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        Map<String, String> cfgInfo = new HashMap<String, String>();
        cfgInfo.put("Client 2.4 GHz", "50");
        cfgInfo.put("Client 5 GHz", "50");
        cfgInfo.put("Client 5 GHz High", "50");
        new WirelessQuickViewPage().enableLoadBalancing(cfgInfo);
        
        boolean result = true;
        outer: for (Entry<String, String> entry : cfgInfo.entrySet()) {
            if (!new WirelessQuickViewPage().checkLoadBalancingStatus(entry.getKey()).equals("Green")) {
                result = false;
                break outer;
            }
            
            MyCommonAPIs.sleepi(60);
            msg = new APUtils(WebportalParam.ap1IPaddress).getLoadBalancingStatus(entry.getKey(), WebportalParam.ap1Model);
            String nowNum = new WirelessQuickViewPage(false).getLoadBalancingTypeNowNum(entry.getKey());
            if (msg != "") {
                if (msg.indexOf("Threshold " + nowNum) == -1 && msg.indexOf("Status 1") == -1) {
                    result = false;
                    break outer;
                }
            }
        }
        assertTrue(result, "<1>Enable load balancing failed");
    }

}
