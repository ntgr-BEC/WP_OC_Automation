package webportal.SwitchManaged.Radius.PRJCBUGEN_T7821;

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
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSummaryPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "807";
    String ip1      = "11.22.33.44";
    String ip2      = "11.22.33.45";

    @Feature("Switch.Radius") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7821") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("015 - Active port mode showing on single port summary") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7821") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Location->Edit Location-> RADIUS")
    public void step2() {
        rdp.gotoPage();
        rdp.enableAuth(ip1, ip2);
    }

    @Step("Test Step 3: Go to Wired Setting-> Group Port Configuration, setting authentication mode (auto, authorized and unauthorized) and deploy")
    public void step3() {
        rcp.gotoPage();
        rcp.enableRadius(true, true);
        rcp.set2PortsMode(1);
    }

    @Step("Test Step 4: View active port mode status under port summary page")
    public void step4() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        DevicesSwitchConnectedNeighboursPortConfiqSummaryPage page = devicesSwitchSummaryPage.enterPortConfigSummary(WebportalParam.sw1LagPort1);
        assertTrue(page.isPortAuth(), "port must be Authorized");

        DevicesSwitchSummaryPage devicesSwitchSummaryPage1 = new DevicesSwitchSummaryPage();
        DevicesSwitchConnectedNeighboursPortConfiqSummaryPage page1 = devicesSwitchSummaryPage1
                .enterPortConfigSummary(WebportalParam.sw1ManagePort);
        assertTrue(page1.isPortAuth(), "port must be Authorized");
    }

}
