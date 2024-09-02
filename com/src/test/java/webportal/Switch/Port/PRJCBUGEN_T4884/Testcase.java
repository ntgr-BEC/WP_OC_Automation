package webportal.Switch.Port.PRJCBUGEN_T4884;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DashboardLocationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;

/**
 * @author zheli
 */
public class Testcase extends TestCaseBase implements Config {

    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4884") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("004-Link with both sides are 10M full-duplex") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4884") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        if (!WebportalParam.isSwitcMS510()) {
            runTest(this);
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter vlan page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
        DashboardLocationPage dashboardLocationPage = new DashboardLocationPage();
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
    }

    @Step("Test Step 2: config DUT1 port 1 and DUT2 port1 at 10M half-duplex")
    public void step2() {
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH_PORTARRAY, BATTCHSETTING);
    }

    @Step("Test Step 3: Config successfully,and ports' status is 10M half-duplex on webportal")
    public void step3() {
        MyCommonAPIs.sleep(60 * 1000);
        // check sw1 on webportal
        for (int i = 0; i < sw1port.length; i++) {
            DevicesDashPage devicesDashPage = new DevicesDashPage();
            devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
            DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
            devicesSwitchSummaryPage.enterPortConfigSummary(sw1port[i]);
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            String duplexMode = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getDuplexMode();
            String portSpeed = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getPortSpeed();
            if (duplexMode.toLowerCase().equals(DUPLEX_MODE.toLowerCase())
                    && devicesSwitchConnectedNeighboursPortConfiqSettingsPage.cmpPortSpeed(PORT_SPEED, portSpeed)) {
                micResult = true;
            } else {
                micResult = false;
                assertTrue(micResult, "fail in check port: " + sw1port[i] + " -> " + DUPLEX_MODE + PORT_SPEED);
            }
        }
    }

    @Step("Test Step 4: Config successfully,and ports' status is 10M half-duplex on CLI")
    public void step4() {
        // check on dut CLI
        String portall = SwitchCLIUtils.getPortInfo("g1");
        if (SwitchCLIUtils.PortClass.sPortSpeed.contains("10") && (SwitchCLIUtils.PortClass.duplexMode == 1)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "Check 10M and Full on port");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        if (!WebportalParam.isSwitcMS510()) {
            System.out.println("start to do restore");
            DevicesDashPage devicesDashPage = new DevicesDashPage();
            WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
            WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
            WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
            wiredGroupPortConfigPage.batchEnablePort(SWITCH_PORTARRAY);
        }
    }
}
