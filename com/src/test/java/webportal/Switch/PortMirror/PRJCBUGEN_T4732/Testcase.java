package webportal.Switch.PortMirror.PRJCBUGEN_T4732;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesSwitchPortMirrorPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;

/**
 * @author xuchen
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;

    @Feature("Switch.PortMirror") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4732") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("001-Set and get Port Mirroring configuration with src as physical port") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4732") // It's a testcase id/link from Jira Test Case.
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
        handle.gotoLocationWireSettings();
        wlp.gotoLagPage();
        wlp.deleteLag();
    }

    @Step("Test Step 2: Web Portal set mirror session 1, source interface select port4, destination port is port5")
    public void step2() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
        MyCommonAPIs.sleep(8000);

        DevicesDashPage devicesDashPage = new DevicesDashPage();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        DevicesSwitchPortMirrorPage devicesSwitchPortMirrorPage = new DevicesSwitchPortMirrorPage();
        devicesSwitchPortMirrorPage.configPortMirrorPorts(enablemirror, srcports, dstports);
        handle.waitCmdReady("session", false);
    }

    @Step("Test Step 3: Check configuration on webportal")
    public void step3() {
        // check sw1 on webportal
        handle.refresh();
        DevicesSwitchPortMirrorPage devicesSwitchPortMirrorPage = new DevicesSwitchPortMirrorPage();
        boolean ischecked = devicesSwitchPortMirrorPage.portMirrorMode.is(Condition.checked);
        System.out.println(String.format("check PortMirror mode result:%s", ischecked));
        if (ischecked) {
            devicesSwitchPortMirrorPage.logger.info(String.format("-----Check Point 1: Pass,check port mirror mode is: %s", ischecked));
        } else {
            Result = false;
            devicesSwitchPortMirrorPage.logger.info(String.format("-----Check Point 1: Fail,check port mirror mode is: %s", ischecked));
        }
    }

    @Step("Test Step 4: Check configuration on CLI")
    public void step4() {
        // check on dut CLI
        String tocheck;
        String result1 = SwitchCLIUtils.getPortMirror();
        System.out.println(result1);
        tocheck = "destination interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, dstports[0]);
        if (result1.contains(tocheck)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "CLI is " + tocheck);
        }

        tocheck = "source interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, srcports[0]);
        if (result1.contains(tocheck)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "CLI did not contain " + tocheck);
        }
        if (!Result) {
            assertTrue(Result, "Check Point 1: Fail,check port mirror mode is not enable when enabled it.");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        DevicesSwitchPortMirrorPage devicesSwitchPortMirrorPage = new DevicesSwitchPortMirrorPage();
        devicesSwitchPortMirrorPage.configPortMirrorPorts(disablemirror, srcports, dstports);
    }

}
