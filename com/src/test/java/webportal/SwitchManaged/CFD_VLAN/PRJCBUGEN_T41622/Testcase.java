package webportal.SwitchManaged.CFD_VLAN.PRJCBUGEN_T41622;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 * @author Tejeshwinin K V
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;
    String         vlanId = "100";
    
    @Feature("Switch.CFD_VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41622") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether VLAN configured on one switch does not affect others switches in the network location") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41622") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to Page ")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();

    }
    
    @Step("Test Step 2: Create vlan 100 add port 1 as untag to vlan100，port 2 as tagged to vlan100.")
    public void step2() {

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.addCustomVlanWithPorts("vlan100", "100", dut1Name, sw1port, "tag", dut2Name, sw2port, "tag", null);

        MyCommonAPIs.sleep(8000);
        handle.waitCmdReady("vlan100", false);
    }
    
    
    @Step("Test Step 3: Now configure VLAN 100 Trunk port")
    public void step3() {
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();

        ddpmg.gotoPage();
        ddpmg.enterDevice(WebportalParam.sw1serialNo);
        devicesSwitchSummaryPage.enterPortConfigSummary(sw1port[0]);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage switchPortConfigSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        MyCommonAPIs.sleepi(15);
        switchPortConfigSettingsPage.setPortVlan("100", 2);

        ddpmg.gotoPage();
        ddpmg.enterDevice(WebportalParam.sw1serialNo);
        devicesSwitchSummaryPage.enterPortConfigSummary(sw1port[1]);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage switchPortConfigSettingsPage1 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        MyCommonAPIs.sleepi(15);
        switchPortConfigSettingsPage1.setPortVlan("100", 2);


        ddpmg.gotoPage();
        ddpmg.enterDevice(WebportalParam.sw1serialNo);
        devicesSwitchSummaryPage.enterPortConfigSummary(sw1port[2]);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage switchPortConfigSettingsPage2 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        MyCommonAPIs.sleepi(15);
        switchPortConfigSettingsPage2.setPortVlan("100", 2);
    }

    @Step("Test Step 4: Now go-to Switch Device dashboard page and mouse hover the Port 5")
    public void step4() {
        ddpmg.gotoPage();
        ddpmg.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        System.out.println("cpompleted");
        String vlan_id1 = devicesSwitchSummaryPage.getPortVlanId(sw1port[0]);
        System.out.println("cpompleted1");
        String vlan_id2 = devicesSwitchSummaryPage.getPortVlanId(sw1port[1]);
        String vlan_id3 = devicesSwitchSummaryPage.getPortVlanId(sw1port[2]);
        assertTrue(vlan_id1.contains("100(U)"), "vlan_id1 does not contain 100(U)");
        assertTrue(vlan_id2.contains("100(U)"), "vlan_id2 does not contain 100(U)");
        assertTrue(vlan_id3.contains("100(U)"), "vlan_id3 does not contain 100(U)");


        ddpmg.gotoPage();
        ddpmg.enterDevicesSwitchSummary(WebportalParam.sw2serialNo);
        String vlan_id4 = devicesSwitchSummaryPage.getPortVlanId(sw2port[0]);
        assertTrue(vlan_id4.contains("100(T)"), "vlan_id4 does not contain 100(T)");

    }
    

    
}
