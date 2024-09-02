package webportal.SwitchManaged.DhcpRelay.PRJCBUGEN_T35584;

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
import webportal.weboperation.WiredDhcpRelayPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.webelements.WiredDhcpRelayElement;


/**
 * @author ravi
 */
public class Testcase extends TestCaseBase {

    @Feature("Switch.DhcpRelay") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35584") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that admin Mode in the Global Configuration needs to be enabled in the switch to activate the DHCP L2 relay function") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35584") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 2: Create vlan 100 and check the configuration")
    public void step2() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.addDataVlanWithPorts("data vlan", "100", null, null, null, null, null, null, null);
        handle.waitCmdReady("100", false);

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
    @Step("Test Step 3: Enable VLAN 100 mode and check the CLI")
    public void step3() {

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        wdrp.gotoDhcpConfigPage(WiredDhcpRelayElement.dhcpL2RelayGlobalConfig);
        wdrp.enableOrDisableDhcpRelayconfigModes(WiredDhcpRelayElement.dhcpRelayGlobalConfigUserVlanEnableButton);

        handle.refresh();
        assertTrue(WiredDhcpRelayElement.dhcpRelayGlobalConfigUserVlanEnableButton.isEnabled(), "User vlan should be enable");

        handle.waitCmdReady("l2-relay", true);
        MyCommonAPIs.sleepsync();

        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config  ", false);
        boolean vlanRelayConfig = tmpStr.contains("ip dhcp l2-relay vlan 100");
        assertTrue(vlanRelayConfig, "Dhcp l2 relay vlan 100 should be enabled");

    }
    
 // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Enable Admin mode and check the CLI")
    public void step4() {
    	
    	 WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
         wdrp.gotoDhcpConfigPage(WiredDhcpRelayElement.dhcpL2RelayGlobalConfig);
         wdrp.enableOrDisableDhcpRelayconfigModes(WiredDhcpRelayElement.enableGlobalConfigAdminMode);
         
         handle.refresh();
         assertTrue(WiredDhcpRelayElement.enableGlobalConfigAdminMode.isEnabled(), "Admin Mode should be enabled");
         
         handle.waitCmdReady("l2-relay", true);
         MyCommonAPIs.sleepsync();
         
         String tmpStr = MyCommonAPIs.getCmdOutput("show running-config  ", false);
         boolean relayConfig = tmpStr.contains("ip dhcp l2-relay");
         boolean vlanRelayConfig = tmpStr.contains("ip dhcp l2-relay vlan 100");
         assertTrue(vlanRelayConfig, "Dhcp l2 relay vlan 100 should be enabled");
         assertTrue(relayConfig, "Dhcp L2 Relay should be enabled");
         
    }
    
    
    @AfterMethod(alwaysRun = true)
    public void restore() {
        wdrp.gotoDhcpConfigPage(WiredDhcpRelayElement.dhcpL2RelayGlobalConfig);
        wdrp.enableOrDisableDhcpRelayconfigModes(WiredDhcpRelayElement.enableGlobalConfigAdminMode);
        handle.refresh();
        wdrp.enableOrDisableDhcpRelayconfigModes(WiredDhcpRelayElement.dhcpRelayGlobalConfigUserVlanEnableButton);
        
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteVlan("100");
        MyCommonAPIs.sleep(8000);
        vlanPage.deleteAllVlan();       

    }

}
