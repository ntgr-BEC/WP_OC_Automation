package webportal.SwitchManaged.ACL.PRJCBUGEN_T11580;

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
import util.SwitchCLIUtilsMNG;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1580";
    String networkName = "testnet" + vlanId;
    String tmpStr;

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11580") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("084-[Network Setup] Edit access type for manual deny policy") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11580") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Create IP ACL use manual,policy is deny")
    public void step2() {
        netsp.mamData.devName = "test-084";
        netsp.mamData.devIp = "100.1.1.1";
        netsp.mamData.devMask = "0.0.0.255";
        netsp.mamData.enableFrom = true;
        netsp.mamData.enableTo = true;
        netsp.createNetwork(networkName, vlanId, true, 1, 0);
    }

    @Step("Test Step 3: verify vlan on webportal")
    public void step3() {
        handle.waitCmdReady(netsp.mamData.devIp, false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(String.format("vlan %s", vlanId)), "verify vlan");

        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(true, vlanId);
        assertFalse(SwitchCLIUtilsMNG.ACLClass.ispermitACL, "check deny acl");
        tmpStr = "deny " + netsp.mamData.devIp;
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "verify deny host"+": "+tmpStr);
        tmpStr = "deny any";
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "verify deny any"+": "+tmpStr);
        tmpStr = netsp.mamData.devMask;
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "verify deny ip mask"+": "+tmpStr);
    }

    @Step("Test Step 4: Edit Access type to \"Access deny from this device\" under network setup")
    public void step4() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.mamData.devIp, false);
        netsp.mamData.enableFrom = true;
        netsp.mamData.enableTo = false;
        netsp.setIpMacACL(true, false);
        netsp.finishAllStep();
    }

    @Step("Test Step 5: verify vlan on webportal")
    public void step5() {
        handle.sleepsync();
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(true, vlanId);
        assertFalse(SwitchCLIUtilsMNG.ACLClass.ispermitACL, "check deny acl");
        tmpStr = "deny " + netsp.mamData.devIp;
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "verify deny host"+": "+tmpStr);
        tmpStr = "deny any " + netsp.mamData.devIp;
        assertFalse(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "verify no deny any"+": "+tmpStr);
        tmpStr = netsp.mamData.devMask;
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "verify deny ip mask new"+": "+tmpStr);
    }

    @Step("Test Step 6: Edit Access type to \"Access deny to this device\" under network setup")
    public void step6() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.mamData.devIp, false);
        netsp.mamData.enableFrom = false;
        netsp.mamData.enableTo = true;
        netsp.setIpMacACL(true, false);
        netsp.finishAllStep();
    }

    @Step("Test Step 7: verify vlan on webportal")
    public void step7() {
        handle.sleepsync();
        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(true, vlanId);
        assertFalse(SwitchCLIUtilsMNG.ACLClass.ispermitACL, "check deny acl");
        tmpStr = "deny " + netsp.mamData.devIp;
        assertFalse(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "verify no deny host"+": "+tmpStr);
        tmpStr = "deny any " + netsp.mamData.devIp;
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "verify deny any"+": "+tmpStr);
        tmpStr = netsp.mamData.devMask;
        assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(tmpStr), "verify deny ip mask new"+": "+tmpStr);
    }
}
