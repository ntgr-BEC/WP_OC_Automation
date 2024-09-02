package webportal.CFD.Migration_Pro.NewZealand.OneDevCreditTestCases.PRJCBUGEN_T27363;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.ContentFilteringPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;


/**
 *
 * @author Tejeshwini
 *
 */
public class Testcase extends TestCaseBase {
    
    String                  locationName     = "OnBoardingTest";
    
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String Email = mailname + "@mailinator.com";
    String EmailName  = Email;
    Map<String, String> CaptivePortalPaymentInfo = new HashMap<String, String>();
    Map<String, String> ssidInfo                 = new HashMap<String, String>();
    

    @Feature("Migration_Pro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27063") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that Insight device assigns to any organization/location after upgrade to Insigh") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T27063") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new AccountPage().deleteOneLocation(locationName);
        new OrganizationPage().deleteOrganizationNew(WebportalParam.Organizations);
    }
    
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        //webportalLoginPage.loginByUserPassword("apwptest8461498@mailinator.com", "Netgear#123");
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T17523");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "New Zealand");


        new HamburgerMenuPage(false).createAccount(accountInfo);
    }
    
    @Step("Test Step 2: Create new location")
    public void step2() {
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "3206");
        locationInfo.put("Country", "New Zealand");
        new HamburgerMenuPage();
        new AccountPage().addNetwork(locationInfo);
        
       
    }
    
    @Step("Test Step 3: Add device To the Network;")
    public void step3() {
        
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
        
        new DevicesDashPage().onboardDummySwitch(WebportalParam.sw1serialNo, WebportalParam.sw1deveiceName, WebportalParam.sw1MacAddress);
                
        assertTrue(new DevicesDashPage().checkNumberOfDevices().equals("Threedevice"), "More device exits");
        
    }
        
    @Step("Test Step 4: logout and migrates to a PRO account.")
    public void step4() {
        
            String sCheck = "[alt=\"User Image\"]";
            System.out.println("try to do logout");
            $(sCheck).click();
            $(Selectors.byCssSelector(".open ul li:last-child a")).click();
            System.out.println("user is logout");
            MyCommonAPIs.waitReady();
            
            new HardBundlePage().checkCreateProAccountPage("checkNext:" + EmailName);
       
            Map<String, String> businessInfo = new HashMap<String, String>();
            businessInfo.put("Licence Key", new HamburgerMenuPage(false).readLicenceKeyByTxt("Write"));
            businessInfo.put("Business Name", "Netgear");
            businessInfo.put("Primary Address of Business", "test 1st");
            businessInfo.put("City", "Waikato");
            businessInfo.put("State", "Waikato");
            businessInfo.put("Zip Code", "3206");
            businessInfo.put("Country", "New Zealand");
            businessInfo.put("Business Phone Number", "1234567890");
            new HamburgerMenuPage(false).inputLicenceAndFinishSignin(businessInfo);
            
            assertTrue(new HardBundlePage().checkLoginSuccessful(), "Create pro account unsuccess.");
        
    }
    
    
}
