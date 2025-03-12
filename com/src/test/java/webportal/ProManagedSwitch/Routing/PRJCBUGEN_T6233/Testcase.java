package webportal.ProManagedSwitch.Routing.PRJCBUGEN_T6233;

import static org.testng.Assert.assertFalse;
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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author Sumanta
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "133";
    String mask     = "255.255.255.0";
    
    @Issue("PRJCBUGEN-10545")
    @Feature("Switch.Routing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6233") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("018-Verify the configed ip address validity") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6233") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        wvp.gotoPage();
        wvp.deleteAllVlan();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create a new vlan 100")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        wvp.gotoPage();
        wvp.newVlan(vlanName, vlanId, 0);
        
        new AccountPage().enterEditNetworkPage();
        rtp.gotoPage();
    }
    
    @Step("Test Step 2: Add ip address 1.0.0.0 and mask to vlan 100")
    public void step2() {
        String err = "invalid ip";
        rtp.deleteVlanRoute(vlanId);
        rtp.addIpToVlan(vlanId, mask, "1.0.0.0", "1.0.0.1");
        assertTrue(handle.getPageErrorMsg().contains(err));
        
        rtp.addIpToVlan(vlanId, mask, "1.0.0.1", "1.0.0.2");
        assertFalse(handle.getPageErrorMsg().contains(err));
        
        rtp.addIpToVlan(vlanId, mask, "129.1.1.1", "129.1.1.2");
        assertFalse(handle.getPageErrorMsg().contains(err));
        
        rtp.addIpToVlan(vlanId, mask, "127.0.0.1", "127.0.0.2");
        assertTrue(handle.getPageErrorMsg().contains(err));
    }
    
}
