package webportal.APBasic.Band.PRJCBUGEN_T28353;

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
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("APBasic.Band") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28353") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify WPA3 Personal Security for 6Ghz Band") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T28353") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp28353");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin"); //must input admin or manager
    }

    @Step("Test Step 2: Edit Wifi ssid and let client connect it;")
    public void step2() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp28353");
        locationInfo.put("Security", "WPA3 Personal");
        locationInfo.put("Password", "123456798");
        locationInfo.put("Band", "Uncheck2.4 GHz");
        new WirelessQuickViewPage().addSsidALL(locationInfo);


//        int sum = 0;
//        while (true) {
//            MyCommonAPIs.sleepi(10);
//            if (new Javasocket()
//                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp28353")
//                    .indexOf("true") != -1) {
//                break;
//            } else if (sum > 30) {
//                assertTrue(false, "Client cannot connected.");
//                break;
//            }
//            sum += 1;
//        }
//
//        boolean result1 = true;
//        if (!new Javasocket()
//                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp28353 123456798 WPA3SAE aes")
//                .equals("true")) {
//            result1 = false;
//            if (new Javasocket()
//                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp28353 123456798 WPA3SAE aes")
//                    .equals("true")) {
//                result1 = true;
//            }
//        }
//
//        assertTrue(result1, "Client cannot connected.");
//    }
//
//    @Step("Test Step 3: Check whether connected connect is shown in client list;")
//    public void step3() {
//        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
//        assertTrue(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Client cannot connected.");
//        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }

        
}
