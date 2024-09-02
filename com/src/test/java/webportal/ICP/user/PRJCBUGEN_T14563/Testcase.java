package webportal.ICP.user.PRJCBUGEN_T14563;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
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

    Random              r                        = new Random();
    int                 num                      = r.nextInt(10000000);
    String              mailname                 = "";
    Map<String, String> CaptivePortalPaymentInfo = new CommonDataType().PAYMENT_INFO;
    Map<String, String> ssidInfo                 = new HashMap<String, String>();

    @Feature("InstantCaptivePortal.Configuration") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14563") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify user is able to select different type of tier on") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14563") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().deleteSsidYes(ssidInfo.get("SSID"));
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);
    }

    @Step("Test Step 2: Check icp configuration;")
    public void step2() {
        if (!new HamburgerMenuPage().checkCaptivePortalServicesCredits()) {
            mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num);
            CaptivePortalPaymentInfo.put("First Name", mailname);
            CaptivePortalPaymentInfo.put("Last Name", "T14563");
            CaptivePortalPaymentInfo.put("Email", mailname + "@mailinator.com");
            CaptivePortalPaymentInfo.put("Quantity", "3"); // can input 1 , 3 , 10 , 40
            CaptivePortalPaymentInfo.put("Duration", "1"); // can input 1 , 3
            new InsightServicesPage().buyCaptivePortalProducts(CaptivePortalPaymentInfo);
        }
        handle.gotoLoction();

        ssidInfo.put("SSID", "apwp14563");
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
        icpInfo.put("Login Modes", "Register.");

        new WirelessQuickViewPage().settingsorquickview.click();
        new WirelessQuickViewPage(false).clickEditSsid(ssidInfo.get("SSID"));
        new WirelessQuickViewPage(false).enableIcpStep();
        Selenide.switchTo().frame(0);
        MyCommonAPIs.sleepi(3);
        new WirelessQuickViewPage(false).clickcaptiveportalstep.click();
        MyCommonAPIs.sleepi(5);
        new WirelessQuickViewPage(false).selectsteptype.selectOption(icpInfo.get("Step Type"));
        MyCommonAPIs.sleepi(3);
        new WirelessQuickViewPage(false).configAuthenticationMethod(icpInfo);
        MyCommonAPIs.sleepi(3);
        new WirelessQuickViewPage(false).savecaptiveportalstep.click();

        icpInfo.put("Step Type", "Display Ad");
        icpInfo.put("Ad Name", "welcome");
        icpInfo.put("Image Path", System.getProperty("user.dir") + "/src/test/resources/displayad.bmp");
        MyCommonAPIs.sleepi(3);
        new WirelessQuickViewPage(false).clickcaptiveportalstep.click();
        MyCommonAPIs.sleepi(5);
        new WirelessQuickViewPage(false).selectsteptype.selectOption(icpInfo.get("Step Type"));
        MyCommonAPIs.sleepi(3);
        new WirelessQuickViewPage(false).configDispalyAd(icpInfo);
        MyCommonAPIs.sleepi(3);
        new WirelessQuickViewPage(false).savecaptiveportalstep.click();

        icpInfo.put("Step Type", "Play Video");
        icpInfo.put("Video Name", "welcome");
        icpInfo.put("Video Path", System.getProperty("user.dir") + "/src/test/resources/welcomevideo.mp4");
        MyCommonAPIs.sleepi(3);
        new WirelessQuickViewPage(false).clickcaptiveportalstep.click();
        MyCommonAPIs.sleepi(5);
        new WirelessQuickViewPage(false).selectsteptype.selectOption(icpInfo.get("Step Type"));
        MyCommonAPIs.sleepi(3);
        new WirelessQuickViewPage(false).configPlayVideo(icpInfo);
        MyCommonAPIs.sleepi(3);
        new WirelessQuickViewPage(false).savecaptiveportalstep.click();

        icpInfo.put("Step Type", "Payment by Paypal");
        icpInfo.put("Paypal Client ID", "12345678");
        icpInfo.put("Currency", "26");
        icpInfo.put("Amount", "1.99");
        MyCommonAPIs.sleepi(3);
        new WirelessQuickViewPage(false).clickcaptiveportalstep.click();
        MyCommonAPIs.sleepi(5);
        new WirelessQuickViewPage(false).selectsteptype.selectOption(icpInfo.get("Step Type"));
        MyCommonAPIs.sleepi(3);
        new WirelessQuickViewPage(false).configPaymentByPaypal(icpInfo);
        MyCommonAPIs.sleepi(3);
        new WirelessQuickViewPage(false).savecaptiveportalstep.click();

        icpInfo.put("Step Type", "Voucher");

        MyCommonAPIs.sleepi(3);
        new WirelessQuickViewPage(false).clickcaptiveportalstep.click();
        MyCommonAPIs.sleepi(5);
        new WirelessQuickViewPage(false).selectsteptype.selectOption(icpInfo.get("Step Type"));
        MyCommonAPIs.sleepi(3);
        new WirelessQuickViewPage(false).savecaptiveportalstep.click();
        MyCommonAPIs.sleepi(5);

        assertTrue($x("//span[text()='Payment by Paypal']").exists() && $x("//span[text()='Voucher']").exists()
                && $x("//span[text()='Play Video']").exists() && $x("//span[text()='Display Ad']").exists()
                && $x("//span[text()='Authentication Method']").exists(), "Configure icp failed.");
    }

}
