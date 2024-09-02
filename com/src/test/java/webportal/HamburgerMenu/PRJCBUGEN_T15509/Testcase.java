package webportal.HamburgerMenu.PRJCBUGEN_T15509;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("HamburgerMenu") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15509") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the \"push/e-mail notifications\" of all the network locations in a account ") // It's a testcase title from Jira
                                                                                                                // Test Case.
    @TmsLink("PRJCBUGEN-T15509") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (new HamburgerMenuPage().checkDefaultNotificationsStatus()) {
            new HamburgerMenuPage().disableOrEnableEmailAndPushAlertsNotifications("disable");
        } else {
            new HamburgerMenuPage().disableOrEnableEmailAndPushAlertsNotifications("enable");
        }
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Check default email notifications switch (it should be enabled) and then we are disable it in step 2;")
    public void step2() {
        assertTrue(!new HamburgerMenuPage().checkEmailNotifications(), "Notifications status is incorrect.");
    }

    @Step("Test Step 3: Disable the push notification for the networks in the account,check email notifications switch;")
    public void step3() {
        if (new HamburgerMenuPage().checkDefaultNotificationsStatus()) {
            System.out.println("enterd 1st");
            new HamburgerMenuPage().disableOrEnableEmailAndPushAlertsNotifications("enable");
        } else {
            System.out.println("enterd 2st");
            new HamburgerMenuPage().disableOrEnableEmailAndPushAlertsNotifications("disable");
        }

        assertTrue(!new HamburgerMenuPage().checkEmailNotifications(), "Notifications status is incorrect.");
    }

}
