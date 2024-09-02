package webportal.AdvanceICP1Device3Year.PRJCBUGEN_T23910;

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
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.PostManPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini KV
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> ssidInfo                 = new HashMap<String, String>();
    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);


    @Feature("AdvanceICP1Device3Year") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23910") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify ICP Supports to buy 1 device for 3 year Portugal") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23910") // It's a testcase id/link from Jira Test Case.

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
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T23910");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Portugal");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Check captive portal services credits is correct;")
    public void step2() {
        
//      new HamburgerMenuPage(false).clickAddInsightIncludedDevices();
        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "8200-593");
        locationInfo.put("Country", "Portugal");
        new AccountPage().addNetwork(locationInfo);
        
    }
    
    
    @Step("Test Step 3: Add device To the Network;")
    public void step3() {
        
        new AccountPage(false).enterLocation(WebportalParam.location1);
 
       
       Map<String, String> firststdevInfo = new HashMap<String, String>();
      
       
       firststdevInfo.put("Serial Number1", WebportalParam.ap1serialNo);
       firststdevInfo.put("MAC Address1", WebportalParam.ap1macaddress);
       
       System.out.println(firststdevInfo);

               
       new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
       
    }

    @Step("Test Step 4: Check buy vpn services;")
    public void step4() {
        
//        mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num);
//
//        ssidInfo.put("SSID", "apwp23910");
//        ssidInfo.put("Security", "WPA2 Personal");
//        ssidInfo.put("Password", "12345678");
//        new WirelessQuickViewPage().addSsid(ssidInfo); 
        
        Map<String, String> CaptivePortalPaymentInfo = new HashMap<String, String>();
        CaptivePortalPaymentInfo = new CommonDataType().CARD_INFO;
//        if (new WirelessQuickViewPage().checkICPInsufficientCreditsDisplayed(ssidInfo.get("SSID"))) {
           
            CaptivePortalPaymentInfo.put("First Name", mailname);
            CaptivePortalPaymentInfo.put("Last Name", "T23910");
            CaptivePortalPaymentInfo.put("Email", mailname + "@mailinator.com");
            CaptivePortalPaymentInfo.put("Quantity", "1"); // can input 1 , 3 , 10 , 40
            CaptivePortalPaymentInfo.put("Duration", "3"); // can input 1 , 3
            CaptivePortalPaymentInfo.put("Street Address", "Praia da Falesia");
            CaptivePortalPaymentInfo.put("City", "Albufeira");
            CaptivePortalPaymentInfo.put("Zip", "8200");
            CaptivePortalPaymentInfo.put("Country", "Portugal");
            CaptivePortalPaymentInfo.put("State", "Albufeira");
            new InsightServicesPage(false).buyCaptivePortalProducts(CaptivePortalPaymentInfo);

            boolean result = false;
           
            result = new HamburgerMenuPage().checkCaptivePortalServicesCredits();
      
            assertTrue(result, "<2> Captive portal services credits is incorrect.");
//        } else {
//            assertTrue(false, "<1> Captive portal services credits is incorrect.");
//        }
    }
     
    @Step("Test Step 5: Cancel ICP subscription;")
    public void step5() {
        
        new HamburgerMenuPage().cancelICPServices();
        assertTrue(!new HamburgerMenuPage().checkCaptivePortalServicesCredits(), "<2> Captive portal services credits is incorrect.");
    }
}
