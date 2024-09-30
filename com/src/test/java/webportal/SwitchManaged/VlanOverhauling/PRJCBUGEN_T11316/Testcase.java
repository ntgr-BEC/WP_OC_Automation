package webportal.SwitchManaged.VlanOverhauling.PRJCBUGEN_T11316;

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
    String vlanId      = "1316";
    String networkName = "testnet" + vlanId;

    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11316") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("048-Create a vlan via the template of \"Data Network\" and change it default settings") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11316") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        netsp.gotoPage();
        netsp.deleteNetwork(vlanName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Configuration to create vlan 8，name data traffic vlan，VoIP Optimization false，IGMP Snooping/Video Optimization true, QoS priority = 7")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork(networkName, 0, vlanName, vlanId);
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.setIgmpSnooping(true);
        netsp.setTrafficPriority("6");
        netsp.finishAllStep();
        handle.sleepsync();
    }

    @Step("Test Step 3: Assign all ports to vlan 8 as tagged")
    public void step3() {
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.setAllPorts(true, 1, false);
        netsp.gotoStep(2);
        netsp.setNetwork2("1", 0, "", 0);
        netsp.finishAllStep();
        handle.sleepsync();
    }

    @Step("Test Step 4: Check vlan 8 configuration on device side")
    public void step4() {
        handle.waitCmdReady(vlanId, false);
        SwitchCLIUtils.getIGMPSnoopingInfo(vlanId);
        assertTrue(SwitchCLIUtils.IGMPSnoopingClass.isVlanEnabled, "vlan igmp is not enabled");
        assertTrue(SwitchCLIUtils.IGMPSnoopingClass.isEnabled, "globle igmp is not enabled");
        if (new WebportalParam().sw1Model.contains("M4350")){
            assertFalse(SwitchCLIUtils.isTagPort("1/0/1", vlanId), "port g1 is tagged");
            assertTrue(SwitchCLIUtils.isTagPort("1/0/2", vlanId), "port g2 is not tagged");
        }
        else   if (new WebportalParam().sw1Model.contains("M4250")){
            assertFalse(SwitchCLIUtils.isTagPort("0/1", vlanId), "port g1 is tagged");
            assertTrue(SwitchCLIUtils.isTagPort("0/2", vlanId), "port g2 is not tagged");
        } else {
        assertFalse(SwitchCLIUtils.isTagPort("g1", vlanId), "port g1 is tagged");
        assertTrue(SwitchCLIUtils.isTagPort("g2", vlanId), "port g2 is not tagged");
        }

        // assertTrue(SwitchCLIUtils.getPortInfo("g1").contains("6"), "check traffic priority");
    }
}
