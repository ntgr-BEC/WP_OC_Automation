package webportal.SwitchManaged.DhcpSnooping.PRJCBUGEN_T35602;

import static com.codeborne.selenide.Selenide.$;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.support.ui.Sleeper;
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
import webportal.weboperation.WiredGroupPortConfigPage;
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
    @Story("PRJCBUGEN_T35602") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user is able to save the global config by clicking save button") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35602") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page and create Vlan")
    public void step1() {
        // enter into the wired settings page
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

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
    @Step("Test Step 2: Enable Dhcp Snooping Admin mode and VLAN 100, VLAN 200 mode check the CLI")
    public void step2() {

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingGlobalConfig);
        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.dhcpSnoopingMode);

        handle.refresh();
        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.selectUserVlan("100"));
        handle.refresh();
        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.selectUserVlan("200"));
        
        handle.refresh();
        assertTrue(WiredDhcpSnoopingElement.dhcpSnoopingModebutton.isSelected(), "Admin Mode should be enabled");
        assertTrue(WiredDhcpSnoopingElement.selectUserVlanbutton("100").isSelected(), "User vlan 100 should be enable");
        assertTrue(WiredDhcpSnoopingElement.selectUserVlanbutton("200").isSelected(), "User vlan 100 should be enable");

        MyCommonAPIs.sleepsync();
        handle.waitCmdReady("dhcp snooping", true);
      
        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config  ", false);
        boolean snoopingConfig = tmpStr.contains("ip dhcp snooping");
        boolean vlanSnoopingConfig = tmpStr.contains("ip dhcp snooping vlan 100,200");
        assertTrue(snoopingConfig, "Dhcp Snooping should be enabled");
        assertTrue(vlanSnoopingConfig, "Dhcp Snooping vlan 100 should be enabled");

    }
    
    //Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Disable DHCP Snooping Admin Mode and VLAN 200 mode and check the CLI")
    public void step3() {

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingGlobalConfig);
        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.dhcpSnoopingMode);
        handle.refresh();
        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.selectUserVlan("200"));
        
        handle.refresh();
  
        System.out.println(WiredDhcpSnoopingElement.dhcpSnoopingModebutton.isSelected());
        System.out.println(WiredDhcpSnoopingElement.selectUserVlanbutton("200").isSelected());
      
        //Checking whether the DHCP SNOOPING MODE and VLAN200 toggle button is disabled 
        assertFalse(WiredDhcpSnoopingElement.selectUserVlanbutton("200").isSelected(), "User vlan should be disable");
        assertFalse(WiredDhcpSnoopingElement.dhcpSnoopingModebutton.isSelected(), "Admin Mode should be disabled");
        
        MyCommonAPIs.sleepsync();
        handle.waitCmdReady("dhcp snooping", true);  
        
        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config  ", false);
        boolean vlanSnoopingConfig = tmpStr.contains("ip dhcp snooping vlan 200");
        assertFalse(vlanSnoopingConfig, "Dhcp Snooping vlan 200 should be enabled");
        assertTrue(MyCommonAPIs.getCmdOutput("show ip dhcp snooping ", false).contains("Disabled"), "admin mode sould be disabled");

        boolean vlan100SnoopingConfig = tmpStr.contains("ip dhcp snooping vlan 100");
        assertTrue(vlan100SnoopingConfig, "Dhcp snooping vlan 100 should be enabled");
        
    }
    

    @AfterMethod(alwaysRun = true)
    public void restore() {

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingGlobalConfig);
        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.selectUserVlan("100"));
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        vlanPage.deleteVlan("100");
        vlanPage.deleteVlan("200");
        MyCommonAPIs.sleep(8000);
        vlanPage.deleteAllVlan();

    }

}
