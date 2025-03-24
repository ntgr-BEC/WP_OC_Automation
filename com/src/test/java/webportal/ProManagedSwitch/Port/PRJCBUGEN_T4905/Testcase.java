package webportal.ProManagedSwitch.Port.PRJCBUGEN_T4905;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSummaryPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Sumanta
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";
    public String portNo      = "g1";

    @Issue("PRJCBUGEN-10030")
    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4905") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("030-Get port neighbour statistics when port is down") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4905") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open wired vlan")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        wvp.gotoPage();
    }

    @Step("Test Step 2: Connect DUT port1 to none device, get port 1 neighbour statistics")
    public void step2() {
        ddpmg.gotoPage();
        ddpmg.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        DevicesSwitchConnectedNeighboursPortConfiqSummaryPage devicesSwitchConnectedNeighboursPortConfiqSummaryPage = devicesSwitchSummaryPage
                .enterPortConfigSummary("1");

        assertTrue(devicesSwitchConnectedNeighboursPortConfiqSummaryPage.NeighborName.getText().length() == 0, "check length of nname desc");
        assertTrue(devicesSwitchConnectedNeighboursPortConfiqSummaryPage.NeightborDescription.getText().length() == 0, "check length of nb desc");
        assertTrue(devicesSwitchConnectedNeighboursPortConfiqSummaryPage.MNGTIPAddress.getText().length() == 0, "check length of ip address");
        assertTrue(devicesSwitchConnectedNeighboursPortConfiqSummaryPage.ChassisID.getText().length() == 0, "check length of chassis id");
        assertTrue(devicesSwitchConnectedNeighboursPortConfiqSummaryPage.portId.getText().length() == 0, "check length of port id");
        assertTrue(devicesSwitchConnectedNeighboursPortConfiqSummaryPage.PortDescription.getText().length() == 0, "check length of port id");
    }
}
