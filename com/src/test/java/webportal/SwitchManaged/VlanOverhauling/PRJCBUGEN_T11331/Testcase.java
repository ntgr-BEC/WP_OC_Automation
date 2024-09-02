package webportal.SwitchManaged.VlanOverhauling.PRJCBUGEN_T11331;

import static org.testng.Assert.assertFalse;
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
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1331";
    String networkName = "testnet" + vlanId;
    
    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11331") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("008-Assign ports as tagged/untagged to a network") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11331") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        netsp.gotoPage();
        netsp.deleteAllNetwork();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Create vlan 100 network by Insight")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork(networkName, 0, vlanName, vlanId);
    }
    
    @Step("Test Step 3: Set port 1 as untagged,port 2 as tagged,port 3 as tagged into vlan 100  at the same time，all port is in general mode.")
    public void step3() {
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.setNetwork2("1", 0, "", 0);
        netsp.gotoStep(2);
        netsp.setNetwork2("2,3", 1, "", 0);
        netsp.finishAllStep();
        
        handle.waitCmdReady(vlanId, false);
        assertFalse(SwitchCLIUtilsMNG.isTagPort("g1", vlanId), "port g1 is Untagged");
        assertTrue(SwitchCLIUtilsMNG.isTagPort("g2", vlanId), "port g2 is Tagged");
        assertTrue(SwitchCLIUtilsMNG.isTagPort("g3", vlanId), "port g3 is Tagged");
    }
    
    @Step("Test Step 4: Change port 3 as untagged")
    public void step4() {
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.setNetwork2("3", 0, "", 0);
        netsp.finishAllStep();
        
        MyCommonAPIs.sleepsync();
        assertFalse(SwitchCLIUtilsMNG.isTagPort("g3", vlanId), "port g3 is Untagged");
    }
    
    @Step("Test Step 5: Remove port 1 from vlan 100")
    public void step5() {
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.setNetwork2("1", 2, "", 0);
        netsp.finishAllStep();
        
        MyCommonAPIs.sleepsync();
        assertFalse(SwitchCLIUtilsMNG.isPortInVlan("g1", vlanId), "port g1 is removed from vlan");
    }
}
