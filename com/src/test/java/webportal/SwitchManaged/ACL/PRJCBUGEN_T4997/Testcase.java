package webportal.SwitchManaged.ACL.PRJCBUGEN_T4997;

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
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();

    String vlanName = "testvlan";
    String tmpStr;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";

    @Issue("PRJCBUGEN-23462")
    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4997") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("035-Customer deny policy: Create ip acl,device range is enable") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4997") // It's a testcase id/link from Jira Test Case.
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
        // if (!WebportalParam.skipIssueCheck)
            // throw new RuntimeException("PRJCBUGEN-23462");
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.newVlan(vlanName, "50", 0);
        wvp.openVlan(vlanName, "50", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: 1.Input from device name\n" + "2.Input from ip 100.1.1.1\n" + "3.Enable range of device,input ip mask 0.0.255.255\n"
            + "4.Input to device name\n" + "5.Input to ip 200.1.1.1\n" + "6,Enable range of deive,input ip mask 0.0.255.255")
    public void step2() {
        wvp.camData.fromname = "devices001";
        wvp.camData.toname = "devices002";
        wvp.camData.enableAdd1 = true;
        wvp.camData.enableAdd2 = true;
        wvp.camData.fromip = "100.1.1.1";
        wvp.camData.toip = "200.1.1.1";
        wvp.camData.fromipmask = "0.0.255.255";
        wvp.camData.toipmask = "0.0.255.255";

        wvp.enaIpFilterCustom = true;
        wvp.editVlanIpFilteringDeny(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Generate 2 IP ACLs on switch:\n" + "--Deny,source-100.1.1.1/0.0.0.0,des-100.1.1.20/0.0.0.0\n" + "--Permit all")
    public void step3() {
        handle.waitCmdReady(wvp.camData.fromip, false);
        tmpStr = SwitchCLIUtils.getIpMACACL(true, "50");
        assertTrue(!SwitchCLIUtils.ACLClass.ispermitACL, "check deny acl");
        assertTrue(
                SwitchCLIUtils.ACLClass.aclResult.contains(
                        String.format("deny %s %s %s %s", wvp.camData.fromip, wvp.camData.fromipmask, wvp.camData.toip, wvp.camData.toipmask)),
                "Deny,source-100.1.1.1/0.0.0.0,des-100.1.1.20/0.0.0.0");
    }

}
