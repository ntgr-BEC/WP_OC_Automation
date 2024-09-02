package webportal.DirectPurchase.NewPremiumUser.PRJCBUGEN_T18376;

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
import util.MyCommonAPIs;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;

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
    @Story("PRJCBUGEN_T18376") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify \"Payment page\" have all four component tab are present") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T18376") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        MyCommonAPIs.sleepsync();
        String url = MyCommonAPIs.getCurrentUrl();
        if (url.contains("/dashboard/account") || url.contains("/#/locked")) {
            new HamburgerMenuPage(false);
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Click login and subscribe button and create account, check billing page;")
    public void step1() {
        new HamburgerMenuPage(false).openInsightSubscriptionPlanPage();
//        new HamburgerMenuPage(false).clickLoginAndSubscribe("premium");

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T18376");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);

        MyCommonAPIs.sleepsync();
        assertTrue(
                new HamburgerMenuPage(false).subscriptionPlanTab.exists() && new HamburgerMenuPage(false).billingInfoTab.exists()
                        && new HamburgerMenuPage(false).paymentInfoTab.exists() && new HamburgerMenuPage(false).orderSummaryTab.exists(),
                "Billing page display incorrect.");
    }

}
