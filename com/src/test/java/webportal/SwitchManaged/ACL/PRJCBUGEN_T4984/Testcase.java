package webportal.SwitchManaged.ACL.PRJCBUGEN_T4984;

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
    String tmpStr;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";
    String oldmac;

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4984") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("077-Edit custom mac acl,the policy is deny") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4984") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 2: Create mac acl for the vlan\n" + "--policy:deny\n" + "Custom address,from-a1:a1:a1:a1:a1:a1,to-b1:b1:b1:b1:b1:b1")
    public void step2() {
        wvp.camData.frommac = "a1:a1:a1:a1:a1:a1";
        wvp.camData.tomac = "b1:b1:b1:b1:b1:b1";
        wvp.enaIpFilterCustom = true;
        wvp.editVlanMacAuthDeny(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: 1.On device,generate 2 mac acls\n" + "--Deny:source-a1:a1:a1:a1:a1:a1,des-b1:b1:b1:b1:b1:b1\n" + "--Permit all\n"
            + "2.All info on webportal should be correctly")
    public void step3() {
        List<String> ls = wvp.getAllowDevices();
        assertTrue(ls.contains(wvp.camData.frommac));

        handle.sleepsync();
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(false, "100");
        tmpStr = String.format("deny %s 00:00:00:00:00:00 %s 00:00:00:00:00:00", wvp.camData.frommac, wvp.camData.tomac);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Edit custom deny policy.\n" + "from-a1:a1:a1:a1:a1:00,to-b1:b1:b1:b1:b1:00")
    public void step4() {
        oldmac = wvp.camData.frommac;
        wvp.openIpFilterMacAuth(oldmac, false);
        wvp.camData.frommac = "a1:a1:a1:a1:a1:00";
        wvp.camData.tomac = "b1:b1:b1:b1:b1:00";
        wvp.editVlanMacAuthDeny(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: Old mac acl is deleted,generate new mac acls on device.\n" + "1.Deny:source-a1:a1:a1:a1:a1:00,des-b1:b1:b1:b1:b1:00\n"
            + "2.Permit all")
    public void step5() {
        handle.sleepsync();
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(false, "100");
        tmpStr = String.format("deny %s 00:00:00:00:00:00 %s 00:00:00:00:00:00", wvp.camData.frommac, wvp.camData.tomac);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), tmpStr);
        tmpStr = oldmac;
        assertFalse(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "no: " + tmpStr);
    }

}
