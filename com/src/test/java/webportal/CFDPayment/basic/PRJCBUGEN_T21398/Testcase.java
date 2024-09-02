package webportal.CFDPayment.basic.PRJCBUGEN_T21398;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.weboperation.AccountPage;
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
    Map<String, String> paymentInfo = new HashMap<String, String>();

    @Feature("CFDPayment") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T21398") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Basic Free --> Basic Paid --> Premium yearly --> Multipack --> Cancel Premium Yearly") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T21398") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T21398");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Buy device credits and change to yearly premium subscription;")
    public void step2() {
        new HamburgerMenuPage(true).checkSubscriptionScreenForBasic();
        if (new HamburgerMenuPage(true).checkSubscriptionScreenForBasic()) {
            paymentInfo.put("Subscription Time", "Yearly");
            paymentInfo.put("Number of Device Credits", "1");
            paymentInfo.put("First Name", mailname);
            paymentInfo.put("Last Name", "T21398");
//            paymentInfo.put("Email", mailname + "@mailinator.com");
            paymentInfo.put("Street Address", "Street 4568 James Avenue");
            paymentInfo.put("City", "INVERNESS");
            paymentInfo.put("Zip", "34451");
            paymentInfo.put("Country", "US");
            paymentInfo.put("State", "Florida");
            paymentInfo.put("Card Number", "4112344112344113");
            paymentInfo.put("CVV Number", "123");
            paymentInfo.put("Expiration Month", "May");
            paymentInfo.put("Expiration Year", "2030");
            new AccountPage();
            new HamburgerMenuPage().buyDeviceCredits(paymentInfo);

            boolean result = new HamburgerMenuPage().checkDevicesBilling(paymentInfo.get("Number of Device Credits"), "basic", "US");
            new HamburgerMenuPage(false).changePlanToPremium(paymentInfo);

            assertTrue(result && new HamburgerMenuPage(false).checkSubscriptionScreenForPremium(), "Premium subscription type incorrect.");
        } else {
            assertTrue(false, "Subscription type incorrect.");
        }
    }

    @Step("Test Step 3:  Buy insight premium subscription and cancel subscription;")
    public void step3() {
        new HamburgerMenuPage().gotoInsightPremiumSubscriptions();
        paymentInfo.put("Device Credits Pack", "5");
        paymentInfo.put("Buy Year", "1");
        new InsightServicesPage(false).buyInsightPremiumSubscriptions(paymentInfo);

        SelenideElement ele = $x(new HamburgerMenuPage(false).deletecurrentdevicecredit);

        if (!MyCommonAPIs.getText(ele).contains("Use this table to view all current device credit packs for your Insight Premium account.")) {
            new HamburgerMenuPage(false).cancelSubscription();
            assertTrue(
                    MyCommonAPIs.getText(new HamburgerMenuPage(false).subscriptionTableCanceled).contains("Cancelled") && !MyCommonAPIs
                            .getText(ele).contains("Use this table to view all current device credit packs for your Insight Premium account."),
                    "Subscription type incorrect.");
        } else {
            assertTrue(false, "Subscription type incorrect.");
        }
    }

}
