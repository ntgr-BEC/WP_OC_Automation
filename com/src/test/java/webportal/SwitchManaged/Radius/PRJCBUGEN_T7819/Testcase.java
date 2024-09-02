package webportal.SwitchManaged.Radius.PRJCBUGEN_T7819;

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
import util.SwitchCLIUtilsMNG;
import util.SwitchTelnetMNG;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSummaryPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "819";
    String ip1      = "11.22.33.44";
    String ip2      = "11.22.33.45";
    
    @Feature("Switch.Radius") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7819") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("013 - Deploy \"Unauthorized\" mode to per switch on vlan level") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7819") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
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
    
    @Step("Test Step 3: Go Enable radius server option on VLAN200 level")
    public void step3() {
        wvp.gotoPage();
        wvp.newVlan(vlanName, vlanId, 0);
        wvp.editPortMode(vlanId, true, false, false);
        MyCommonAPIs.sleepsync();
    }
    
    @Step("Test Step 4: Go to Switch CLI to check port option")
    public void step4() {
        SwitchTelnetMNG switchTelnet = new SwitchTelnetMNG(WebportalParam.sw2IPaddress, false);
        SwitchCLIUtilsMNG.RadiusClass.init(switchTelnet, switchTelnet.getCLICommand(
                "show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw2Model, WebportalParam.sw2LagPort1)));
        assertTrue(SwitchCLIUtilsMNG.RadiusClass.portStatus == 2, "check radius option on port");
    }
    
    @Step("Test Step 5: Back to Web Portal, view Switch Daskboard and ports status")
    public void step5() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw2serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        DevicesSwitchConnectedNeighboursPortConfiqSummaryPage page = devicesSwitchSummaryPage.enterPortConfigSummary(WebportalParam.sw2LagPort1);
        assertTrue(!page.isPortAuth(), "port must be Unauthorized");
    }
    
    @Step("Test Step 6: Delete VLAN")
    public void step6() {
        netsp.gotoPage();
        netsp.deleteNetwork(vlanName);
    }
}
