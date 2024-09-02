package webportal.SwitchManaged.ACL.PRJCBUGEN_T4957;

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

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4957") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("014-Deny ip acl,match source ip address or destination ip address") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4957") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
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
        wvp.newVlan(vlanName, "50", 0);
        wvp.openVlan(vlanName, "50", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: add port1 and port2 to vlan50")
    public void step2() {
        wvp.addPortToVlan(false, false, false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Select Deny access from this device and deny access to this device")
    public void step3() {
        wvp.openVlan(vlanName, "50", 0);
        wvp.ipFilterMacOpt = 0;
        wvp.editVlanIpFilteringDeny(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: ​1.On webportal,list ip address under deny device lists")
    public void step4() {
        List<String> ls = wvp.getAllowDevices();
        assertTrue(ls.contains(ipaclIp));
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: ​2.On switch,generate 3 IP acls")
    public void step5() {
        String sRet = handle.waitCmdReady(ipaclIp, false);
        sRet = SwitchCLIUtilsMNG.getIpMACACL(true, "50");
        assertFalse(SwitchCLIUtilsMNG.ACLClass.ispermitACL, "deny mac acl");
        sRet = "deny any " + ipaclIp;
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(sRet), sRet);
        sRet = "deny " + ipaclIp;
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(sRet), sRet);
    }
}
