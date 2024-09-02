package webportal.SwitchManaged.ACL.PRJCBUGEN_T4989;

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
    String oldValue;

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4989") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("027-Test IP mask of manual deny policy") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4989") // It's a testcase id/link from Jira Test Case.
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
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, "50", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: ​​​​​Use manual type,create a deny policy for source and destination ip\n" + "address,IP mask input 0.0.255.255")
    public void step2() {
        wvp.ipFilterIpMask = "0.0.255.255";
        wvp.ipFilterMacOpt = 0;
        wvp.enaIpFilterIpMask = true;
        wvp.editVlanIpFilteringDeny(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: ​​​​​Generate 3 IP ACL on the device:\n" + "--Deny,source 11.1.1.1/0.0.255.255\n"
            + "--Deny,destination 11.1.1.1/0.0.255.255\n" + "--Permit all")
    public void step3() {
        handle.waitCmdReady(ipaclIp, false);
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(true, "50");
        assertTrue(!SwitchCLIUtilsMNG.ACLClass.ispermitACL, "check allow acl");
        tmpStr = String.format("deny %s", ipaclIp);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "Deny,source 11.1.1.1/0.0.255.255: " + tmpStr);
        tmpStr = String.format("deny any %s", ipaclIp);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "--Deny,destination 11.1.1.1/0.0.255.255: " + tmpStr);
        tmpStr = wvp.ipFilterIpMask;
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "Verify Mask: " + tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Edit the IP mask to 0.0.0.0")
    public void step4() {
        oldValue = wvp.ipFilterIpMask;
        wvp.ipFilterIpMask = "0.0.0.0";
        wvp.openIpFilterMacAuth(ipaclIp, true);
        wvp.editVlanIpFilteringDeny(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: ​​​​​​​​​​Generate 3 IP ACL on the device:\n" + "--Deny,source 11.1.1.1/0.0.0.0\n" + "--Deny,destination 11.1.1.1/0.0.0.0\n"
            + "--Permit all")
    public void step5() {
        handle.waitCmdReady(oldValue, true);
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(true, "50");
        assertTrue(!SwitchCLIUtilsMNG.ACLClass.ispermitACL, "check allow acl");
        tmpStr = String.format("deny %s", ipaclIp);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "Deny,source 11.1.1.1/0.0.0.0: " + tmpStr);
        tmpStr = String.format("deny any %s", ipaclIp);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "--Deny,destination 11.1.1.1/0.0.0.0: " + tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 6: Edit the IP mask to 255.255.255.255")
    public void step6() {
        wvp.ipFilterIpMask = "255.255.255.255";
        wvp.openIpFilterMacAuth(ipaclIp, true);
        wvp.editVlanIpFilteringDeny(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 7: Generate 3 IP ACL on the device:\n" + "--Deny,source 11.1.1.1/255.255.255.255\n"
            + "--Deny,destination 11.1.1.1/255.255.255.255\n" + "--Permit all")
    public void step7() {
        handle.waitCmdReady(wvp.ipFilterIpMask, false);
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(true, "50");
        assertTrue(!SwitchCLIUtilsMNG.ACLClass.ispermitACL, "check allow acl");
        tmpStr = String.format("deny %s", ipaclIp);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "Deny,source 11.1.1.1/255.255.255.255: " + tmpStr);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(String.format("deny any %s", ipaclIp)),
                "--Deny,destination 11.1.1.1/255.255.255.255");
        tmpStr = wvp.ipFilterIpMask;
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "Verify Mask: " + tmpStr);
    }

}
