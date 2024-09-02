package webportal.DirectPurchase.NewProUser.PRJCBUGEN_T18327;

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
    Map<String, String> accountInfo = new HashMap<String, String>();

    @Feature("DirectPurchase.NewProUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18327") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify Direct Purchase at Purchase Order History Update for one year") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T18327") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        Selenide.close();
        startBrowser();
        WebportalLoginPage webportallogin = new WebportalLoginPage(true);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create new account;")
    public void step1() {
        new HamburgerMenuPage(false).openInsightSubscriptionPlanPage();
        new HamburgerMenuPage(false).clickLoginAndSubscribe("pro");

        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T18327");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Buy pro subscription;")
    public void step2() {
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T18327");
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
        MyCommonAPIs.sleepi(15);

        Map<String, String> businessInfo = new HashMap<String, String>();
        businessInfo.put("Business Name", "Netgear");
        businessInfo.put("Primary Address of Business", "test 1st");
        businessInfo.put("City", "NewYork");
        businessInfo.put("State", "test");
        businessInfo.put("Zip Code", "12345");
        businessInfo.put("Country", "United States of America");
        businessInfo.put("Business Phone Number", "1234567890");
        new HamburgerMenuPage(false).inputBusinessInfo(businessInfo);
        new HamburgerMenuPage(false).clickBusinessInfoPageButton();
        MyCommonAPIs.sleepsync();

        assertTrue(new HamburgerMenuPage().checkPremiunDirectPurchaseCorrect(), "Purchase order history incorrect.");
    }

}
