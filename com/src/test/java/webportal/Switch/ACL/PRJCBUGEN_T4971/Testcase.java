package webportal.Switch.ACL.PRJCBUGEN_T4971;

import static org.testng.Assert.assertFalse;
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

    @Issue("PRJCBUGEN-9145")
    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4971") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("062-Delete MAC ACL,the policy is allow") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4971") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 2: Create manual mac acl\n" + "--policy:allow\n" + "--Mac address: 12:12:12:12:12:12\n"
            + "--option:allow access from this device")
    public void step2() {
        ipaclMac = "12:12:12:12:12:12";
        wvp.ipFilterMacOpt = 2;
        wvp.editVlanMacAuthAllow(vlanName, ipaclName + "1", ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Create manual mac acl\n" + "--policy:allow\n" + "--Mac address: 13:13:13:13:13:13\n"
            + "--option:allow access to this device")
    public void step3() {
        ipaclMac = "13:13:13:13:13:13";
        wvp.ipFilterMacOpt = 1;
        wvp.editVlanMacAuthAllow(vlanName, ipaclName + "2", ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Create manual mac acl\n" + "--policy:allow\n" + "--Mac address: 14:14:14:14:14:14\n"
            + "--option:allow access from this device &allow access to this device")
    public void step4() {
        ipaclMac = "14:14:14:14:14:14";
        wvp.ipFilterMacOpt = 0;
        wvp.editVlanMacAuthAllow(vlanName, ipaclName + "3", ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: Create customer mac acl\n" + "--policy:allow\n" + "--customer: from-15:15:15:15:15:15,to-16:16:16:16:16:16")
    public void step5() {
        wvp.camData.frommac = "15:15:15:15:15:15";
        wvp.camData.tomac = "16:16:16:16:16:16";
        wvp.enaIpFilterCustom = true;
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 6: Check MAC ACLs on device")
    public void step6() {
        handle.waitCmdReady("m151515151515161616161616100", false);
        tmpStr = SwitchCLIUtils.getIpMACACL(false, "100");
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "allow mac acl");
        assertTrue(tmpStr.contains("m121212121212000000000000100"), "permit 12:12:12:12:12:12");
        assertTrue(tmpStr.contains("m131313131313000000000000100"), "permit 13:13:13:13:13:13");
        assertTrue(tmpStr.contains("m1414141414140000000000001002"), "permit any 14:14:14:14:14:14");
        assertTrue(tmpStr.contains("m141414141414000000000000100"), "permit 14:14:14:14:14:14 any");
        assertTrue(tmpStr.contains("m151515151515161616161616100"), "permit 15:15:15:15:15:15");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 7: Delete vlan on webportal")
    public void step7() {
        wvp.removeAllAcl();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 8: All 5 mac acls are deleted on device")
    public void step8() {
        handle.waitCmdReady("m151515151515161616161616100", true);
        tmpStr = SwitchCLIUtils.getIpMACACL(false, "100");
        assertFalse(tmpStr.contains("m121212121212000000000000100"), "no permit 12:12:12:12:12:12");
        assertFalse(tmpStr.contains("m131313131313000000000000100"), "no permit 13:13:13:13:13:13");
        assertFalse(tmpStr.contains("m1414141414140000000000001002"), "no permit any 14:14:14:14:14:14");
        assertFalse(tmpStr.contains("m141414141414000000000000100"), "no permit 14:14:14:14:14:14 any");
        assertFalse(tmpStr.contains("m151515151515161616161616100"), "no permit 15:15:15:15:15:15");
    }

}
