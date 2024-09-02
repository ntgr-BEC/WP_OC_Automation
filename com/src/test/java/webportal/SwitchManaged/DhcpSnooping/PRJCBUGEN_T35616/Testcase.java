package webportal.SwitchManaged.DhcpSnooping.PRJCBUGEN_T35616;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.*;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

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
import webportal.webelements.WiredDhcpRelayElement;
import webportal.webelements.WiredDhcpSnoopingElement;

/**
 * @author ravi
 */
public class Testcase extends TestCaseBase {

    @Feature("Switch.DhcpSnooping") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35615") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that If user disable the DHCP relay then the previous config on DHCP Snooping should"
            + " remain the same on the Selected VLAN") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35615") // It's a testcase id/link from Jira Test Case.
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

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Create vlan 100 and 200 check the configuration")
    public void step2() {
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
    @Step("Test Step 3: Enable relay Admin mode and check")
    public void step3() {

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        wdrp.gotoDhcpConfigPage(WiredDhcpRelayElement.dhcpL2RelayGlobalConfig);
        wdrp.enableOrDisableDhcpRelayconfigModes(WiredDhcpRelayElement.enableGlobalConfigAdminMode);

        handle.refresh();
        wdrp.enableOrDisableDhcpRelayconfigModes(WiredDhcpRelayElement.selectUserVlan("100"));

        handle.refresh();
        assertTrue(WiredDhcpRelayElement.enableGlobalConfigAdminMode.isEnabled(), "Admin Mode should be enabled");
        assertTrue(WiredDhcpRelayElement.dhcpRelayGlobalConfigUserVlanEnableButton.isEnabled(), "User vlan should be enable");
        
        handle.waitCmdReady("l2-relay", true);
        MyCommonAPIs.sleepsync();

        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config  ", false);
        boolean relayConfig = tmpStr.contains("ip dhcp l2-relay");
        boolean vlanRelayConfig = tmpStr.contains("ip dhcp l2-relay vlan 100");
        if (relayConfig && vlanRelayConfig) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 2:vlanId is:" + (relayConfig && vlanRelayConfig));
        }

    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Enable snooping Admin mode and check")
    public void step4() {

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingGlobalConfig);
        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.dhcpSnoopingMode);

        handle.refresh();
        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.selectUserVlan("100"));

        handle.refresh();
        assertTrue(WiredDhcpSnoopingElement.dhcpSnoopingMode.isEnabled(), "Admin Mode should be enabled");
        assertTrue(WiredDhcpSnoopingElement.selectUserVlan("100").isEnabled(), "User vlan should be enable");
        
        handle.waitCmdReady("dhcp snooping", true);
        MyCommonAPIs.sleepsync();

        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config  ", false);
        boolean snoopingConfig = tmpStr.contains("ip dhcp snooping");
        boolean vlanSnoopingConfig = tmpStr.contains("ip dhcp snooping vlan 100");
        assertTrue(snoopingConfig, "Dhcp Snooping should be enabled");
        assertTrue(vlanSnoopingConfig, "Dhcp Snooping vlan 100 should be enabled");

    } 

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: disable relay admin and vlan mode, check configurations")
    public void step5() {
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        wdrp.gotoDhcpConfigPage(WiredDhcpRelayElement.dhcpL2RelayGlobalConfig);
        wdrp.enableOrDisableDhcpRelayconfigModes(WiredDhcpRelayElement.enableGlobalConfigAdminMode);

        handle.refresh();
        wdrp.enableOrDisableDhcpRelayconfigModes(WiredDhcpRelayElement.selectUserVlan("100"));

        handle.refresh();
        assertTrue(WiredDhcpRelayElement.enableGlobalConfigAdminMode.isEnabled(), "Admin Mode should be enabled");
        assertTrue(WiredDhcpRelayElement.dhcpRelayGlobalConfigUserVlanEnableButton.isEnabled(), "User vlan should be enable");
        
        handle.waitCmdReady("l2-relay", true);
        MyCommonAPIs.sleepsync();

        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config  ", false);
        boolean relayConfig = tmpStr.contains("ip dhcp l2-relay");
        boolean vlanRelayConfig = tmpStr.contains("ip dhcp l2-relay vlan 100");
        assertFalse(relayConfig, "Relay admin should be disable");
        assertFalse(vlanRelayConfig, "100 vlan relay mode should be disable");
        
        boolean snoopingConfig = tmpStr.contains("ip dhcp snooping");
        boolean vlanSnoopingConfig = tmpStr.contains("ip dhcp snooping vlan 100");
        assertTrue(snoopingConfig, "Dhcp Snooping should be enabled");
        assertTrue(vlanSnoopingConfig, "Dhcp Snooping vlan 100 should be enabled");
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        wdrp.gotoDhcpConfigPage(WiredDhcpRelayElement.dhcpL2RelayGlobalConfig);
        wdrp.enableOrDisableDhcpRelayconfigModes(WiredDhcpRelayElement.enableGlobalConfigAdminMode);
        handle.refresh();
        wdrp.enableOrDisableDhcpRelayconfigModes(WiredDhcpRelayElement.selectUserVlan("100"));

        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteVlan("100");
        handle.refresh();
        vlanPage.deleteVlan("200");
        MyCommonAPIs.sleep(8000);
        vlanPage.deleteAllVlan();
    }
}