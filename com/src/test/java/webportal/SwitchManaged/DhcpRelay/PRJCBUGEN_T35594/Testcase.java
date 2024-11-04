package webportal.SwitchManaged.DhcpRelay.PRJCBUGEN_T35594;

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
import webportal.weboperation.WiredDhcpRelayPage;
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
    @Story("PRJCBUGEN_T35594") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that If Switch is Rebooted and activities back then the previous config DHCP Relay should remain the same on the Selected VLAN") // It's a testcase
                                                                                                                                                                           // title
                                                                                                                                                                           // from
    // Jira Test Case.
    @TmsLink("PRJCBUGEN-T35594") // It's a testcase id/link from Jira Test Case.
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
        vlanPage.deleteAllVlan();
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
    @Step("Test Step 3: Enable Admin mode and check")
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
        boolean relayConfig = tmpStr.contains("dhcp l2relay");
        boolean vlanRelayConfig = tmpStr.contains("dhcp l2relay vlan 100");
        if (relayConfig && vlanRelayConfig) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 2:vlanId is:" + (relayConfig && vlanRelayConfig));
        }

    }

    @Step("Test Step 4: Reboot the device via insight and check configurations")
    public void step4() {
        ddpmg.gotoPage();
        ddpmg.waitDevicesReboot(WebportalParam.sw1serialNo);
        
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        wdrp.gotoDhcpConfigPage(WiredDhcpRelayElement.dhcpL2RelayGlobalConfig);

        handle.refresh();
        assertTrue(WiredDhcpRelayElement.enableGlobalConfigAdminMode.isEnabled(), "Admin Mode should be enabled");
        assertTrue(WiredDhcpRelayElement.selectUserVlan("100").isEnabled(), "User vlan should be enable");
        
        handle.waitCmdReady("l2relay", true);
        MyCommonAPIs.sleepsync();

        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config  ", false);
        boolean relayConfig = tmpStr.contains("dhcp l2relay");
        boolean vlanRelayConfig = tmpStr.contains("dhcp l2relay vlan 100");
        if (relayConfig && vlanRelayConfig) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 2:vlanId is:" + (relayConfig && vlanRelayConfig));
        }

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
