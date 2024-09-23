package webportal.SwitchManaged.ACL.PRJCBUGEN_T11589;

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
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1589";
    String networkName = "testnet" + vlanId;
    String tmpStr, tmpStr1;

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11589") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("093-[Network Setup] Edit custom mac acl,the policy is deny") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11589") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        netsp.gotoPage();
    }

    @Step("Test Step 2: Create mac acl for the vlan --policy:deny Custom address,from-a1:a1:a1:a1:a1:a1,to-b1:b1:b1:b1:b1:b1")
    public void step2() {
        netsp.camData.frommac = "a1:a1:a1:a1:11:11";
        netsp.camData.tomac = "b1:b1:b1:b1:22:22";
        netsp.createNetwork(networkName, vlanId, false, 1, 1);
    }

    @Step("Test Step 3: verify vlan on webportal")
    public void step3() {
        handle.waitCmdReady(":11:11", false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(String.format("vlan %s", vlanId)), "verify vlan");

        tmpStr = SwitchCLIUtils.getIpMACACL(false, vlanId);
        assertFalse(SwitchCLIUtils.ACLClass.ispermitACL, "check deny acl");
        tmpStr = "deny " + netsp.camData.frommac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify deny mac: " + tmpStr);
    }

    @Step("Test Step 4: Edit custom deny policy. from-a1:a1:a1:a1:a1:00,to-b1:b1:b1:b1:b1:00")
    public void step4() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.camData.frommac, true);
        tmpStr1 = netsp.camData.frommac;
        netsp.camData.frommac = "a1:a1:a1:a1:11:22";
        netsp.camData.tomac = "b1:b1:b1:b1:22:33";
        netsp.setIpMacACL(false, true);
        netsp.finishAllStep();
    }

    @Step("Test Step 5: verify vlan on webportal")
    public void step5() {
        handle.waitCmdReady(":11:22", false);

        tmpStr = SwitchCLIUtils.getIpMACACL(false, vlanId);
        assertFalse(SwitchCLIUtils.ACLClass.ispermitACL, "check deny acl");
        tmpStr = "deny " + tmpStr1;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify deny mac is removed: " + tmpStr);
    }
}
