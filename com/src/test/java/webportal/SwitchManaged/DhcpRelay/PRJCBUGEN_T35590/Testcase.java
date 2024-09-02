package webportal.SwitchManaged.DhcpRelay.PRJCBUGEN_T35590;

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
    @Story("PRJCBUGEN_T35590") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user able to enable all ports in the DHCP L2 Relay interface by clicking select all button") // It's a
                                                                                                                                   // testcase
                                                                                                                                   // title from
    // Jira Test Case.
    @TmsLink("PRJCBUGEN-T35590") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 2: Enable Admin mode and check the CLI")
    public void step2() {

        handle.click(WiredDhcpRelayElement.selectAllButton);
        handle.click(WiredDhcpRelayElement.dhcpRelayPortConfigAdminMode);
        handle.click(WiredDhcpRelayElement.dhcpRelayPortConfigSaveButton);

        int numberOfPorts = WiredDhcpRelayElement.allPorts.size();
        int numberOfSelectedPorts = WiredDhcpRelayElement.allAdminModes.size();
        assertEquals(numberOfPorts, numberOfSelectedPorts, "all ports admin mode not enabled");

        handle.waitCmdReady("l2-relay", true);
        MyCommonAPIs.sleepsync();

        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config  ", false);
        boolean relayConfig = tmpStr.contains("ip dhcp l2-relay option");
        assertTrue(relayConfig, "Dhcp L2 Relay should be enabled");

    }

    @AfterMethod(alwaysRun = true)
    public void restore() {

        handle.refresh();
        handle.click(WiredDhcpRelayElement.selectAllButton);
        handle.click(WiredDhcpRelayElement.deletePortConfig);
        handle.click(WiredDhcpRelayElement.dhcpRelayPortConfigSaveButton);
    }
}
