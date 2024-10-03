package webportal.SwitchManaged.VlanOverhauling.PRJCBUGEN_T11326;

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

    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11326") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("015-Change IGMP Snooping on network") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11326") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        netsp.gotoPage();
        netsp.deleteAllNetwork();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Create vlan 100 network and port 1,port 2,port 3 to it")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork(networkName, 0, vlanName, vlanId);
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.setNetwork2("1,2,3", 0, "", 0);
        netsp.finishAllStep();
        handle.sleepsync();
    }

    @Step("Test Step 3: Successful(igmpstate is disabled by default)")
    public void step3() {
        handle.waitCmdReady(vlanId, false);
        SwitchCLIUtils.getIGMPSnoopingInfo(vlanId);
        assertFalse(SwitchCLIUtils.IGMPSnoopingClass.isVlanEnabled, "vlan igmp is enabled");
        assertTrue(SwitchCLIUtils.IGMPSnoopingClass.isEnabled, "globle igmp is not enabled");
    }

    @Step("Test Step 4: Change igmpState to enable by Insight")
    public void step4() {
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.setIgmpSnooping(true);
        netsp.finishAllStep();

        handle.sleepsync();
        if (new WebportalParam().sw1Model.contains("M4350")){
            assertFalse(SwitchCLIUtils.isTagPort("1/0/3", vlanId), "port g3 is Untagged");
            
        }else  if (new WebportalParam().sw1Model.contains("M4250")){
            assertFalse(SwitchCLIUtils.isTagPort("0/3", vlanId), "port g3 is Untagged");
            
        }else {
        assertFalse(SwitchCLIUtils.isTagPort("g3", vlanId), "port g3 is Untagged");
        }
    }

    @Step("Test Step 5: Successful")
    public void step5() {
        SwitchCLIUtils.getIGMPSnoopingInfo(vlanId);
        assertTrue(SwitchCLIUtils.IGMPSnoopingClass.isVlanEnabled, "vlan igmp is not enabled");
        assertTrue(SwitchCLIUtils.IGMPSnoopingClass.isEnabled, "globle igmp is not enabled");
    }
}
