package webportal.PROPreInvitingUser.SecondaryAdmin.PRJCBUGEN_T40766;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.ManagerPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @Pratik
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);

    @Feature("PROPreInvitingUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40766") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the user is able to see the Secondary Admin account as active in the PRO admin Secondary Admin list.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40766") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

    }

    @Step("Test Step 2: VerifyExisting Manager;")
    public void step2() {

        assertTrue(new ManagerPage().verifyExistingSecondaryAdmin(WebportalParam.SecondadminName),"Invite sec admin failed.");

    }

}
