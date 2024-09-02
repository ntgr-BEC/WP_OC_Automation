package webportal.Switch.ACL.PRJCBUGEN_T4963;

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
    String vlanId   = "50";
    String tmpStr;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4963") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("046-Permit manual source MAC address") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4963") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 1: Create vlan50 on switch,add port1 and port2 to vlan50")
    public void step1() {
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);

        wvp.addPortToVlan(false, false, false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: 1.Input device name\n" + "2.Input MAC address is 11:22:33:11:22:33\n"
            + "3.Select option \"Allow access from this device\"\n" + "4.Add")
    public void step2() {
        wvp.ipFilterMacOpt = 2;
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: 2.On switch,generate 1 mac acls,permit source mac\n" + "11:22:33:11:22:33/00:00:00:00:00:00,the mac acl binding to vlan50")
    public void step3() {
        handle.waitCmdReady(ipaclMac, false);
        tmpStr = SwitchCLIUtils.getIpMACACL(false, vlanId);
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = String.format("permit %s 00:00:00:00:00:00 any", ipaclMac);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), tmpStr);
        tmpStr = String.format("permit any %s 00:00:00:00:00:00", ipaclMac);
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "no allow to: " + tmpStr);
    }

}
