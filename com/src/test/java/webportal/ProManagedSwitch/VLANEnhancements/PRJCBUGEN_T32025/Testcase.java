package webportal.ProManagedSwitch.VLANEnhancements.PRJCBUGEN_T32025;

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
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;


/**
 * @author sumanta
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result      = true;
    String         vlanId      = "25";
    String         vlanName    = "testvlan25";
    String         networkName = "testnet" + vlanId;
    String         vlanId2      = "35";
    String         vlanName2    = "testvlan35";
    String         networkName2 = "testnet" + vlanId2;

    @Feature("Switch.VLANEnhancements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32025") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Webportal : Verify whether user able to configure VLAN of the Switch using Switch dashboard --> double clicking on the Switch Port") // It's a testcase title from Jira Test
                                                                                                            // Case.
    @TmsLink("PRJCBUGEN-T32025") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to IM-6.9 Webportal ")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        handle.gotoLoction();
    }

    @Step("Test Step 2: In Location we have configured VLAN 1 , VLAN 25 , VLAN 35")
    public void step2() {
        // Create vlan 25 if it's not exist
        netsp.gotoPage();
        if (!netsp.getNetworks().contains(vlanName)) {
            netsp.createNetwork(networkName, 0, vlanName, vlanId);
        }
        // Create vlan 35 if it's not exist
        netsp.gotoPage();
        if (!netsp.getNetworks().contains(vlanName2)) {
            netsp.createNetwork(networkName2, 0, vlanName2, vlanId2);
        }
        // Assign port 5 as tagged port
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.setNetwork2("5", 1, "", 0);
        netsp.finishAllStep();
    }

    @Step("Test Step 3: Go-to Devices --> Double click over switches")
    public void step3() {
        ddpmg.gotoPage();
        ddpmg.enterDevice(WebportalParam.sw1serialNo);
        ddpmg.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
    }

    @Step("Test Step 4: In switch summary page , double click over the listed switch porte")
    public void step4() {
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        devicesSwitchSummaryPage.enterPortConfigSummary("5");
    }
    
    @Step("Test Step 5: Go-to port configuration setting page")
    public void step5() {
        // Check 1, 25
        DevicesSwitchConnectedNeighboursPortConfiqSummaryPage switchPortConfigSummaryPage = new DevicesSwitchConnectedNeighboursPortConfiqSummaryPage();
        String vlanids = switchPortConfigSummaryPage.getVlanId();
        System.out.println(vlanids);
        assertTrue(vlanids.contains("1,"), "In Existing VLAN setting VLAN 1");
        assertTrue(vlanids.contains("25"), "In Existing VLAN setting VLAN 25");
    }
    
    @Step("Test Step 6: Verify whether VLAN ID list all the configured VLAN's")
    public void step6() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage switchPortConfigSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        List<String> vlanlist = switchPortConfigSettingsPage.getVlanList();

        String trimmedVlanList = String.join(",", vlanlist);
        System.out.println(trimmedVlanList);
        assertTrue(trimmedVlanList.contains("1"), "In Existing VLAN setting VLAN 1");
        assertTrue(trimmedVlanList.contains("25"), "In Existing VLAN setting VLAN 25");
        assertTrue(trimmedVlanList.contains("35"), "In Existing VLAN setting VLAN 35");
    }
    

    @AfterMethod(alwaysRun = true)
    public void restore() {
        // Restore
        netsp.gotoPage();
        if (netsp.getNetworks().contains(vlanName)) {
            netsp.deleteNetwork(vlanName);
        }
        if (netsp.getNetworks().contains(vlanName2)) {
            netsp.deleteNetwork(vlanName2);
        }
    }

}
