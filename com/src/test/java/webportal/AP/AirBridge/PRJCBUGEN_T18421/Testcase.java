package webportal.AP.AirBridge.PRJCBUGEN_T18421;

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
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String aAP      = null;
    String toVerify = "";

    @Feature("AP.AirBridge") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18421") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Bridge device onboarding with Serial # entry & with Air Bridge Group Creation.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T18421") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        aAP = handle.getFakeDeviceNo("WAC502");
        toVerify = WebportalParam.getLocText("im5.6Keys", "addNewAirBridgeGroup");
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
        ddp.gotoPage();
    }
    
    @Step("Test Step 2: 3. Click on the plus icon and add device via serial number")
    public void step2() {
        ddp.gotoPage();
        ddp.clickAddDevice();
        MyCommonAPIs.waitReady();
        ddp.serialNo.sendKeys(aAP);
        MyCommonAPIs.waitReady();
        ddp.addDeviceBtn.click();
        MyCommonAPIs.waitReady();
        MyCommonAPIs.sleepi(10);
        assertTrue(handle.pageSource().contains(toVerify), "verify there will be a ab group button");
        handle.clickBoxFirstButton();
    }
}
