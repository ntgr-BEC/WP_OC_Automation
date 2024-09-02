package webportal.Orbi.OrbiTestCases_IM63.Router.PRJCBUGEN_T26469;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;
import webportal.param.WebportalParam;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiTroubleshootPage;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("Orbi.OrbiTestCases_IM63") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26469") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the ping test functionality for the orbi device") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26469") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 / Go to Device page")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
    }

    @Step("Test Step 2: Go to Troubleshoot page / Ping one success and one fail")
    public void step2() {
        boolean checkpoint;
        ddp.openOB2();
        checkpoint = new DevicesOrbiTroubleshootPage().PingTest("8.8.8.8");
        assertTrue(checkpoint,"checkpoint 1 : ping 8.8.8.8 success");
        checkpoint = new DevicesOrbiTroubleshootPage().PingTest("172.17.1.100");
        assertFalse(checkpoint,"checkpoint 1 : ping 172.17.1.100 failed");
    }
    
    
    
}