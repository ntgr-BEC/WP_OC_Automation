package webportal.MPSK.prouser.PRJCBUGEN_T29816;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    
    Map<String, String> mpskKeyInfo = new HashMap<String, String>();
    Map<String, String> mpskKeyInfo1 = new HashMap<String, String>();

    @Feature("MPSK") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T29816") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, clients can connect with different password for same SSID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T29816") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteALLSSID();
        new WirelessQuickViewPage().deleteAllMPSKKey();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        new HamburgerMenuPage(false).closeLockedDialog();
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);

        handle.gotoLoction();
        // new DevicesDashPage().checkDeviceInAdminAccount();
    }
    
    @Step("Test Step 2: Add MPSK Key.")
    public void step2() {
        
        new WirelessQuickViewPage().gotoAddMPSKKey();
               
       
        mpskKeyInfo.put("passwordMPSK", "Netgear1@");
        mpskKeyInfo.put("mpskKeyName", "MPSK1");
        assertTrue(new WirelessQuickViewPage(false).addMPSKKeys(mpskKeyInfo),"MPSK Key is not added Successfully");   
        
        mpskKeyInfo1.put("passwordMPSK", "Netgear2@");
        mpskKeyInfo1.put("mpskKeyName", "MPSK2");
        assertTrue(new WirelessQuickViewPage(false).addMPSKKeys(mpskKeyInfo1),"MPSK Key is not added Successfully");           
        
        
    }

    @Step("Test Step 3: add ssid")
    public void step3() {
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp14270");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(locationInfo);
        
        new WirelessQuickViewPage(false).editAndMPSK1st(locationInfo.get("SSID"), mpskKeyInfo.get("mpskKeyName"));
        new WirelessQuickViewPage(false).editAndMPSK2st(locationInfo.get("SSID"), mpskKeyInfo1.get("mpskKeyName"));
                   
        
    }
    
    @Step("Test Step 4: Add WIFI ssid and now connect client to this ssid;")
    public void step4() {

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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14270 Netgear1@ WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14270 Netgear1@ WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");
    }

    @Step("Test Step 5: Check whether connected connect is shown in client list;")
    public void step5() {
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        assertTrue(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Client cannot connected.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    
}
    
    @Step("Test Step 6: Add WIFI ssid and now connect client to this ssid;")
    public void step6() {

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(30);
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14270 Netgear2@ WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14270 Netgear2@ WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");
    }

    @Step("Test Step 7: Check whether connected connect is shown in client list;")
    public void step7() {
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        assertTrue(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Client cannot connected.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    
}
}