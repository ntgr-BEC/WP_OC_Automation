package webportal.SwitchManaged.Port.PRJCBUGEN_T4896;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.weboperation.DashboardLocationPage;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {
    static String stormControlRatetvalue = "";

    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4896") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("019-Set Storm control rate") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4896") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
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
        DashboardLocationPage dashboardLocationPage = new DashboardLocationPage();
    }

    @Step("Test Step 2: set port 1 strom control rate at random value")
    public void step2() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        devicesSwitchSummaryPage.enterPortConfigSummary(WebportalParam.sw1LagPort1);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        stormControlRatetvalue = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyStormControlRateRandom();
        handle.waitCmdReady(stormControlRatetvalue, false);
    }

    @Step("Test Step 3: Check configuration on webportal")
    public void step3() {
        // check sw1 on webportal
        Selenide.refresh();
        MyCommonAPIs.sleep(3000);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        String actualRate = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getStormControlRateValue();
        if (actualRate.equals(stormControlRatetvalue)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "actualRate is: " + actualRate + ", expect stormControlRatetvalue is: " + stormControlRatetvalue);
        }
    }

    @Step("Test Step 4: Check configuration on CLI")
    public void step4() {
        // check on dut CLI
        SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress);
        String rate = switchTelnet.getStormControlRate(WebportalParam.sw1LagPort1CLI);
        if (rate.contains(stormControlRatetvalue)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "actualRate is " + rate + ", expect rate limit value  is: " + stormControlRatetvalue);
        }
    }
}
