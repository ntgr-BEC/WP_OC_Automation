package webportal.CAA.PRJCBUGEN_T24561;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.PostManPage;
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
    String Currency = "HU";
    String MonthlyAmount = "303"; 
    String YearlyAmount = "3057"; 

    @Feature("CAA") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24561") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Check whether Device subscription cost for the local currency account is proper (Hungary)") // It's a testcase title
                                                                                                                 // from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24561") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void tearUp() {
       
       new PostManPage().Deregister(WebportalParam.ap5serialNo);
        
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation("OnBoardingTest");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account in Poland success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
//        webportalLoginPage.loginByUserPassword("apwptest2778734@mailinator.com", "Netgear#123");
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T23126");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Hungary");

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
        //new HamburgerMenuPage();
        new AccountPage().addNetwork(locationInfo);
    }
    
    
    @Step("Test Step 3: Add device To the Network;")
    public void step3() {
        
        new AccountPage().enterLocation("OnBoardingTest");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
       
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        
        System.out.println(firststdevInfo);
 
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        new AccountPage().enterLocation("OnBoardingTest");
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap5serialNo);
      
    }
    @Step("Test Step 4: chnage premium Trail to Premium Monthly;")
    public void step4() {

        new HamburgerMenuPage(false).closeLockedDialog();
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Subscription Time", "Monthly");
        paymentInfo.put("Number of Device Credits", "3");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T17531");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Vaci Utca");
        paymentInfo.put("City", "Budapest");
        paymentInfo.put("Zip", "1056");
        paymentInfo.put("Country", "Hungary");
        paymentInfo.put("State", "Budapest  ");
        paymentInfo.put("Card Number", "5193911111111112");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        String paymentPagetotalvalue = new HamburgerMenuPage().inputPaymentPageInfoforCAA(paymentInfo);
        assertTrue (new HamburgerMenuPage(false).checkMonthlyCurency(Currency, MonthlyAmount, paymentPagetotalvalue), "Currency and Amount is wrong");
        
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
        paymentInfo.put("Subscription Time", "Yearly");
        String paymentPagetotalvalue1 = new HamburgerMenuPage(false).changePlanToPremiumForCAA(paymentInfo);
        assertTrue (new HamburgerMenuPage(false).checkMonthlyCurency(Currency, YearlyAmount, paymentPagetotalvalue1), "Currency and Amount is wrong");
    }
    
    
}