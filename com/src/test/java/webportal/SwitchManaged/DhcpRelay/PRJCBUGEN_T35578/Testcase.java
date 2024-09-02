package webportal.SwitchManaged.DhcpRelay.PRJCBUGEN_T35578;

import static com.codeborne.selenide.Selenide.$;
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
import webportal.webelements.WiredDhcpRelayElement;




/**
 * @author ravi
 */
public class Testcase extends TestCaseBase {

    @Feature("Switch.DhcpRelay") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35578") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that DHCP Relay is available in the switch device configuration") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35578") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page and verify DHCP L2 Relay")
    public void step1() {
        // enter into the wired settings page
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();  
        
       
        assertTrue(WiredDhcpRelayElement.checkDhcpL2Relay.exists(), "Dhcp L2 Relay not available in this page");

        
    }
   
}