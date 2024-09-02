package webportal.ICP.user.PRJCBUGEN_T17777;

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

    Map<String, String> ssidInfo                 = new HashMap<String, String>();
    Random              r                        = new Random();
    int                 num                      = r.nextInt(10000000);
    String              mailname                 = "";
    Map<String, String> CaptivePortalPaymentInfo = new CommonDataType().PAYMENT_INFO;

    @Feature("ICP.user") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17777") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify ICP Supports to buy 3 device for 3 year") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17777") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes(ssidInfo.get("SSID"));
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

        handle.gotoLoction();
    }

    @Step("Test Step 2: Check captive portal services credits is correct;")
    public void step2() {
        mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num);
        if (new HamburgerMenuPage().checkCaptivePortalServicesCredits()) {
            new HamburgerMenuPage().cancelICPServices();
        }

        ssidInfo.put("SSID", "apwp17777");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        if (new WirelessQuickViewPage().checkICPInsufficientCreditsDisplayed(ssidInfo.get("SSID"))) {
            CaptivePortalPaymentInfo.put("First Name", mailname);
            CaptivePortalPaymentInfo.put("Last Name", "T17777");
            CaptivePortalPaymentInfo.put("Email", mailname + "@mailinator.com");
            CaptivePortalPaymentInfo.put("Quantity", "3"); // can input 1 , 3 , 10 , 40
            CaptivePortalPaymentInfo.put("Duration", "3"); // can input 1 , 3
            new InsightServicesPage().buyCaptivePortalProducts(CaptivePortalPaymentInfo);

            boolean result = false;
            if (!new WirelessQuickViewPage().checkICPInsufficientCreditsDisplayed(ssidInfo.get("SSID"))) {
                result = new HamburgerMenuPage().checkCaptivePortalServicesCredits();
            }
            assertTrue(result, "<2> Captive portal services credits is incorrect.");
        } else {
            assertTrue(false, "<1> Captive portal services credits is incorrect.");
        }
    }

}
