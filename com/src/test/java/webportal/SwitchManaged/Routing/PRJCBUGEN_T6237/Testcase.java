package webportal.SwitchManaged.Routing.PRJCBUGEN_T6237;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "137";
    String ip1      = "10.10.1.1";
    String ip2      = "10.10.1.2";
    
    @Feature("Switch.Routing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6237") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("022-Add ip address to a vlan with at least two switches") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6237") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        if (!rtp.isRoutingDisabled()) {
            runTest(this);
        }
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (!rtp.isRoutingDisabled()) {
            wvp.gotoPage();
            wvp.deleteAllVlan();
        }
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create a new vlan 100")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        wvp.gotoPage();
        wvp.newVlan(vlanName, vlanId, 0);
        
        new AccountPage().enterEditNetworkPage();
        rtp.gotoPage();
    }
    
    @Step("Test Step 2: Add ip addrss for the two siwtches")
    public void step2() {
        rtp.deleteVlanRoute(vlanId);
        rtp.addIpToVlan(vlanName, "255.255.255.0", "10.1.1.1", "10.2.1.1");
        assertTrue(handle.getPageErrorMsg().length() > 0, "10.1 & 10.2");
        rtp.addIpToVlan(vlanName, "255.255.0.255", ip1, ip2);
        assertTrue(handle.getPageErrorMsg().contains("is not contiguous"), "0.255");
        rtp.addIpToVlan(vlanName, "255.255.0.251", ip1, ip2);
        assertTrue(handle.getPageErrorMsg().contains("is not contiguous"), "0.251");
    }
    
}
