package webportal.AP.AirBridge.PRJCBUGEN_T18427;

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
    @Feature("AP.AirBridge") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18427") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the Airbridge device can be rebooted from the Web Portal") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T18427") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login into IM WP page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: 5. On the device dashboard, click on the reboot icon")
    public void step2() {
        ddp.gotoPage();
        String predate = ddp.getDeviceUptime(WebportalParam.ap1serialNo, true);
        ddp.rebootDevice(WebportalParam.ap1serialNo);

        ddp.gotoPage();
        ddp.waitDevicesReConnected(WebportalParam.ap1serialNo);

        ddp.gotoPage();
        String curdate = ddp.getDeviceUptime(WebportalParam.ap1serialNo, true);
        assertTrue(Integer.parseInt(predate) > Integer.parseInt(curdate), "check reboot is done");
    }
}
