package webportal.Switch.ACL.PRJCBUGEN_T4975;

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
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    
    String vlanName = "testvlan";
    String tmpStr;
    
    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";
    
    @Issue("PRJCBUGEN-9730")
    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4975") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("067-A vlan binding MAC ACL and IP ACL") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4975") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    @Issue("PRJCBUGEN-23462")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        wvp.gotoPage();
        wvp.deleteAllVlan();
        wvp.removeAllAclCli();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create vlan50 on switch")
    public void step1() {
        // if (!WebportalParam.skipIssueCheck)
            // throw new RuntimeException("PRJCBUGEN-23462");
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        
        wvp.gotoPage();
        wvp.newVlan(vlanName, "50", 0);
        wvp.openVlan(vlanName, "50", 0);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Create a vlan,binding IP ACLs for it")
    public void step2() {
        ipaclIp = "192.168.1.0";
        wvp.enaIpFilterIpMask = true;
        wvp.ipFilterIpMask = "0.0.0.255";
        wvp.ipFilterMacOpt = 2;
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Create a MAC ACL binding to same vlan")
    public void step3() {
        ipaclMac = "AA:BB:CC:11:22:00";
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclMac);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: verify ip/mac acl on webportal")
    public void step4() {
        handle.waitCmdReady("11:22:00", false);
        tmpStr = SwitchCLIUtils.getIpMACACL(true, "50");
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "allow ip acl");
        tmpStr = "permit 192.168.1.0 0.0.0.255";
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "Create IP ACL successfully" + ": " + tmpStr);
        
        tmpStr = SwitchCLIUtils.getIpMACACL(false, "50");
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "allow mac acl");
        tmpStr = "permit aa:bb:cc:11:22:00 00:00:00:00:00:00 any";
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "Create MAC ACL successfully" + ": " + tmpStr);
    }
    
}
