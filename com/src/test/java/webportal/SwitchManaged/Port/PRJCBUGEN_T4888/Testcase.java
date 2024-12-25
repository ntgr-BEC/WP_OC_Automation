package webportal.SwitchManaged.Port.PRJCBUGEN_T4888;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

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
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {

    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4888") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("010-Link between 10M full-duplex and auto") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4888") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: DUT1 port 1 conenct to DUT2 through port 1")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
        DashboardLocationPage dashboardLocationPage = new DashboardLocationPage();
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
    }

    @Step("Test Step 2: config DUT port 1 at 10M full-duplex Switch port1 at auto")
    public void step2() {
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
        Selenide.refresh();
        wiredGroupPortConfigPage.multiSetting(SWITCH2_PORTARRAY, BATTCHSETTING2);
    }

    @Step("Test Step 3: Config successfully,and ports' status is 10M full-duplex on webportal")
    public void step3() {
        MyCommonAPIs.sleep(120 * 1000);    //replaced 60 by 180
        // check sw1 on webportal
        for (int i = 0; i < sw1port.length; i++) {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
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
                assertTrue(micResult, "fail in check " + sw1port[i]);
            }
        }
    }

    @Step("Test Step 4: Config successfully,and ports' status is 10M full-duplex on CLI")
    public void step4() {
        // check on dut CLI
        String portall = SwitchCLIUtils.getPortInfo(WebportalParam.sw1Port1);        //replaced g1 by WebportalParam.sw1Port1
        if (SwitchCLIUtils.PortClass.sPortSpeed.contains("1000") && SwitchCLIUtils.PortClass.duplexMode == 1) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.batchEnablePort(SWITCH1_PORTARRAY);
        handle.clickYesNo(true);
        wiredGroupPortConfigPage.batchEnablePort(SWITCH2_PORTARRAY);
        handle.clickYesNo(true);
    }
}
