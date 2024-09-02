package webportal.SwitchManaged.ACL.PRJCBUGEN_T11584;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
    String       vlanName    = "testvlan";
    String       vlanId      = "1584";
    String       networkName = "testnet" + vlanId;
    String       tmpStr, tmpStr1;
    List<String> lsExpectACL = new ArrayList<String>();

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11584") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("088-[Network Setup] Delete Customer IP ACL") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11584") // It's a testcase id/link from Jira Test Case.
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

//    @Step("Test Step 2: Create 5 Customer IP ACLs")
//    public void step2() {
//        netsp.clickAdd();
//        netsp.setNetwork1(networkName, "", 0, vlanName, vlanId);
//        for (int i = 1; i < 6; i++) {
//            netsp.clickMacIpButton(true, 1);
//            netsp.camData.fromname = "1";
//            netsp.camData.fromip = "11.1.1." + i;
//            netsp.camData.fromipmask = "0.0.0.255";
//            netsp.camData.toname = "2";
//            netsp.camData.toip = "12.1.1." + i;
//            netsp.camData.toipmask = "0.0.0.255";
//            lsExpectACL.add(netsp.camData.fromip);
//            netsp.setIpMacACL(true, true);
//        }
//
//        netsp.enableMacIpAcl(true, true);
//        netsp.finishAllStep();
//    }
//
//    @Step("Test Step 3: verify acl on webportal")
//    public void step3() {
//        handle.waitCmdReady(netsp.camData.fromip, false);
//        tmpStr = handle.getCmdOutputShowRunningConfig(false);
//        assertTrue(tmpStr.contains(String.format("vlan %s", vlanId)), "verify vlan");
//
//        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(true, vlanId);
//        assertTrue(SwitchCLIUtilsMNG.ACLClass.ispermitACL, "check allow acl");
//        for (String s : lsExpectACL) {
//            assertTrue(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(String.format("permit %s %s", s, netsp.camData.fromipmask)),
//                    "verify permit ip host" + s);
//        }
//    }

    @Step("Test Step 4: Delete all 5 customer IP ACLs under network setup")
    public void step4() {
        netsp.gotoPage();
        netsp.deleteNetwork(vlanName);
    }

    @Step("Test Step 5: All 5 customer IP ACLs is deleted")
    public void step5() {
        handle.waitCmdReady(netsp.camData.fromip, true);

        tmpStr = SwitchCLIUtilsMNG.getIpMACACL(true, vlanId);
        for (String s : lsExpectACL) {
            assertFalse(SwitchCLIUtilsMNG.ACLClass.aclResult.contains(String.format("permit %s %s", s, netsp.camData.fromipmask)),
                    "verify no permit ip host " + s);
        }
    }
}
