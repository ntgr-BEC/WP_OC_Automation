package webportal.CFD.CFD_Testcases.PRJCBUGEN_T27429;

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
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 * @author Anusha H
 */
public class Testcase extends TestCaseBase {
    
    Map<String, String> ssidInfo                 = new HashMap<String, String>();
    Map<String, String> CaptivePortalPaymentInfo = new CommonDataType().PAYMENT_INFO;
    String              mailname                 = "";
    Random              r                        = new Random();
    int                 num                      = r.nextInt(10000000);
    
    @Feature("CFD.CFD_Testcases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28362") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Create ICP with register with customize(default value)") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13875") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.defaultLogin();

            handle.gotoLoction();
        }


    @Step("Test Step 2: Add WIFI ssid and enable instant captive portal, check client connect wifi;")
    public void step2() {
        if (!new HamburgerMenuPage().checkCaptivePortalServicesCredits()) {
//            mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num);
            CaptivePortalPaymentInfo.put("First Name", mailname);
            CaptivePortalPaymentInfo.put("Last Name", "T16637");
            CaptivePortalPaymentInfo.put("Email", mailname + "@mailinator.com");
            CaptivePortalPaymentInfo.put("Quantity", "3"); // can input 1 , 3 , 10 , 40
            CaptivePortalPaymentInfo.put("Duration", "1"); // can input 1 , 3
            new InsightServicesPage().buyCaptivePortalProducts(CaptivePortalPaymentInfo);
        }

        ssidInfo.put("SSID", "apwp16637");
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
        icpInfo.put("Login Modes", "Register_Customize");
        new WirelessQuickViewPage().enableCaptivePortalType(ssidInfo.get("SSID"), icpInfo);

        MyCommonAPIs.sleepi(1 * 60);

    }
}
