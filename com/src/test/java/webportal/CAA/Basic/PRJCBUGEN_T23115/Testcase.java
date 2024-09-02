package webportal.CAA.Basic.PRJCBUGEN_T23115;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Tejeshwini
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String Currency = "HKD";

    @Feature("CAA") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23115") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Check whether account created with country Hongkong shows the purchase currency symbol in  HKD") // It's a testcase title
                                                                                                                 // from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23115") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account in Sweden success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
//        webportalLoginPage.loginByUserPassword("apwptest8924697@mailinator.com", "Netgear#123");
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T23113");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Hong Kong");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Check buy  basic  subscription in Country South Africa;")
    public void step2() {
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Number of Device Credits", "1");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T17558");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Mong Kok");
        paymentInfo.put("City", "Causeway Bay");
        paymentInfo.put("Zip", "999077");
        paymentInfo.put("Country", "Hong Kong");
        paymentInfo.put("State", "Causeway Bay");
        paymentInfo.put("Card Number", "5193911111111112");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        new HamburgerMenuPage().buyDeviceCredits(paymentInfo);
        assertTrue(new HamburgerMenuPage(false)
                .checkSubscriptionScreen(String.valueOf(Integer.valueOf(paymentInfo.get("Number of Device Credits")) + 2)), "Amount is incorrect.");
        assertTrue(new HamburgerMenuPage(false).checkBasicAnuallyCurrency(Currency), "Currency is inncorrect");
        
    }
    
    @Step("Test Step 3: Purchase One more device credit and check whether it will allow us to add one more device;")
    public void step3() {
        
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
        
        Map<String, String> paymentInfo = new HashMap<String, String>();
        
        paymentInfo.put("Subscription Time", "Yearly");
        paymentInfo.put("Number of Device Credits", "1");
  
        new HamburgerMenuPage(false).BuydeviceCredit(paymentInfo);
        
        assertTrue(new HamburgerMenuPage(false).checkSubscriptionScreen(String.valueOf(Integer.valueOf(paymentInfo.get("Number of Device Credits")) + 0)), "Amount is incorrect.");
        assertTrue(new HamburgerMenuPage(false).checkPremiumAnuallyCurrency(Currency), "Currency is inncorrect");
    }
     
    
    @Step("Test Step 4: Purchase Multipack;")
    public void step4() {
        
        new HamburgerMenuPage().gotoInsightPremiumSubscriptions();
        Map<String, String> paymentInfo = new HashMap<String, String>();

        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T18232");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Device Credits Pack", "5");
        paymentInfo.put("Buy Year", "1");     
        paymentInfo.put("Street Address", "Mong Kok");
        paymentInfo.put("City", "Causeway Bay");
        paymentInfo.put("Zip", "999077");
        paymentInfo.put("Country", "Hong Kong");
        paymentInfo.put("State", "Causeway Bay");
        paymentInfo.put("Card Number", "5193911111111112");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        new InsightServicesPage(false).buyInsightPremiumSubscriptions(paymentInfo);

        assertTrue(new HamburgerMenuPage(false).checkSubscriptionsPage("Insight Premium", paymentInfo.get("Device Credits Pack")),
                "Subscriptions page display error.");
        
        assertTrue(new HamburgerMenuPage(false).checkMultipackCurrency(Currency), "Currency is inncorrect");
        
    }
    
   

}