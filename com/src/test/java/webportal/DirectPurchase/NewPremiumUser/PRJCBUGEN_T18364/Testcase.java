package webportal.DirectPurchase.NewPremiumUser.PRJCBUGEN_T18364;

import static org.testng.Assert.assertTrue;

import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.HamburgerMenuPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);

    @Feature("DirectPurchase.NewPremiumUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18364") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify new user has \"Create account\" option") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T18364") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Click login and subscribe button and check jump to login page;")
    public void step1() {
        new HamburgerMenuPage(false).openInsightSubscriptionPlanPage();
//        new HamburgerMenuPage(false).clickLoginAndSubscribe("premium");

        assertTrue(new HamburgerMenuPage(false).createaccount.exists(), "Login page display incorrect.");
    }

}
