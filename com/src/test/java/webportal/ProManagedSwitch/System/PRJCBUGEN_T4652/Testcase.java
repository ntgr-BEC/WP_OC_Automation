package webportal.SwitchManaged.System.PRJCBUGEN_T4652;

import static org.testng.Assert.assertEquals;

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

    @Issue("192.168.1.0 can be set")
    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4652") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("018-Set invalid IP address") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4652") // It's a testcase id/link from Jira Test Case.
    @Test(enabled = true, groups = "p3")
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

    @Step("Test Step 2: Set invalid IP address and gateway from Web Portal")
    public void step2() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw2serialNo);
        DevicesSwitchIpSettingsPage devicesSwitchIpSettingsPage = new DevicesSwitchIpSettingsPage();
        devicesSwitchIpSettingsPage.setIp(IPINFO1);
        String message = devicesSwitchIpSettingsPage.getErrorMessage();
        MyCommonAPIs.sleepi(2);
        Selenide.refresh();
        MyCommonAPIs.sleepi(2);
        assertEquals(message, "Enter a valid IP Address");
        devicesSwitchIpSettingsPage.setIp(IPINFO2);
        message = devicesSwitchIpSettingsPage.getErrorMessage();
        MyCommonAPIs.sleepi(2);
        Selenide.refresh();
        MyCommonAPIs.sleepi(2);
        assertEquals(message, "Enter a valid IP Address");
        devicesSwitchIpSettingsPage.setIp(IPINFO3);
        message = devicesSwitchIpSettingsPage.getErrorMessage();
        MyCommonAPIs.sleepi(2);
        Selenide.refresh();
        MyCommonAPIs.sleepi(2);
        assertEquals(message, "Enter a valid IP Address");
        devicesSwitchIpSettingsPage.setIp(IPINFO4);
        message = devicesSwitchIpSettingsPage.getErrorMessage();
        MyCommonAPIs.sleepi(2);
        Selenide.refresh();
        MyCommonAPIs.sleepi(2);
        assertEquals(message, "Enter a valid IP Address");
        devicesSwitchIpSettingsPage.setIp(IPINFO5);
        message = devicesSwitchIpSettingsPage.getErrorMessage();
        MyCommonAPIs.sleepi(2);
        Selenide.refresh();
        MyCommonAPIs.sleepi(2);
        assertEquals(message, "Enter a valid IP Address");
        devicesSwitchIpSettingsPage.setIp(IPINFO6);
        message = devicesSwitchIpSettingsPage.getErrorMessage();
        MyCommonAPIs.sleepi(2);
        Selenide.refresh();
        MyCommonAPIs.sleepi(2);
        assertEquals(message, "Enter a valid IP Address");
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
    }
}
