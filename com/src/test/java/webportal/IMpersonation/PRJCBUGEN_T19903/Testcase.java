package webportal.IMpersonation.PRJCBUGEN_T19903;

import static com.codeborne.selenide.Selenide.$x;
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
import webportal.publicstep.UserManage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("IMpersonation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T19903") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the functionality of the \"End Access\" button") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T19903") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        UserManage userManage = new UserManage();
        userManage.logout();

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

        new HamburgerMenuPage().grantAccessToSupport();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminSupportUser, WebportalParam.loginPassword);
    }

    @Step("Test Step 2: Check \"End Access\" button;")
    public void step2() {
        new HamburgerMenuPage(false).endAccessSupportUser(WebportalParam.loginName);
        

        assertTrue(!$x(String.format(new HamburgerMenuPage(false).supportRequestTableEmail, WebportalParam.loginName)).exists(),
                "Remove account failed.");
    }

}
