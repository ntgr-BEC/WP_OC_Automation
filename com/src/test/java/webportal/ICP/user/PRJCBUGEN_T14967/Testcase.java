package webportal.ICP.user.PRJCBUGEN_T14967;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
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

    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    Map<String, String> CaptivePortalPaymentInfo = new CommonDataType().PAYMENT_INFO;
    Map<String, String> ssidInfo                 = new HashMap<String, String>();

    @Feature("InstantCaptivePortal.IcpUnderAccount.BasicOrPremiumUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14967") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that buy now option is present when one of the subscriptions has expired.") // It's a test case title from Jira TestCase.
    @TmsLink("PRJCBUGEN-T14967") // It's a testcase id/link from Jira Test Case.

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
        new WirelessQuickViewPage().deleteSsidYes(ssidInfo.get("SSID"));
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
     WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
     webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);
     new AccountPage(false).enterLocation(WebportalParam.location1);
     new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
     
     String sCheck = "[alt=\"User Image\"]";
     System.out.println("try to do logout");
     $(sCheck).click();
     $(Selectors.byCssSelector(".open ul li:last-child a")).click();
     System.out.println("user is logout");
     MyCommonAPIs.waitReady();
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T15598");
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
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "Australia");
        new AccountPage().addNetwork(locationInfo);
        new AccountPage(false).enterLocation(WebportalParam.location1);
        
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);

        new DevicesDashPage(false).addNewDevice(devInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
    }

    @Step("Test Step 2: Check ICP credits;")
    public void step2() {
        mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num);
        CaptivePortalPaymentInfo.put("First Name", mailname);
        CaptivePortalPaymentInfo.put("Last Name", "T14967");
        CaptivePortalPaymentInfo.put("Email", mailname + "@mailinator.com");
        CaptivePortalPaymentInfo.put("Quantity", "3"); // can input 1 , 3 , 10 , 40
        CaptivePortalPaymentInfo.put("Duration", "1"); // can input 1 , 3
        new InsightServicesPage().buyCaptivePortalProducts(CaptivePortalPaymentInfo);

        if (new HamburgerMenuPage().checkCaptivePortalServicesCredits()) {
            handle.gotoLoction();
            ssidInfo.put("SSID", "apwp14967");
            ssidInfo.put("Security", "WPA2 Personal");
            ssidInfo.put("Password", "12345678");
            new WirelessQuickViewPage().addSsid(ssidInfo);

            Map<String, String> icpInfo = new HashMap<String, String>();
            icpInfo.put("Portal Name", "showad");
            icpInfo.put("Welcome Headline", "dhfjsdfjasdfhjsdhfshdfhsdjhfsdfjsdgfsk;fgjsdgsdfjsfdgsgfd");
            icpInfo.put("Captive Portal Logo", "DEFAULT_LOGO");
            icpInfo.put("Desktop Background Image", "DEFAULT_BG");
            icpInfo.put("Landing Page URL", "https://www.rediff.com");
            icpInfo.put("Session Duration", "5 min");
            icpInfo.put("Step Type", "Authentication Method");
            icpInfo.put("Login Modes", "Facebook.");
            new WirelessQuickViewPage().enableCaptivePortalType(ssidInfo.get("SSID"), icpInfo);

            new HamburgerMenuPage().cancelICPServices();
            boolean result = new HamburgerMenuPage().checkIcpGracePeriod();
            new HamburgerMenuPage(false).gracePeriodWarningBuyBtn.click();
            MyCommonAPIs.sleepsync();
            String url = MyCommonAPIs.getCurrentUrl();
            assertTrue(result && url.contains("/captiveportal"), "Warning no displayed.");
        } else {
            assertTrue(false, "Captive portal services credits is incorrect.");
        }
    }

}
