package webportal.Switch.PortMirror.PRJCBUGEN_T4734;

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
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesSwitchPortMirrorPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;

/**
 *
 * @author xuchen
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;

    @Feature("Switch.PortMirror") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4734") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("007-Set and get Port Mirroring configuration with src as none") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4734") // It's a testcase id/link from Jira Test Case.
    @Issue("PRJCBUGEN-8444")
    @Test(alwaysRun = true, groups = "p3")
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
    }

    @Step("Test Step 2: set source interface as none")
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
        devicesSwitchPortMirrorPage.configPortMirrorPorts(enablemirror, null, dstports);
        String msg = devicesSwitchPortMirrorPage.getPageErrorMsg();
        System.out.println("1.page shows:" + msg);
        if (msg.contains("Please select")) {
            devicesSwitchPortMirrorPage.logger.info(String.format("-----Check Point 1: Pass,set source interface as none,popup error info: %s", msg));

        } else {
            Result = false;
            devicesSwitchPortMirrorPage.logger.info(String.format("-----Check Point 1: Fail,set source interface as none,popup info: %s", msg));

        }
        Selenide.refresh();
        MyCommonAPIs.sleep(3000);
        devicesSwitchPortMirrorPage.configPortMirrorPorts(disablemirror, srcports, dstports);

        devicesSwitchPortMirrorPage.configPortMirrorPorts(enablemirror, srcports, null);
        String message = devicesSwitchPortMirrorPage.getPageErrorMsg();
        System.out.println("2.page shows:" + message);
        if (message.contains("port")) {
            devicesSwitchPortMirrorPage.logger
                    .info(String.format("-----Check Point 2: Pass,set desitination interface as none,popup error info: %s", message));
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "Check Point 2:Failed,set desitination interface as none,page did not show error,it shows: " + message);
        }
        if (!Result) {
            assertTrue(Result, "Check Point 1:Fail,set source interface as none,did not popup error info");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        Selenide.refresh();
        MyCommonAPIs.sleep(3000);
        DevicesSwitchPortMirrorPage devicesSwitchPortMirrorPage = new DevicesSwitchPortMirrorPage();
        devicesSwitchPortMirrorPage.configPortMirrorPorts(disablemirror, srcports, dstports);
    }

}
