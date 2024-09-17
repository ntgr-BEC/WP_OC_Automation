package webportal.SwitchManaged.ACL.PRJCBUGEN_T11577;

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
    String vlanId      = "1577";
    String networkName = "testnet" + vlanId;
    String tmpStr;

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11577") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("081-[Network Setup] Edit device name,ip address,ip mask for manual ip allow policy") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11577") // It's a testcase id/link from Jira Test Case.
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
        wvp.gotoPage();
        wvp.deleteAllVlan();
        wvp.removeAllAclCli();
        netsp.gotoPage();
    }

    @Step("Test Step 2: Create a vlan,add IP acl allow policy for this vlan via manual type")
    public void step2() {
        netsp.mamData.devName = "test-025";
        netsp.mamData.devIp = "13.1.1.1";
        netsp.mamData.devMask = "255.255.255.255";
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
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit ip host" + ": " + tmpStr);
        tmpStr = netsp.mamData.devMask;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit ip mask" + ": " + tmpStr);
    }

    @Step("Test Step 4: Under Network setup,select the vlan,edit device name,ip address,ip mask")
    public void step4() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.openACL(netsp.mamData.devIp, false);
        netsp.mamData.devName = "test-025-new";
        netsp.mamData.devIp = "100.1.1.100";
        netsp.mamData.devMask = "0.0.255.255";
        netsp.setIpMacACL(true, false);
        netsp.finishAllStep();
    }

    @Step("Test Step 5: verify vlan on webportal")
    public void step5() {
        handle.waitCmdReady(netsp.mamData.devIp, false);

        tmpStr = SwitchCLIUtils.getIpMACACL(true, vlanId);
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "check allow acl");
        tmpStr = "permit " + netsp.mamData.devIp;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit ip host new" + ": " + tmpStr);
        tmpStr = netsp.mamData.devMask;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit ip mask new" + ": " + tmpStr);
    }

}
