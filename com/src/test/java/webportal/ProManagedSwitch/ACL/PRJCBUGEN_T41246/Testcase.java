package webportal.ProManagedSwitch.ACL.PRJCBUGEN_T41246;

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
    String vlanId   = "78";
    String tmpStr;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41246") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("045-Test MAC ACL name") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41246") // It's a testcase id/link from Jira Test Case.
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
        wvp.openVlan(vlanName, vlanId, 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: On webportal,create mac acl for one vlan")
    public void step2() {
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: On switch,generate 2 MAC ACL,the MAC ACL name is\n"
            + "\"m11223311223300000000000078\" and \"m11223311223300000000000000782\"")
    public void step3() {
        handle.waitCmdReady(ipaclMac, false);
        tmpStr = SwitchCLIUtils.getIpMACACL(false, vlanId);
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        assertTrue(tmpStr.contains(String.format("%s", "m11223311223300000000000078")), "m11223311223300000000000078");
        assertTrue(tmpStr.contains(String.format("%s", "m112233112233000000000000782")), "m112233112233000000000000782");
    }

}
