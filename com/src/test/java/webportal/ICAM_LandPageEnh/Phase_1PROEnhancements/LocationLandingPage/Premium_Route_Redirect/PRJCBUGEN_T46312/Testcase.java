package webportal.ICAM_LandPageEnh.Phase_1PROEnhancements.LocationLandingPage.Premium_Route_Redirect.PRJCBUGEN_T46312;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Premium_Route_Redirect") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T46312") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that On clicking \"Connected Clients\", it should route to location Clients table with filter option selected")
    @TmsLink("PRJCBUGEN_T46312") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("PRJCBUGEN_T46312");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Add WIFI ssid and now connect client to this ssid;")
    public void step2() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "PRJCBUGEN_T46312");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(locationInfo);

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID PRJCBUGEN_T46312")
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect PRJCBUGEN_T46312 123456798 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect PRJCBUGEN_T46312 123456798 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");
    }

    @Step("Test Step 3: Check whether connected connect is shown in Connected Client header tab count;")
    public void step3() {

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        new AccountPage();
        MyCommonAPIs.sleepi(10);
        String clients = new OrganizationPage(false).locDashConnectedClients.shouldBe(Condition.visible).getText();
        System.out.println("connected Clients on Location dashboard page : " + clients);
        assertTrue(clients.trim().equals("1"), "connected connect is not shown in Connected Client header tab count");
        assertTrue(new OrganizationPage(false).verifyConnectedclientsClientsPage(), "AP Details and Clients details are not shown on Clients Page");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }

}
