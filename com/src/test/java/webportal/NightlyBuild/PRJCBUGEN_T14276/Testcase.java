package webportal.NightlyBuild.PRJCBUGEN_T14276;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("NightlyBuild") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14276") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify reboot functionally as non-pro admin") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14276") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

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

    @Step("Test Step 2: click on reboot on AP under device,AP should be going to reboot;")
    public void step2() {
        int upTimeBefore = new WirelessQuickViewPage().getApUptime();

        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);

        new DevicesApSummaryPage().clickReboot();

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

        int upTimeAfter = new WirelessQuickViewPage().getApUptime();

        assertTrue(upTimeAfter < upTimeBefore, "Reboot AP device failed.");
    }

}
