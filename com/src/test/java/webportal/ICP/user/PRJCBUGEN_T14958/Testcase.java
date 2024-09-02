package webportal.ICP.user.PRJCBUGEN_T14958;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selectors;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random              r                        = new Random();
    int                 num                      = r.nextInt(10000000);
    String              mailname                 = "apwptest" + String.valueOf(num);
    Map<String, String> CaptivePortalPaymentInfo = new CommonDataType().PAYMENT_INFO;
    Map<String, String> ssidInfo                 = new HashMap<String, String>();

    @Feature("InstantCaptivePortal.IcpUnderAccount.BasicOrPremiumUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14958") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that is user is able to delete Instant Captive Portal enabled SSID if there are insufficient credits") // It's a test case title from Jira TestCase.
    @TmsLink("PRJCBUGEN-T14958") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        if (new HamburgerMenuPage().checkCaptivePortalServicesCredits()) {
            new HamburgerMenuPage().cancelICPServices();
        }
        new WirelessQuickViewPage().deleteALLSSID();
        new AccountPage().deleteOneLocation("OnBoardingTest");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);
        handle.gotoLoction();
        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
        String sCheck = "[alt=\"User Image\"]";
        System.out.println("try to do logout");
        $(sCheck).click();
        $(Selectors.byCssSelector(".open ul li:last-child a")).click();
        System.out.println("user is logout");
        MyCommonAPIs.waitReady();
    }
    
    @Step("Test Step 2: Login IM WP success;")
    public void step2() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T17512");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }
    
    @Step("Test Step 3: Create the Location;")
    public void step3() {
        
        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);
        new AccountPage(false).enterLocation(WebportalParam.location1);
    }
    
    @Step("Test Step 4:Add Device to Location;")
    public void step4() {
        new AccountPage().enterLocation("OnBoardingTest");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap1serialNo);
     
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        
        boolean result = true;
        
        //assertTrue(new DevicesDashPage().checkNumberOfDevices().equals("Twodevice"), "More device exits");
    }
    
    @Step("Test Step 5: Check ICP credits;")
    public void step5() {
        mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num);
        CaptivePortalPaymentInfo.put("First Name", "New");
        CaptivePortalPaymentInfo.put("Last Name", "Netgear");
        CaptivePortalPaymentInfo.put("Email", mailname + "@mailinator.com");
        CaptivePortalPaymentInfo.put("Quantity", "1"); // can input 1 , 3 , 10 , 40
        CaptivePortalPaymentInfo.put("Duration", "1"); // can input 1 , 3
        new InsightServicesPage().buyCaptivePortalProducts(CaptivePortalPaymentInfo);

        if (new HamburgerMenuPage().checkCaptivePortalServicesCredits()) {
            handle.gotoLoction();
            ssidInfo.put("SSID", "apwp14958");
            ssidInfo.put("Security", "WPA2 Personal");
            ssidInfo.put("Password", "12345678");
            new WirelessQuickViewPage().addSsid(ssidInfo);

            Map<String, String> icpInfo = new HashMap<String, String>();
            icpInfo.put("Portal Name", "showad");
            icpInfo.put("Welcome Headline", "Welcome to Automaton world");
            icpInfo.put("Captive Portal Logo", "DEFAULT_LOGO");
            icpInfo.put("Desktop Background Image", "DEFAULT_BG");
            icpInfo.put("Landing Page URL", "https://www.rediff.com");
            icpInfo.put("Session Duration", "5 min");
            icpInfo.put("Step Type", "Authentication Method");
            icpInfo.put("Login Modes", "Facebook.");
            new WirelessQuickViewPage().enableCaptivePortalType(ssidInfo.get("SSID"), icpInfo);

            assertTrue(new WirelessQuickViewPage().checkICPInsufficientCreditsDisplayed("apwp14958two")
                    && !new HamburgerMenuPage().checkCaptivePortalServicesCredits(), "Available captive portal services credits is incorrect.");
        } else {
            assertTrue(false, "Captive portal services credits is incorrect.");
        }
    }

}
