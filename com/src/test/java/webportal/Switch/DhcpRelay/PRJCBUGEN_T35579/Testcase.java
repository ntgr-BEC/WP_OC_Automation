package webportal.Switch.DhcpRelay.PRJCBUGEN_T35579;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

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
import webportal.param.URLParam;
import webportal.webelements.WiredDhcpRelayElement;
import webportal.webelements.WiredDhcpSnoopingElement;

/**
 * @author ravi
 */
public class Testcase extends TestCaseBase {

    @Feature("Switch.DhcpRelay") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35579") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that DHCP Snooping and DHCP Relay has Global Configuration & Port Configuration") // It's a testcase title from
                                                                                                                   // Jira Test Case.
    @TmsLink("PRJCBUGEN-T35579") // It's a testcase id/link from Jira Test Case.
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

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Test to verify that DHCP Snooping and DHCP Relay has Global Configuration & Port Configuration")
    public void step2() {

        String goToPage = wdrp.gotoDhcpConfigPage(WiredDhcpRelayElement.dhcpL2RelayGlobalConfig);
        if (goToPage != null) {
            assertTrue(WiredDhcpRelayElement.enableGlobalConfigAdminMode.exists(), "Dhcp L2 Relay Global Config is not available in this page");
        }

        String goToRelayPortPage = wdrp.gotoDhcpConfigPage(WiredDhcpRelayElement.dhcpL2RelayPortConfig);
        if (goToRelayPortPage != null) {
            assertTrue(WiredDhcpRelayElement.checkDhcpL2RelayPortConfig.exists(), "Dhcp L2 Relay Port Config is not available in this page");
        }

        String goToSnoopingGlobalConfigPage = wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingGlobalConfig);
        if (goToSnoopingGlobalConfigPage != null) {
            assertTrue(WiredDhcpSnoopingElement.dhcpSnoopingMode.exists(), "Dhcp Snooping Global Config is not available in this page");

        }

        String goToSnoopingPortConfigPage = wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingPortConfig);
        if (goToSnoopingPortConfigPage != null) {
            assertTrue(WiredDhcpSnoopingElement.selectButton("Trust Mode").exists(), "Dhcp Snooping Global Config is not available in this page");
        }

    }

}
