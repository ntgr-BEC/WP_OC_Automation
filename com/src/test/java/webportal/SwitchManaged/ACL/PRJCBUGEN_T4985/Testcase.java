package webportal.SwitchManaged.ACL.PRJCBUGEN_T4985;

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

    @Issue("PRJCBUGEN-9145")
    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4985") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("078-Delete MAC ACL,the policy is deny") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4985") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 1: Create vlan100 on switch")
    public void step1() {
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, "100", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Add manual mac acl\n" + "-policy:deny\n" + "-mac address: a1:a1:a1:a1:a1:a1\n" + "-option:deny access from this device")
    public void step2() {
        ipaclMac = "a1:a1:a1:a1:a1:a1";
        wvp.ipFilterMacOpt = 2;
        wvp.editVlanMacAuthDeny(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Add manual mac acl\n" + "-policy:deny\n" + "-mac address: a1:a1:a1:a1:a1:b1\n" + "-option:deny access to this device")
    public void step3() {
        ipaclMac = "a1:a1:a1:a1:a1:b1";
        wvp.ipFilterMacOpt = 1;
        wvp.editVlanMacAuthDeny(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Add manual mac acl\n" + "-policy:deny\n" + "-mac address: a1:a1:a1:a1:a1:22\n" + "-option:all")
    public void step4() {
        ipaclMac = "a1:a1:a1:a1:a1:22";
        wvp.ipFilterMacOpt = 0;
        wvp.editVlanMacAuthDeny(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: Add custom mac acl\n" + "-policy:deny\n" + "-From:a1:a1:a1:a1:a1:77,To:a1:a1:a1:a1:a1:88")
    public void step5() {
        wvp.camData.frommac = "a1:a1:a1:a1:a1:77";
        wvp.camData.tomac = "a1:a1:a1:a1:11:88";
        wvp.enaIpFilterCustom = true;
        wvp.editVlanMacAuthDeny(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 6: On device,existed 6 mac acls:\n" + "1.deny source a1:a1:a1:a1:a1:a1\n" + "2.deny des a1:a1:a1:a1:a1:b1\n"
            + "3.deny source a1:a1:a1:a1:a1:22\n" + "4.deny des a1:a1:a1:a1:a1:22\n" + "5.deny source a1:a1:a1:a1:a1:77,des:a1:a1:a1:a1:a1:88\n"
            + "6.permit all")
    public void step6() {
        MyCommonAPIs.sleepsync();
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(false, "100");
        tmpStr = "deny a1:a1:a1:a1:a1:a1 00:00:00:00:00:00 any";
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), tmpStr);
        tmpStr = "deny any a1:a1:a1:a1:a1:b1 00:00:00:00:00:00";
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), tmpStr);
        tmpStr = "deny any a1:a1:a1:a1:a1:22 00:00:00:00:00:00";
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), tmpStr);
        tmpStr = "deny a1:a1:a1:a1:a1:22 00:00:00:00:00:00 any";
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), tmpStr);
        tmpStr = "deny a1:a1:a1:a1:a1:77 00:00:00:00:00:00 a1:a1:a1:a1:11:88 00:00:00:00:00:00";
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 7: Delete all policy")
    public void step7() {
        wvp.removeAllAcl();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 8: All policies are deleted")
    public void step8() {
        handle.waitCmdReady("1:11:88", true);
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(false, "100");
        assertTrue(!SwitchCLIUtilsMNG.ACLClass.aclResult.contains(ipaclMac), ipaclMac);
    }

}
