package webportal.AP.SsidOnOrOffBySchedule.PRJCBUGEN_T22240;

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
    @Story("PRJCBUGEN_T22240") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the user can create minimum 1 slot per day") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T22240") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().deleteWifiSchedulesYes("scheduled22240");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
//        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Check wifi schedules page;")
    public void step2() {
        Map<String, String> scheduleInfo = new HashMap<String, String>();
        scheduleInfo.put("Schedule Name", "scheduled22240");

        MyCommonAPIs.waitElement(new WirelessQuickViewPage().settingsorquickview);
        new WirelessQuickViewPage(false).settingsorquickview.click();
        MyCommonAPIs.waitReady();
        new WirelessQuickViewPage(false).wifischedules.click();
        MyCommonAPIs.sleepi(5);
        new WirelessQuickViewPage(false).addscheduledssid.click();
        MyCommonAPIs.waitElement(new WirelessQuickViewPage(false).scheduleName);
        new WirelessQuickViewPage(false).scheduleName.setValue(scheduleInfo.get("Schedule Name"));
        new WirelessQuickViewPage(false).editschedulesave.click();
        MyCommonAPIs.sleepi(5);
        boolean result = false;
        if (new WirelessQuickViewPage(false).icpValueErrorAlert.exists()) {
            if (MyCommonAPIs.getText(new WirelessQuickViewPage(false).icpValueErrorAlert)
                    .contains("You must set a start time or end time to create a WiFi Schedule")) {
                result = true;
            }
        }
        assertTrue(result, "Wifi schedules option error.");
    }

    @Step("Test Step 3: Check wifi schedules page;")
    public void step3() {
        Map<String, String> scheduleInfo = new HashMap<String, String>();
        scheduleInfo.put("Schedule Name", "scheduled22240");
        new WirelessQuickViewPage().createWifiSchedule(scheduleInfo);

        MyCommonAPIs.waitElement(new WirelessQuickViewPage().settingsorquickview);
        new WirelessQuickViewPage(false).settingsorquickview.click();
        MyCommonAPIs.waitReady();
        new WirelessQuickViewPage(false).wifischedules.click();
        MyCommonAPIs.sleepi(5);
        assertTrue(
                $x("//h6[text()='" + scheduleInfo.get("Schedule Name") + "']").isDisplayed() && $x("//*[text()='1 slot(s) in 1 day(s)']").exists(),
                "Wifi schedules option error.");
    }

}
