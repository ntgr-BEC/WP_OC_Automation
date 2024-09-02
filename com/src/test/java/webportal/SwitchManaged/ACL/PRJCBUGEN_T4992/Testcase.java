package webportal.SwitchManaged.ACL.PRJCBUGEN_T4992;

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
    @Story("PRJCBUGEN_T4992") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("030-Customer allow policy: Create ip acl,device range is disable") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4992") // It's a testcase id/link from Jira Test Case.
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
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.newVlan(vlanName, "50", 0);
        wvp.openVlan(vlanName, "50", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Use Customer to create IP ACL,the policy is allow,do not use range of device")
    public void step2() {
        wvp.camData.fromname = "devices001";
        wvp.camData.toname = "devices002";
        wvp.camData.fromip = "100.1.1.1";
        wvp.camData.toip = "100.1.1.2";

        wvp.enaIpFilterCustom = true;
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Allow,source ip 100.1.1.1/0.0.0.0,des ip 100.1.1.2/0.0.0.0")
    public void step3() {
        handle.waitCmdReady(wvp.camData.toip, false);
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(true, "50");
        assertTrue(SwitchCLIUtilsMNG.ACLClass.ispermitACL, "check allow acl");
        tmpStr = String.format("permit %s", wvp.camData.fromip);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "source ip -100.1.1.1"+": "+tmpStr);
    }

}
