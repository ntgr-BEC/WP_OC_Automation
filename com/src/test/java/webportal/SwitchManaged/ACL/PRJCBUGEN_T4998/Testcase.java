package webportal.SwitchManaged.ACL.PRJCBUGEN_T4998;

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

    @Issue("PRJCBUGEN-9730")
    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4998") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("036-Customer deny policy: Edit From and To") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4998") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 2: 1.From device name: fromname\n" + "2.From IP/mask:1.1.1.1/0.0.0.255\n" + "3.To device name:toname\n"
            + "4.To IP/mask:2.2.2.2/0.0.0.255")
    public void step2() {
        wvp.camData.fromname = "fromname";
        wvp.camData.toname = "toname";
        wvp.camData.enableAdd1 = true;
        wvp.camData.enableAdd2 = true;
        wvp.camData.fromip = "1.1.1.1";
        wvp.camData.toip = "2.2.2.2";
        wvp.camData.fromipmask = "0.0.0.255";
        wvp.camData.toipmask = "0.0.0.255";

        wvp.enaIpFilterCustom = true;
        wvp.editVlanIpFilteringDeny(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Generate 2 IP acls on device\n" + "--deny,source-1.1.1.1/0.0.0.255,destination-2.2.2.2/0.0.0.255\n" + "--permit all")
    public void step3() {
        handle.waitCmdReady(wvp.camData.fromip, false);
        tmpStr = SwitchCLIUtils.getIpMACACL(true, "50");
        assertTrue(!SwitchCLIUtils.ACLClass.ispermitACL, "check deny acl");
        assertTrue(
                SwitchCLIUtils.ACLClass.aclResult.contains(
                        String.format("deny %s %s %s %s", wvp.camData.fromip, wvp.camData.fromipmask, wvp.camData.toip, wvp.camData.toipmask)),
                "deny,source-1.1.1.1/0.0.0.255,destination-2.2.2.2/0.0.0.255");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: 1.From device name: fromname01\n" + "2.From IP/mask:1.1.1.2/0.0.255.255.To device name:toname02\n"
            + "4.To IP/mask:2.2.2.3/0.0.255.255")
    public void step4() {
        wvp.openIpFilterMacAuth(wvp.camData.fromip, true);

        wvp.camData.fromname = "fromname01";
        wvp.camData.toname = "toname02";
        wvp.camData.fromip = "1.1.1.2";
        wvp.camData.toip = "2.2.2.3";
        wvp.camData.fromipmask = "0.0.255.255";
        wvp.camData.toipmask = "0.0.255.255";

        wvp.enaIpFilterCustom = true;
        wvp.editVlanIpFilteringDeny(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: 1.On app,all info should be correctly\n" + "2.On device,the old ip acl is deleted,generate 2 new ip acls:\n"
            + "--deny,source-1.1.1.2/0.0.255.255,destination-2.2.2.3/0.0.255.255\n" + "--permit all")
    public void step5() {
        handle.waitCmdReady(wvp.camData.fromip, false);
        tmpStr = SwitchCLIUtils.getIpMACACL(true, null);
        assertTrue(!SwitchCLIUtils.ACLClass.ispermitACL, "check deny acl");
        assertTrue(
                SwitchCLIUtils.ACLClass.aclResult.contains(
                        String.format("deny %s %s %s %s", wvp.camData.fromip, wvp.camData.fromipmask, wvp.camData.toip, wvp.camData.toipmask)),
                "deny,source-1.1.1.2/0.0.255.255,destination-2.2.2.3/0.0.255.255");
    }

}
