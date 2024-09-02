package webportal.SwitchManaged.DhcpSnooping.PRJCBUGEN_T35613;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredDhcpSnoopingPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.webelements.WiredDhcpSnoopingElement;

/**
 * @author ravi
 */
public class Testcase extends TestCaseBase {

    @Feature("Switch.DhcpSnooping") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35613") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that if the DHCP Snooping configuration is applied from the insight portal , it should be reflected in the LOCAL GUI of the switch") // It's a testcase title from Jira
                                                                                                              // Test Case.
    @TmsLink("PRJCBUGEN-T35613") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page and check Global Config Page")
    public void step1() {
        // enter into the wired settings page
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingGlobalConfig);
        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.dhcpSnoopingMACAddressValidation);
        
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.addDataVlanWithPorts("data vlan", "100", null, null, null, null, null, null, null);
        handle.waitCmdReady("100", false);
        handle.refresh();
        vlanPage.addDataVlanWithPorts("data vlan", "200", null, null, null, null, null, null, null);
        handle.waitCmdReady("200", false);

        // check sw1 on webportal
        WiredVLANPageForVLANPage vlansPage = new WiredVLANPageForVLANPage();
        List<String> vlans = vlansPage.getVlans();
        MyCommonAPIs.sleep(5000);

        System.out.println("vlan is:" + vlans);
        if (vlans.contains("data vlan")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 1:vlanId is:" + vlans);
        }

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Enable DHCP Snooping MAC address Validation and check the CLI")
    public void step2() {

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingGlobalConfig);
        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.dhcpSnoopingMode);
        handle.refresh();

        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.dhcpSnoopingMACAddressValidation);
        handle.refresh();
        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.selectUserVlan("100"));
        handle.refresh();

        assertTrue(WiredDhcpSnoopingElement.dhcpSnoopingMACAddressValidation.isEnabled(), "DHCP Snooping Mac validation not enabled");

        handle.waitCmdReady("dhcp snooping", true);
        MyCommonAPIs.sleepsync();

        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config  ", false);
        boolean snoopingConfig = tmpStr.contains("ip dhcp snooping");
        assertTrue(snoopingConfig, "Dhcp Snooping admin mode is not enabled");
        
        boolean str1 = tmpStr.contains("no ip dhcp snooping verify mac-address");
        assertFalse(str1, "Dhcp Snooping Mac Address is not enabled");
        
        boolean vlanSnoopingConfig = tmpStr.contains("ip dhcp snooping vlan 100");
        assertTrue(vlanSnoopingConfig, "Dhcp Snooping vlan 100 should be enabled");

    }
    


    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: enable trust mode and check the CLI")
    public void step3() {

        wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingPortConfig);

        wdsp.enablePortSpecificConfigOnPort(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort1) - 1), "Trust Mode", "Invalid Packets");
        handle.refresh();

        assertTrue(WiredDhcpSnoopingElement.txtPortTrustModeCheck(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort1) - 1)).isDisplayed());
        assertTrue(WiredDhcpSnoopingElement.txtPortInvalidPacketsCheck(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort1)-1)).isDisplayed());

        assertTrue(MyCommonAPIs.getCmdOutput("show running-config interfaces " + WebportalParam.sw1LagPort1CLI, false)
                .contains("ip dhcp snooping trust"), "Trust mode not enabled");
        assertTrue(MyCommonAPIs.getCmdOutput("show running-config interfaces " + WebportalParam.sw1LagPort1CLI, false)
                .contains("ip dhcp snooping logging"), "Invalid packets logging not enabled");
    }
    
    @AfterMethod(alwaysRun = true)
    public void restore() {
        
        wdsp.deletePortConfigTrustOrInvalidPackets(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort1)-1));
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingGlobalConfig);
        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.dhcpSnoopingMode);
        handle.refresh();
        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.selectUserVlan("100"));
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        vlanPage.deleteVlan("100");
        handle.refresh();
        vlanPage.deleteVlan("200");
        MyCommonAPIs.sleep(8000);
        vlanPage.deleteAllVlan();
        

    }
}