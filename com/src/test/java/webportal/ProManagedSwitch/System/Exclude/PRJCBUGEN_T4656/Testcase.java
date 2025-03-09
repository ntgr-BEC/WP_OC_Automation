package webportal.SwitchManaged.System.Exclude.PRJCBUGEN_T4656;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchTelnet;
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
    boolean       isDHCP      = true;

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4656") // 对应testcasekey
    @Description("023- Configure new DNS server") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4656") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
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

    @Step("Test Step 2: add new DNS 114.114.114.114,8.8.8.8")
    public void step2() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchIpSettingsPage devicesSwitchIpSettingsPage = new DevicesSwitchIpSettingsPage();
        isDHCP = devicesSwitchIpSettingsPage.isDHCP();
        devicesSwitchIpSettingsPage.setIp(IPINFO);
    }

    @Step("Test Step 3: check DNS in switch telnet")
    public void step3() {
        handle.waitCmdReady(IPINFO.get("DNS Server1"), false);

        SwitchTelnet switchTelnet = new SwitchTelnet(webportalParam.sw1IPaddress);
        String DNSName = switchTelnet.getDNS();
        if (DNSName.contains(IPINFO.get("DNS Server1")) && DNSName.contains(IPINFO.get("DNS Server2"))) {
            micResult = true;
        }
        assertTrue(micResult);
        SwitchTelnet.disconnect();
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        if (isDHCP) {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
            DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
            DevicesSwitchIpSettingsPage devicesSwitchIpSettingsPage = new DevicesSwitchIpSettingsPage();
            devicesSwitchIpSettingsPage.setIpToDhcp();
        }
    }
}
