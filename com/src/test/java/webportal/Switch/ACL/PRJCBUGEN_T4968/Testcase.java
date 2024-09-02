package webportal.Switch.ACL.PRJCBUGEN_T4968;

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
    String vlanId   = "50";
    String tmpStr;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4968") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("051-Add custom mac address,the policy is deny") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4968") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 1: Create vlan50 on switch,add port1 and port2 to vlan50")
    public void step1() {
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.newVlan(vlanName, vlanId, 0);
        wvp.openVlan(vlanName, vlanId, 0);

        wvp.addPortToVlan(false, false, false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Create MAC ACL via webportal\n" + "--action is deny\n"
            + "--Add custom mac address(from-11:11:11:11:11:11,to-22:22:22:22:22:22)")
    public void step2() {
        wvp.camData.frommac = "11:11:11:11:11:11";
        wvp.camData.tomac = "22:22:22:22:22:22";
        wvp.enaIpFilterCustom = true;
        wvp.openVlan(vlanName, "50", 0);
        wvp.editVlanMacAuthDeny(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: On webportal,all info display correctly")
    public void step3() {
        List<String> ls = wvp.getAllowDevices();
        assertTrue(ls.contains(wvp.camData.frommac));

        handle.waitCmdReady(wvp.camData.frommac, false);
        tmpStr = SwitchCLIUtils.getIpMACACL(false, vlanId);
        assertTrue(!SwitchCLIUtils.ACLClass.ispermitACL, "check deny acl");
        tmpStr = String.format("deny %s 00:00:00:00:00:00 %s 00:00:00:00:00:00", wvp.camData.frommac, wvp.camData.tomac);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), tmpStr);
    }

}
