package webportal.SwitchManaged.VlanOverhauling.PRJCBUGEN_T11319;

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
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String networkName1 = "test1net";
    String networkName2 = "test2net";
    String vlanName1    = "test1vlan";
    String vlanName2    = "test2vlan";
    String vlanId1      = "1319";
    String vlanId2      = "2319";

    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11319") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("027- Change network port PVID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11319") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: Create vlan 100，add port 1 as untag to vlan100，port 2 as tagged to vlan200")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork(networkName1, 0, vlanName1, vlanId1);
        netsp.openNetwork(vlanId1);
        netsp.gotoStep(2);
        netsp.setNetwork2("1", 0, "", 0);
        netsp.gotoStep(2);
        netsp.setNetwork2("2", 1, "", 0);
        netsp.finishAllStep();
    }

    @Step("Test Step 3: Create vlan 200，add port 1 as untag to vlan200，port 2 as tagged to vlan200")
    public void step3() {
        netsp.gotoPage();
        netsp.createNetwork(networkName2, 0, vlanName2, vlanId2);
        netsp.openNetwork(vlanId2);
        netsp.gotoStep(2);
        netsp.setNetwork2("1", 0, "", 0);
        netsp.gotoStep(2);
        netsp.setNetwork2("2", 1, "", 0);
        netsp.finishAllStep();
        handle.sleepsync();
    }

    @Step("Test Step 4: set port 1 pvid as 100")
    public void step4() {
        new DevicesDashPageMNG().openSW1().enterPortConfigSummary("1");
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page1 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page1.setPortPVID(vlanId1);
    }

    @Step("Test Step 5: check cli successfully")
    public void step5() {
        
        if (new WebportalParam().sw1Model.contains("M4350")){
            assertTrue(SwitchCLIUtils.isPortInVlan("1/0/1", vlanId1), "port1 should be in vlan");
            assertTrue(SwitchCLIUtils.isPortInVlan("1/0/2", vlanId1), "port2 should be in vlan");
            assertFalse(SwitchCLIUtils.isTagPort("1/0/1", vlanId1), "port1 should be untag");
            assertTrue(SwitchCLIUtils.isTagPort("1/0/2", vlanId1), "port2 should be tag");
            String tmpStr = SwitchCLIUtils.getPortInfo("1/0/1");
        }else if (new WebportalParam().sw1Model.contains("M4250")){
            
            assertTrue(SwitchCLIUtils.isPortInVlan("0/1", vlanId1), "port1 should be in vlan");
            assertTrue(SwitchCLIUtils.isPortInVlan("0/2", vlanId1), "port2 should be in vlan");
            assertFalse(SwitchCLIUtils.isTagPort("0/1", vlanId1), "port1 should be untag");
            assertTrue(SwitchCLIUtils.isTagPort("0/2", vlanId1), "port2 should be tag");
            String tmpStr = SwitchCLIUtils.getPortInfo("0/1");
            
        }else {
        assertTrue(SwitchCLIUtils.isPortInVlan("g1", vlanId1), "port1 should be in vlan");
        assertTrue(SwitchCLIUtils.isPortInVlan("g2", vlanId1), "port2 should be in vlan");
        assertFalse(SwitchCLIUtils.isTagPort("g1", vlanId1), "port1 should be untag");
        assertTrue(SwitchCLIUtils.isTagPort("g2", vlanId1), "port2 should be tag");
        String tmpStr = SwitchCLIUtils.getPortInfo("g1");
        }
        assertTrue(SwitchCLIUtils.PortClass.sPortPvid.contains(vlanId1), "port1 pvid should be to vlan1");
    }

    @Step("Test Step 6: set port 1 pvid as 100")
    public void step6() {
        new DevicesDashPageMNG().openSW1().enterPortConfigSummary("1");
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page1 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page1.setPortPVID(vlanId2);
    }

    @Step("Test Step 7: check cli successfully")
    public void step7() {
        
        
        if (new WebportalParam().sw1Model.contains("M4350")){
            
            assertTrue(SwitchCLIUtils.isPortInVlan("1/0/1", vlanId1), "port1 should be in vlan");
            assertTrue(SwitchCLIUtils.isPortInVlan("1/0/2", vlanId1), "port2 should be in vlan");
            assertFalse(SwitchCLIUtils.isTagPort("1/0/1", vlanId1), "port1 should be untag");
            assertTrue(SwitchCLIUtils.isTagPort("1/0/2", vlanId1), "port2 should be tag");
            String tmpStr = SwitchCLIUtils.getPortInfo("1/0/1");
            
        }else if (new WebportalParam().sw1Model.contains("M4250")){
            
            assertTrue(SwitchCLIUtils.isPortInVlan("0/1", vlanId1), "port1 should be in vlan");
            assertTrue(SwitchCLIUtils.isPortInVlan("0/2", vlanId1), "port2 should be in vlan");
            assertFalse(SwitchCLIUtils.isTagPort("0/1", vlanId1), "port1 should be untag");
            assertTrue(SwitchCLIUtils.isTagPort("0/2", vlanId1), "port2 should be tag");
            String tmpStr = SwitchCLIUtils.getPortInfo("0/1");
        
        }else {
        
            assertTrue(SwitchCLIUtils.isPortInVlan("g1", vlanId1), "port1 should be in vlan");
            assertTrue(SwitchCLIUtils.isPortInVlan("g2", vlanId1), "port2 should be in vlan");
            assertFalse(SwitchCLIUtils.isTagPort("g1", vlanId1), "port1 should be untag");
            assertTrue(SwitchCLIUtils.isTagPort("g2", vlanId1), "port2 should be tag");
            String tmpStr = SwitchCLIUtils.getPortInfo("g1");
        
        }
        assertTrue(SwitchCLIUtils.PortClass.sPortPvid.contains(vlanId2), "port2 pvid should be to vlan2");
    }

}
