package webportal.AP.SsidOnOrOffBySchedule.PRJCBUGEN_T15928;

import static com.codeborne.selenide.Selenide.$x;

import static org.hamcrest.CoreMatchers.containsString;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.MyCommonAPIs;
import util.Javasocket;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesApRadioAndChannelsPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("AP.SsidOnOrOffBySchedule") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15928") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the User able to create SSID Schedule in \"Create SSID Page\"") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T15928") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().deleteSsidYes("apwp15928");
        new WirelessQuickViewPage().deleteWifiSchedulesYes("scheduled15928");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Check wifi schedules page;")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp15928");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "123456798");
        ssidInfo.put("Schedule Name", "scheduled15928");
        new WirelessQuickViewPage().scheduleWifi(ssidInfo);

        MyCommonAPIs.waitElement(new WirelessQuickViewPage().settingsorquickview);
        new WirelessQuickViewPage(false).settingsorquickview.click();
        MyCommonAPIs.waitReady();
        new WirelessQuickViewPage(false).wifischedules.click();
        MyCommonAPIs.sleepi(5);
        assertTrue($x("//h6[text()='" + ssidInfo.get("Schedule Name") + "']").isDisplayed(), "Wifi schedules option error.");
    }

}
