package webportal.BasicToBasicAnnually.PRJCBUGEN_T18556;

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
 * @author Tejeshwini
 *
 */
public class Testcase extends TestCaseBase {
    
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);

    @Feature("BasicToBasicAnnually") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18556") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Buying Basic paid Subscription for Italy country (one device)") // It's a testcase title
                                                                                                      // from Jira Test Case.
    @TmsLink("PRJCBUGEN_T18556") // It's a testcase id/link from Jira Test Case.

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
        accountInfo.put("Last Name", "T17536");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Italy");



        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Buy devices number by basic plan;")
    public void step2() {
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Number of Device Credits", "1");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T17536");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Via Bari");
        paymentInfo.put("City", "Palermo");
        paymentInfo.put("Zip", "90133");
        paymentInfo.put("Country", "Italy");
        paymentInfo.put("State", "Palermo");
        paymentInfo.put("Card Number", "5193911111111112");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        new HamburgerMenuPage().buyDeviceCredits(paymentInfo);
        assertTrue(new HamburgerMenuPage(false)
                .checkSubscriptionScreen(String.valueOf(Integer.valueOf(paymentInfo.get("Number of Device Credits")) + 2)), "Amount is incorrect.");
    }
    
    @Step("Test Step 3: cancel Subscription")
    public void step3() {
      
      new HamburgerMenuPage(false).cancelSubscription();     
      assertTrue(new HamburgerMenuPage().CancelSubscriptionformpremiumanually(), "did not cancel");
    
        
    }
            
}
