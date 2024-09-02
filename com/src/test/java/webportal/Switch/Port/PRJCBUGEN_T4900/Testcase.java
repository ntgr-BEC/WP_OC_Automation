package webportal.Switch.Port.PRJCBUGEN_T4900;

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
import util.MyCommonAPIs;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";
    public String portNo      = "g1";

    @Issue("PRJCBUGEN-10030")
    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4900") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("024-Config port when DUT is offline") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4900") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @Step("Test Step 1: Put DUT out of internet")
    public void step0() {
        SwitchCLIUtils.CloudModeSet(false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: enter setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 3: config port 1 descrip, speed mode,maxFrameSize max,duplexMode,Rate limit.")
    public void step2() {
        DevicesDashPage devicesDashPage = new DevicesDashPage();
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        devicesSwitchSummaryPage.enterPortConfigSummary(portNo.replace("g", ""));
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyPortDescription(portDesc);
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.setPortSpeed(PORT_SPEED);
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyMaxFrameSize("max");
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.setDeplexMode(DUPLEX_MODE);
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyStormControlRate(STROM_Set);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: After about 5min ,connect DUT to internet")
    public void step3() {
        // MyCommonAPIs.sleepi(4 * 60);

        SwitchCLIUtils.CloudModeSet(true);
    }

    @Step("Test Step 5: Check configuration on CLI")
    public void step4() {
        handle.waitCmdReady(portDesc, false);
        MyCommonAPIs.sleepsync();
        String sRet = SwitchCLIUtils.getPortInfo(portNo);
        assertTrue(sRet.contains(portDesc), "check for " + portDesc);
        assertTrue(SwitchCLIUtils.PortClass.sPortSpeed.contains("100") && (SwitchCLIUtils.PortClass.duplexMode == 2), "check for " + PORTSPEED_CLI);
        assertTrue(SwitchCLIUtils.PortClass.sPortFramesize.contains("9198"), "check for " + FRAMESIZE_CLI);
        assertTrue(SwitchCLIUtils.PortClass.sPortStormControlRate.contains(STROM_Set), "check for " + STROM_Set_CLI);
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        SwitchCLIUtils.CloudModeSet(true);
        handle.refresh();
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyPortDescription("");
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.setPortSpeed(PORT_SPEED_RESTORE);
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyMaxFrameSize("min");
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.setDeplexMode(DUPLEX_MODE_RESTORE);
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyStormControlRate("max");
    }
}
