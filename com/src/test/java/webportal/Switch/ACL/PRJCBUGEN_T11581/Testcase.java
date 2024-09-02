package webportal.Switch.ACL.PRJCBUGEN_T11581;

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
    String vlanName    = "testvlan";
    String vlanId      = "1581";
    String networkName = "testnet" + vlanId;
    String tmpStr, tmpStr1;

    @Issue("PRJCBUGEN-15757")
    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11581") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("085-[Network Setup] Customer allow policy: Edit From") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11581") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
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

    @Step("Test Step 2: Add Customer IP ACL,policy is allow")
    public void step2() {
        netsp.camData.fromname = "1";
        netsp.camData.fromip = "11.1.1.1";
        netsp.camData.fromipmask = "0.0.0.255";
        netsp.camData.toname = "2";
        netsp.camData.toip = "12.1.1.1";
        netsp.camData.toipmask = "0.0.0.255";
        netsp.createNetwork(networkName, vlanId, true, 0, 1);
    }

    @Step("Test Step 3: verify vlan on webportal")
    public void step3() {
        handle.waitCmdReady(netsp.camData.fromip, false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(String.format("vlan %s", vlanId)), "verify vlan");

        tmpStr = SwitchCLIUtils.getIpMACACL(true, vlanId);
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = String.format("permit %s %s %s %s", netsp.camData.fromip, netsp.camData.fromipmask, netsp.camData.toip, netsp.camData.toipmask);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit ip host: " + tmpStr);
    }

    @Step("Test Step 4: Edit From info under network setup")
    public void step4() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.camData.fromip, true);
        netsp.camData.fromname = "from1";
        tmpStr1 = netsp.camData.fromip;
        netsp.camData.fromip = "13.1.1.1";
        netsp.camData.fromipmask = "0.0.255.255";
        netsp.setIpMacACL(true, true);
        netsp.finishAllStep();
    }

    @Step("Test Step 5: verify vlan on webportal")
    public void step5() {
        handle.waitCmdReady(netsp.camData.fromip, true);

        tmpStr = SwitchCLIUtils.getIpMACACL(true, vlanId);
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = String.format("permit %s %s %s %s", netsp.camData.fromip, netsp.camData.fromipmask, netsp.camData.toip, netsp.camData.toipmask);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit ip host: " + tmpStr);
        tmpStr = String.format("permit %s %s %s %s", tmpStr1, netsp.camData.fromipmask, netsp.camData.toip, netsp.camData.toipmask);
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify old permit ip host is removed: " + tmpStr);
    }
}
