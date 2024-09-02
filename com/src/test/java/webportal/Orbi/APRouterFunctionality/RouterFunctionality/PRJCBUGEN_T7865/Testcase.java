package webportal.Orbi.APRouterFunctionality.RouterFunctionality.PRJCBUGEN_T7865;

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
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiSatellitesPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    @Feature("APRouterFunctionality.RouterFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7865") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("[Satellites] Test to verify the functionality of \"skip\" and \"continue\" button while the user adds satellite")
    @TmsLink("PRJCBUGEN-T7865") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP/Select one Location/Click \"Device\" button")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        
        handle.gotoLoction();
        
        ddp.gotoPage();
    }
    
    @Step("Test Step 2: Choose online device, click \"Edit\" button")
    public void step2() {
        ddp.openOBDevice();
        new DevicesOrbiDeviceModePage(false).initDeviceMode(false);
    }
    
    @Step("Test Step 3: verify the functionality of \"skip\" and \"continue\" button while the user adds satellite")
    public void step3() {
        DevicesOrbiSatellitesPage page = new DevicesOrbiSatellitesPage();
        page.btnAdd.click();
        MyCommonAPIs.sleepi(1);
        if(page.btnCancel.exists()) {
            page.btnCancel.click();
            MyCommonAPIs.sleepi(1);
        }else {
            page.btnClose.click();
            MyCommonAPIs.sleepi(1);
        }
        
        assertTrue(!page.btnNext.isDisplayed(), "Check skip is doable");
        page.btnAdd.click();
        MyCommonAPIs.sleepi(1);
        page.btnNext.click();
        MyCommonAPIs.sleepi(1);
        assertTrue(page.btnNext.exists(), "Check continue is doable");
        
        if(page.btnCancel.exists()) {
            page.btnCancel.click();
            MyCommonAPIs.sleepi(1);
        }else {
            page.btnClose.click();
            MyCommonAPIs.sleepi(1);
        }
    }
}
