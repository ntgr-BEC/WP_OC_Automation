package webportal.DirectPurchase.ExistingPremiumUser.PRJCBUGEN_T19917;

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
import webportal.weboperation.InsightServicesPage;
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

    @Feature("DirectPurchase.ExistingPremiumUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T19917") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify user able to purchase only 1 device credit using the Direct purchase") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T19917") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        String url = MyCommonAPIs.getCurrentUrl();
        if (!url.contains("dashboard")) {
            Selenide.close();
            startBrowser();
            WebportalLoginPage webportallogin = new WebportalLoginPage(true);
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T19917");
        accountInfo.put("Email Address", account);
        accountInfo.put("Confirm Email", account);
        accountInfo.put("Password", password);
        accountInfo.put("Confirm Password", password);
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Check account try trial in subscriptions page;")
    public void step2() {
        paymentInfo.put("Subscription Time", "Yearly");
        paymentInfo.put("Number of Device Credits", "4");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T19917");
        paymentInfo.put("Email", account);
        paymentInfo.put("Street Address", "Street 4568 James Avenue");
        paymentInfo.put("City", "INVERNESS");
        paymentInfo.put("Zip", "34451");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        paymentInfo.put("Card Number", "4112344112344113");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");

        assertTrue(new HamburgerMenuPage().checkAccountTryTrial(), "Account try trial unsuccessful.");
    }

    @Step("Test Step 3: Buy premium subscription;")
    public void step3() {
        UserManage userManage = new UserManage();
        userManage.logout();

        new HamburgerMenuPage(false).openInsightSubscriptionPlanPage();
//        new HamburgerMenuPage(false).clickLoginAndSubscribe("premium");

        new WebportalLoginPage(true).inputLogin(account, password);

        new InsightServicesPage(false).inputPaymentPage(paymentInfo);
        MyCommonAPIs.sleepsync();

        String url = MyCommonAPIs.getCurrentUrl();
        if (url.contains("dashboard")) {
            assertTrue(MyCommonAPIs.getText(new HamburgerMenuPage(true).deviceredsubscriptionNew).equals("1"),
                    "Buy premium subscription unsuccessful.");
        } else {
            assertTrue(false, "Place order failed.");
        }
    }

}
