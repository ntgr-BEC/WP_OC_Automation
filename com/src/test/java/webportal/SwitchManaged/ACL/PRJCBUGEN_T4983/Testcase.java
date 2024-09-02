package webportal.SwitchManaged.ACL.PRJCBUGEN_T4983;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

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
    String oldmac;

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4983") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("076 -Edit manual mac acl,the policy is deny") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4983") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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
    @Step("Test Step 1: Create vlan10 on switch")
    public void step1() {
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, "10", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Add a manual mac acl to deny source address\n" + "--policy:deny\n" + "--Mac address: 11:11:11:11:11:11\n"
            + "--option:Deny access from this device")
    public void step2() {
        ipaclMac = "11:11:11:11:11:11";
        wvp.ipFilterMacOpt = 2;
        wvp.editVlanMacAuthDeny(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Generate 2 mac acl on device\n" + "1.deny source mac address:11:11:11:11:11:11\n" + "2.Permit all")
    public void step3() {
        handle.waitCmdReady(ipaclMac, false);
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(false, "10");
        assertFalse(SwitchCLIUtilsMNG.ACLClass.ispermitACL, "deny mac acl");
        tmpStr = ipaclMac;
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "allow source mac: " + tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Edit mac address from \"11:11:11:11:11:11\" to \"11:11:11:11:11:22\"")
    public void step4() {
        oldmac = ipaclMac;
        wvp.openIpFilterMacAuth(ipaclMac, false);
        ipaclMac = "11:11:11:11:11:22";
        wvp.editVlanMacAuthDeny(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: On device,the mac acl changed as follows:\n" + "1.deny source mac address:11:11:11:11:11:22\n" + "2.Permit all")
    public void step5() {
        handle.waitCmdReady(ipaclMac, false);
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(false, "10");
        assertFalse(SwitchCLIUtilsMNG.ACLClass.ispermitACL, "deny mac acl");
        tmpStr = ipaclMac;
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "deny source mac: " + tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 6: Edit option from \"Deny access from this device\" to \"Deny access to this device")
    public void step6() {
        wvp.openIpFilterMacAuth(ipaclMac, false);
        wvp.ipFilterMacOpt = 1;
        wvp.editVlanMacAuthDeny(vlanName, ipaclName, ipaclMac);
        handle.sleepsync();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 7: On device,the mac acl changed as follows:\n" + "1.deny des mac address:11:11:11:11:11:22\n" + "2.Permit all")
    public void step7() {
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(false, "10");
        assertFalse(SwitchCLIUtilsMNG.ACLClass.ispermitACL, "deny mac acl");
        tmpStr = String.format("%s", ipaclMac);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "deny des mac address:11:11:11:11:11:22: " + tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 8: Edit option select all (\"Deny access from this device\" and \"Deny access to\n" + "this device\")")
    public void step8() {
        wvp.openIpFilterMacAuth(ipaclMac, false);
        wvp.ipFilterMacOpt = 0;
        wvp.editVlanMacAuthDeny(vlanName, ipaclName, ipaclMac);
        handle.sleepsync();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 9: On device,the mac acl changed as follows:\n" + "1.deny des mac address:11:11:11:11:11:22\n"
            + "2.deny source mac address 11:11:11:11:11:22\n" + "3.Permit all")
    public void step9() {
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(false, "10");
        assertFalse(SwitchCLIUtilsMNG.ACLClass.ispermitACL, "deny mac acl");
        tmpStr = String.format("deny any %s", ipaclMac);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), tmpStr);
        tmpStr = String.format("deny any %s 00:00:00:00:00:00", ipaclMac);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), tmpStr);
        tmpStr = String.format("deny %s 00:00:00:00:00:00 any", ipaclMac);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 10: Edit mac acl\n" + "--policy:deny\n" + "--Mac address: 11:11:11:11:11:33\n" + "--option:Deny access from this device")
    public void step10() {
        wvp.openIpFilterMacAuth(ipaclMac, false);
        wvp.ipFilterMacOpt = 2;
        oldmac = ipaclMac;
        ipaclMac = "11:11:11:11:11:33";
        wvp.editVlanMacAuthDeny(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 11: On device,the mac acl changed as follows:\n" + "1.deny source mac address 11:11:11:11:11:33\n" + "2.Permit all\n" + "\n"
            + "All info on webportal should be correctly")
    public void step11() {
        List<String> ls = wvp.getAllowDevices();
        assertTrue(ls.contains(ipaclMac));

        handle.waitCmdReady(ipaclMac, false);
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(false, "10");
        assertFalse(SwitchCLIUtilsMNG.ACLClass.ispermitACL, "deny mac acl");
        tmpStr = String.format("deny %s 00:00:00:00:00:00 any", ipaclMac);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), tmpStr);
    }

}
