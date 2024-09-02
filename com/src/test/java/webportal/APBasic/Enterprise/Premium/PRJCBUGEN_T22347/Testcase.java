package webportal.APBasic.Enterprise.Premium.PRJCBUGEN_T22347;

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
 * @author Sumanta
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("APBasic.Enterpries.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T22347") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify 2.4 GHz and WPA2 Enterprise Security as premium user") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T22347") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp22347");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Add WIFI ssid and now connect client to this ssid;")
    public void step2() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp22347");
        locationInfo.put("Security", "WPA2 Enterprise");
        locationInfo.put("Band", "check only 2ghz");
        new WirelessQuickViewPage().addSsid1(locationInfo);
        
        
         new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "sudo -S nmcli --terse connection show | grep wireless | cut -d : -f 2 |   while read name; do sudo nmcli connection delete $name; done");
         MyCommonAPIs.sleepi(60);
         new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "sudo nmcli device wifi rescan");
         MyCommonAPIs.sleepi(20);
         new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "sudo nmcli device wifi list | grep -F \"apwp22347\"");
         MyCommonAPIs.sleepi(20);
         new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "sudo -S nmcli c add type wifi con-name \"apwp22347\" ifname wlp4s0 ssid \"apwp22347\"");      
         MyCommonAPIs.sleepi(20);
         new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "sudo -S nmcli connection modify \"apwp22347\" wifi-sec.key-mgmt wpa-eap 802-1x.eap peap 802-1x.phase2-auth mschapv2 802-1x.identity \"teju\" 802-1x.password \"teju\"");
         MyCommonAPIs.sleepi(20);
         new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "sudo -S nmcli connection up \"apwp22347\"");
     
    }

    @Step("Test Step 3: Check whether connected connect is shown in client list;")
    public void step3() {
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        assertTrue(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Client cannot connected.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }

}
