package webportal.SwitchManaged.VlanOverhauling.PRJCBUGEN_T11340;

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
import util.MyCommonAPIs;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1340";
    String ip1         = "192.168.34.100";
    String ip2         = "192.168.34.200";
    String networkName = "testnet" + vlanId;
    
    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11340") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("073-Create a network and set static IP address") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11340") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Enable port1 & 2, Go to Location->Network page, and use template of Data Network Configuration to create vlan 600")
    public void step2() {
        new DevicesDashPageMNG().openSW1().enterPortConfigSummary("1");
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page1 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page1.enablePort();
        new DevicesSwitchSummaryPage().enterPortConfigSummary("2");
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page2 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page2.enablePort();
        MyCommonAPIs.sleepsync();
        netsp.gotoPage();
        netsp.createNetwork(networkName, 0, vlanName, vlanId);
    }
    
    @Step("Test Step 3: Assign port1 and port2 to vlan 600 with tag")
    public void step3() {
        netsp.gotoPage();
        netsp.openNetwork("Management VLAN");
        netsp.gotoStep(4);
        netsp.setNetwork4(true, ip1, ip2);
        netsp.finishAllStep();
        
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.setNetwork2("1,2", 1, "", 0);
        netsp.gotoStep(4);
        netsp.setNetwork4(false, ip1, ip2);
        netsp.finishAllStep();
    }
    
    @Step("Test Step 4: Check vlan 600 configuration by APP and GUI")
    public void step4() {
        if (!rtp.isRoutingDisabled(WebportalParam.sw1Model)) {
            handle.waitCmdReady(ip1, false);
        } else {
            MyCommonAPIs.sleepsync();
        }
        
        String tocheck1 = MyCommonAPIs.getCmdOutput("show running-config interface vlan " + vlanId, false);
        String tocheck2 = MyCommonAPIs.getCmdOutput("show running-config interface vlan " + vlanId, true);
        assertTrue(SwitchCLIUtils.isPortInVlan("g1", vlanId), "g1 is added to vlan on switch");
        assertTrue(SwitchCLIUtils.isTagPort("g2", vlanId), "g2 is added to switch in tag");
        if (!rtp.isRoutingDisabled(WebportalParam.sw1Model)) {
            assertTrue(tocheck1.contains(ip1), "switch 1 with ip1");
        }
        if (!rtp.isRoutingDisabled(WebportalParam.sw2Model)) {
            assertTrue(tocheck2.contains(ip2), "switch 2 with ip2");
        }
    }
    
    @Step("Test Step 5: Delete VLAN 600 by APP")
    public void step5() {
        netsp.gotoPage();
        netsp.deleteAllNetwork();
    }
    
    @Step("Test Step 6: Delete VLAN success, check by APP and GUI")
    public void step6() {
        if (!rtp.isRoutingDisabled(WebportalParam.sw1Model)) {
            handle.waitCmdReady(ip1, true);
        } else {
            MyCommonAPIs.sleepsync();
        }
        
        if (!rtp.isRoutingDisabled(WebportalParam.sw1Model)) {
            String tocheck1 = MyCommonAPIs.getCmdOutput("show running-config interfaces vlan " + vlanId, false);
            assertFalse(tocheck1.contains(ip1), "switch 1 without ip1");
        } else {
            String tocheck1 = MyCommonAPIs.getCmdOutput("show vlan", false);
            assertFalse(tocheck1.contains(vlanId), "switch 1 without vlan");
        }
    }
}
