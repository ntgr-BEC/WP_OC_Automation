package webportal.SwitchManaged.ACL.PRJCBUGEN_T4966;

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
    String vlanId   = "50";
    String tmpStr;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4966") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("049-Deny manual source mac address") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4966") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 2: Create MAC ACL include 1 rules,binding it to vlan50.\n"
            + "-action is deny,input source MAC address,option select allow access from this device")
    public void step2() {
        wvp.ipFilterMacOpt = 2;
        wvp.editVlanMacAuthDeny(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: generated 2 MAC acl on switch.\n" + "1.Deny source mac address\n" + "2.Permit all mac address")
    public void step3() {
        handle.waitCmdReady(ipaclMac, false);
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(false, vlanId);
        assertTrue(!SwitchCLIUtilsMNG.ACLClass.ispermitACL, "check deny acl");
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(String.format("deny %s 00:00:00:00:00:00 any", ipaclMac)),
                "1.Deny source mac address");
    }

}
