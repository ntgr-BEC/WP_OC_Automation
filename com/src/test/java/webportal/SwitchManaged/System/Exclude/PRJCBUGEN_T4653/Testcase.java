package webportal.SwitchManaged.System.Exclude.PRJCBUGEN_T4653;

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
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchIpSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";

    boolean isDHCP      = true;
    boolean needRestore = false;

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4653") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("019- Set static IP address from DHCP") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4653") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1", enabled = false)
    @Issue("PRJCBUGEN-26465")
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

    @Step("Test Step 2: Turn off Assign IP address automatically ,and set valid static IP address/gateway/DNS from Web Portal")
    public void step2() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchIpSettingsPage devicesSwitchIpSettingsPage = new DevicesSwitchIpSettingsPage();

        isDHCP = devicesSwitchIpSettingsPage.isDHCP();
        if (isDHCP) {
            devicesSwitchIpSettingsPage.setIpToStatic();
            needRestore = true;
            ddpmg.gotoPage();
            ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);
            
            DevicesSwitchSummaryPage devicesSwitchSummaryPage1 = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
            DevicesSwitchIpSettingsPage devicesSwitchIpSettingsPage1 = new DevicesSwitchIpSettingsPage();
            micResult = devicesSwitchIpSettingsPage.isDHCP();
            assertTrue(!micResult, "ip should be static");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        if (needRestore) {
            DevicesSwitchIpSettingsPage devicesSwitchIpSettingsPage = new DevicesSwitchIpSettingsPage();
            devicesSwitchIpSettingsPage.setIpToDhcp();
            ddpmg.gotoPage();
            ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);
        }
    }
}
