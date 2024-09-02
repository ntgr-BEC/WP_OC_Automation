package webportal.SwitchManaged.ACL.PRJCBUGEN_T4995;

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
import util.SwitchCLIUtilsMNG;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();

    String vlanName = "testvlan";
    String tmpStr;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4995") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("033-Customer allow policy: Edit To") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4995") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
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
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, "1995", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Use Customer to create IP ACL,the policy is allow")
    public void step2() {
        wvp.camData.fromname = "name1";
        wvp.camData.toname = "name2";
        wvp.camData.fromip = "100.1.1.1";
        wvp.camData.toip = "200.1.1.2";

        wvp.enaIpFilterCustom = true;
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: verify record")
    public void step3() {
        handle.waitCmdReady(wvp.camData.toip, false);
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(true, "1995");
        assertTrue(SwitchCLIUtilsMNG.ACLClass.ispermitACL, "check allow acl");
        tmpStr = String.format("permit %s", wvp.camData.fromip);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "to ip"+": "+tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Use Customer to create IP ACL,the policy is allow")
    public void step4() {
        ipaclMac = wvp.camData.toip;
        wvp.camData.toname = "testcase033";
        wvp.camData.toip = "10.10.10.10";

        wvp.openIpFilterMacAuth(wvp.camData.fromip, true);
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: verify record again")
    public void step5() {
        handle.waitCmdReady(wvp.camData.toip, false);
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(true, "1995");
        assertTrue(SwitchCLIUtilsMNG.ACLClass.ispermitACL, "check allow acl");
        tmpStr = ipaclMac;
        assertFalse(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "ip changed before"+": "+tmpStr);
        tmpStr = wvp.camData.fromip;
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "changed to fromip"+": "+tmpStr);
        tmpStr = wvp.camData.toip;
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "changed to toip"+": "+tmpStr);
    }

}
