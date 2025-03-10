package webportal.ProManagedSwitch.System.PRJCBUGEN_T4679;

import static org.testng.Assert.assertNotEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchStatisticsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author sumanta
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4679") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("017- Clear Statictics Counters") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4679") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

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
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw2serialNo);
        DevicesSwitchStatisticsPage devicesSwitchStatisticsPage = new DevicesSwitchStatisticsPage();
        String txData = devicesSwitchStatisticsPage.getTxData();
        String rxData = devicesSwitchStatisticsPage.getRxData();
        devicesSwitchStatisticsPage.clearCounters();
        MyCommonAPIs.sleep(7 * 60 * 1000);
        handle.refresh();
        MyCommonAPIs.waitReady();
        String txData2 = devicesSwitchStatisticsPage.getTxData();
        String rxData2 = devicesSwitchStatisticsPage.getRxData();
        assertNotEquals(txData, txData2, "after clear counter,tx data must different");
        assertNotEquals(rxData, rxData2, "after clear counter,rx data must different");
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
    }
}
