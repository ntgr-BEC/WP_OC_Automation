package webportal.APBasic.WirelessSecurity.Location.Premium.ICP.PRJCBUGEN_T35745;

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
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author sjena
 *
 */
public class Testcase extends TestCaseBase {
    
    Map<String, String> ssidInfo = new HashMap<String, String>();
    Random              r                        = new Random();
    int                 num                      = r.nextInt(10000000);
    

    @Feature("WirelessSecurity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35745") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Add ICP ssid With facebook and Fast Roaming enabled for pro admin user and client connectivity") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T35745") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp35745");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Add WIFI ssid and connect a client ")
    public void step2() {
        
        if (!new HamburgerMenuPage().checkCaptivePortalServicesCredits()) {
            assertTrue(false, "Account need to add instant captive portal key.");
        }

        ssidInfo.put("SSID", "apwp35745");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid(ssidInfo);
        //Test Step 3: enable 802.11kvr
        new WirelessQuickViewPage().enable80211(ssidInfo.get("SSID"));
        
        Map<String, String> icpInfo = new HashMap<String, String>();
        icpInfo.put("Portal Name", "BEC Automation ");
        icpInfo.put("Welcome Headline", "BEC Insight Automation");
        icpInfo.put("Captive Portal Logo", "DEFAULT_LOGO");
        icpInfo.put("Desktop Background Image", "DEFAULT_BG");
        icpInfo.put("Landing Page URL", "https://www.rediff.com");
        icpInfo.put("Session Duration", "5 min");
        icpInfo.put("Step Type", "Authentication Method");
        icpInfo.put("Login Modes", "Facebook.");
        new WirelessQuickViewPage().enableCaptivePortalType(ssidInfo.get("SSID"), icpInfo);
        MyCommonAPIs.sleepi(3 * 60);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        assertTrue(new WirelessQuickViewPage().connectClient(ssidInfo),"did not connect to client");
    }

    @Step("Test Step 3: Check whether captive portal page is shown or not;")
    public void step3() {
        MyCommonAPIs.sleepsync();
        assertTrue(
                new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                        "WAFruncaptive PRJCBUGEN-T16637.py www.rediff.com test test").indexOf("finalresult: 1") != -1,
                "Captive portal not take effect.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }

}
