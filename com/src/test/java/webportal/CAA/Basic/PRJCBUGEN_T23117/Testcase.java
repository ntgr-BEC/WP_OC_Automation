package webportal.CAA.Basic.PRJCBUGEN_T23117;

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
    String Currency = "SGD";

    @Feature("CAA") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23117") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Check whether account created with country Singapore shows the purchase currency symbol in  sgd") // It's a testcase title
                                                                                                                 // from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23117") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account in Singapore success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword("apwptest6839847@mailinator.com", "Netgear#123");
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T23113");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Singapore");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Check buy  basic  subscription in Country Singapore;")
    public void step2() {
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Number of Device Credits", "1");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T17562");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Raffles Avenue");
        paymentInfo.put("City", "Singapore");
        paymentInfo.put("Zip", "39799");
        paymentInfo.put("Country", "Singapore");
        paymentInfo.put("State", "Singapore");
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
    
    @Step("Test Step 4: Create new location")
    public void step4() {
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "39799");
        locationInfo.put("Country", "Singapore");
        new AccountPage().addNetwork(locationInfo);
        
       
    }
    
    @Step("Test Step 5: Purchase ICP subscription;")
    public void step5() {
        
        Map<String, String> CaptivePortalPaymentInfo = new HashMap<String, String>();
        CaptivePortalPaymentInfo.put("First Name", mailname);
        CaptivePortalPaymentInfo.put("Last Name", "T24144");
        CaptivePortalPaymentInfo.put("Email", mailname + "@mailinator.com");
        CaptivePortalPaymentInfo.put("Quantity", "10"); // can input 1 , 3 , 10 , 40
        CaptivePortalPaymentInfo.put("Duration", "3"); // can input 1 , 3
        CaptivePortalPaymentInfo.put("Street Address", "Raffles Avenue");
        CaptivePortalPaymentInfo.put("City", "Singapore");
        CaptivePortalPaymentInfo.put("Zip", "39799");
        CaptivePortalPaymentInfo.put("Country", "Singapore");
        CaptivePortalPaymentInfo.put("State", "Singapore");
        CaptivePortalPaymentInfo.put("Card Number", "5193911111111112");
        CaptivePortalPaymentInfo.put("CVV Number", "123");
        CaptivePortalPaymentInfo.put("Expiration Month", "May");
        CaptivePortalPaymentInfo.put("Expiration Year", "2030");
        new InsightServicesPage().buyCaptivePortalProducts(CaptivePortalPaymentInfo);

        boolean result = false;
       
        result = new HamburgerMenuPage().checkCaptivePortalServicesCredits();
  
        assertTrue(result, "<2> Captive portal services credits is incorrect.");
//        
        assertTrue(new HamburgerMenuPage(false).checkICPCurrency(Currency), "Currency is inncorrect");
      
    }
    
   

}