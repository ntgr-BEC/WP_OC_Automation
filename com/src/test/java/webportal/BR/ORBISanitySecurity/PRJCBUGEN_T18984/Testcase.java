package webportal.BR.ORBISanitySecurity.PRJCBUGEN_T18984;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.GlobalNotificationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Orbi Security sanity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18984") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Remove  Orbi") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T18984") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoctionOrbi();
        
        new DevicesDashPage().checkDeviceInAdminAccountorbi();
    }
    
    @Step("Test Step 2: Remove Orbi")
    public void step2() {
        new DevicesDashPage(false).gotoPage();
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob1serialNo);
        MyCommonAPIs.sleep(10 * 1000);
        assertTrue(new GlobalNotificationPage().ConformORbideleted(WebportalParam.ob1deveiceName), "Device is not deleted");
    }
    
}