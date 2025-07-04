package webportal.OC_changes_Signoff.PRJCBUGEN_T48201;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import webportal.weboperation.PostManPage;
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
import webportal.weboperation.WebportalLoginPage;


/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {
    
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);

    @Feature("OC_changes_Signoff") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T48201") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Purchase of Premium subscription plan (premium trial to premium monthly)") // It's a testcase title
                                                                                                      // from Jira Test Case.
    @TmsLink("PRJCBUGEN_T48201") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void tearUp() {
       
       new PostManPage().Deregister(WebportalParam.ap5serialNo);
       new PostManPage().Deregister(WebportalParam.ap6serialNo);
       new PostManPage().Deregister(WebportalParam.ap7serialNo);
       new PostManPage().Deregister(WebportalParam.ap8serialNo);
        
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
        accountInfo.put("Country", "Australia");


        new HamburgerMenuPage(false).createAccount(accountInfo);
        
        
   
    }
    
    
    @Step("Test Step 2: Check account free trial;")
    public void step2() {
        assertTrue(new HamburgerMenuPage(false).checkAccountTryTrial());
        new HamburgerMenuPage(false).expandinsigtdivCreditsSection();
        new HamburgerMenuPage(false).verifyfreetrailOnPurchaseOrderHistoryPage();       
    }
    
    @Step("Test Step 3: Create new location")
    public void step3() {
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "Australia");
//        new HamburgerMenuPage(false);
        new AccountPage().addNetwork(locationInfo);
    }
    
    @Step("Test Step 4: Add device To the Network;")
    public void step4() {
        
 new AccountPage().enterLocation("OnBoardingTest");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        Map<String, String> firststdevInfo1 = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        firststdevInfo1.put("Serial Number1", WebportalParam.ap6serialNo);
        firststdevInfo1.put("MAC Address1", WebportalParam.ap6macaddress);
        
        System.out.println(firststdevInfo);
        System.out.println(firststdevInfo1);
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo1);
        
        assertTrue(new DevicesDashPage().checkNumberOfDevices().equals("Twodevice"), "More device exits");
        new AccountPage().enterLocation("OnBoardingTest");
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap5serialNo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap6serialNo);
      
    }
    

    @Step("Test Step 5: change premium Trail to Premium Monthly;")
    public void step5() {
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo = new CommonDataType().CARD_INFO;
        paymentInfo.put("Subscription Time", "Monthly");
        paymentInfo.put("Number of Device Credits", "2");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T17512");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Main Street");
        paymentInfo.put("City", "Montville");
        paymentInfo.put("Zip", "4560");
        paymentInfo.put("Country", "Australia");
        paymentInfo.put("State", "Queensland");
        new HamburgerMenuPage(false).upgradeSubscription(paymentInfo);
        new AccountPage().enterLocation("OnBoardingTest");
        assertTrue(new HamburgerMenuPage(false).verifyInsightPageData(String.valueOf(Integer.valueOf(paymentInfo.get("Number of Device Credits")) + 1)), "Amount is incorrect.");
    }
    
    @Step("Test Step 6: Add device and check whether it throws error while adding Thisrd device;")
    public void step6() {
        
        new AccountPage().enterLocation("OnBoardingTest");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap7serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap7macaddress);
       
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        
        Map<String, String> SeconddevInfo = new HashMap<String, String>();
        
        SeconddevInfo.put("Serial Number1", WebportalParam.ap8serialNo);
        SeconddevInfo.put("MAC Address1", WebportalParam.ap8macaddress);
        new DevicesDashPage(false).addNewdummyDevice(SeconddevInfo);
        
        new AccountPage().enterLocation("OnBoardingTest");
        assertTrue(new DevicesDashPage(false).isDeviceNotUnmanaged(WebportalParam.ap8serialNo), "More device exits");
        assertTrue(new HamburgerMenuPage(false).verifyFourDevicesOnboardedSubscriptionPage(), "total four insight devices are not showing correctly on subscription page");
        
    }

    
    @Step("Test Step 7: cancel Subscription")
    public void step7() {
        
        new HamburgerMenuPage(false).cancelSubscription();     
        assertTrue(new HamburgerMenuPage().CancelSubscriptionformpremiumanually(), "did not cancel");
        
    }
    
}
