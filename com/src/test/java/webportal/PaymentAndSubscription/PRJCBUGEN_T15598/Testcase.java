package webportal.PaymentAndSubscription.PRJCBUGEN_T15598;

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

    @Feature("PaymentAndSubscription") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15598") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify user is able to migrate Subscription plan from Trial Free to Premium Monthly and then to Premium Yearly") // It's a
                                                                                                                                           // testcase
                                                                                                                                           // title
                                                                                                                                           // from
                                                                                                                                           // Jira
                                                                                                                                           // Test
    // Case.
    @TmsLink("PRJCBUGEN-T15598") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account in United States success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T15598");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

//    @Step("Test Step 2: Check account try trial in subscriptions page;")
//    public void step2() {
//        assertTrue(new HamburgerMenuPage().checkAccountTryTrial(), "Account try trial unsuccessful.");
//    }

    @Step("Test Step 3: Buy monthly premium subscription, then change subscription to yearly;")
    public void step3() {
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Subscription Time", "Monthly");
        paymentInfo.put("Number of Device Credits", "4");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T15598");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Street 4568 James Avenue");
        paymentInfo.put("City", "INVERNESS");
        paymentInfo.put("Zip", "34451");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        paymentInfo.put("Card Number", "4112344112344113");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        new HamburgerMenuPage(false).upgradeSubscription(paymentInfo);

        if (new HamburgerMenuPage(false)
                .checkMonthlySubscriptionScreen(String.valueOf(Integer.valueOf(paymentInfo.get("Number of Device Credits"))))) {
            paymentInfo.put("Subscription Time", "Yearly");
            new HamburgerMenuPage(false).changePlanToPremium(paymentInfo);
            assertTrue(new HamburgerMenuPage(false).checkSubscriptionScreen(paymentInfo.get("Number of Device Credits")),
                    "Buy premium subscription unsuccessful.");
        } else {
            assertTrue(false);
        }
    }

}
