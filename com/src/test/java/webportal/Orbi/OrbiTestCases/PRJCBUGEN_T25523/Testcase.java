package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25523;

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
    @Feature("Orbi.OrbiTestCases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25523") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify disabling and saving content filtering from the content filtering tab")
    @TmsLink("PRJCBUGEN-T25523") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP/Select one Location/Click \"Device\" button / Change to router mode")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        
        handle.gotoLoction();
        
        ddp.gotoPage();
        ddp.openOB2();
        DevicesOrbiDeviceModePage page = new DevicesOrbiDeviceModePage();
        if(!page.isRouterMode()) {
            page.setDeviceMode(false);
            MyCommonAPIs.sleepsyncorbi();
        }
    }
    
    @Step("Test Step 2: Open Content Filtering / Disable and save")
    public void step2() {
        
        DevicesOrbiContentFilteringPage contentpage = new DevicesOrbiContentFilteringPage();
        // if disabled, restore to enabled first
        if(!contentpage.inputEnableCF.isSelected()) {
            contentpage.spanEnableCF.click();
            contentpage.btnSave.click();
            MyCommonAPIs.sleepsync();
        }
        
        contentpage.gotoPage();
        contentpage.spanEnableCF.click();
        contentpage.btnYes.click();
        MyCommonAPIs.sleepsync();
    }
    
    @Step("Test Step 3: Goto another page and then goto content filtering page check if it's enabled")
    public void step3() {
        new DevicesOrbiSummaryPage();
        
        DevicesOrbiContentFilteringPage page = new DevicesOrbiContentFilteringPage();
        assertTrue(!page.inputEnableCF.isSelected(), "Check if CF is not enabled");
    }
}
