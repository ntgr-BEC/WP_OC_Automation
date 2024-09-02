package webportal.SwitchManaged.SwitchRoutingVLANEnhancement.PRJCBUGEN_T17732;

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
import webportal.weboperation.DevicesSwitchIpSettingsPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String                      netName                     = "testnet";
    String                      vlanName                    = "testvlan";
    String                      vlanId                      = "732";
    String                      networkName                 = "1";
    DevicesSwitchIpSettingsPage devicesSwitchIpSettingsPage = null;
    
    @Feature("Switch.SwitchRoutingVLANEnhancement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17732") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("007-Create new routing VLAN with Static mode") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17732") // It's a testcase id/link from Jira Test Case.
    
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
    
    @Step("Test Step 2: Go to Wired-->Setting-->VLAN/Network Setup page, create new VLAN, on 'VLAN IP Configuration' step select Static mode;")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork(netName, vlanId, 1);
    }
    
    @Step("Test Step 3: Config success, check by Insight and Switch local;")
    public void step3() {
        if (!rtp.isRoutingDisabled(WebportalParam.sw1Model)) {
            handle.waitCmdReady(vlanId, false);
        } else {
            MyCommonAPIs.sleepsync();
        }
        
        String tocheck1 = MyCommonAPIs.getCmdOutput("show running-config interface vlan " + vlanId, false);
        String tocheck2 = MyCommonAPIs.getCmdOutput("show running-config interface vlan " + vlanId, true);
        if (!rtp.isRoutingDisabled(WebportalParam.sw1Model)) {
            assertTrue(tocheck1.contains("192.168."), "switch 1 with ip1");
        }
        if (!rtp.isRoutingDisabled(WebportalParam.sw2Model)) {
            assertTrue(tocheck2.contains("192.168."), "switch 2 with ip2");
        }
    }
}
