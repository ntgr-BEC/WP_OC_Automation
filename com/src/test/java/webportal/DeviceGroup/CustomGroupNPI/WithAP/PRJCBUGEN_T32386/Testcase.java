package webportal.DeviceGroup.CustomGroupNPI.WithAP.PRJCBUGEN_T32386;

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
    @Story("PRJCBUGEN_T32386") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify, MAC ACL allow/deny for client") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32386") // It's a testcase id/link from Jira Test Case.

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
        new WirelessQuickViewPage().deleteSsidYes(ssidInfo.get("SSID"));
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
    
    @Step("Test Step 3: Enable ACL Policy popup is shown because  no device is added;")
    public void step3() {
       
        ssidInfo.put("SSID", "apwp14476");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage(false).addSsid1(ssidInfo);

        assertTrue(new WirelessQuickViewPage(false).enableMacAcl(ssidInfo.get("SSID")), "Policy popup is not shown.");
    }

    @Step("Test Step 4: Add device and enable acl,change policy is allow then deny;")
    public void step4() {
        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp14476")
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14476 12345678 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14476 12345678 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        if (result1) {
           
//            new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac);
            
            OrganizationPage.openOrg(WebportalParam.Organizations);
            new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
            MyCommonAPIs.sleepi(5);
            new DeviceGroupPage().goinsideDG("Automation1");
            MyCommonAPIs.sleepi(5);
            new DeviceGroupPage().DGWiFi.click();
            MyCommonAPIs.sleepi(30);
            new WirelessQuickViewPage(false).addMacaclDevices(ssidInfo.get("SSID"));
       
            new WirelessQuickViewPage(false).changeMacaclPolicyAndType(ssidInfo.get("SSID"), "Local ACL", "Deny");

            new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

            boolean result3 = new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14476 12345678 WPA2PSK aes")
                    .equals("true");

            assertTrue(result3);
        } else {
            assertTrue(false, "Client cannot connected.");
        }
    }

}