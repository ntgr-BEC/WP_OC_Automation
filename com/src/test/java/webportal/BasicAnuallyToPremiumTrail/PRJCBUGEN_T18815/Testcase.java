package webportal.BasicAnuallyToPremiumTrail.PRJCBUGEN_T18815;

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

    @Feature("BasicAnuallyToPremiumTrail") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17512") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Basic Anually to premium trail Subscription for  Norway country") // It's a testcase title
                                                                                                                 // from Jira Test Case.
    @TmsLink("PRJCBUGEN_T17512") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation("OnBoardingTest");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account in Norway success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T17512");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Norway");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Check buy  basic subscription in Country Norway;")
    public void step2() {
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Number of Device Credits", "1");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T17543");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Storgaten ");
        paymentInfo.put("City", "Tromso");
        paymentInfo.put("Zip", "9008");
        paymentInfo.put("Country", "Norway");
        paymentInfo.put("State", "Tromso");
        paymentInfo.put("Card Number", "5193911111111112");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        new HamburgerMenuPage().buyDeviceCredits(paymentInfo);
        assertTrue(new HamburgerMenuPage(false)
                .checkSubscriptionScreen(String.valueOf(Integer.valueOf(paymentInfo.get("Number of Device Credits")) + 2)), "Amount is incorrect.");
    }
    
    @Step("Test Step 3: Check account try trial;")
    public void step3() {
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
        assertTrue(new HamburgerMenuPage().checkAccountTryTrial());
    }
    
    @Step("Test Step 4: Create new location")
    public void step4() {
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "9008");
        locationInfo.put("Country", "Norway");
        new AccountPage().addNetwork(locationInfo);
        
       
    }
    
    @Step("Test Step 5: Add device and check whether it throws error while adding second device;")
    public void step5() {
        
        new AccountPage().enterLocation("OnBoardingTest");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("Serial Number2", WebportalParam.ap6serialNo);
        firststdevInfo.put("Serial Number3", WebportalParam.ap7serialNo);
        firststdevInfo.put("Serial Number4", WebportalParam.ap8serialNo);
        System.out.println("teju size of"+firststdevInfo.size()+"::::"+ firststdevInfo );
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        
        boolean result = true;
        
        new MyCommonAPIs().open(URLParam.hrefDevices, true);
        assertTrue(new DevicesDashPage().checkNumberOfDevices().equals("Fourdevice"), "More device exits");
      
    }

}