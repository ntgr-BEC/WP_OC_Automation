package webportal.ICP.user.PRJCBUGEN_T14961;

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
    String              mailname                 = "";
    Map<String, String> CaptivePortalPaymentInfo = new CommonDataType().PAYMENT_INFO;
    Map<String, String> ssidInfo                 = new HashMap<String, String>();

    @Feature("InstantCaptivePortal.IcpUnderAccount.BasicOrPremiumUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14961") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that user is notified on all Captive Portal screens about the grace period") // It's a test case title from Jira TestCase.
    @TmsLink("PRJCBUGEN-T14961") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new AccountPage().deleteOneLocation("OnBoardingTest");
       
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);
    }

    @Step("Test Step 2: Check ICP credits;")
    public void step2() {
        mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num);
        CaptivePortalPaymentInfo.put("First Name", mailname);
        CaptivePortalPaymentInfo.put("Last Name", "T14961");
        CaptivePortalPaymentInfo.put("Email", mailname + "@mailinator.com");
        CaptivePortalPaymentInfo.put("Quantity", "3"); // can input 1 , 3 , 10 , 40
        CaptivePortalPaymentInfo.put("Duration", "1"); // can input 1 , 3
        new InsightServicesPage().buyCaptivePortalProducts(CaptivePortalPaymentInfo);

        if (new HamburgerMenuPage().checkCaptivePortalServicesCredits()) {
            handle.gotoLoction();
            ssidInfo.put("SSID", "apwp14961");
            ssidInfo.put("Security", "WPA2 Personal");
            ssidInfo.put("Password", "12345678");
            new WirelessQuickViewPage().addSsid(ssidInfo);
            
            
            new AccountPage().enterLocation("OnBoardingTest");
            
            Map<String, String> firststdevInfo = new HashMap<String, String>();
            
            firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
            firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);

            
            System.out.println(firststdevInfo);
                    
            new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
            

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
            assertTrue(new HamburgerMenuPage().checkIcpGracePeriod(), "Warning no displayed.");
        } else {
            assertTrue(false, "Captive portal services credits is incorrect.");
        }
    }
}
