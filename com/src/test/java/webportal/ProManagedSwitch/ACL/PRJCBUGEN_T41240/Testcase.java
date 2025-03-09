package webportal.ProManagedSwitch.ACL.PRJCBUGEN_T41240;

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
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Sumanta
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
    @Story("PRJCBUGEN_T41240") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("044-Create mac acl for a vlan,then delete the vlan") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41240") // It's a testcase id/link from Jira Test Case.
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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, "50", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Create a vlan,binding IP ACLs for it")
    public void step2() {
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: verify vlan on webportal")
    public void step3() {
        handle.waitCmdReady(ipaclMac, false);
        tmpStr = SwitchCLIUtils.getIpMACACL(false, "50");
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = String.format("permit %s", ipaclMac);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit mac: " + ipaclMac+": "+tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Delete vlan on webportal")
    public void step4() {
        wvp.gotoPage();
        wvp.deleteVlan("50", true);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: verify vlan on webportal")
    public void step5() {
        handle.sleepsync();
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertFalse(tmpStr.contains(String.format("vlan %s", 50)), "no vlan");
        assertFalse(tmpStr.contains(ipaclMac), "verify no permit ip any any");
    }

}
