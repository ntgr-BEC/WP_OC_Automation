package webportal.Premium_To_Pro_Migration.PRJCBUGEN_T33708;

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
    String organizationName = "PRJCBUGEN_T33708";

    @Feature("Premium_To_Pro_Migration") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33708") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that ,user should able to see added multple ICP pack in ICP list.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T33708") // It's a testcase id/link from Jira Test Case.

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
        new AccountPage().deleteOneLocation("OnBoardingTest");
        //new OrganizationPage().deleteOrganizationNew(organizationName);
        System.out.println("start to do tearDown");
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
    }
    
    @Step("Test Step 2: Create new location;")
    public void step2() {
        
//      new HamburgerMenuPage(false).clickAddInsightIncludedDevices();
        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);
        
    }
    
    
    @Step("Test Step 3: Add dummy non hardbundle device To the Network;")
    public void step3() {
        
        new AccountPage(false).enterLocation(WebportalParam.location1);
        
         Map<String, String> firststdevInfo = new HashMap<String, String>();
       
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap6serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap6macaddress);
        
        System.out.println(firststdevInfo);
 
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        
    }
    
    @Step("Test Step 4: Add icp credits;")
    public void step4() {
        
//      new HamburgerMenuPage(false).clickAddInsightIncludedDevices();
        Map<String, String> CaptivePortalPaymentInfo = new HashMap<String, String>();
        CaptivePortalPaymentInfo = new CommonDataType().CARD_INFO;
        CaptivePortalPaymentInfo.put("First Name", mailname);
        CaptivePortalPaymentInfo.put("Last Name", "T23919");
        CaptivePortalPaymentInfo.put("Email", mailname + "@mailinator.com");
        CaptivePortalPaymentInfo.put("Quantity", "10"); // can input 1 , 3 , 10 , 40
        CaptivePortalPaymentInfo.put("Duration", "1"); // can input 1 , 3
        CaptivePortalPaymentInfo.put("Street Address", "Springs Rd");
        CaptivePortalPaymentInfo.put("City", "Red Bank");
        CaptivePortalPaymentInfo.put("Zip", "32003");
        CaptivePortalPaymentInfo.put("Country", "US");
        CaptivePortalPaymentInfo.put("State", "Florida");
        new InsightServicesPage().buyCaptivePortalProducts(CaptivePortalPaymentInfo);
        MyCommonAPIs.sleepi(10);
        boolean result = false;
        handle.gotoLoction();
        result = new HamburgerMenuPage().checkCaptivePortalServicesCredits();
        assertTrue(result, "<2> Captive portal services credits is incorrect.");
        
    }
}
