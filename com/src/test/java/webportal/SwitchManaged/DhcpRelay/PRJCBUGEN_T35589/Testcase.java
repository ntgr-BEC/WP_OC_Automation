package webportal.SwitchManaged.DhcpRelay.PRJCBUGEN_T35589;

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
    @Story("PRJCBUGEN_T35589") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("82 Option Trust Mode- Enable or Disable an interface to be trusted for DHCP L2 Relay (Option-82) received") // It's a testcase
                                                                                                                                // title from
    // Jira Test Case.
    @TmsLink("PRJCBUGEN-T35589") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // enter into the wired settings page
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        String goToRelayPortPage = wdrp.gotoDhcpConfigPage(WiredDhcpRelayElement.dhcpL2RelayPortConfig);
        if (goToRelayPortPage != null) {
            assertTrue(WiredDhcpRelayElement.checkDhcpL2RelayPortConfig.exists(), "Dhcp L2 Relay Port Config is not available in this page");
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Enable Admin mode and trust mode check the CLI")
    public void step2() {

        wdrp.enablePortConfigAdminModeOnPort(WiredDhcpRelayElement.txtPortSelection(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort1))), "admin Mode", "trust mode");
        handle.refresh();
        wdrp.enablePortConfigAdminModeOnPort(WiredDhcpRelayElement.txtPortSelection(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort2))), "admin Mode", "trust mode");

        handle.refresh();
        assertTrue(WiredDhcpRelayElement.txtPortAdminModeCheck(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort1))).isDisplayed());
        assertTrue(WiredDhcpRelayElement.txtPortAdminModeCheck(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort2))).isDisplayed());
        

        
        MyCommonAPIs.sleepi(60);
      //check the cli output on sw1LagPort1CLI
        
        if(WebportalParam.sw1Model.contains("M4250") || WebportalParam.sw1Model.contains("M4350")) {
        assertTrue(MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.sw1LagPort1CLI, false)
                .contains("dhcp l2relay trust"), "admin mode not enabled");
        assertTrue(MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.sw1LagPort1CLI, false)
                .contains("dhcp l2relay"), "admin mode not enabled");

        //check the cli output on sw1LagPort2CLI
        assertTrue(MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.sw1LagPort2CLI, false)
                .contains("dhcp l2relay trust"), "admin mode not enabled");
        assertTrue(MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.sw1LagPort2CLI, false)
                .contains("dhcp l2relay"), "admin mode not enabled");
        
        }else {
            
            
            assertTrue(MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.sw1LagPort1CLI, false)
                    .contains("dhcp l2relay trust"), "admin mode not enabled");
            assertTrue(MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.sw1LagPort1CLI, false)
                    .contains("dhcp l2relay option"), "admin mode not enabled");

            //check the cli output on sw1LagPort2CLI
            assertTrue(MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.sw1LagPort2CLI, false)
                    .contains("dhcp l2relay trust"), "admin mode not enabled");
            assertTrue(MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.sw1LagPort2CLI, false)
                    .contains("dhcp l2relay option"), "admin mode not enabled");
            
        }


    }

 // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Disable Admin and trust mode and check the CLI")
    public void step3() {

        handle.refresh();
        wdrp.deletePortConfigAdminOrTrustModeOnPort(WiredDhcpRelayElement.txtPortSelection(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort1))));
        
        handle.refresh();
        assertFalse(WiredDhcpRelayElement.txtPortAdminModeCheck(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort1))).isDisplayed());
        assertFalse(WiredDhcpRelayElement.txtPortTrustModeCheck(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort1))).isDisplayed());

        //check the CLI output on sw1lagPort1CLI
        if(WebportalParam.sw1Model.contains("M4250") || WebportalParam.sw1Model.contains("M4350")) {
        assertFalse(MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.sw1LagPort1CLI, false)
                .contains("dhcp l2relay trust"), "admin mode not enabled");
        assertFalse(MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.sw1LagPort1CLI, false)
                .contains("dhcp l2relay"), "admin mode should be disabled");
        }else
        {
            assertFalse(MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.sw1LagPort1CLI, false)
                    .contains("dhcp l2relay trust"), "admin mode not enabled");
            assertFalse(MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.sw1LagPort1CLI, false)
                    .contains("dhcp l2relay option"), "admin mode should be disabled");   
        }
    }
    
    @AfterMethod(alwaysRun = true)
    public void restore() {

        handle.refresh();
        wdrp.deletePortConfigAdminOrTrustModeOnPort(WiredDhcpRelayElement.txtPortSelection(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort2))));

    }

}
