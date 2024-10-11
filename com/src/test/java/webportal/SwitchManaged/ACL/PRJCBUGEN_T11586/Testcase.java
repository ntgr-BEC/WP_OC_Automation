package webportal.SwitchManaged.ACL.PRJCBUGEN_T11586;

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
    String vlanId      = "1586";
    String networkName = "testnet" + vlanId;
    String tmpStr, tmpStr1;

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11586") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("090-[Network Setup] Edit manual mac acl,the policy is allow") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11586") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Create MAC ACL for vlan 200 to permit source mac address")
    public void step2() {
        netsp.mamData.devName = "test-1586";
        netsp.mamData.devMac = "22:22:22:33:33:33";
        netsp.mamData.enableFrom = true;
        netsp.mamData.enableTo = false;
        netsp.createNetwork(networkName, vlanId, false, 0, 0);
    }

    @Step("Test Step 3: verify vlan on webportal")
    public void step3() {
        handle.waitCmdReady(netsp.mamData.devMac, false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(String.format("vlan %s", vlanId)), "verify vlan");

        tmpStr = SwitchCLIUtils.getIpMACACL(false, vlanId);
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = "permit " + netsp.mamData.devMac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit mac"+": "+tmpStr);
    }

    @Step("Test Step 4: On allow device list,select the mac address \"22-22-22-33-33-33\",edit to 22-22-22-33-33-44\"")
    public void step4() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.mamData.devMac, false);
        tmpStr1 = netsp.mamData.devMac;
        netsp.mamData.devMac = "22:22:22:33:33:44";
        netsp.setIpMacACL(false, false);
        netsp.finishAllStep();
    }

    @Step("Test Step 5: verify vlan on webportal")
    public void step5() {
        handle.waitCmdReady(netsp.mamData.devMac, false);

        tmpStr = SwitchCLIUtils.getIpMACACL(false, vlanId);
        tmpStr = "permit " + netsp.mamData.devMac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit mac"+": "+tmpStr);
        tmpStr = "permit " + tmpStr1;
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit mac is removed"+": "+tmpStr);
    }

    @Step("Test Step 6: On allow device list,select the mac address \"22-22-22-33-33-44\",edit option from \"allow access from this device\" to \"\"allow access to this device\"\"")
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
        tmpStr = "permit any " + netsp.mamData.devMac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit mac direct is changed"+": "+tmpStr);
        tmpStr = "permit " + netsp.mamData.devMac;
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit mac direct is changed"+": "+tmpStr);
    }

    @Step("Test Step 8: On allow device list,select the mac address \"22-22-22-33-33-44\",edit option\r\n"
            + "to select all (\"allow access from this device\" and \"allow access to this device\")")
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
        tmpStr = "permit any " + netsp.mamData.devMac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit mac direct to"+": "+tmpStr);
        tmpStr = "permit " + netsp.mamData.devMac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit mac direct from"+": "+tmpStr);
    }

    @Step("Test Step 10: Edit option to \"allow access from this device\" again")
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
        tmpStr = "permit any " + netsp.mamData.devMac;
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit mac direct to"+": "+tmpStr);
        tmpStr = "permit " + netsp.mamData.devMac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit mac direct to"+": "+tmpStr);
    }
}
