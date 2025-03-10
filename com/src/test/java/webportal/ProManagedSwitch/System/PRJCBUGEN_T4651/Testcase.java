package webportal.ProManagedSwitch.System.PRJCBUGEN_T4651;

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
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchStatisticsPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author sumanta
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4651") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("016-Get Statistics Info") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4651") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        handle.gotoLoction();
    }

    @Step("Test Step 2: check Statistics Info")
    public void step2() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.openPoEDevice();

        DevicesSwitchStatisticsPage devicesSwitchStatisticsPage = new DevicesSwitchStatisticsPage();
        boolean result = devicesSwitchStatisticsPage.checkStatistics();
        assertTrue(result, "Statistics info isn't correctly");
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        WebportalParam.updateSwitchOneOption(false, null);
    }
}
