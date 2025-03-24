package webportal.ProManagedSwitch.ACL.PRJCBUGEN_T41324;

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
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1324";
    String networkName = "testnet" + vlanId;
    String tmpStr, tmpStr1;

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41324") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("091-[Network Setup] Edit custom mac acl,the policy is allow") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41324") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        netsp.gotoPage();
        netsp.deleteNetwork(networkName);
        wvp.removeAllAclCli();
    }

    @Step("Test Step 1: Go to Network Setup page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);


        handle.gotoLoction();
        wvp.gotoPage();
        wvp.deleteAllVlan();
        wvp.removeAllAclCli();
        netsp.gotoPage();
    }

    @Step("Test Step 2: Add custom mac acl,the policy is allow")
    public void step2() {
        netsp.camData.frommac = "11:11:11:11:11:11";
        netsp.camData.tomac = "22:22:22:22:22:22";
        netsp.createNetwork(networkName, vlanId, false, 0, 1);
    }
    
    @Step("Test Step 3: verify vlan on webportal")
    public void step3() {
        handle.waitCmdReady(netsp.camData.frommac, false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(String.format("vlan %s", vlanId)), "verify vlan");

        tmpStr = SwitchCLIUtils.getIpMACACL(false, vlanId);
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = "permit " + netsp.camData.frommac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit mac: " + tmpStr);
    }

    @Step("Test Step 4: Edit the custom mac acl under network setup")
    public void step4() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.camData.frommac, true);
        tmpStr1 = netsp.camData.frommac;
        netsp.camData.frommac = "11:11:11:11:11:22";
        netsp.camData.tomac = "22:22:22:22:22:33";
        netsp.setIpMacACL(false, true);
        netsp.finishAllStep();
    }

    @Step("Test Step 5: verify vlan on webportal")
    public void step5() {
        handle.waitCmdReady(netsp.camData.frommac, false);

        tmpStr = SwitchCLIUtils.getIpMACACL(false, vlanId);
        tmpStr = "permit " + netsp.camData.frommac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit mac: " + tmpStr);
        tmpStr = "permit " + tmpStr1;
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify no permit mac: " + tmpStr);
    }
}
