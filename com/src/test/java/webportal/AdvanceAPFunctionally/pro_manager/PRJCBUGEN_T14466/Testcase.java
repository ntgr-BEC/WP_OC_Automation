package webportal.AdvanceAPFunctionally.pro_manager.PRJCBUGEN_T14466;

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
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("AdvanceAPFunctionally.pro_manager") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14466") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify MAC ACL functionally as manager") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14466") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp14466");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        String mac = WebportalParam.clientwlanmac;
        System.out.println(mac.substring(mac.length() - 2, mac.length()));
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("manager");
    }

    @Step("Test Step 2: Enable ACL Policy popup is shown because  no device is added;")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp14476");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        assertTrue(new WirelessQuickViewPage().enableMacAcl(ssidInfo.get("SSID")), "Policy popup is not shown.");
    }

    @Step("Test Step 3: Add device and enable acl,change policy is allow then deny;")
    public void step3() {
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
            new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

            new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac);

            new WirelessQuickViewPage().addMacaclDevices("apwp14476");

//            new WirelessQuickViewPage().modifyMacInMacaclPage("apwp14476");
//
//            int count = 0;
//            while (true) {
//                MyCommonAPIs.sleepi(20);
//                if (!new APUtils(WebportalParam.ap1IPaddress)
//                        .getMacaclStatus(WebportalParam.clientwlanmac.substring(0, WebportalParam.clientwlanmac.length() - 1) + "2").equals("")) {
//                    break;
//                } else if (count == 30) {
//                    assertTrue(false, "Mac acl not take effact.");
//                    break;
//                }
//                count += 1;
//            }
//
//            boolean result2 = new Javasocket()
//                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14476 12345678 WPA2PSK aes")
//                    .equals("false");
//
            new WirelessQuickViewPage().changeMacaclPolicyAndType("apwp14476", "Local ACL", "Deny");

            new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

            boolean result3 = new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14476 12345678 WPA2PSK aes")
                    .equals("true");
//
            assertTrue(result3);
        } else {
            assertTrue(false, "Client cannot connected.");
        }
        // lack radius type
    }

}
