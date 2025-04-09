package webportal.PromoCode.PRJCBUGEN_T21747;

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
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);

    @Feature("PromoCode") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T21747") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, Billing information is hidden after applying the promo code.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T21747") // It's a testcase id/link from Jira Test Case.

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
    @Step("Test Step 1:  Create an account;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T21510");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");
        new HamburgerMenuPage(false).createAccount(accountInfo);
        WebportalLoginPage webportalLoginPage1 = new WebportalLoginPage(true);

      
    }
        
    @Step("Test Step 2: Create new location")
    public void step2() {
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
       // new HamburgerMenuPage();
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

    
    @Step("Test Step 4: Add device To the Network;")
    public void step4() {
        WebportalLoginPage webportalLoginPage1 = new WebportalLoginPage(true);
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo = new CommonDataType().CARD_INFO;
        paymentInfo.put("Subscription Time", "Yearly");
        paymentInfo.put("Number of Device Credits", "2");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T17564");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Springs Rd");
        paymentInfo.put("City", "Red Bank");
        paymentInfo.put("Zip", "32003");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        paymentInfo.put("Promo Code", WebportalParam.promocode1);
        assertTrue(
                new HamburgerMenuPage(false).checkPromoCode(paymentInfo),
                "Buy premium subscription unsuccessful.");
        
        
        
    }

}
