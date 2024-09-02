package webportal.DirectPurchase.ErrorScenarios.PRJCBUGEN_T18227;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.publicstep.UserManage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);
    String              account     = mailname + "@mailinator.com";
    String              password    = "Netgear#123";
    Map<String, String> paymentInfo = new HashMap<String, String>();

    @Feature("DirectPurchase.ErrorScenarios") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18227") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether Basic Free user gets error message when user tries to do Direct Premium / Pro Purchase") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T18227") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        String url = MyCommonAPIs.getCurrentUrl();
        Selenide.close();
        startBrowser();
        WebportalLoginPage webportallogin = new WebportalLoginPage(true);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T18227");
        accountInfo.put("Email Address", account);
        accountInfo.put("Confirm Email", account);
        accountInfo.put("Password", password);
        accountInfo.put("Confirm Password", password);
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);

        assertTrue(new HamburgerMenuPage(true).checkSubscriptionScreenForBasic(), "Subscription status is incorrect.");
    }

    @Step("Test Step 2: Check buy direct purchase;")
    public void step2() {
        UserManage userManage = new UserManage();
        userManage.logout();

        new HamburgerMenuPage(false).openInsightSubscriptionPlanPage();
        new HamburgerMenuPage(false).clickLoginAndSubscribe("premium");

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        if (webportalLoginPage.checkLoginErrorMsg(account, password)) {
            new HamburgerMenuPage(false).openInsightSubscriptionPlanPage();
            new HamburgerMenuPage(false).clickLoginAndSubscribe("pro");

            assertTrue(webportalLoginPage.checkLoginErrorMsg(account, password), "Buy direct purchase failed.");
        } else {
            assertTrue(false, "Buy direct purchase failed.");
        }
    }

}
