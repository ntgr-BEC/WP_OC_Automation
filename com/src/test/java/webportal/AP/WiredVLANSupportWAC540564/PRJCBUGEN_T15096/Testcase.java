package webportal.AP.WiredVLANSupportWAC540564.PRJCBUGEN_T15096;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1096";
    String networkName = "testnet";

    @Feature("AP.WiredVLANSupportWAC540564") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15096") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the user can create a VLAN for WAC540 using network location settings") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T15096") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: In Device-Summary there in the Ports page Summary and Settings menu are available")
    public void step2() {
        netsp.gotoPage();
        netsp.openNetwork("1");
        netsp.gotoStep(2);

        assertTrue($x(String.format("//h5[text()='%s']", WebportalParam.ap1deveiceName)).exists(),
                "check Existing WAC540 APs will be present with the ports to select");
    }
}
