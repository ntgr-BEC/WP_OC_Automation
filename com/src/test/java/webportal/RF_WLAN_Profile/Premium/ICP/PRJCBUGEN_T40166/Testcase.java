package webportal.RF_WLAN_Profile.Premium.ICP.PRJCBUGEN_T40166;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Pratik
 *
 */
public class Testcase extends TestCaseBase {
    
    Map<String, String> ssidInfo = new HashMap<String, String>();

    @Feature("RF_WLAN_Profile.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case
    @Story("PRJCBUGEN_T40166") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the behaviour of the AP when RF Profile is assigned  which is configured with Instant captive portal") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40166") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("PRJCBUGEN_T40166");
        System.out.println("start to do tearDown");

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
       
    }

    @Step("Test Step 2: Delete device and enable IGMP")
    public void step2() {
           
        ssidInfo.put("SSID", "PRJCBUGEN_T40166");
        ssidInfo.put("Security", "WPA2 Personal Mixed");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(ssidInfo);
       
    }
    
    
    @Step("Test Step 3: verify Rate Limit and ARS tabs are not visible after enabling customer profile")
    public void step3() {
        ssidInfo.put("custom", "enable");
        new WirelessQuickViewPage().checkRateLimitARSTabsVisible(ssidInfo);
    }
    
    @Step("Test Step 4: Add WIFI ssid and enable instant captive portal, check client connect wifi;")
    public void step4() {

        Map<String, String> icpInfo = new HashMap<String, String>();
        icpInfo.put("Portal Name", "welcome to Automation");
        icpInfo.put("Welcome Headline", "Automation test in BEC");
        icpInfo.put("Captive Portal Logo", "DEFAULT_LOGO");
        icpInfo.put("Desktop Background Image", "DEFAULT_BG");
        icpInfo.put("Landing Page URL", "https://www.rediff.com");
        icpInfo.put("Session Duration", "15 min");
        icpInfo.put("Step Type", "Authentication Method");
        icpInfo.put("Login Modes", "Facebook.");
        new WirelessQuickViewPage().enableCaptivePortalType(ssidInfo.get("SSID"), icpInfo);

        MyCommonAPIs.sleepi(1 * 60);
        
       

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID PRJCBUGEN_T40166")
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect PRJCBUGEN_T40166 123456798 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            MyCommonAPIs.sleepi(20);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect PRJCBUGEN_T40166 123456798 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");

    }

    @Step("Test Step 5: Check whether captive portal page is shown or not;")
    public void step5() {
        MyCommonAPIs.sleepsync();


        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        assertTrue(
                new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                        "WAFruncaptive PRJCBUGEN-T16635.py www.rediff.com test test").indexOf("finalresult: 1") != -1,
                "Captive portal not take effect.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }   

}
