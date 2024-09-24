package webportal.SwitchManaged.ACL.PRJCBUGEN_T4981;

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
import util.SwitchCLIUtils;
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
    @Story("PRJCBUGEN_T4981") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("074-Edit manual mac acl,the policy is allow") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4981") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 1: Create vlan200 on switch")
    public void step1() {
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, "200", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: 1.Policy:Allow\n" + "2.Mac address:22-22-22-33-33-33\n" + "3.option select \"allow access from this device\"\n" + "4.save")
    public void step2() {
        ipaclMac = "22:22:22:33:33:33";
        wvp.ipFilterMacOpt = 2;
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: On device,generate 1 mac acl binding to the vlan\n" + "--Allow:source mac 22-22-22-33-33-33")
    public void step3() {
        MyCommonAPIs.sleepsync();
        handle.waitCmdReady(ipaclMac, false);
        tmpStr = SwitchCLIUtils.getIpMACACL(false, "200");
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(String.format("permit %s 00:00:00:00:00:00 any", ipaclMac)),
                "Allow:source mac 22-22-22-33-33-33");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: On allow device list,select the mac address \"22-22-22-33-33-33\",edit to\n" + "\"22-22-22-33-33-44\"")
    public void step4() {
        oldmac = ipaclMac;
        wvp.openIpFilterMacAuth(ipaclMac, false);
        ipaclMac = "22:22:22:33:33:44";
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: 1.On device,generate 1 mac acl binding to the vlan\n" + "--Allow:source mac 22-22-22-33-33-44\n"
            + "2.Old mac acl is deleted")
    public void step5() {
        MyCommonAPIs.sleepsync();
        handle.waitCmdReady(oldmac, true);
        tmpStr = SwitchCLIUtils.getIpMACACL(false, "200");
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(String.format("permit %s 00:00:00:00:00:00 any", ipaclMac)),
                "Allow:source mac 22-22-22-33-33-44");
        tmpStr = String.format("permit %s 00:00:00:00:00:00 any", oldmac);
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "Old mac acl is deleted"+": "+tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 6: On allow device list,select the mac address \"22-22-22-33-33-44\",edit option\n"
            + "from \"allow access from this device\" to \"\"allow access to this device\"\"")
    public void step6() {
        wvp.openIpFilterMacAuth(ipaclMac, false);
        wvp.ipFilterMacOpt = 1;
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 7: On device,have 1 mac acl bindint to the vlan\n" + "--Allow:des mac:22-22-22-33-33-44")
    public void step7() {
        handle.sleepsync();
        tmpStr = SwitchCLIUtils.getIpMACACL(false, "200");
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(String.format("permit %s 00:00:00:00:00:00 any", ipaclMac)),
                "Allow:des mac:22-22-22-33-33-44");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 8: On allow device list,select the mac address \"22-22-22-33-33-44\",edit option\n"
            + "to select all (\"allow access from this device\" and \"allow access to this\n" + "device\")")
    public void step8() {
        wvp.openIpFilterMacAuth(ipaclMac, false);
        wvp.ipFilterMacOpt = 0;
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 9: On device,have 2 mac acl bindint to the vlan\n" + "--Allow:source mac:22-22-22-33-33-44\n"
            + "--Allow:des mac:22-22-22-33-33-44")
    public void step9() {
        MyCommonAPIs.sleepsync();
        handle.waitCmdReady(String.format("permit %s", ipaclMac), false);
        tmpStr = SwitchCLIUtils.getIpMACACL(false, "200");
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(String.format("permit %s 00:00:00:00:00:00 any", ipaclMac)),
                "Allow:des mac:22-22-22-33-33-44");
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(String.format("permit %s 00:00:00:00:00:00 any", ipaclMac)),
                "Allow:source mac 22-22-22-33-33-44");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 10: Edit option to \"allow access from this device\" again")
    public void step10() {
        wvp.openIpFilterMacAuth(ipaclMac, false);
        wvp.ipFilterMacOpt = 2;
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 11: On device,have 1 mac acl bindint to the vlan\n" + "--Allow:source mac:22-22-22-33-33-44")
    public void step11() {
        MyCommonAPIs.sleepsync();
        handle.waitCmdReady("permit any", true);
        tmpStr = SwitchCLIUtils.getIpMACACL(false, "200");
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(String.format("permit any %s 00:00:00:00:00:00", ipaclMac)),
                "Allow:des mac:22-22-22-33-33-44");
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(String.format("permit %s 00:00:00:00:00:00 any", ipaclMac)),
                "Allow:source mac 22-22-22-33-33-44");
    }

}
