package webportal.CFD.Migration_Pro.US.PRJCBUGEN_T27049;

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
    @Story("PRJCBUGEN_T27049") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("converting premiun to pro account ,closed the the broswer and reopen the broswer before assigning the location to an organzation") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T27049") // It's a testcase id/link from Jira Test Case.
    
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
      //webportalLoginPage.loginByUserPassword("apwptest1551841@mailinator.com", "Netgear#123");
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T27049");
        accountInfo.put("Email Address", EmailName );
        accountInfo.put("Confirm Email", EmailName);
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");
        new HamburgerMenuPage(false).createAccount(accountInfo);
    }
    
               
         
    
    @Step("Test Step 2: Create Location ")
    public void step2() {
        new HamburgerMenuPage();  
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);
        
    }
    
    @Step("Test Step 3: Add  device ")
    public void step3() {
        
        new AccountPage().enterLocation(locationName);
        
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap2serialNo);
        devInfo.put("Device Name", WebportalParam.ap2deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap2macaddress);

        new DevicesDashPage(false).addNewDevice(devInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);
    }
          
        @Step("Test Step 4: Add WIFI ssid and enable instant captive portal, check client connect wifi;")
        public void step4() {
            Map<String, String> CaptivePortalPaymentInfo = new HashMap<String, String>();
            CaptivePortalPaymentInfo = new CommonDataType().CARD_INFO;
                CaptivePortalPaymentInfo.put("First Name", mailname);
                CaptivePortalPaymentInfo.put("Last Name", "T24089");
                CaptivePortalPaymentInfo.put("Email", EmailName);
                CaptivePortalPaymentInfo.put("Quantity", "10"); // can input 1 , 3 , 10 , 40
                CaptivePortalPaymentInfo.put("Duration", "1"); // can input 1 , 3
                CaptivePortalPaymentInfo.put("Street Address", "Springs Rd");
                CaptivePortalPaymentInfo.put("City", "Red Bank");
                CaptivePortalPaymentInfo.put("Zip", "32003");
                CaptivePortalPaymentInfo.put("Country", "US");
                CaptivePortalPaymentInfo.put("State", "Florida");
                new InsightServicesPage(false).buyCaptivePortalProducts(CaptivePortalPaymentInfo);

                boolean result = false;
               
                result = new HamburgerMenuPage().checkCaptivePortalServicesCredits();
          
                assertTrue(result, "<2> Captive portal services credits is incorrect.");


            ssidInfo.put("SSID", "1apwp27049");
            ssidInfo.put("Security", "WPA2 Personal");
            ssidInfo.put("Password", "12345678");
            new AccountPage().enterLocation(locationName);
            new WirelessQuickViewPage().addSsid(ssidInfo);

            Map<String, String> icpInfo = new HashMap<String, String>();
            icpInfo.put("Portal Name", "welcome to Automation");
            icpInfo.put("Welcome Headline", "Automation test in BEC");
            icpInfo.put("Captive Portal Logo", "DEFAULT_LOGO");
            icpInfo.put("Desktop Background Image", "DEFAULT_BG");
            icpInfo.put("Landing Page URL", "https://www.rediff.com");
            icpInfo.put("Session Duration", "15 min");
            icpInfo.put("Step Type", "Authentication Method");
            icpInfo.put("Login Modes", "Facebook");
            new WirelessQuickViewPage().enableCaptivePortalType(ssidInfo.get("SSID"), icpInfo);
            
            MyCommonAPIs.sleepi(2 * 60);
            

            new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);

            int sum = 0;
            while (true) {
                MyCommonAPIs.sleepi(10);
                if (new Javasocket()
                        .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID 1apwp27049")
                        .indexOf("true") != -1) {
                    break;
                } else if (sum > 30) {
                    assertTrue(false, "Client cannot connected.");
                    break;
                }
                sum += 1;
            }

            boolean result1 = true;
            if (!new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect 1apwp27049 12345678 WPA2PSK aes")
                    .equals("true")) {
                result1 = false;
                MyCommonAPIs.sleepi(20);
                if (new Javasocket()
                        .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect 1apwp27049 12345678 WPA2PSK aes")
                        .equals("true")) {
                    result1 = true;
                }
            }

            assertTrue(result1, "Client cannot connected.");

    }
        
        
//        @Step("Test Step 5: Check whether captive portal page is shown or not;")
//        public void step5() {
//            MyCommonAPIs.sleepsync();
//            MyCommonAPIs.sleepi(10);
//            assertTrue(
//                    new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
//                            "WAFruncaptive PRJCBUGEN-T16635.py www.rediff.com test test").indexOf("finalresult: 1") != -1,
//                    "Captive portal not take effect.");
//            new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
//        }

    
    @Step("Test Step 6: logout and migrates to a PRO account.")
    public void step6() {
        
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
            businessInfo.put("Primary Address of Business", "Springs Rd");
            businessInfo.put("City", "Red Bank");
            businessInfo.put("State", "Florida");
            businessInfo.put("Zip Code", "32003");
            businessInfo.put("Country", "United States of America");
            businessInfo.put("Business Phone Number", "1234567890");
            new HamburgerMenuPage(false).inputLicenceAndFinishSignin(businessInfo);
             
            new ContentFilteringPage().refresh();

                     
            Selenide.open("https://www.rediffmail.com");
            MyCommonAPIs.sleepi(10);
            Selenide.open(WebportalParam.serverUrlLogin);
            MyCommonAPIs.sleepi(10);
            new HardBundlePage().checkCreateProAccountPage("checkNext:" + EmailName);
            MyCommonAPIs.sleepi(10);
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(EmailName, "Netgear#123"); 
            MyCommonAPIs.sleepi(10);
            assertTrue(new HardBundlePage().checkLoginSuccessful(), "Create pro account unsuccess.");
        
    }
    
    @Step("Test Step 7: cpnnect to client in Pro Account")
    public void step7() {
        
         new AccountPage().enterLocation(locationName);
        
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID 1apwp27049")
                    .indexOf("true") != -1) {
                break;
            } else if (sum > 30) {
                assertTrue(false, "Client cannot connected.");
                break;
            }
            sum += 1;
        }

        boolean result1 = true;
        if (!new Javasocket()
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect 1apwp27049 12345678 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            MyCommonAPIs.sleepi(20);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect 1apwp27049 12345678 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");
    }
    
//    @Step("Test Step 8: Check whether captive portal page is shown or not;")
//    public void step8() {
//        MyCommonAPIs.sleepsync();
//        MyCommonAPIs.sleepi(10);
//        assertTrue(
//                new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
//                        "WAFruncaptive PRJCBUGEN-T16635.py www.rediff.com test test").indexOf("finalresult: 1") != -1,
//                "Captive portal not take effect.");
//        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
//    }

    
}
