package webportal.BR.switchSanitySecurity.PRJCBUGEN_T18981;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.GlobalNotificationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Switch Security Sanity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18981") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify share diagnostics functionally for switch as non-pro admin") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T18981") // It's a testcase id/link from Jira Test Case.

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

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccountswitch();
    }

    @Step("Test Step 2: Click on x on switch under device tab;")
    public void step2() {
        
     new WiredQuickViewPage().checkswitchIsExist(WebportalParam.sw1deveiceName);
        
        
        handle.gotoLoction();
        ddp.gotoPage();
        ddp.enterDevice(WebportalParam.sw1serialNo);
        
        new GlobalNotificationPage().ShareDiognastic("apwptest@mailinator.com");

       // assertTrue(new DevicesApSummaryPage().checkAlertIsExist(), "Cannot share diagnostics from email");
        
        
        
    }

}



