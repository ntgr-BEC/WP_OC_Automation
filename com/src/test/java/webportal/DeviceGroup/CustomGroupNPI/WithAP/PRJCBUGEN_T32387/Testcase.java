package webportal.DeviceGroup.CustomGroupNPI.WithAP.PRJCBUGEN_T32387;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceGroupPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.webelements.DeviceGroupElement;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    OrganizationPage OrganizationPage = new OrganizationPage();
    Map<String, String> ssidInfo = new HashMap<String, String>();

    @Feature("DeviceGroup CustomGroupNPI") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32387") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify, rate limiting toggle enabled") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32387") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().goinsideDG("Automation1");
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().DGWiFi.click();
        MyCommonAPIs.sleepi(5);
        new WirelessQuickViewPage(false).deleteSsidYes(ssidInfo.get("SSID"));
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
    }

    @Step("Test Step 2: Check CG name and description shown;")
    public void step2() {
        
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().goinsideDG("Automation1");
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().DGWiFi.click();
       
    }
    
    @Step("Test Step 3: Add WIFI ssid and configure rate limit;")
    public void step3() {
       
        ssidInfo.put("SSID", "apwp14479");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage(false).addSsidDG(ssidInfo);

        new WirelessQuickViewPage(false).editRateLimit(ssidInfo.get("SSID"), 20.0000, 40.0000); // need modify

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp14479")
                    .indexOf("true") != -1) {
                break;
            } else if (sum > 30) {
                assertTrue(false, "Client cannot connected.");
                break;
            }
            sum += 1;
        }

        MyCommonAPIs.sleepi(20);

        boolean result1 = true;
        if (!new Javasocket()
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14479 12345678 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            MyCommonAPIs.sleepi(20);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14479 12345678 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");

    }

    @Step("Test Step 4: Check speedtest result;")
    public void step4() {
        String str = new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                "WAFruncaptive PRJCBUGEN-T14479.py www.speedtest.net test test");
        if (str.indexOf("ERROR:") == -1 && !str.contains("finalresult: 0")) {
            String downloadSpeed = str.substring(str.indexOf("download-speed: ") + 16, str.indexOf("upload-speed"));
            String uploadSpeed = str.substring(str.indexOf("upload-speed: ") + 14, str.indexOf("finalresult"));
            System.out.println(str);
            System.out.println(downloadSpeed);
            System.out.println(uploadSpeed);
            assertTrue(Float.parseFloat(downloadSpeed) < 40 && Float.parseFloat(downloadSpeed) > 0 && Float.parseFloat(uploadSpeed) < 20
                    && Float.parseFloat(uploadSpeed) > 0, "Speedtest result is error.");
        } else {
            assertTrue(false, "Cannot run speedtest.");
        }
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }

}
