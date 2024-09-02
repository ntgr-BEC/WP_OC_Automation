package webportal.SwitchManaged.SwitchRoutingVLANEnhancement.PRJCBUGEN_T17731;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchTelnetMNG;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String netName     = "testnet";
    String vlanName    = "testvlan";
    String vlanId      = "731";
    String networkName = "1";
    
    @Feature("Switch.SwitchRoutingVLANEnhancement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17731") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("006-Create new routing VLAN with DHCP mode") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17731") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        netsp.gotoPage();
        netsp.deleteAllNetwork();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open WP page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        ddpmg.gotoPage();
    }
    
    @Step("Test Step 2: Go to Wired-->Setting-->VLAN/Network Setup page, create new VLAN, on 'VLAN IP Configuration' step select DHCP mode;")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork(netName, vlanId, 0);
    }
    
    @Step("Test Step 3: Config success, check by Insight and Switch local;")
    public void step3() {
        handle.waitCmdReady(vlanId, false);
        SwitchTelnetMNG switchTelnet = new SwitchTelnetMNG(WebportalParam.sw1IPaddress, false);
        assertTrue(switchTelnet.checkVlanDHCP(vlanId), "By default dhcp mode is on for vlan");
    }
}
