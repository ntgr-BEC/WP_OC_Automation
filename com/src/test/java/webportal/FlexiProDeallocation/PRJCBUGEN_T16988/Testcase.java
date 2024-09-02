package webportal.FlexiProDeallocation.PRJCBUGEN_T16988;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("FlexiProDeallocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16988") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify organisation manager have option to \"Deallocate\" in account") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16988") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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
        webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);
    }

    @Step("Test Step 2: Check credits allocate page with manager account;")
    public void step2() {
        new HamburgerMenuPage().gotoCreditsAllocationPage();
        assertTrue(!new HamburgerMenuPage(false).deallocate.exists(), "Credits allocate page display error.");
    }

}
