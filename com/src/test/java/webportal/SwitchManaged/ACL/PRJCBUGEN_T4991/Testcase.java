package webportal.SwitchManaged.ACL.PRJCBUGEN_T4991;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

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

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4991") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("029-Edit access type for manual deny policy") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4991") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 1: Create vlan50 on switch")
    public void step1() {
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, "50", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: 1.Device name:029\n" + "2.IP: 100.1.1.1\n" + "3.Mask:0.0.0.255\n"
            + "4.Access type: access deny from this device,access deny to this device")
    public void step2() {
        ipaclIp = "100.1.1.1";
        wvp.ipFilterIpMask = "0.0.0.255";
        wvp.ipFilterMacOpt = 0;
        wvp.enaIpFilterIpMask = true;
        wvp.editVlanIpFilteringDeny(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: ​​​​​Generate 3 IP acls on devices:\n" + "1.Deny,source IP 100.1.1.1/0.0.0.255\n"
            + "2.Deny,destination IP 100.1.1.1/0.0.0.255\n" + "3.Permit all")
    public void step3() {
        handle.waitCmdReady(ipaclIp, false);
        tmpStr = SwitchCLIUtils.getIpMACACL(true, "50");
        assertTrue(!SwitchCLIUtils.ACLClass.ispermitACL, "check deny acl");
        tmpStr = String.format("deny %s", ipaclIp);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "Deny,source IP 100.1.1.1/0.0.0.255"+": "+tmpStr);
        tmpStr = String.format("deny any %s", ipaclIp);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "Deny,destination IP 100.1.1.1/0.0.0.255"+": "+tmpStr);
        tmpStr = wvp.ipFilterIpMask;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "Verify Mask"+": "+tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Edit Access type to \"Access deny from this device\"")
    public void step4() {
        wvp.ipFilterMacOpt = 2;
        wvp.openIpFilterMacAuth(ipaclIp, true);
        wvp.editVlanIpFilteringDeny(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: Generate 2 IP acls on devices,old acls is deleted:\n" + "1.Deny,source IP 100.1.1.1/0.0.0.255\n" + "2.Permit all")
    public void step5() {
        handle.waitCmdReady("deny any", true);
        tmpStr = SwitchCLIUtils.getIpMACACL(true, null);
        assertTrue(!SwitchCLIUtils.ACLClass.ispermitACL, "check deny acl");
        tmpStr = String.format("deny %s", ipaclIp);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "Deny,source IP 100.1.1.1/0.0.0.255"+": "+tmpStr);
        tmpStr = wvp.ipFilterIpMask;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "Verify Mask"+": "+tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 6: Edit Access type to \"Access deny to this device\"")
    public void step6() {
        Selenide.refresh();
        wvp.ipFilterMacOpt = 1;
        wvp.openIpFilterMacAuth(ipaclIp, true);
        wvp.editVlanIpFilteringDeny(vlanName, ipaclName, ipaclIp);
        handle.sleepsync();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 7: Generate 2 IP acls on devices,old acls is deleted:\n" + "1.Deny,destination IP 100.1.1.1/0.0.0.255\n" + "2.Permit all")
    public void step7() {
        tmpStr = SwitchCLIUtils.getIpMACACL(true, "50");
        assertTrue(!SwitchCLIUtils.ACLClass.ispermitACL, "check deny acl");
        tmpStr = String.format("deny any %s", ipaclIp);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "Deny,destination IP 100.1.1.1/0.0.0.255"+": "+tmpStr);
        tmpStr = wvp.ipFilterIpMask;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "Verify Mask"+": "+tmpStr);
    }

}
