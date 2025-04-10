package webportal.PremiumMonthlyToPremiumYearly.PRJCBUGEN_T25191;

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
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.PostManPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);

    @Feature("PremiumMonthlyToPremiumYearly") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25191") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Buying Premium Monthly To Premium Yearly Subscription for Norway country") // It's a testcase title
                                                                                                                 // from Jira Test Case.
    @TmsLink("PRJCBUGEN_T25191") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void tearUp() {
       
       new PostManPage().Deregister(WebportalParam.ap1serialNo);
        
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
        accountInfo.put("Last Name", "T15598");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Norway");
 
        new HamburgerMenuPage(false).createAccount(accountInfo);
        assertTrue(new HamburgerMenuPage(false).checkAccountTryTrial());
        new HamburgerMenuPage(false).expandinsigtdivCreditsSection();
        new HamburgerMenuPage(false).verifyfreetrailOnPurchaseOrderHistoryPage();
        
    }
    
    @Step("Test Step 2: Create new location")
    public void step2() {
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "Australia");
        new HamburgerMenuPage();
        new AccountPage().addNetwork(locationInfo);
    }
    
    
    @Step("Test Step 3: Add device To the Network;")
    public void step3() {
        
        new AccountPage().enterLocation("OnBoardingTest");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
       
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap1serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap1macaddress);
        
        System.out.println(firststdevInfo);
 
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        new AccountPage().enterLocation("OnBoardingTest");
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
      
    }


    @Step("Test Step 4: Buy monthly premium subscription, then change subscription to yearly;")
    public void step4() {
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo = new CommonDataType().CARD_INFO;
        paymentInfo.put("Subscription Time", "Monthly");
        paymentInfo.put("Number of Device Credits", "4");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T17544");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Storgaten ");
        paymentInfo.put("City", "Tromso");
        paymentInfo.put("Zip", "9008");
        paymentInfo.put("Country", "Norway");
        paymentInfo.put("State", "Tromso");
      
        new HamburgerMenuPage(false).upgradeSubscription(paymentInfo);
        new AccountPage().enterLocation("OnBoardingTest");
        assertTrue(new HamburgerMenuPage(false).verifyInsightPageData(String.valueOf(Integer.valueOf(paymentInfo.get("Number of Device Credits")) + 1)), "Amount is incorrect.");
        paymentInfo.put("Subscription Time", "Yearly");
        new HamburgerMenuPage(false).changePlanToPremium(paymentInfo);
        new AccountPage().enterLocation("OnBoardingTest");
        assertTrue(new HamburgerMenuPage(false).verifyInsightPageData(String.valueOf(Integer.valueOf(paymentInfo.get("Number of Device Credits")) + 1)), "Amount is incorrect.");
       

        
        } 
    
    @Step("Test Step 5: cancel Subscription")
    public void step5() {
        
      new HamburgerMenuPage(false).cancelSubscription();     
      assertTrue(new HamburgerMenuPage().CancelSubscriptionformpremiumanually(), "did not cancel");
    }

}
