package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25521;

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
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author ann.fang
 */
public class Testcase extends TestCaseBase {
    @Feature("Orbi.OrbiTestCases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25521") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify changing the device mode from AP to router from the device mode page")
    @TmsLink("PRJCBUGEN-T25521") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        ddp.gotoPage();
        ddp.openOB2();
        DevicesOrbiDeviceModePage page = new DevicesOrbiDeviceModePage();
        if(!page.isRouterMode()) {
            page.setDeviceMode(false);
            MyCommonAPIs.sleepsyncorbi();
        }
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP/Select one Location/Click \"Device\" button")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        
        handle.gotoLoction();
        
        ddp.gotoPage();
    }
    
    @Step("Test Step 2: If device is router mode, change it to AP mode")
    public void step2() {
        ddp.openOB2();
        DevicesOrbiDeviceModePage page = new DevicesOrbiDeviceModePage();
        if(page.isRouterMode()) {
            new DevicesOrbiDeviceModePage(false).initDeviceMode(true);
            MyCommonAPIs.sleepsyncorbi();
        }
    }
    
    @Step("Test Step 3: Change to router to AP mode")
    public void step3() {
        DevicesOrbiDeviceModePage page = new DevicesOrbiDeviceModePage();
        page.setDeviceMode(false);
        ddp.gotoPage();
        ddp.waitDevicesReConnected(WebportalParam.ob2serialNo);

        ddp.openOB2();
        //new DevicesOrbiDeviceModePage(false).initDeviceMode(true);
        page.gotoPage();
        //DevicesOrbiDeviceModePage page1 = new DevicesOrbiDeviceModePage();
        assertTrue(page.isRouterMode(), "OB is in router mode");
        MyCommonAPIs.sleepsyncorbi();
    }
}
