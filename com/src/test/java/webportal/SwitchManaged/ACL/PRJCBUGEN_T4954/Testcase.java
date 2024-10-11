package webportal.SwitchManaged.ACL.PRJCBUGEN_T4954;

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
    String vlanId   = "1954";

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:11:11:11:11:11";

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4954") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("011-Permit ip acl,match source ip or destination ip") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4954") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        wvp.gotoPage();
        wvp.deleteAllVlan();
        wvp.removeAllAclCli();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create vlan 1000 on switch")
    public void step1() {
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.deleteAllVlan();
        wvp.newVlan(vlanName, vlanId, 0);
        wvp.openVlan(vlanName, vlanId, 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Add manual mac acl that allow source and des mac address")
    public void step2() {
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: verify vlan on webportal")
    public void step3() {
        List<String> ls = wvp.getAllowDevices();
        assertTrue(ls.contains(ipaclMac));
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: 2.On Switch,generate 2 mac acls,one is allow source mac,other is allow des")
    public void step4() {
        handle.waitCmdReady(ipaclMac, false);
        tmpStr = SwitchCLIUtils.getIpMACACL(false, vlanId);
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = String.format("permit any %s 00:00:00:00:00:00", ipaclMac);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "allow source mac: " + tmpStr);
        tmpStr = String.format("permit %s 00:00:00:00:00:00 any", ipaclMac);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "other is allow des: " + tmpStr);
    }

}
