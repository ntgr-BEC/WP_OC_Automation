package webportal.ProManagedSwitch.PortMirror.PRJCBUGEN_T4733;

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
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchPortMirrorPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;

/**
 * @author Sumanta
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;
    
    @Feature("Switch.PortMirror") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4733") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("006-Verify conflict configuration for port mirror") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4733") // It's a testcase id/link from Jira Test Case.
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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        handle.gotoLoction();

    }
    
    @Step("Test Step 2: destination port and source port select the same port")
    public void step2() {
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
        String msg = devicesSwitchPortMirrorPage.getPageErrorMsg();
        System.out.println("After set the same port,page shows:" + msg);
        if (msg.contains("port")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "Failed,after set the same port,page did not show error,it shows: " + msg);
        }
    }
    
    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
    }
}
