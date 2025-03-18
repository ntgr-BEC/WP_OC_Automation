package webportal.APBasic.WirelessSecurity.Location.Premium.ICP.PRJCBUGEN_T35740;

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
 * @author sjena
 *
 */
public class Testcase extends TestCaseBase {
    
    Map<String, String> ssidInfo = new HashMap<String, String>();
    
    String urls[] = {
            "www.rediff.com",  "www.yahoo.com"};

    @Feature("WirelessSecurity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35740") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Add BCP  ssid With client isolation enabled for pro admin user and client connectivity") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T35740") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp35740");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Add WIFI ssid and enable Captive Portal and Connect Client")
    public void step2() {

        ssidInfo.put("SSID", "apwp35740");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid(ssidInfo);
        new WirelessQuickViewPage().editCaptivePortal(ssidInfo.get("SSID"), "http://www.rediff.com", "BEC office", "welcome to BEC Automation");
              
        new WirelessQuickViewPage().AddDomain(ssidInfo.get("SSID"), urls);
     
    
        assertTrue(new WirelessQuickViewPage().connectClient(ssidInfo),"did not connect to client");
        MyCommonAPIs.sleepi(60);
        
    }

    @Step("Test Step 3: Check whether connected connect is shown in client list;")
    public void step3() {
//        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        assertTrue(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Client cannot connected.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }

}
