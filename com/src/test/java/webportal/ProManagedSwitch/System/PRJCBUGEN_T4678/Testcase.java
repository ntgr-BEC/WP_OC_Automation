package webportal.SwitchManaged.System.PRJCBUGEN_T4678;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchIpSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4678") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("070-Set static IP address and gateway are not in same subnet") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4678") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Set valid static IP address and gateway from Web Portal (192.168.1.100/24)gateway-192.168.0.1)")
    public void step2() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchIpSettingsPage devicesSwitchIpSettingsPage = new DevicesSwitchIpSettingsPage();
        
        devicesSwitchIpSettingsPage.setIp(IPINFO1);
        String message = devicesSwitchIpSettingsPage.getErrorMessage();
        assertTrue(message.length() > 4, "IP and gateway are not in same sub network");
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
    }
}
