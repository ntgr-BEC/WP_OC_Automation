package webportal.PremiumTrailToPremiumAnnually.PRJCBUGEN_T18750;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.PostManPage;
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

    @Feature("PremiumTrailToPremiumAnnually") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18750") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Buying Premium Trail to Premium Yearly Subscription for Denmark country") // It's a testcase title
                                                                                                      // from Jira Test Case.
    @TmsLink("PRJCBUGEN_T18750") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @BeforeMethod(alwaysRun = true)
    public void tearUp() {
       
       new PostManPage().Deregister(WebportalParam.ap1serialNo);
       new PostManPage().Deregister(WebportalParam.ap2serialNo);
       new PostManPage().Deregister(WebportalParam.ap3serialNo);
       new PostManPage().Deregister(WebportalParam.ap4serialNo);
        
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println(mailname + "@mailinator.com");
        new AccountPage().deleteOneLocation("OnBoardingTest");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T17523");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Denmark");


        new HamburgerMenuPage(false).createAccount(accountInfo);
    }
    
    
//    @Step("Test Step 2: Check account try trial;")
//    public void step2() {
//        assertTrue(new HamburgerMenuPage().checkAccountTryTrial());
//    }
    
    @Step("Test Step 3: Create new location")
    public void step3() {
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "5000");
        locationInfo.put("Country", "Denmark");
        new HamburgerMenuPage();
        new AccountPage().addNetwork(locationInfo);
        
       
    }
    
    @Step("Test Step 4: Add device To the Network;")
    public void step4() {
        
 new AccountPage().enterLocation("OnBoardingTest");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        Map<String, String> firststdevInfo1 = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap1serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap1macaddress);
        firststdevInfo1.put("Serial Number1", WebportalParam.ap2serialNo);
        firststdevInfo1.put("MAC Address1", WebportalParam.ap2macaddress);
        
        System.out.println(firststdevInfo);
        System.out.println(firststdevInfo1);
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo1);
        
        assertTrue(new DevicesDashPage().checkNumberOfDevices().equals("Twodevice"), "More device exits");
        
        new AccountPage().enterLocation("OnBoardingTest");
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);
             
    }

    @Step("Test Step 5: chnage premium Trail to Premium Yearly;")
    public void step5() {
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo = new CommonDataType().CARD_INFO;
        paymentInfo.put("Subscription Time", "Yearly");
        paymentInfo.put("Number of Device Credits", "2");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T17519");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Ostre Stationsvej");
        paymentInfo.put("City", "Odense");
        paymentInfo.put("Zip", "5000");
        paymentInfo.put("Country", "Denmark");
        paymentInfo.put("State", "Odense");
       
        new HamburgerMenuPage(false).upgradeSubscription(paymentInfo);
        assertTrue(new HamburgerMenuPage().checkSubscriptionScreen(String.valueOf(Integer.valueOf(paymentInfo.get("Number of Device Credits")) + 1)), "Amount is incorrect.");
    }
            
    
    @Step("Test Step 6: Add device and check whether it throws error while adding Thisrd device;")
    public void step6() {
        
   new AccountPage().enterLocation("OnBoardingTest");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap3serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap3macaddress);
       
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        
        Map<String, String> SeconddevInfo = new HashMap<String, String>();
        
        SeconddevInfo.put("Serial Number1", WebportalParam.ap4serialNo);
        SeconddevInfo.put("MAC Address1", WebportalParam.ap4macaddress);
        new DevicesDashPage(false).addNewdummyDevice(SeconddevInfo);
        
        new AccountPage().enterLocation("OnBoardingTest");
        assertTrue(new DevicesDashPage(false).isDeviceNotUnmanaged(WebportalParam.ap4serialNo), "More device exits");
      
    }

    
    @Step("Test Step 7: cancel Subscription")
    public void step7() {
        
      new HamburgerMenuPage(false).cancelSubscription();     
      assertTrue(new HamburgerMenuPage().CancelSubscriptionformpremiumanually(), "did not cancel");
        
    }
}
