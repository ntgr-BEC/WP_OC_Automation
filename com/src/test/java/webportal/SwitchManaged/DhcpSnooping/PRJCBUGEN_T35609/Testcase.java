package webportal.SwitchManaged.DhcpSnooping.PRJCBUGEN_T35609;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.commands.Click;

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
    @Story("PRJCBUGEN_T35609") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user is able to select all the ports in available switches in the network") // It's a testcase title from Jira
                                                                                                                  // Test Case.
    @TmsLink("PRJCBUGEN-T35609") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 2: enable trust mode to select one by one port and check the CLI")
    public void step2() {

        int totalPorts = WiredDhcpSnoopingElement.allPorts().size();
        System.out.println(totalPorts);
        for (int portNumber = 1; portNumber <= totalPorts; portNumber++) {

            wdsp.enablePortSpecificConfigOnPort(Integer.toString(portNumber), "Trust Mode", null);
            handle.refresh();
            assertTrue(WiredDhcpSnoopingElement.txtPortTrustModeCheck(Integer.toString(portNumber)).isDisplayed());

            String portType = webportalParam.getSwitchPort(webportalParam.sw1Model, portNumber);

            assertTrue(MyCommonAPIs.getCmdOutput("show running-config interfaces " + portType, false).contains("ip dhcp snooping trust"),
                    "Trust mode not enabled");
        }

        handle.click(WiredDhcpSnoopingElement.selectButton("Select All"));
        assertTrue(WiredDhcpSnoopingElement.selectButton("Deselect All").isDisplayed());
        handle.click(WiredDhcpSnoopingElement.selectButton("Delete"));
        handle.click(WiredDhcpSnoopingElement.saveGlobalConfiguration);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: enable trust mode by selecting all and check the CLI")
    public void step3() {

        handle.refresh();
        handle.click(WiredDhcpSnoopingElement.selectButton("Select All"));
        handle.click(WiredDhcpSnoopingElement.selectButton("Trust Mode"));
        handle.click(WiredDhcpSnoopingElement.saveGlobalConfiguration);

        int totalPorts = WiredDhcpSnoopingElement.allPorts().size();
        for (int portNumber = 1; portNumber <= totalPorts; portNumber++) {

            handle.refresh();
            assertTrue(WiredDhcpSnoopingElement.txtPortTrustModeCheck(Integer.toString(portNumber)).isDisplayed());

            String portType = webportalParam.getSwitchPort(webportalParam.sw1Model, portNumber);

            assertTrue(MyCommonAPIs.getCmdOutput("show running-config interfaces " + portType, false).contains("ip dhcp snooping trust"),
                    "Trust mode not enabled");
        }

        handle.click(WiredDhcpSnoopingElement.selectButton("Select All"));
        handle.click(WiredDhcpSnoopingElement.selectButton("Delete"));
        handle.click(WiredDhcpSnoopingElement.saveGlobalConfiguration);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: select all and deselect all then check the config")
    public void step4() {
        handle.refresh();
        handle.click(WiredDhcpSnoopingElement.selectButton("Select All"));
        int totalPorts = WiredDhcpSnoopingElement.allPorts().size();
        for (int portNumber = 0; portNumber < totalPorts; portNumber++) {
            assertTrue(WiredDhcpSnoopingElement.checkSelectedOrNot(Integer.toString(portNumber)).isDisplayed());
        }      
        
        handle.click(WiredDhcpSnoopingElement.selectButton("Deselect All"));

        for (int portNumber = 0; portNumber < totalPorts; portNumber++) {
            assertFalse(WiredDhcpSnoopingElement.checkSelectedOrNot(Integer.toString(portNumber)).isDisplayed());
        }
    }
}
