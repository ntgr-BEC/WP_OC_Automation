package webportal.SwitchManaged.System.PRJCBUGEN_T4690;

import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchTelnet;
import util.TimeUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public static Date webportalDate;
    public static Date switchDate;

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4690") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("094- Default time zone showing after managing by Insight") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4690") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: get device time in webportal")
    public void step2() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.waitAllSwitchDevicesConnected();

        DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        webportalDate = devicesSwitchSummaryPage.getTime();
    }

    @Step("Test Step 3: get device time in CLI")
    public void step3() {
        SwitchTelnet switchTelnet = new SwitchTelnet(webportalParam.sw1IPaddress);
        switchDate = switchTelnet.getSNTPTime();
        SwitchTelnet.disconnect();
    }

    @Step("Test Step 4: Computational time difference")
    public void step4() {
        long diff = TimeUtils.getDateDifferentMin(webportalDate, switchDate);
        if (diff < 15) {
            micResult = true;
        }
        assertTrue(micResult, "compare: '" + webportalDate.toString() + "' to: '" + switchDate.toString());
    }

}
