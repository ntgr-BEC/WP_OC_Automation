package webportal.ProManagedSwitch.Port.PRJCBUGEN_T4910;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Sumanta
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";

    @Issue("PRJCBUGEN-10030")
    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4910") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("037-change port speed mode when port is physical down") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4910") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: choose a port that do not connect to any device")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        handle.gotoLoction();
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        devicesSwitchSummaryPage.portChoice(WebportalParam.sw1Port6).click();
    }

    @Step("Test Step 2: Change port speed mode to 100M full")
    public void step2() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.setPortSpeed(PORT_SPEED);
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.setDeplexMode(DUPLEX_MODE1);
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.clickSave();
    }

    @Step("Test Step 3: Check port 1 speed mode")
    public void step3() {
        handle.waitCmdReady(PORTSPEED_CLI, false);
        expectValue = SwitchCLIUtils.getPortInfo("g" + WebportalParam.sw1Port6);
        assertTrue(SwitchCLIUtils.PortClass.sPortSpeed.contains("100") && SwitchCLIUtils.PortClass.duplexMode == 1, "check port speed/deplex");
    }

    @Step("Test Step 4: Change port speed mode to 100M half")
    public void step4() {
        DevicesSwitchSummaryPage page = new DevicesSwitchSummaryPage();
        page.portChoice(WebportalParam.sw1Port6).click();
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.setPortSpeed(PORT_SPEED);
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.setDeplexMode(DUPLEX_MODE2);
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.clickSave();
        handle.sleepsync();
    }

    @Step("Test Step 5: Check port 1 speed mode")
    public void step5() {
        expectValue = SwitchCLIUtils.getPortInfo("g" + WebportalParam.sw1Port6);
        assertTrue(SwitchCLIUtils.PortClass.sPortSpeed.contains("100") && SwitchCLIUtils.PortClass.duplexMode == 2, "check port speed/deplex");
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.setPortSpeed(PORT_SPEED_RESTORE);
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.setDeplexMode(DUPLEX_MODE_RESTORE);
    }
}
