package webportal.Orbi.OrbiTestCases_IM63.AP.PRJCBUGEN_T26672;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiTroubleshootPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    @Feature("Orbi.OrbiTestCases_IM63") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26672") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the DNS look up test for orbi device")
    @TmsLink("PRJCBUGEN-T26672") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
    
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP / Select one Location / Click \"Device\" button / ")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
    }
    
    @Step("Test Step 2: Click troubleshooting page on Insight, run DNS lookup.")
    public void step2() {
        boolean result = false;
        ddp.openOBDevice();
        new DevicesOrbiDeviceModePage(false).initDeviceMode(true);
        
        DevicesOrbiTroubleshootPage page = new DevicesOrbiTroubleshootPage();
        page.gotoPage(1);
        result = page.DNSLookup("ipv4.google.com");
        
        assertTrue(result, "DNS Lookup failed");
    }
}
