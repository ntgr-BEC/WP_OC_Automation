package webportal.Switch.ACL.PRJCBUGEN_T4959;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    
    String vlanName = "testvlan";
    
    String ipaclName = "test";
    String ipaclIp   = "1.1.1.1";
    
    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4959") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("018-Reboot switch after set IP ACL") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4959") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3") // p3
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
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        
        wvp.gotoPage();
        wvp.openVlan(vlanName, "50", 0);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: ​​​​​Set IP Acl to switch via webportal,then reboot switch")
    public void step2() {
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
        String sRet = handle.waitCmdReady(ipaclIp, false);
        handle.doSwitchCommand(1);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: ​​The IP ACL do not lose after reboot switch")
    public void step3() {
        String sRet = handle.waitCmdReady(ipaclIp, false);
        assertTrue(sRet.contains(ipaclIp));
    }
    
}
