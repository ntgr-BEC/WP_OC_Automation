package webportal.ScheduledWiFi.Premium.PRJCBUGEN_T15985;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Pragya
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("ScheduledWiFi.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15985") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify if the user can configure the same WiFi Scheduler to different SSID.Premium")// It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T15985") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp15985");
        new WirelessQuickViewPage().deleteSsidYes("apwp15985b");
        new WirelessQuickViewPage().deleteWifiSchedulesYes("scheduled15985");
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

    @Step("Test Step 2: configure the same WiFi Scheduler to different SSID;")
    public void step2() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp15985");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        locationInfo.put("Schedule Name", "scheduled15985");
        new WirelessQuickViewPage().scheduleWifi(locationInfo);

    }

    @Step("Test Step 3: Check whether existing scheduled ssid selected;")
    public void step3() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp15985b");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        locationInfo.put("Schedule Name", "scheduled15985");
        new WirelessQuickViewPage().existingscheduleWifi(locationInfo);
    }

}
