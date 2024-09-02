package webportal.SwitchManaged.ACL.PRJCBUGEN_T4978;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    
    String       vlanName     = "testvlan";
    String       tmpStr;
    List<String> lsExpectACL  = new ArrayList<String>();
    List<String> lsExpectVlan = new ArrayList<String>();
    
    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";
    
    @Issue("PRJCBUGEN-7634")
    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4978") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("071-Test max vlan number (24) that support to binding ACL") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4978") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p5") // "p3"
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        wvp.gotoPage();
        wvp.deleteAllVlan();
        MyCommonAPIs.sleep(120, "wait all clean sync");
        handle.waitCmdReady(vlanName, true);
        wvp.removeAllAclCli();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login WP")
    public void step1() {
        // if (!WebportalParam.skipIssueCheck)
            // throw new RuntimeException("PRJCBUGEN-23462");
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        
        wvp.gotoPage();
        wvp.deleteAllVlan();
        wvp.deleteAllVlanCli();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Create 24 vlans that every vlan include 1 IP ACL")
    public void step2() {
        for (int i = 1; i <= 24; i++) {
            wvp.gotoPage();
            vlanName = String.format("10%d", i);
            lsExpectVlan.add(vlanName);
            System.out.printf("create vlan: %s\n", vlanName);
            wvp.openVlan(vlanName, vlanName, 0);
            ipaclIp = String.format("100.1.1.%d", i);
            lsExpectACL.add(ipaclIp);
            System.out.printf("create ip acl: %s\n", ipaclIp);
            wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
        }
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Create vlan 25 that every vlan include 1 IP ACL")
    public void step4() {
        vlanName = "120";
        wvp.gotoPage();
        lsExpectVlan.add(vlanName);
        wvp.openVlan(vlanName, vlanName, 0);
        
        ipaclIp = String.format("120.1.1.1");
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: webportal app pop up error message")
    public void step5() {
        assertTrue(handle.getPageErrorMsg().contains("max"));
    }
    
    @Step("Test Step 5: verify all acl")
    public void step6() {
        handle.waitCmdReady(lsExpectACL.get(lsExpectACL.size() - 1), false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        for (String acl : lsExpectACL) {
            assertTrue(tmpStr.contains(acl), acl);
        }
    }
}
