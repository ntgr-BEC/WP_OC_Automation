package webportal.ProManagedSwitch.ACL.PRJCBUGEN_T41311;

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
 * @author Sumanta
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1311";
    String networkName = "testnet" + vlanId;
    String tmpStr;

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41311") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("082-[Network Setup] Edit access type for manual ip allow policy") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41311") // It's a testcase id/link from Jira Test Case.
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
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        netsp.gotoPage();
    }

    @Step("Test Step 2: Create a vlan,add IP acl allow policy for this vlan via manual type")
    public void step2() {
        netsp.mamData.devName = "test-082";
        netsp.mamData.devIp = "11.1.1.1";
        netsp.mamData.devMask = "0.0.255.255";
        netsp.mamData.enableFrom = true;
        netsp.mamData.enableTo = false;
        netsp.createNetwork(networkName, vlanId, true, 0, 0);
    }

    @Step("Test Step 3: verify vlan on webportal")
    public void step3() {
        handle.waitCmdReady(netsp.mamData.devIp, false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(String.format("vlan %s", vlanId)), "verify vlan");

        tmpStr = SwitchCLIUtils.getIpMACACL(true, vlanId);
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = "permit " + netsp.mamData.devIp;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit host"+": "+tmpStr);
        tmpStr = "permit any " + netsp.mamData.devIp;
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify no permit any"+": "+tmpStr);
        tmpStr = netsp.mamData.devMask;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit ip mask"+": "+tmpStr);
    }

    @Step("Test Step 4: Under Network setup,edit Access type to \"access allow to this device\"")
    public void step4() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.mamData.devIp, false);
        netsp.mamData.enableFrom = false;
        netsp.mamData.enableTo = true;
        netsp.setIpMacACL(true, false);
        netsp.finishAllStep();
    }

    @Step("Test Step 5: verify vlan on webportal")
    public void step5() {
        handle.sleepsync();
        tmpStr = SwitchCLIUtils.getIpMACACL(true, vlanId);
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = "permit " + netsp.mamData.devIp;
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify no permit host"+": "+tmpStr);
        tmpStr = "permit any " + netsp.mamData.devIp;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit any"+": "+tmpStr);
        tmpStr = netsp.mamData.devMask;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit ip mask new"+": "+tmpStr);
    }

    @Step("Test Step 6: Under Network setup,edit Access type to \"access allow from this device\" and \"access allow to this device\"")
    public void step6() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.mamData.devIp, false);
        netsp.mamData.enableFrom = true;
        netsp.mamData.enableTo = true;
        netsp.setIpMacACL(true, false);
        netsp.finishAllStep();
    }

    @Step("Test Step 7: verify vlan on webportal")
    public void step7() {
        handle.sleepsync();
        tmpStr = SwitchCLIUtils.getIpMACACL(true, vlanId);
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = "permit " + netsp.mamData.devIp;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit host"+": "+tmpStr);
        tmpStr = "permit any " + netsp.mamData.devIp;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit any"+": "+tmpStr);
        tmpStr = netsp.mamData.devMask;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit ip mask new"+": "+tmpStr);
    }

    @Step("Test Step 8: Under Network setup.edit Access type to \"access allow to this device\"")
    public void step8() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.mamData.devIp, false);
        netsp.mamData.enableFrom = false;
        netsp.mamData.enableTo = true;
        netsp.setIpMacACL(true, false);
        netsp.finishAllStep();
    }

    @Step("Test Step 9: verify vlan on webportal")
    public void step9() {
        handle.sleepsync();

        tmpStr = SwitchCLIUtils.getIpMACACL(true, vlanId);
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = "permit " + netsp.mamData.devIp;
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify no permit host"+": "+tmpStr);
        tmpStr = "permit any " + netsp.mamData.devIp;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit any"+": "+tmpStr);
        tmpStr = netsp.mamData.devMask;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit ip mask new"+": "+tmpStr);
    }
}
