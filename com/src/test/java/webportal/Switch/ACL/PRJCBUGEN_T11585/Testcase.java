package webportal.Switch.ACL.PRJCBUGEN_T11585;

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
    String vlanId      = "1585";
    String networkName = "testnet" + vlanId;
    String tmpStr;

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11585") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("089-[Network Setup] Create ACL binding to vlan,then delete the vlan") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11585") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Create a vlan,binding IP ACLs and MAC ACL for it")
    public void step2() {
        netsp.clickAdd();
        netsp.setNetwork1(networkName, "", 0, vlanName, vlanId);
        netsp.mamData.devName = "test-089";
        netsp.mamData.devIp = "11.22.1.1";
        netsp.mamData.devMask = "0.0.255.255";
        netsp.mamData.enableFrom = true;
        netsp.mamData.enableTo = true;
        netsp.clickMacIpButton(true, 0);
        netsp.setIpMacACL(true, false);
        netsp.enableMacIpAcl(true, true);

        netsp.mamData.devMac = "11:22:33:11:11:11";
        netsp.clickMacIpButton(false, 0);
        netsp.setIpMacACL(false, false);
        netsp.enableMacIpAcl(false, true);
        netsp.finishAllStep();
    }

    @Step("Test Step 3: verify vlan on webportal")
    public void step3() {
        handle.waitCmdReady(netsp.mamData.devMac, false);

        SwitchCLIUtils.getIpMACACL(true, vlanId);
        tmpStr = "permit " + netsp.mamData.devIp;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit ip"+": "+tmpStr);
        SwitchCLIUtils.getIpMACACL(false, vlanId);
        tmpStr = "permit " + netsp.mamData.devMac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify permit mac"+": "+tmpStr);
    }

    @Step("Test Step 4: Delete the vlan on app under network setup")
    public void step4() {
        netsp.gotoPage();
        netsp.deleteNetwork(vlanName);
    }

    @Step("Test Step 5: verify vlan on webportal")
    public void step5() {
        handle.waitCmdReady(netsp.mamData.devIp, true);

        SwitchCLIUtils.getIpMACACL(true, vlanId);
        tmpStr = "permit " + netsp.mamData.devIp;
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify no permit ip"+": "+tmpStr);
        SwitchCLIUtils.getIpMACACL(false, vlanId);
        tmpStr = "permit " + netsp.mamData.devMac;
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "verify no permit mac"+": "+tmpStr);
    }
}
