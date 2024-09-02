package webportal.Switch.DhcpRelay.PRJCBUGEN_T35580;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

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
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.webelements.WiredDhcpRelayElement;

/**
 * @author ravi
 */
public class Testcase extends TestCaseBase {

    @Feature("Switch.DhcpRelay") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35580") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify user able to configure the Admin Mode as in a Global configuration") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35580") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 2: Enable Admin mode and check the CLI")
    public void step2() {

        wdrp.gotoDhcpConfigPage(WiredDhcpRelayElement.dhcpL2RelayGlobalConfig);
        wdrp.enableOrDisableDhcpRelayconfigModes(WiredDhcpRelayElement.enableGlobalConfigAdminMode);

        assertTrue(WiredDhcpRelayElement.enableGlobalConfigAdminMode.isEnabled(), "Admin Mode not enabled");
               
        
        handle.waitCmdReady("l2-relay", true);
        MyCommonAPIs.sleepsync();

        
        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config  ", false);
        boolean str1 = tmpStr.contains("ip dhcp l2-relay");
        assertTrue(str1, "Dhcp L2 Relay is not enabled");

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Disable Admin mode and check the CLI")
    public void step3() {
        handle.refresh();
        wdrp.enableOrDisableDhcpRelayconfigModes(WiredDhcpRelayElement.enableGlobalConfigAdminMode);

        assertTrue(WiredDhcpRelayElement.enableGlobalConfigAdminMode.isEnabled(), "Admin Mode not disabled");

        handle.waitCmdReady("l2-relay", true);
        MyCommonAPIs.sleepsync();

        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config  ", false);
        boolean str1 = tmpStr.contains("ip dhcp l2-relay");
        assertFalse(!str1, "Dhcp L2 Relay line is there");

    }

}
