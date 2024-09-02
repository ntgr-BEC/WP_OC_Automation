package webportal.APBasic.pro_manager.PRJCBUGEN_T14290;

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
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("APBasic.pro_manager") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14290") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify share diagnostics functionally for pro manager") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14290") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.loginByUserPassword(WebportalParam.managerName,WebportalParam.managerPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("manager"); //must input admin or manager
    }

    @Step("Test Step 2: click on  share diagnostics at top enter valid email and click on sent,check alert;")
    public void step2() {
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);

        new DevicesApSummaryPage().shareEmail("apwptest@mailinator.com");

        assertTrue(new DevicesApSummaryPage().checkAlertIsExist(), "Cannot share diagnostics from email");

    }

}
