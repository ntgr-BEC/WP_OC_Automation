package webportal.EEM.Premium.PRJCBUGEN_T30966;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
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
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.webelements.WirelessQuickViewElement;

/**
 *
 * @author tejeshwini   K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("EEM") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T30966") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, when Antenna power save is enabled with clients connected, all antenna are turned on") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T30966") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().GoToEEM();
        new WirelessQuickViewPage(false).disableEEM();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Enable EEM and check whether it is push to AP")
    public void step2() {
        new WirelessQuickViewPage().GoToEEM();
        new WirelessQuickViewPage(false).EnableEEM();
        
        MyCommonAPIs.sleepi(120);
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getEEMEnableStatus(WebportalParam.ap1Model), "EEM is disabled after restoring");
    }
    
    @Step("Test Step 3: Add WIFI ssid and now connect client to this ssid;")
    public void step3() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp14270");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(locationInfo);

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp14270")
                    .indexOf("true") != -1) {
                break;
            } else if (sum > 30) {
                assertTrue(false, "Client cannot connected.");
                break;
            }
            sum += 1;
        }

        boolean result1 = true;
        if (!new Javasocket()
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14270 123456798 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14270 123456798 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");
    }


}
