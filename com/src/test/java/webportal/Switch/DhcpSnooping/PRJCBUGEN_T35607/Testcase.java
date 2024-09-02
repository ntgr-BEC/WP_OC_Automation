package webportal.Switch.DhcpSnooping.PRJCBUGEN_T35607;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.webelements.WiredDhcpSnoopingElement;

/**
 * @author ravi
 */
public class Testcase extends TestCaseBase {

    @Feature("Switch.DhcpSnooping") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35604") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user is able to enable or disable trust mode in port config") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35604") // It's a testcase id/link from Jira Test Case.
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
        wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingPortConfig);

        assertTrue(WiredDhcpSnoopingElement.selectButton("Trust Mode").exists(), "Dhcp snooping trust not available in this page");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: enable trust mode and check the CLI")
    public void step2() {

        wdsp.enablePortSpecificConfigOnPort(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort1)-1), "Trust Mode", null);
        handle.refresh();
        
        assertTrue(WiredDhcpSnoopingElement.txtPortTrustModeCheck(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort1)-1)).isDisplayed());
        
        assertTrue(MyCommonAPIs.getCmdOutput("show running-config interfaces " + WebportalParam.sw1LagPort1CLI, false)
                .contains("ip dhcp snooping trust"), "Trust mode not enabled");
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Disable trust mode and check the CLI")
    public void step3() {

        wdsp.deletePortConfigTrustOrInvalidPackets(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort1)-1));

        handle.refresh();
        assertFalse(WiredDhcpSnoopingElement.txtPortTrustModeCheck(Integer.toString(Integer.parseInt(WebportalParam.sw1LagPort1)-1)).isDisplayed());
        
        assertFalse(MyCommonAPIs.getCmdOutput("show running-config interfaces " + WebportalParam.sw1LagPort1CLI, false)
                .contains("ip dhcp snooping trust"), "trust mode should be disabled");

    }
}
