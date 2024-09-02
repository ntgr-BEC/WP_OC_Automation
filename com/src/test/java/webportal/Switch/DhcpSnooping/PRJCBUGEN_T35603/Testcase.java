package webportal.Switch.DhcpSnooping.PRJCBUGEN_T35603;

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
    @Story("PRJCBUGEN_T35603") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user is able to redirected to Port config page by clicking the port config") // It's a testcase title from
                                                                                                                   // Jira Test Case.
    @TmsLink("PRJCBUGEN-T35603") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 2: Test to verify that DHCP Relay has Port Configuration")
    public void step2() {

        wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingPortConfig);

        assertTrue(WiredDhcpSnoopingElement.selectButton("Trust Mode").exists(), "Dhcp L2 Relay Port Config is not available in this page");

        int totalPorts = WiredDhcpSnoopingElement.allPorts().size();
        System.out.println(totalPorts);
        for (int portNumber = 1; portNumber <= totalPorts; portNumber++) {

            assertTrue(WiredDhcpSnoopingElement.txtPortSelection(WebportalParam.sw1deveiceName ,Integer.toString(portNumber)).exists(), "Ports not visible");

        }

    }

}
