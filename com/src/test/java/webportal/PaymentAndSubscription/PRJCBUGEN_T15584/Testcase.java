package webportal.PaymentAndSubscription.PRJCBUGEN_T15584;

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
import webportal.publicstep.UserManage;
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
    @Story("PRJCBUGEN_T15584") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Check proper currency symbol and code is displayed based on the country selected.") // It's a testcase title
                                                                                                      // from Jira Test Case.
    @TmsLink("PRJCBUGEN-T15584") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account for US success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T15584");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Buy premium subscription;")
    public void step2() {
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Subscription Time", "Yearly");
        paymentInfo.put("Number of Device Credits", "5");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T15584");
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
        if (new HamburgerMenuPage().checkSubscriptionScreen(paymentInfo.get("Number of Device Credits"))) {
            assertTrue(new HamburgerMenuPage().checkCurrencySymbolAndCode("$", paymentInfo.get("Zip"), paymentInfo.get("Country")),
                    "Currency symbol displayed is incorrect.");
        } else {
            assertTrue(false, "Currency symbol displayed is incorrect.");
        }
    }

    @Step("Test Step 3: Create new account in Australia, then buy premium subscription;")
    public void step3() {
        UserManage userManage = new UserManage();
        userManage.logout();

        Random r = new Random();
        int num = r.nextInt(10000000);
        String mailname = "apwptest" + String.valueOf(num);
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T15584");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Australia");

        new HamburgerMenuPage(false).createAccount(accountInfo);

        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Subscription Time", "Yearly");
        paymentInfo.put("Number of Device Credits", "6");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T15584");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "street Pork street");
        paymentInfo.put("City", "Sydney");
        paymentInfo.put("Zip", "2000");
        paymentInfo.put("Country", "Australia");
        paymentInfo.put("State", "New South Wales");
        paymentInfo.put("Card Number", "4112344112344113");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        new HamburgerMenuPage(false).upgradeSubscription(paymentInfo);
        if (new HamburgerMenuPage().checkSubscriptionScreen(paymentInfo.get("Number of Device Credits"))) {
            assertTrue(new HamburgerMenuPage().checkCurrencySymbolAndCode("A$", paymentInfo.get("Zip"), "AU"),
                    "Currency symbol displayed is incorrect.");
        } else {
            assertTrue(false, "Currency symbol displayed is incorrect.");
        }
    }

}
