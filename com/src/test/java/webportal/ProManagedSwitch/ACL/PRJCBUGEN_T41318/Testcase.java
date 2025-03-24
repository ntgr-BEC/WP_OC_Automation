package webportal.ProManagedSwitch.ACL.PRJCBUGEN_T41318;

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
    String vlanId      = "1318";
    String networkName = "testnet" + vlanId;
    String tmpStr, tmpStr1;

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11318") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("087-[Network Setup] Customer deny policy: Edit From and To") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11318") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Create customer ip deny policy")
    public void step2() {
        netsp.camData.fromname = "name1";
        netsp.camData.fromip = "1.1.1.1";
        netsp.camData.fromipmask = "0.0.0.255";
        netsp.camData.toname = "name2";
        netsp.camData.toip = "2.2.2.2";
        netsp.camData.toipmask = "0.0.0.255";
        netsp.createNetwork(networkName, vlanId, true, 1, 1);
    }

    @Step("Test Step 3: verify vlan on webportal")
    public void step3() {
        handle.waitCmdReady(netsp.camData.fromip, false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(String.format("vlan %s", vlanId)), "verify vlan");

        tmpStr = SwitchCLIUtils.getIpMACACL(true, vlanId);
        assertFalse(SwitchCLIUtils.ACLClass.ispermitACL, "check deny acl");
        tmpStr = String.format("deny %s %s %s %s", netsp.camData.fromip, netsp.camData.fromipmask, netsp.camData.toip, netsp.camData.toipmask);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify deny ip host: " + tmpStr);
    }

    @Step("Test Step 4: Edit the policy info under network setup")
    public void step4() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.camData.fromip, true);
        netsp.camData.fromname = "fromname01";
        netsp.camData.toname = "toname02";
        tmpStr1 = netsp.camData.toip;
        netsp.camData.fromip = "1.1.1.2";
        netsp.camData.fromipmask = "0.0.255.255";
        netsp.camData.toip = "2.2.2.3";
        netsp.camData.toipmask = "0.0.255.255";
        netsp.setIpMacACL(true, true);
        netsp.finishAllStep();
    }

    @Step("Test Step 5: verify vlan on webportal")
    public void step5() {
        handle.waitCmdReady(netsp.camData.toip, false);

        tmpStr = SwitchCLIUtils.getIpMACACL(true, vlanId);
        assertFalse(SwitchCLIUtils.ACLClass.ispermitACL, "check deny acl");
        tmpStr = String.format("deny %s %s %s %s", netsp.camData.fromip, netsp.camData.fromipmask, netsp.camData.toip, netsp.camData.toipmask);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify deny ip host: " + tmpStr);
        tmpStr = String.format("deny %s %s %s %s", netsp.camData.fromip, netsp.camData.fromipmask, tmpStr1, netsp.camData.toipmask);
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify old deny ip host is removed: " + tmpStr);
    }

    @Step("Test Step 6: Edit the policy,disable range of device under network setup")
    public void step6() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.camData.fromip, true);
        tmpStr1 = netsp.camData.fromipmask;
        netsp.camData.fromipmask = "";
        netsp.camData.toipmask = "";
        netsp.setIpMacACL(true, true);
        netsp.finishAllStep();
    }

    @Step("Test Step 7: verify vlan on webportal")
    public void step7() {
        handle.sleepsync();

        tmpStr = SwitchCLIUtils.getIpMACACL(true, vlanId);
        assertFalse(SwitchCLIUtils.ACLClass.ispermitACL, "check deny acl");
        tmpStr = String.format("deny %s", netsp.camData.fromip);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify deny ip from: " + tmpStr);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(netsp.camData.toip), "verify deny ip to: " + netsp.camData.toip);
        tmpStr = String.format("deny %s %s", netsp.camData.fromip, tmpStr1);
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify old deny ip host is removed: " + tmpStr);
    }
}
