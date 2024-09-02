package webportal.SwitchManaged.ACL.PRJCBUGEN_T4965;

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
    String vlanId   = "965";
    String tmpStr;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4965") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("048-Add custom mac addrss,the policy is allow") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4965") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 1: Create vlan50 on switch,add port1 and port2 to vlan50")
    public void step1() {
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);

        wvp.addPortToVlan(false, false, false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: 1.Policy:Allow\n" + "2.Click Custom button,Input from is \"88:88:88:88:88:88\",to is\n" + "\"88:88:88:88:88:99\"\n"
            + "3.Save")
    public void step2() {
        wvp.camData.frommac = "88:88:88:88:88:88";
        wvp.camData.tomac = "88:88:88:88:88:99";
        wvp.enaIpFilterCustom = true;
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Generate 1 mac acl on switch\n" + "--Allow,source-88:88:88:88:88:88,des-88:88:88:88:88:99")
    public void step3() {
        handle.waitCmdReady(wvp.camData.frommac, false);
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(false, vlanId);
        assertTrue(SwitchCLIUtilsMNG.ACLClass.ispermitACL, "check allow acl");
        assertTrue(
                SwitchCLIUtilsMNG.ACLClass.aclResult
                        .contains(String.format("permit %s 00:00:00:00:00:00 %s 00:00:00:00:00:00", wvp.camData.frommac, wvp.camData.tomac)),
                "Allow,source-88:88:88:88:88:88,des-88:88:88:88:88:99");
    }

}
