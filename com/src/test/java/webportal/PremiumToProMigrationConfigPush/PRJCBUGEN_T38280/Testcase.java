package webportal.PremiumToProMigrationConfigPush.PRJCBUGEN_T38280;

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
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
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
    String organizationName = "PRJCBUGEN_T38280";
    Map<String, String> ssidInfo                 = new HashMap<String, String>();
    Map<String, String> ECPInfo = new HashMap<String, String>();

    @Feature("Premium_To_Pro_Migration") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T38280") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that premium account having ECP SSID with connected client migrated to pro account and verify same config is there or not") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T38280") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
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
        //Selenide.clearBrowserLocalStorage();
        Selenide.refresh();
        
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
    
    
    @Step("Test Step 3: Add dummy hardbundle device To the Network;")
    public void step3() {
        
        new AccountPage(false).enterLocation(WebportalParam.location1);
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("Device Name", WebportalParam.ap1deveiceName);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);
        new DevicesDashPage().addNewDevice(devInfo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        
        new AccountPage(false).editLocationandOpenRadiusSectionPremiumAcc();
        new AccountPage(false).enableTheRadiusServer();
        new AccountPage(false).enterTheServerIP();
        assertTrue(new AccountPage(false).VerifyRadiussucessMsg(),"Radius server is not enabled successfully");
             
    }
    
    @Step("Test Step 4: Check buy ICP services;")
    public void step4() {
         
        Map<String, String> CaptivePortalPaymentInfo = new HashMap<String, String>();
        CaptivePortalPaymentInfo = new CommonDataType().CARD_INFO;     
            CaptivePortalPaymentInfo.put("First Name", mailname);
            CaptivePortalPaymentInfo.put("Last Name", "T24065");
            CaptivePortalPaymentInfo.put("Email", mailname + "@mailinator.com");
            CaptivePortalPaymentInfo.put("Quantity", "10"); // can input 1 , 3 , 10 , 40
            CaptivePortalPaymentInfo.put("Duration", "1"); // can input 1 , 3
            CaptivePortalPaymentInfo.put("Street Address", "Main Street");
            CaptivePortalPaymentInfo.put("City", "Montville");
            CaptivePortalPaymentInfo.put("Zip", "4560");
            CaptivePortalPaymentInfo.put("Country", "Australia");
            CaptivePortalPaymentInfo.put("State", "Queensland");
            new InsightServicesPage(false).buyCaptivePortalProducts(CaptivePortalPaymentInfo);

            boolean result = false;
           
            result = new HamburgerMenuPage().checkCaptivePortalServicesCredits();
      
            assertTrue(result, "<2> Captive portal services credits is incorrect.");
            
    }
    
    @Step("Test Step 5: Add WIFI ssid and enable instant captive portal, check client connect wifi;")
    public void step5() {
      
        ssidInfo.put("SSID", "PRJCBUGEN_T38280");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);
        
        ECPInfo.put("Walled Garden", "*.jazenetworks.com");
        ECPInfo.put("Splash Page URL", "http://portal.jazenetworks.com/netgear/");
        ECPInfo.put("Captive Portal Authentication Type", "Radius");
        ECPInfo.put("IPv4 Address", "180.179.56.164");
        ECPInfo.put("Password", "jazenetworks");
        ECPInfo.put("Failsafe", "Enable");

        new WirelessQuickViewPage().enableECP(ssidInfo.get("SSID"), ECPInfo);

        MyCommonAPIs.sleepi(180);
        
        String CMD = "WalledGarden" ;
        String Result = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD);
        System.out.println(Result);
        assertTrue(Result.contains(ECPInfo.get("Walled Garden")), "ECP walled Garden is not applied");
        
        
        String CMD1 = "SplashPageURL" ;
        String Result1 = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD1);
        System.out.println(Result1);
        assertTrue(Result1.contains("http\\://portal.jazenetworks.com/netgear/"), "ECP SplashPageURL is not applied");

    }

    @Step("Test Step 6: Navigate to Account Management, check upgrade to pro option and click on it;")
    public void step6() {
        System.out.println("starting with setup 2");
        assertTrue(new HamburgerMenuPage().insightPritoinsightPro(),"Failed navigate to Account Management");
        
    }
    
    @Step("Test Step 7: Fill the tset data into the form and click on upgrade button;")
    public void step7() {
        
        Map<String, String> businessInfo = new HashMap<String, String>();
        businessInfo.put("Business Name", "Netgear");
        businessInfo.put("Primary Address of Business", "test 1st");
        businessInfo.put("City", "NewYork");
        businessInfo.put("State", "test");
        businessInfo.put("Zip Code", "12345");
        businessInfo.put("Country", "United States of America");
        businessInfo.put("Business Phone Number", "1234567890");
        new HamburgerMenuPage(false).inputBusinessInfo(businessInfo);
        new HamburgerMenuPage(false).clickBusinessInfoPageButton();
        assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create pro account unsuccess."); 
        assertTrue(new HamburgerMenuPage(false).addLocationsToOrg(organizationName), "Location is not Successfully added to new created orgnizqation");
        
        new OrganizationPage(false).openOrg(organizationName);
        new MyCommonAPIs().gotoLoction("OnBoardingTest");
        new OrganizationPage(false).goWirelessSetting();
        assertTrue(new OrganizationPage(false).checkOrgSsidIsExist("PRJCBUGEN_T38280"),"ssid does not exits in Loc 1");
        
        String CMD = "WalledGarden" ;
        String Result = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD);
        System.out.println(Result);
        assertTrue(Result.contains(ECPInfo.get("Walled Garden")), "ECP walled Garden is not applied");
        
        
        String CMD1 = "SplashPageURL" ;
        String Result1 = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD1);
        System.out.println(Result1);
        assertTrue(Result1.contains("http\\://portal.jazenetworks.com/netgear/"), "ECP SplashPageURL is not applied");
    }
    
}
