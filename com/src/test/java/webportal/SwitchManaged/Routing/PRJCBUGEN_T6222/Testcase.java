package webportal.SwitchManaged.Routing.PRJCBUGEN_T6222;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "102";
    String ipMask   = "255.255.255.0";
    String ip1      = "100.1.1.1";
    String ip2      = "100.1.1.2";
   
    
    @Issue("PRJCBUGEN-10531")
    @Feature("Switch.Routing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6222") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("007-Add gateway same as management gateway") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6222") // It's a testcase id/link from Jira Test Case.
    
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
        // throw new RuntimeException("Check Issue");
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        
        wvp.gotoPage();
        wvp.newVlan(vlanName, vlanId, 0);
        
        new AccountPage().enterEditNetworkPage();
        rtp.gotoPage();
    }
    
    @Step("Test Step 2: Add IP1 to vlan 100")
    public void step2() {
        rtp.addIpToVlan(vlanName, ipMask, ip1, ip2);
    }
    
    @Step("Test Step 3: Add gateway to a known ip address")
    public void step3() {
        rtp.setGateway(ip1);     //same as IP of VLAN
        assertTrue(handle.getPageErrorMsg().contains("static route next hop address can't be same"), "nexthop address of any specified static route can't");
    }
    
}
