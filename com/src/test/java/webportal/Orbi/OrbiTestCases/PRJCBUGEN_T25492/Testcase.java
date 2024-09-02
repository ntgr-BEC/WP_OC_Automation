package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25492;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesOrbiContentFilteringPage;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author ann.fang
 */
public class Testcase extends TestCaseBase {
    String       predate      = "";
    String       curdate      = "";
    
    @Feature("Orbi.OrbiTestCases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25492") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the reboot functionality")
    @TmsLink("PRJCBUGEN-T25492") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP/Select one Location/Click \"Device\" button ")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        
        handle.gotoLoction();
        
        ddp.gotoPage();
        predate = ddp.getDeviceUptime(WebportalParam.ob2serialNo, true);
        ddp.openOB2();
        
    }
    
    @Step("Test Step 2: Click reboot icon")
    public void step2() {
        DevicesOrbiSummaryPage page = new DevicesOrbiSummaryPage();
        page.btnActionReboot.click();
        page.btnContinue.click();
        MyCommonAPIs.sleepsyncorbi();
    }
    
    @Step("Test Step 3: Check the device up time")
    public void step3() {
        
        ddp.gotoPage();
        curdate = ddp.getDeviceUptime(WebportalParam.ob2serialNo, true);
        assertTrue(Integer.parseInt(predate) > Integer.parseInt(curdate), "Device should reboot.");
        
    }
}
