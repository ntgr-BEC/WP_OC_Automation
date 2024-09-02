package webportal.ClientIsolationWithDLAN.IncreaseClientIsolationFrom5to16.PRJCBUGEN_T40707;

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
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredVLANPageForVLANPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Pratik
 *
 */
public class Testcase extends TestCaseBase {
    
    String urls[] = {
            "www.rediff.com"
    };
    String vlan ="manvlan40707(20)";
    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("IncreaseClientIsolationFrom5to16") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40707") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that make changes in VLAN wizard and check in SSID page is showing properly or not and vice versa.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40707") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        System.out.println("start to do tearDown");
        handle.gotoLoction();
        new WirelessQuickViewPage().deleteSsidYes("apwp40707");
//        new WiredVLANPageForVLANPage().deleteVLAN("manvlan40707");

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Add SSID.")
    public void step2() {
        
        locationInfo.put("SSID", "apwp40707");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid1(locationInfo);
        
    }
    
    @Step("Test Step 3: Create VLAN with edit Network page and connect client")
    public void step3() {
        
//        new WiredVLANPageForVLANPage().CreateVLANforClientIsolation(vlan);
        assertTrue(new WirelessQuickViewPage().editSSIDandEnableClientIsolationAndSelectVLAN20(locationInfo.get("SSID"), urls, vlan),
                "SSID having Client isolation with VLAN20 is not saved");
        MyCommonAPIs.sleepi(180);
        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp40707")
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp40707 12345678 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp40707 12345678 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");
        MyCommonAPIs.sleepi(60);
        assertTrue(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Client cannot connected.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
        
    }
    
    @Step("Test Step 4: deslect VLAN20 and Select Default Vlan with Client Isolation and connect client")
    public void step4() {

        assertTrue(new WirelessQuickViewPage().deslectVLAN20andSelectDefaultVlanwithClientIsolation(locationInfo.get("SSID"), urls),
                "SSID having Client isolation with default vlan is not saved");
        MyCommonAPIs.sleepi(180);
        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp40707")
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp40707 12345678 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp40707 12345678 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");
        MyCommonAPIs.sleepi(60);
        assertTrue(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Client cannot connected.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
        
    }
    
}
