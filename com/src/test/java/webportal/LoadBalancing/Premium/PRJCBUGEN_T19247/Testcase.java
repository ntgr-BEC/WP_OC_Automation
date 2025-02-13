package webportal.LoadBalancing.Premium.PRJCBUGEN_T19247;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.CopyConfigurationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    String              msg     = "";
    Map<String, String> cfgInfo = new HashMap<String, String>();

    @Feature("LoadBalancing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T19247") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the cancel button functionality on the load balancing screen, on cancel button click user should reset the data and disable the save button") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T19247") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
//        new WirelessQuickViewPage().disableLoadBalancing();
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
        cfgInfo.put("Radio 2.4 GHz", "27");
        cfgInfo.put("Radio 5 GHz", "27");
        cfgInfo.put("Radio 5 GHz High", "27");
        cfgInfo.put("Client 2.4 GHz", "28");
        cfgInfo.put("Client 5 GHz", "28");
        cfgInfo.put("Client 5 GHz High", "28");
        cfgInfo.put("Channel 2.4 GHz", "58");
        cfgInfo.put("Channel 5 GHz", "58");
        cfgInfo.put("Channel 5 GHz High", "58");
        cfgInfo.put("Check Button", "true");
        new WirelessQuickViewPage().enableLoadBalancing(cfgInfo);
        
        MyCommonAPIs.sleepi(60);
        
        
        boolean result = true;
        outer: for (Entry<String, String> entry : cfgInfo.entrySet()) {
            if(entry.getKey() == "Check Button") {
                continue;
            }  
            msg = new APUtils(WebportalParam.ap1IPaddress).getLoadBalancingStatus(entry.getKey(), WebportalParam.ap1Model);
            if (msg != "") {
                System.out.println("check message entered");
                System.out.println(msg);
                
                if (entry.getKey().indexOf("Radio") != -1 || entry.getKey().indexOf("Channel") != -1) {
                    System.out.println("inside radio");
                    if (msg.indexOf("Status 0") == -1) {
                        result = false;
                        break outer;
                    }
                } else {
                    if (msg.indexOf("Threshold 28") != -1 || msg.indexOf("Status 0") == -1) {
                        System.out.println("inside threshold");
                        result = false;
                        break outer;
                    }
                }
            } else {
                System.out.println("did not enter message ");
                result = false;
                break outer;
            }
        }
        assertTrue(result, "<1>Enable load balancing failed");
    }

}
