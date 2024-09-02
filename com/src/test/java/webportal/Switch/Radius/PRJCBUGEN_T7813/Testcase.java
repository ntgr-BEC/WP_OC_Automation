package webportal.Switch.Radius.PRJCBUGEN_T7813;

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
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
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
    @Story("PRJCBUGEN_T7813") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("007 - Deploy \"Auto\" mode to multiple ports under Group Port Configuration") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7813") // It's a testcase id/link from Jira Test Case.
    @Issue("ask denping PRJCBUGEN-13443")

    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        rcp.gotoPage();
        rcp.disableRadius(false);
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

    @Step("Test Step 3: Go to Group Port configuration and select 2+ ports of this GC swtich, set authentication mode \"Auto\"")
    public void step3() {
        rcp.gotoPage();
        rcp.enableRadius(true, true);
        rcp.set2PortsMode(0);
    }

    @Step("Test Step 4: After save and deploy the command, go to swtich local GUI check the configuration")
    public void step4() {
        handle.waitCmdReady(ip1, false);
        String tmpStr = SwitchCLIUtils.getRadiusInfo("g" + WebportalParam.sw1LagPort1);
        assertTrue(SwitchCLIUtils.RadiusClass.portStatus == 0, "check radius option on port1");
        tmpStr = SwitchCLIUtils.getRadiusInfo("g" + WebportalParam.sw1LagPort2);
        assertTrue(SwitchCLIUtils.RadiusClass.portStatus == 0, "check radius option on port2");
    }

    @Step("Test Step 5: Back to Web Portal, view Switch Daskboard and ports status")
    public void step5() {
        DevicesDashPage devicesDashPage = new DevicesDashPage();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        DevicesSwitchConnectedNeighboursPortConfiqSummaryPage page = devicesSwitchSummaryPage.enterPortConfigSummary(WebportalParam.sw1LagPort1);
        assertTrue(!page.isPortAuth(), "port must be Unauthorized");
    }

}
