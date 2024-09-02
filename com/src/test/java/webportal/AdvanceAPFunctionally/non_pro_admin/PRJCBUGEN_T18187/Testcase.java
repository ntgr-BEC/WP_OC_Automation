package webportal.AdvanceAPFunctionally.non_pro_admin.PRJCBUGEN_T18187;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Pragya
 *
 */
public class Testcase extends TestCaseBase {

    String              region       = "";
    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("AdvanceAPFunctionally.non_pro_admin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18187") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify instant wifi functionally as non pro user for country China") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T18187") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        locationInfo.put("Wireless Region", region);
        new AccountPage().editLocation(WebportalParam.location1, locationInfo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage().deleteSsidYes("apwp18187");
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

    @Step("Test Step 2: Change location wireless region to China and click optimize now button, then check ap log;")
    public void step2() {
        region = new AccountPage().getWirelessRegion(WebportalParam.location1);
        locationInfo.put("Wireless Region", "China");
        new AccountPage().editLocation(WebportalParam.location1, locationInfo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp18187");
        ssidInfo.put("Security", "WPA2 Personal Mixed");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        new APUtils(WebportalParam.ap1IPaddress).deleteMessages();
        if (!new WirelessQuickViewPage().optimizeInstantWifi(false)) {
            int i = 0;
            while (i < 2) {
                MyCommonAPIs.sleepi(10 * 60);
                new APUtils(WebportalParam.ap1IPaddress).deleteMessages();
                if (new WirelessQuickViewPage().optimizeInstantWifi(false)) {
                    break;
                }
            }
        }

        int count = 0;
        while (true) {
            MyCommonAPIs.sleepsync();
            if (new APUtils(WebportalParam.ap1IPaddress).checkInstantWifiProfile(false)) {
                assertTrue(true);
                break;
            } else if (count == 10) {
                assertTrue(false, "Instant wifi logs not output.");
                break;
            }
            count += 1;
        }

        new APUtils(WebportalParam.ap1IPaddress).deleteMessages();
        if (!new WirelessQuickViewPage().optimizeInstantWifi(false)) {
            MyCommonAPIs.sleepi(20 * 60);

            count = 0;
            while (true) {
                MyCommonAPIs.sleepsync();
                if (new APUtils(WebportalParam.ap1IPaddress).checkInstantWifiProfile(false)) {
                    assertTrue(true);
                    break;
                } else if (count == 10) {
                    assertTrue(false, "Instant wifi logs not output.");
                    break;
                }
                count += 1;
            }
        } else {
            assertTrue(false, "Instant wifi not optimize.");
        }

    }
}



