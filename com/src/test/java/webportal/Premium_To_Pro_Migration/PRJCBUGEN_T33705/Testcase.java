package webportal.Premium_To_Pro_Migration.PRJCBUGEN_T33705;

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
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.PostManPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

import com.codeborne.selenide.Selenide;

/**
 *
 * @Pratik
 *
 */
public class Testcase extends TestCaseBase {
    
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String organizationName = "PRJCBUGEN_T33705";
    Map<String, String> ssidInfo                 = new HashMap<String, String>();

    @Feature("Premium_To_Pro_Migration") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33705") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, add buy instant captive portal service and migrate premium to pro same month then current date and end date should same.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T33705") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void tearUp() {
       
       new PostManPage().Deregister(WebportalParam.ap5serialNo);
       new PostManPage().Deregister(WebportalParam.ap6serialNo);
        
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (new OrganizationPage().checkOrganizationIsExist(organizationName)){
            new OrganizationPage().deleteOrganizationNew(organizationName);
            System.out.println("start to do tearDown");
        } else {
            new AccountPage().deleteOneLocation("OnBoardingTest");
            System.out.println("start to do tearDown");
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        
        Selenide.clearBrowserCookies();
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T18721");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");


        new HamburgerMenuPage(false).createAccount(accountInfo);
        
        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);
    }
    
    @Step("Test Step 2: Add dummy hardbundle device To the Network;")
    public void step2() {
        
new AccountPage().enterLocation("OnBoardingTest");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        
        System.out.println(firststdevInfo);
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        
        boolean result = true;
        
    }
         
    @Step("Test Step 3: Check buy icp services;")
    public void step3() {
         
        Map<String, String> CaptivePortalPaymentInfo = new HashMap<String, String>();
        CaptivePortalPaymentInfo = new CommonDataType().CARD_INFO;
            CaptivePortalPaymentInfo.put("First Name", mailname);
            CaptivePortalPaymentInfo.put("Last Name", "T24253");
            CaptivePortalPaymentInfo.put("Email", mailname + "@mailinator.com");
            CaptivePortalPaymentInfo.put("Quantity", "40"); // can input 1 , 3 , 10 , 40
            CaptivePortalPaymentInfo.put("Duration", "3"); // can input 1 , 3
            CaptivePortalPaymentInfo.put("Street Address", "Springs Rd");
            CaptivePortalPaymentInfo.put("City", "Red Bank");
            CaptivePortalPaymentInfo.put("Zip", "32003");
            CaptivePortalPaymentInfo.put("Country", "US");
            CaptivePortalPaymentInfo.put("State", "Florida");
          
            new InsightServicesPage().buyCaptivePortalProducts(CaptivePortalPaymentInfo);

            boolean result = false;
           
            result = new HamburgerMenuPage().checkCaptivePortalServicesCredits();
      
            assertTrue(result, "<2> Captive portal services credits is incorrect.");
    } 
        
    @Step("Test Step 4: Check start date and end date of icp subscription and upgrade to pro;")
    public void step4() {
        
        new MyCommonAPIs().open(URLParam.hrefICP, true);
        new HamburgerMenuPage(false).premiumVPNServicesStartDateEndDate();
        
    }

    @Step("Test Step 5: Navigate to Account Management, check upgrade to pro option and click on it;")
    public void step5() {
        System.out.println("starting with setup 2");
        assertTrue(new HamburgerMenuPage().insightPritoinsightPro(),"Failed navigate to Account Management");
        
    }
    
    @Step("Test Step 6: Check upgraded to pro")
    public void step6() {
        
        Map<String, String> businessInfo = new HashMap<String, String>();
        businessInfo.put("Business Name", "Netgear");
        businessInfo.put("Primary Address of Business", "test 1st");
        businessInfo.put("City", "NewYork");
        businessInfo.put("State", "test");
        businessInfo.put("Zip Code", "12345");
        businessInfo.put("Country", "United States of America");
        businessInfo.put("Business Phone Number", "1234567890");
        businessInfo.put("Confirm Email", mailname + "@mailinator.com");
        businessInfo.put("Password", "Netgear#123");
        businessInfo.put("Licence Key", new HamburgerMenuPage(false).readLicenceKeyByTxt("Write"));
        new HamburgerMenuPage(false).inputLicenceAndFinishSignin(businessInfo);
        assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create pro account unsuccess.");
        assertTrue(new HamburgerMenuPage(false).addLocationsToOrg(organizationName), "Location is not Successfully added to new created orgnizqation");
        
    }
    
    @Step("Test Step 7: create new organization and verify icp start date and end date in pro account with premium")
    public void step7() {
                
        new MyCommonAPIs().open(URLParam.hrefICP, true);
        assertTrue(new HamburgerMenuPage(false).proVPNServicesStartDateEndDate(), "Both Dates are not matching");
        
    }
    
}
