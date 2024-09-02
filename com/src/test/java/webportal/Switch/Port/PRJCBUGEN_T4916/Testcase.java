package webportal.Switch.Port.PRJCBUGEN_T4916;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";

    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4916") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("046-Get port settings from web portal") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4916") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Go to port lag 4")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
        DevicesDashPage devicesDashPage = new DevicesDashPage();
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        devicesSwitchSummaryPage.portChoice(WebportalParam.sw1LagPort1).click();
    }

    @Step("Test Step 2: Config every value on ports from web portal")
    public void step2() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyPortDescription(portDesc);
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.disablePort();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.setPortSpeed(PORT_SPEED);
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.setDeplexMode(DUPLEX_MODE);
        expectValue = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyMaxFrameSizeRandom();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.clickSave();
    }

    @Step("Test Step 3: Check port all valuese")
    public void step3() {
        handle.waitCmdReady(portDesc, false);
        SwitchCLIUtils.getPortInfo("g" + WebportalParam.sw1LagPort1);
        assertTrue(SwitchCLIUtils.PortClass.sPortVal.contains(portDesc), "check port desc");
        assertTrue(SwitchCLIUtils.PortClass.isShutdown, "check shutdown");
        assertTrue(SwitchCLIUtils.PortClass.sPortFramesize.contains(expectValue), "check framesize");
        assertTrue(SwitchCLIUtils.PortClass.sPortSpeed.contains("100") && (SwitchCLIUtils.PortClass.duplexMode == 1), "check port speed/deplex");
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.enablePort();
    }
}
