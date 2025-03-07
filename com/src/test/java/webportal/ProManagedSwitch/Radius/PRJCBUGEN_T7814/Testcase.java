package webportal.SwitchManaged.Radius.PRJCBUGEN_T7814;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
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
    String lagName  = "testlag7814";
    String vlanId   = "814";
    String ip1      = "11.22.33.44";
    String ip2      = "11.22.33.45";

    @Feature("Switch.Radius") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7814") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("008 - Deploy \"Authorized\" mode to multiple ports under Group Port Configuration") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7814") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        wlp.gotoLagPage();
        wlp.deleteLag();
        wlp.deleteLagCli();
        rcp.gotoPage();
        rcp.disableRadius(false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page, add lag")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        wlp.gotoLagPage();
        wlp.addLag(lagName, false, false);
    }

    @Step("Test Step 2: Go to Location->Edit Location-> RADIUS")
    public void step2() {
        rdp.gotoPage();
        rdp.enableAuth(ip1, ip2);

        rcp.gotoPage();
        rcp.enableRadius(true, true);
    }

    @Step("Test Step 3: Go to Wired Setting->Radius Configuration, all switches present in the current selected network, using a toggle button to right side to deploy Radius configuration")
    public void step3() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        DevicesSwitchConnectedNeighboursPortConfiqSummaryPage page = devicesSwitchSummaryPage.enterPortConfigSummary(WebportalParam.sw1LagPort1);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.setPortConfigMode(1);
    }

    @Step("Test Step 4: Go to local switch GUI, slected ports should stay in \"Authorized\" status")
    public void step4() {
        handle.waitCmdReady(lagName, false);
        String tmpStr = SwitchCLIUtils.getRadiusInfo("g" + WebportalParam.sw1LagPort1);
        assertTrue(SwitchCLIUtils.RadiusClass.portStatus == 1, "check radius option on port");
    }

    @Step("Test Step 5: Back to Web Portal, view Switch Daskboard and ports status")
    public void step5() {
        DevicesSwitchConnectedNeighboursPortConfiqSummaryPage page = new DevicesSwitchConnectedNeighboursPortConfiqSummaryPage();
        assertTrue(page.isPortAuth(), "port must be Authorized");
    }

}
