package webportal.CFDPayment.PRJCBUGEN_T21396;

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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random              r                 = new Random();
    int                 num               = r.nextInt(10000000);
    String              mailname          = "apwptest" + String.valueOf(num);
    Map<String, String> DeviceCreditsInfo = new HashMap<String, String>();

    @Feature("CFDPayment") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T21396") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Basic free -->Premium Trail --> Multipack --> Cancel Multipack within 3 days and still premium trail is active") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T21396") // It's a testcase id/link from Jira Test Case.

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
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
//        webportalLoginPage.loginByUserPassword("apwptest3110571@mailinator.com","Netgear#123");

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T21396");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Create new location")
    public void step2() {
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "STJ3310");
        locationInfo.put("Country", "Malta");
        new HamburgerMenuPage();
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

      
    }

    @Step("Test Step 4:  Buy insight premium subscription and cancel subscription;")
    public void step4() {
        new HamburgerMenuPage().gotoInsightPremiumSubscriptions();

        DeviceCreditsInfo.put("First Name", mailname);
        DeviceCreditsInfo.put("Last Name", "T21396");
//        DeviceCreditsInfo.put("Email", mailname + "@mailinator.com");
        DeviceCreditsInfo.put("Street Address", "Street 4568 James Avenue");
        DeviceCreditsInfo.put("City", "INVERNESS");
        DeviceCreditsInfo.put("Zip", "34451");
        DeviceCreditsInfo.put("Country", "US");
        DeviceCreditsInfo.put("State", "Florida");
        DeviceCreditsInfo.put("Card Number", "4112344112344113");
        DeviceCreditsInfo.put("CVV Number", "123");
        DeviceCreditsInfo.put("Expiration Month", "May");
        DeviceCreditsInfo.put("Expiration Year", "2030");
        DeviceCreditsInfo.put("Device Credits Pack", "5");
        DeviceCreditsInfo.put("Buy Year", "1");
        new InsightServicesPage(false).buyInsightPremiumSubscriptions(DeviceCreditsInfo);

        new HamburgerMenuPage().cancelDeviceCredits();
        assertTrue(new HamburgerMenuPage(false).checkCancelSubscription(), "Subscription type incorrect.");
    }

}
