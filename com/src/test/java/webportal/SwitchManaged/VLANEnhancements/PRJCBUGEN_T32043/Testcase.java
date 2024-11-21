package webportal.SwitchManaged.VLANEnhancements.PRJCBUGEN_T32043;

import static org.testng.Assert.assertTrue;

import java.util.List;

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
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSummaryPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.NetworkSetupPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;


/**
 * @author annfang
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result      = true;
    String         vlanId      = "25";
    String         vlanName    = "testvlan25";
    String         networkName = "testnet" + vlanId;
    

    @Feature("Switch.VLANEnhancements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32043") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether we are able to see the configured VLAN over the switch port") // It's a testcase title from Jira Test
                                                                                                            // Case.
    @TmsLink("PRJCBUGEN-T32043") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to the latest webportal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go-to locations --> Wired -->  Settings --> VLAN/Network page")
    public void step2() {
        // sw1 and sw2 are already onboarded
    }

    @Step("Test Step 3: In wired --> Settings --> VLAN/Network setup page , Add a new VLAN")
    public void step3() {
        // Create vlans if not exist
        wvp.gotoPage();
        if (!wvp.getVlanIDs().contains(vlanId)) {
            wvp.clickAddVlanBtn();
            netsp.setNetwork1(vlanName, null, 0, vlanName, vlanId);
            netsp.setNetwork2("5", 1, "", 0);
            netsp.finishAllStep();
        }
        
    }

    @Step("Test Step 4: Now go to Switch summary page --> Ports graph and move the mouse cursor over the ports")
    public void step4() {
        ddpmg.gotoPage();
        MyCommonAPIs.sleepi(60);
        new NetworkSetupPage().waitReady();
        ddpmg.enterDevice(WebportalParam.sw1serialNo);
        ddpmg.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
//        devicesSwitchSummaryPage.enterPortConfigSummary(WebportalParam.sw1LagPort2);
        String vlan_id = devicesSwitchSummaryPage.getPortVlanId("5");
        System.out.println(vlan_id);
        assertTrue(vlan_id.contains("1"), "Hover port 5, VLAN 1 should exist");
        assertTrue(vlan_id.contains("25"), "Hover port 5, VLAN 25 should exist");
    }
    

    @AfterMethod(alwaysRun = true)
    public void restore() {
        // Restore
        netsp.gotoPage();
        if (netsp.getNetworks().contains(vlanName)) {
            netsp.deleteNetwork(vlanName);
        }
    }

}
