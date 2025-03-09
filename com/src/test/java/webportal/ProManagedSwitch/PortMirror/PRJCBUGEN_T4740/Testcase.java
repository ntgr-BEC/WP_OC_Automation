package webportal.SwitchManaged.PortMirror.PRJCBUGEN_T4740;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;

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
import webportal.weboperation.DevicesDashPageMNG;
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
    @Story("PRJCBUGEN_T4740") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("013-Add and delete mirror source and destination port") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4740") // It's a testcase id/link from Jira Test Case.
    @Issue("PRJCBUGEN-16680")
    @Test(alwaysRun = true, groups = "p3")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        System.out.println("step1......");
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Web Portal set mirror session 1, source interface select port1,port2, destination port is port3")
    public void step2() {
        System.out.println("step2......");
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
        MyCommonAPIs.sleep(8000);

        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        DevicesSwitchPortMirrorPage devicesSwitchPortMirrorPage = new DevicesSwitchPortMirrorPage();
        devicesSwitchPortMirrorPage.configPortMirrorPorts(enablemirror, srcports, dstports);
    }

    @Step("Test Step 3: Check configuration on webportal")
    public void step3() {
        // check sw1 on webportal
        System.out.println("step3......");
        Selenide.refresh();
        MyCommonAPIs.sleep(3000);
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
        System.out.println("step4......");
        // check on dut CLI
        String result1 = SwitchCLIUtils.getPortMirror();
        System.out.println(result1);
        if (result1.contains("destination interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, dstports[0]))) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "CLI is " + result1);
        }
        if (result1.contains("source interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, srcports[0]))) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "CLI did not contain source interface");
        }
    }

    @Step("Test Step 5: Add and delete souce port and dst port")
    public void step5() {
        System.out.println("step5......");
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        DevicesSwitchPortMirrorPage devicesSwitchPortMirrorPage = new DevicesSwitchPortMirrorPage();
        devicesSwitchPortMirrorPage.configPortMirrorPorts(enablemirror, srcports1, dstports1);
    }

    @Step("Test Step 6: Check configuration on CLI")
    public void step6() {
        // check on dut CLI
        String tocheck;
        System.out.println("step6......");
        String result1 = SwitchCLIUtils.getPortMirror();
        System.out.println(result1);
        tocheck = "destination interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, dstports1[0]);
        if (result1.contains(tocheck)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "CLI is " + tocheck);
        }

        tocheck = "source interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, srcports1[1]);
        if (result1.contains(tocheck)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "CLI did not contain " + tocheck);
        }

        if (!Result) {
            assertTrue(Result, "Check Point 2: Fail,check port mirror mode is not enable when enabled it.");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        DevicesSwitchPortMirrorPage devicesSwitchPortMirrorPage = new DevicesSwitchPortMirrorPage();
        devicesSwitchPortMirrorPage.configPortMirrorPorts(disablemirror, srcports, dstports);
    }

}
