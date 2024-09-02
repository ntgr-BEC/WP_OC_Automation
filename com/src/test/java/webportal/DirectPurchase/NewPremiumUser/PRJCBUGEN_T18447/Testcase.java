package webportal.DirectPurchase.NewPremiumUser.PRJCBUGEN_T18447;

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
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random              r            = new Random();
    int                 num          = r.nextInt(10000000);
    String              mailname     = "apwptest" + String.valueOf(num);
    Map<String, String> paymentInfo  = new HashMap<String, String>();
    Map<String, String> accountInfo  = new HashMap<String, String>();
    String              locationName = "ntk18447";

    @Feature("DirectPurchase.NewPremiumUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18447") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify if user able to add device credit through existing process") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T18447") // It's a testcase id/link from Jira Test Case.

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
    @Step("Test Step 1: Click login and subscribe button and create new account;")
    public void step1() {
        new HamburgerMenuPage(false).openInsightSubscriptionPlanPage();
//        new HamburgerMenuPage(false).clickLoginAndSubscribe("premium");

        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T18447");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Check premium subscription;")
    public void step2() {
        paymentInfo.put("Subscription Time", "Yearly");
        paymentInfo.put("Number of Device Credits", "2");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T18447");
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
        new InsightServicesPage(false).inputPaymentPage(paymentInfo);
        MyCommonAPIs.sleepsync();
        assertTrue(new HamburgerMenuPage(true).checkDirectPurchaseTableInfo(), "Subscription page incorrect.");
    }

    @Step("Test Step 3: Check locked page;")
    public void step3() {
        
        new  HamburgerMenuPage(false).inputPaymentPage(paymentInfo);
        new InsightServicesPage(false).inputBillingInfo(paymentInfo);
        MyCommonAPIs.sleepi(10);
        new InsightServicesPage(false).inputPaymentInfo(paymentInfo);
        new InsightServicesPage(false).terms();
        new InsightServicesPage(false).clickPlaceOrderButton();
        
        MyCommonAPIs.sleepsync();
        boolean result = false;
        if(new HamburgerMenuPage(false).gotosubscriptionnow.exists()) {
            new HamburgerMenuPage(false).gotosubscriptionnow.click();
            result = true;
        }
        assertTrue(result);
    }

}
