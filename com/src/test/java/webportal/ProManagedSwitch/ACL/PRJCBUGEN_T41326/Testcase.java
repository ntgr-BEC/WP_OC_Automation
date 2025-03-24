package webportal.ProManagedSwitch.ACL.PRJCBUGEN_T41326;

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
    String vlanId      = "1326";
    String networkName = "testnet" + vlanId;
    String tmpStr, tmpStr1;

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41326") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("092-[Network Setup] Edit manual mac acl,the policy is deny") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41326") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Add a manual mac acl to deny source address"
            + "--policy:deny --Mac address: 11:11:11:11:11:11 --option:Deny access from this device")
    public void step2() {
        netsp.mamData.devName = "test-1588";
        netsp.mamData.devMac = "11:11:11:11:11:11";
        netsp.mamData.enableFrom = true;
        netsp.mamData.enableTo = false;
        netsp.createNetwork(networkName, vlanId, false, 1, 0);
    }

    @Step("Test Step 3: verify vlan on webportal")
    public void step3() {
        handle.waitCmdReady(netsp.mamData.devMac, false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(String.format("vlan %s", vlanId)), "verify vlan");

        tmpStr = SwitchCLIUtils.getIpMACACL(false, vlanId);
        assertFalse(SwitchCLIUtils.ACLClass.ispermitACL, "check deny acl");
        tmpStr = "deny " + netsp.mamData.devMac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify deny mac"+": "+tmpStr);
    }

    @Step("Test Step 4: Edit mac address from \"11:11:11:11:11:11\" to \"11:11:11:11:11:22\"")
    public void step4() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.mamData.devMac, false);
        tmpStr1 = netsp.mamData.devMac;
        netsp.mamData.devMac = "11:11:11:11:11:22";
        netsp.setIpMacACL(false, false);
        netsp.finishAllStep();
    }

    @Step("Test Step 5: verify vlan on webportal")
    public void step5() {
        handle.waitCmdReady(netsp.mamData.devMac, false);

        tmpStr = SwitchCLIUtils.getIpMACACL(false, vlanId);
        tmpStr = "deny " + netsp.mamData.devMac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify deny mac"+": "+tmpStr);
        tmpStr = "deny " + tmpStr1;
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify deny mac is removed"+": "+tmpStr);
    }

    @Step("Test Step 6: Edit option from \"Deny access from this device\" to \"Deny access to this device\"")
    public void step6() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.mamData.devMac, false);
        netsp.mamData.enableFrom = false;
        netsp.mamData.enableTo = true;
        netsp.setIpMacACL(false, false);
        netsp.finishAllStep();
    }

    @Step("Test Step 7: verify vlan on webportal")
    public void step7() {
        handle.sleepsync();

        tmpStr = SwitchCLIUtils.getIpMACACL(false, vlanId);
        tmpStr = "deny any " + netsp.mamData.devMac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify deny mac direct is changed"+": "+tmpStr);
        tmpStr = "deny " + netsp.mamData.devMac;
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify deny mac direct is changed"+": "+tmpStr);
    }

    @Step("Test Step 8: Edit option select all (\"Deny access from this device\" and \"Deny access to this device\")")
    public void step8() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.mamData.devMac, false);
        netsp.mamData.enableFrom = true;
        netsp.mamData.enableTo = true;
        netsp.setIpMacACL(false, false);
        netsp.finishAllStep();
    }

    @Step("Test Step 9: verify vlan on webportal")
    public void step9() {
        handle.sleepsync();

        tmpStr = SwitchCLIUtils.getIpMACACL(false, vlanId);
        tmpStr = "deny any " + netsp.mamData.devMac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify deny mac direct to"+": "+tmpStr);
        tmpStr = "deny " + netsp.mamData.devMac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify deny mac direct from"+": "+tmpStr);
    }

    @Step("Test Step 10: Edit mac acl --policy:deny --Mac address: 11:11:11:11:11:33 --option:Deny access from this device")
    public void step10() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.mamData.devMac, false);
        netsp.mamData.enableFrom = true;
        netsp.mamData.enableTo = false;
        netsp.setIpMacACL(false, false);
        netsp.finishAllStep();
    }

    @Step("Test Step 11: verify vlan on webportal")
    public void step11() {
        handle.sleepsync();

        tmpStr = SwitchCLIUtils.getIpMACACL(false, vlanId);
        tmpStr = "deny any " + netsp.mamData.devMac;
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify deny mac direct to"+": "+tmpStr);
        tmpStr = "deny " + netsp.mamData.devMac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify deny mac direct to"+": "+tmpStr);
    }
}
