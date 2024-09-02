package webportal.ScheduledWiFi.Premium.PRJCBUGEN_T16060;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

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
 * @author Pragya
 *
 */
public class Testcase extends TestCaseBase {
    Map<String, String> locationInfo = new HashMap<String, String>(); 

    @Feature("ScheduledWiFi.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16060") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that other than the Scheduled time SSID stays in disabled state.Premium")// It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16060") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp16060");
        new WirelessQuickViewPage().deleteWifiSchedulesYes("scheduled16060");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
//        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: check other than the Scheduled time SSID stays in disabled state;")
    public void step2() {

        locationInfo.put("SSID", "apwp16060");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        locationInfo.put("Schedule Name", "scheduled16060");
        locationInfo.put("unselect day", "yes");
        
        new WirelessQuickViewPage().scheduleWifi(locationInfo);
        System.out.println("scdeule is done" );

        
        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp16060")
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp16060 123456798 WPA2PSK aes")
                .equals("true")) {
            result1 = true;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp16060 123456798 WPA2PSK aes")
                    .equals("true")) {
                result1 = false;
            }
        }

        assertTrue(result1, "Client connected.");
    }
    


    @Step("Test Step 3: Check whether connected connect is shown in client list;")
    public void step3() {

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        assertTrue(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Client cannot connected.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }
    
    @Step("Test Step 4: check other than the Scheduled time SSID stays in disabled state;")
    public void step4() {
        new WirelessQuickViewPage().editWifiSchedule(locationInfo);
        MyCommonAPIs.sleep(3000);   
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        assertFalse(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Client cannot connected.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
        
    }

}


   
