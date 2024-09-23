package webportal.SwitchManaged.DhcpSnooping.PRJCBUGEN_T35605;

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
    @Story("PRJCBUGEN_T35605") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the user is able to enable or disable invalid packets in Port config") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35605") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
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

        assertTrue(WiredDhcpSnoopingElement.selectButton("Invalid Packets").exists(), "Dhcp snooping invalid packets not available in this page");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: enable Invalid packets and check the CLI")
    public void step2() {

        wdsp.enablePortSpecificConfigOnPort(WebportalParam.sw1LagPort1, null,"Invalid Packets");
        handle.refresh();
        wdsp.enablePortSpecificConfigOnPort(WebportalParam.sw1LagPort2, null,"Invalid Packets");
        handle.refresh();
        
        assertTrue(WiredDhcpSnoopingElement.txtPortInvalidPacketsCheck(WebportalParam.sw1LagPort1).isDisplayed());
        assertTrue(WiredDhcpSnoopingElement.txtPortInvalidPacketsCheck(WebportalParam.sw1LagPort2).isDisplayed());
        
        assertTrue(MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.sw1LagPort1CLI, false)
                .contains("ip dhcp snooping log-invalid"), "Invalid packets logging not enabled");
        assertTrue(MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.sw1LagPort2CLI, false)
                .contains("ip dhcp snooping log-invalid"), "Invalid packets logging not enabled");

    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Disable Invalid packets and check the CLI")
    public void step3() {

        wdsp.deletePortConfigTrustOrInvalidPackets(WebportalParam.sw1LagPort1);

        handle.refresh();
        assertFalse(WiredDhcpSnoopingElement.txtPortInvalidPacketsCheck(WebportalParam.sw1LagPort1).isDisplayed());
        
        assertFalse(MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.sw1LagPort1CLI, false)
                .contains("ip dhcp snooping log-invalid"), "Invalid packets logging should be disabled");

    }

    @AfterMethod(alwaysRun = true)
    public void restore() {

        wdsp.deletePortConfigTrustOrInvalidPackets(WebportalParam.sw1LagPort2);

    }

}
