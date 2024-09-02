package webportal.Switch.DhcpRelay.PRJCBUGEN_T35591;

import static com.codeborne.selenide.Selenide.$;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.ElementsCollection;

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
    @Story("PRJCBUGEN_T35591") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user able to disable the all selected ports in the DHCP L2 interface") // It's a testcase title from
    // Jira Test Case.
    @TmsLink("PRJCBUGEN-T35591") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 2: Press Switch Select All button")
    public void step2() {

        handle.click(WiredDhcpRelayElement.selectAllButton);
        int numberOfPorts = WiredDhcpRelayElement.allPorts.size();
        int numberOfSelectedPorts = WiredDhcpRelayElement.allSelectChecks.size();
        assertEquals(numberOfPorts, numberOfSelectedPorts, "all ports are not selected");

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Deselected port one by one")
    public void step3() {

        // Define the total number of ports
        int totalPorts = WiredDhcpRelayElement.allPorts.size();

        for (int portNumber = 0; portNumber < totalPorts; portNumber++) {

            handle.click(WiredDhcpRelayElement.txtPortSelection(Integer.toString(portNumber)));
            assertFalse(WiredDhcpRelayElement.checkPortSelectedOrNot(Integer.toString(portNumber)));

        }

        assertTrue(WiredDhcpRelayElement.deselectAllButton.isDisplayed());

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Press all Switch ports one by one")
    public void step4() {

        // Define the total number of ports
        int totalPorts = WiredDhcpRelayElement.allPorts.size();

        for (int portNumber = 0; portNumber < totalPorts; portNumber++) {

            handle.click(WiredDhcpRelayElement.txtPortSelection(Integer.toString(portNumber)));

        }
        
        int numberOfPorts = WiredDhcpRelayElement.allPorts.size();
        int numberOfSelectedPorts = WiredDhcpRelayElement.allSelectChecks.size();
        System.out.println(numberOfPorts);
        System.out.println(numberOfSelectedPorts);
        assertEquals(numberOfPorts, numberOfSelectedPorts, "all ports are not selected");

    }
    
 // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: Press Deselect All button")
    public void step5() { 
        handle.click(WiredDhcpRelayElement.deselectAllButton);
        assertFalse(WiredDhcpRelayElement.checkPortSelectedOrNot("0"));
        
    }

}
