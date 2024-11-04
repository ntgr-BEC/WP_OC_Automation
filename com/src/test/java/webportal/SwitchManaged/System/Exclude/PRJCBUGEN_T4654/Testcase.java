package webportal.SwitchManaged.System.Exclude.PRJCBUGEN_T4654;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
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

    boolean isDHCP = true;

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4654") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("021- Set static IP address from DHCP") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4654") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p4") // "p1"
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
        devicesSwitchIpSettingsPage.newsetIp(IPINFO1);
        MyCommonAPIs.sleep(2 * 60 * 1000);
        new DevicesDashPageMNG().waitAllSwitchDevicesConnected();

        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        devicesSwitchIpSettingsPage = new DevicesSwitchIpSettingsPage();

        String staicIp = devicesSwitchIpSettingsPage.getIp();
        devicesSwitchIpSettingsPage.setIpToDhcp();
        MyCommonAPIs.sleep(2 * 60 * 1000);
        new DevicesDashPageMNG().waitAllSwitchDevicesConnected();

        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        devicesSwitchIpSettingsPage = new DevicesSwitchIpSettingsPage();

        String dhcpIp = devicesSwitchIpSettingsPage.getIp();
        micResult = devicesSwitchIpSettingsPage.isDHCP();
        assertTrue(micResult);
        if (staicIp == dhcpIp) {
            micResult = false;
        }
        assertTrue(micResult);
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        if (!isDHCP) {
            DevicesSwitchIpSettingsPage devicesSwitchIpSettingsPage = new DevicesSwitchIpSettingsPage();
            devicesSwitchIpSettingsPage.setIp(IPINFO2);
        }
    }
}
