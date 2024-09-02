package webportal.WebPoratlPerformanceImprovement.PRJCBUGEN_T33367;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
* @ Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Web Portal Performance Improvements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33367") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that manager user can read only access.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T33367") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.loginByUserPassword(WebportalParam.readManagerName,WebportalParam.readManagerPassword);

        handle.gotoLoction();
    }

    @Step("Test Step 2: Verify that user (Admin) is able to create an organization")
    public void step2() {
        OrganizationPage page = new OrganizationPage();
        page.gotoPage();

        assertTrue(!page.AddOrg.exists(), "check Add org must be existed for admin user");
    }

}
