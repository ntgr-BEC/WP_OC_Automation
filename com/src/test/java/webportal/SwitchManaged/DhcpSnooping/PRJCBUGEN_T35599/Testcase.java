package webportal.SwitchManaged.DhcpSnooping.PRJCBUGEN_T35599;

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
    @Story("PRJCBUGEN_T35599") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user is able to enable or disable DHCP snooping mode in global config") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35599") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
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
    @Step("Test Step 2: Enable DHCP Snooping mode and check the CLI")
    public void step2() {

        wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingGlobalConfig);
        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.dhcpSnoopingMode);

        MyCommonAPIs.sleepi(5);
        assertTrue(WiredDhcpSnoopingElement.dhcpSnoopingModebutton.isSelected(), "DHCP Snooping Mode not enabled");

        MyCommonAPIs.sleepsync();
        handle.waitCmdReady("Snooping", true);
     
        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config  ", false);
        boolean str1 = tmpStr.contains("ip dhcp snooping");
        assertTrue(str1, "Dhcp Snooping is not enabled");

    }
    
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Disable DHCP Snooping mode and check the CLI")
    public void step3() {
        handle.refresh();
        wdsp.enableOrDisableDhcpSnoopingconfigModes(WiredDhcpSnoopingElement.dhcpSnoopingMode);

        MyCommonAPIs.sleepi(5);
        assertFalse(WiredDhcpSnoopingElement.dhcpSnoopingModebutton.isSelected(), "DHCP Snooping Mode not disabled");

        MyCommonAPIs.sleepsync();
        handle.waitCmdReady("snooping", true);
     
//        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config  ", false);
//        boolean str1 = tmpStr.contains("no ip dhcp snooping verify mac-address");
//        assertTrue(str1, "Dhcp Snooping line is there");

    }

}
