package webportal.ProManagedSwitch.ACL.PRJCBUGEN_T41181;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
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

    String ipaclName = "test-025";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";

    @Issue("PRJCBUGEN-9730")
    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41181") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("026-Edit access type for manual ip allow policy") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41181") // It's a testcase id/link from Jira Test Case.
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
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, "50", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: ​​​​​--Allow,source ip 11.1.1.1,ip mask 0.0.255.255 Option: access allow from this device")
    public void step2() {
        wvp.ipFilterIpMask = "0.0.0.255";
        wvp.ipFilterMacOpt = 2;
        wvp.enaIpFilterIpMask = true;
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: ​​​​​On device,generate 1 IP acl on device:\n" + "--Allow,source ip 11.1.1.1,mask 0.0.0.255")
    public void step3() {
        handle.waitCmdReady("i1111100025550", false);
        tmpStr = SwitchCLIUtils.getIpMACACL(true, "50");
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = String.format("permit %s", ipaclIp);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify the ip is set: " + tmpStr);
        tmpStr = wvp.ipFilterIpMask;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify the mask is set: " + tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Edit Access type to \"access allow to this device\"")
    public void step4() {
        wvp.ipFilterMacOpt = 1;
        wvp.openIpFilterMacAuth(ipaclIp, true);
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
        handle.sleepsync();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: ​​​​​​​​​​On device,generate 1 IP acl on device:\n" + "--Allow,destination ip 11.1.1.1,mask 0.0.0.255")
    public void step5() {
        tmpStr = SwitchCLIUtils.getIpMACACL(true, "50");
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = String.format("permit any %s", ipaclIp);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify the new ip is set: " + tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 6: Edit Access type to \"access allow from this device\" and \"access allow to\n" + "this device\"")
    public void step6() {
        Selenide.refresh();
        wvp.ipFilterMacOpt = 0;
        wvp.openIpFilterMacAuth(ipaclIp, true);
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 7: ​​​​​​​​​​On device,generate 2 IP acl on device:\n" + "--Allow,source ip 11.1.1.1,mask 0.0.0.255\n"
            + "--Allow,des ip 11.1.1.1,mask 0.0.0.255")
    public void step7() {
        handle.waitCmdReady(String.format("permit %s", ipaclIp), false);
        tmpStr = SwitchCLIUtils.getIpMACACL(true, "50");
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = String.format("permit any %s", ipaclIp);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "new ip allow from on device: " + tmpStr);
        tmpStr = String.format("permit %s", ipaclIp);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "new ip allow to on device: " + tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 8: Edit Access type to \"access allow to this device\"")
    public void step8() {
        wvp.ipFilterMacOpt = 1;
        wvp.openIpFilterMacAuth(ipaclIp, true);
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 9: ​​​​​​​​​​1.On device,generate 1 IP acl on device:\n" + "--Allow,source ip 11.1.1.1,mask 0.0.0.255\n"
            + "2.Old ip acl is deleted")
    public void step9() {
        handle.waitCmdReady(String.format("permit %s", ipaclIp), true);
        tmpStr = SwitchCLIUtils.getIpMACACL(true, "50");
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = String.format("permit any %s", ipaclIp);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "new ip allow to only on device: " + tmpStr);
    }

}
