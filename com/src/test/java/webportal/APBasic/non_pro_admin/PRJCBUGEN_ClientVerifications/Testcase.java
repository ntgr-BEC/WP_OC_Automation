package webportal.APBasic.non_pro_admin.PRJCBUGEN_ClientVerifications;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesApIpSettingsPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("APBasic.non_pro_admin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_ClientVerifications") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify connected client is showing in various pages ") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_ClientVerifications") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
        new WirelessQuickViewPage().deleteSsidYes("ClientVerifications");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Edit Wifi ssid and let client connect it;")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "ClientVerifications");
        ssidInfo.put("Security", "Open");
        new WirelessQuickViewPage().addSsid1(ssidInfo);

        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().editWifiYes(ssidInfo);

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID ClientVerifications")
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect ClientVerifications 123456798 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect ClientVerifications 123456798 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");
    }

    @Step("Test Step 3: Check whether connected connect is shown in client list;")
    public void step3() {
        
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        assertTrue(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Connected Client not showing on wireless page.");
        logger.info("Connected client details is showned on wireless page");
        
        assertTrue(new DevicesDashPage().verifyNoOfClientsinDeviceDashboardPage(), "Client not showing on device dashboard page." );
        logger.info("Connected client details is showned on device dashboard page");
        
        WebCheck.checkHrefIcon(URLParam.hrefClients);
        assertTrue(new WirelessQuickViewPage(false).checkClientConnect(WebportalParam.clientwlanmac), "Connected Client not showing on clients page.");
        logger.info("Connected client details is showned on clients page");
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage(false).clientsTab.click();
        assertTrue(new WirelessQuickViewPage(false).checkClientConnect(WebportalParam.clientwlanmac), "Connected Client not showing on inside AP client page.");
        logger.info("Connected client details is showned under AP Clients page");
        
        assertTrue(new SummaryPage().verifyNoOfClientsinDeviceSummaryPage(WebportalParam.location1), "Client not showing on summary dashboard page." );
        logger.info("Connected client details is showned on Summary Page");
     
    }

}
