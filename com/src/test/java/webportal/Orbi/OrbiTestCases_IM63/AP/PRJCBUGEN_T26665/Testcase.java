package webportal.Orbi.OrbiTestCases_IM63.AP.PRJCBUGEN_T26665;

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
    @Feature("Orbi.OrbiTestCases_IM63") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26665") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify changing the device mode from router mode to AP mode from the drop down on the device mode tab on the orbi device dashboard")
    @TmsLink("PRJCBUGEN-T26665") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
    
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
    
    @Step("Test Step 2: If device is not router mode, change it to router mode")
    public void step2() {
        ddp.openOB2();
        DevicesOrbiDeviceModePage page = new DevicesOrbiDeviceModePage();
        if(!page.isRouterMode()) {
            new DevicesOrbiDeviceModePage(false).initDeviceMode(false);
            MyCommonAPIs.sleepsyncorbi();
        }
    }
    
    @Step("Test Step 3: Change to AP from router mode")
    public void step3() {
        DevicesOrbiDeviceModePage page = new DevicesOrbiDeviceModePage();
        page.setDeviceMode(true);
        ddp.gotoPage();
        ddp.waitDevicesReConnected(WebportalParam.ob2serialNo);

        ddp.openOB2();
        page.gotoPage();
        assertTrue(!page.isRouterMode(), "OB is in AP mode");
        MyCommonAPIs.sleepsyncorbi();
    }
}
