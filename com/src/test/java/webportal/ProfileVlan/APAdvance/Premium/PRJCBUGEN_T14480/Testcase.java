package webportal.ProfileVlan.APAdvance.Premium.PRJCBUGEN_T14480;

import static org.testng.Assert.assertTrue;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
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
    
    
    Random random = new Random();
    int randomNumber = random.nextInt(1000000);
    String SSID    = "SSID" + String.valueOf(randomNumber);

    @Feature("AdvanceAPFunctionally.AdvanceAPFunctionally") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14480") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify user is able to url filter functionally as non pro admin") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14480") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteBlacklistUrl("www.rediff.com");
        new WirelessQuickViewPage().disableUrlFiltering();
        new WirelessQuickViewPage().deleteSsidYes(SSID);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Add WIFI ssid and add url to blacklist,connect client to this ssid;")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo = new CommonDataType().PROFILE_VLAN;
        ssidInfo.put("SSID", SSID);
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");

        new WirelessQuickViewPage().addSsid(ssidInfo);

        new WirelessQuickViewPage().enableUrlFilteringAndAddUrl("www.rediff.com");

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID "+SSID+"")
                    .indexOf("true") != -1) {
                break;
            } else if (sum > 20) {
                assertTrue(false, "Client cannot connected.");
                break;
            }
            sum += 1;
        }
        
        MyCommonAPIs.sleepi(20);

        boolean result1 = true;
        if (!new Javasocket()
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect "+SSID+" 12345678 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            MyCommonAPIs.sleepi(20);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect "+SSID+" 12345678 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");
    }

    @Step("Test Step 3: Check whether user is able to open url or not;")
    public void step3() {
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        if (new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac)) {
            String ipaddress = new WirelessQuickViewPage().getClientIP(WebportalParam.clientwlanmac);
            while (true) {
                String output = new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                        "ping www.rediff.com " + ipaddress);
                System.out.println(output);
                if (output.indexOf("could not find host") == -1) {
                    assertTrue(output.indexOf("Lost = 4") != -1 || output.indexOf("Lost = 3") != -1, "Blacklist not take effect.");
                    break;
                }
            }
        } else {
            assertTrue(false, "Client cannot connected.");
        }

        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }

}
