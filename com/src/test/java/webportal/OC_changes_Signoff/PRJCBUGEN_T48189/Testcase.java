package webportal.OC_changes_Signoff.PRJCBUGEN_T48189;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("OC_changes_Signoff") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T48189") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Logout of pro account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T48189") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);   

        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
    }

    @Step("Test Step 2: In the hamburger menu, click on the \"Logout\" button, check logout;")
    public void step2() {
        boolean result = new HamburgerMenuPage().checkLogout();
        assertTrue(result);
        if (result) {
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);  
        }
    }

}
