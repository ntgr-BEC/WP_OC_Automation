package webportal.TwoFA.PRJCBUGEN_T6189;

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

    @Feature("TwoFA") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6189") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify how many devices an user able to add in trusted device list") // It's a testcase title
                                                                                       // from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6189") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new HamburgerMenuPage().disableTwoFA();

        // String[] oldphonenum = {
        // "2059002006", "6262130471", "8183013902"
        // };
        // for (int i = 0; i < oldphonenum.length; i++) {
        // new HamburgerMenuPage().deleteTwoFAPhonenum(oldphonenum[i]);
        // }
        // new HamburgerMenuPage(false).backToDashboardPage();

        new HamburgerMenuPage().deleteAllTwoFAPhonenumWithoutPrimary();

        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        // handle.gotoLoction();
    }

    @Step("Test Step 2: Enable 2FA and should be able to 4 SMS factors;")
    public void step2() {
        String[] oldphonenum = {
                "7015168317", "7077190993", "8044064234"
        };
        new HamburgerMenuPage().enableTwoFA(oldphonenum[0], WebportalParam.CountryOTP);

        for (int i = 0; i < oldphonenum.length; i++) {
            new HamburgerMenuPage().addTwoFAPhonenum(oldphonenum[i], WebportalParam.CountryOTP);
        }

        assertTrue(new HamburgerMenuPage().checkPhonenumMaxLimit("6203221059"), "Add 5 SMS factors successful.");
    }
}
