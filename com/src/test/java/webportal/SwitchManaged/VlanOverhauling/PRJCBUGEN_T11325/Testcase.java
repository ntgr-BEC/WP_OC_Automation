package webportal.SwitchManaged.VlanOverhauling.PRJCBUGEN_T11325;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchCLIUtilsMNG;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1325";
    String networkName = "testnet" + vlanId;
    
    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11325") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("014-Add and delete all ports to a special network") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11325") // It's a testcase id/link from Jira Test Case.
    @Issue("PRJCBUGEN-16159")
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new DevicesDashPageMNG().openSW1().enterPortConfigSummary("1");
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page1 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page1.enablePort();
        netsp.deleteAllNetwork();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Make port 1 disabled, port 2 is enabled")
    public void step2() {
        new DevicesDashPageMNG().openSW1().enterPortConfigSummary("1");
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page1 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page1.disablePort();
        new DevicesSwitchSummaryPage().enterPortConfigSummary("2");
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page2 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page2.enablePort();
        MyCommonAPIs.sleepsync();
    }
    
    @Step("Test Step 3: Insight APP manage the switch success, create one network 1325 without port")
    public void step3() {
        netsp.gotoPage();
        netsp.createNetwork(networkName, 0, vlanName, vlanId);
        
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.setAllPorts(true, 1, true);
        netsp.finishAllStep();
    }
    
    @Step("Test Step 4: All ports should not be join to VLAN1325, check by Insight and Web GUI;")
    public void step4() {
        handle.waitCmdReady(vlanId, false);
        assertFalse(SwitchCLIUtilsMNG.isPortInVlan("g1", vlanId), "port g1 is in vlan");
        assertFalse(SwitchCLIUtilsMNG.isPortInVlan("g2", vlanId), "port g2 is in vlan");
    }
    
    @Step("Test Step 5: Insight go to VLAN1325 configure page and select port 1,2,3 to 'Access Port'")
    public void step5() {
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        // netsp.setAllPorts(true, 0, false);
        netsp.setPortMembers(WebportalParam.sw1deveiceName, "1,2,3", 0);
        netsp.finishAllStep();
        MyCommonAPIs.sleepsync();
    }
    
    @Step("Test Step 6: Check by Insight and Web GUI; Configure should not apply for disabled ports but enabled ports")
    public void step6() {
        handle.waitCmdReady(vlanId, false);
        assertFalse(SwitchCLIUtilsMNG.isPortInVlan("g1", vlanId), "port g1 is in vlan because it was disabled");
        assertTrue(SwitchCLIUtilsMNG.isPortInVlan("g2", vlanId), "port g2 is in not vlan");
        assertFalse(SwitchCLIUtilsMNG.isTagPort("g2", vlanId), "port g2 is in tagged");
    }
    
    @Step("Test Step 7: Insight go to VLAN1325 configure page and click 'Select All', then set to 'Trunk Port', save")
    public void step7() {
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.setAllPorts(true, 1, false);
        netsp.finishAllStep();
        MyCommonAPIs.sleepsync();
    }
    
    @Step("Test Step 8: All enabled ports should be join to VLAN1325 with untaged mode, check by Insight and Web GUI; Configure should not apply for all disabled ports;")
    public void step8() {
        handle.waitCmdReady(vlanId, false);
        assertFalse(SwitchCLIUtilsMNG.isPortInVlan("g1", vlanId), "port g1 is in vlan because it was disabled");
        assertTrue(SwitchCLIUtilsMNG.isTagPort("g2", vlanId), "port g2 is in untagged");
    }
    
    @Step("Test Step 9: Insight go to VLAN1325 configure page and click 'Select All', then set to 'Delete', save")
    public void step9() {
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.setAllPorts(true, 2, false);
        netsp.finishAllStep();
        MyCommonAPIs.sleepsync();
    }
    
    @Step("Test Step 10: All enabled ports should be join to VLAN1325 with untaged mode, check by Insight and Web GUI; Configure should not apply for all disabled ports;")
    public void step10() {
        handle.waitCmdReady(vlanId, true);
        assertFalse(SwitchCLIUtilsMNG.isPortInVlan("g1", vlanId), "port g1 is in vlan");
        assertFalse(SwitchCLIUtilsMNG.isPortInVlan("g2", vlanId), "port g2 is in vlan");
    }
}
