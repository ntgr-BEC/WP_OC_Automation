package webportal.SwitchManaged.Port.PRJCBUGEN_T4894;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
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

    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4894") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("017-Change Max FrameSize") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4894") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
    @Issue("PRJCBUGEN-11692")
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
    }

    @Step("Test Step 2: set port4 framesize at 1500")
    public void step2() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        devicesSwitchSummaryPage.enterPortConfigSummary(WebportalParam.sw1LagPort1);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyMaxFrameSize("min");
    }

    @Step("Test Step 3: Check configuration on webportal")
    public void step3() {
        // check sw1 on webportal
        Selenide.refresh();
        MyCommonAPIs.sleep(2000);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        String framesize = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getMaxFrameSize();
        if (framesize.equals(FRAME_SIZE_MIN)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult);
        }
    }

    @Step("Test Step 4: Check min frame size 1518  on CLI")
    public void step4() {
        // check on dut CLI
        SwitchTelnet switchTelnet = new SwitchTelnet(webportalParam.sw1IPaddress);
        String frameSize = switchTelnet.getMaxFrameSize(WebportalParam.sw1LagPort1CLI);
        if (!frameSize.contains(FRAME_SIZE_MIN)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult);
        }
    }

    @Step("Test Step 5: change port frame size to 9198, and check it on webportal")
    public void step5() {
        Selenide.refresh();
        MyCommonAPIs.sleep(2000);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyMaxFrameSize("max");
        Selenide.refresh();
        MyCommonAPIs.sleep(2000);
        String framesize = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getMaxFrameSize();
        if (framesize.equals(FRAME_SIZE_MAX)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult);
        }
    }

    @Step("Test Step 6: Check max frame size 9198 on CLI")
    public void step6() {
        // check on dut CLI
        SwitchTelnet switchTelnet = new SwitchTelnet(webportalParam.sw1IPaddress);
        String frameSize = switchTelnet.getMaxFrameSize(WebportalParam.sw1LagPort1CLI);
        if (frameSize.contains(FRAME_SIZE_MAX)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        Selenide.refresh();
        MyCommonAPIs.sleep(2000);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyMaxFrameSize("min");
    }
}
