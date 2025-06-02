package webportal.TwoFA.PRJCBUGEN_T6188;

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

    @Feature("TwoFA") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6188") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether user able to change the Primary trusted devices") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6188") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new HamburgerMenuPage().addTwoFAPhonenumAndChangePrimary("7015168317", "5417083275", WebportalParam.CountryOTP);
        new HamburgerMenuPage().disableTwoFA();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        // handle.gotoLoction();
    }

    @Step("Test Step 2: Enable 2FA and change primary phone number,check login is correct;")
    public void step2() {
        String oldphonenum = "485906541";
        String newphonenum = "7015168317";
        new HamburgerMenuPage().enableTwoFA(oldphonenum, WebportalParam.CountryOTP);
        new HamburgerMenuPage().addTwoFAPhonenumAndChangePrimary(oldphonenum, newphonenum, WebportalParam.CountryOTP);

        UserManage userManage = new UserManage();
        userManage.logout();

        new HamburgerMenuPage(false).checkTwoFAIsCorrect(newphonenum, true, WebportalParam.CountryOTP);

        boolean result = new HamburgerMenuPage().checkLoginSuccessful();

        assertTrue(result, "Login successful");
    }

}
