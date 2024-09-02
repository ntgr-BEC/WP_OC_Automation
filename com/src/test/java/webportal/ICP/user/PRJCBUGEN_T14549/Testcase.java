package webportal.ICP.user.PRJCBUGEN_T14549;

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
    Map<String, String> icpInfo                  = new HashMap<String, String>();

    @Feature("InstantCaptivePortal.Configuration") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14549") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify user is able to select exiting Desktop Background Image for dropdown below Add Desktop Background Image") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14549") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        String url = MyCommonAPIs.getCurrentUrl();
        if (url.contains("/wireless/captivePortal")) {
            new WirelessQuickViewPage(false).deleteIcpImage(ssidInfo.get("SSID"), "background");
        }
        new WirelessQuickViewPage().deleteSsidYes(ssidInfo.get("SSID"));
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);
    }

    @Step("Test Step 2: check ICP configuration;")
    public void step2() {
        if (!new HamburgerMenuPage().checkCaptivePortalServicesCredits()) {
            mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num);
            CaptivePortalPaymentInfo.put("First Name", mailname);
            CaptivePortalPaymentInfo.put("Last Name", "T14549");
            CaptivePortalPaymentInfo.put("Email", mailname + "@mailinator.com");
            CaptivePortalPaymentInfo.put("Quantity", "3"); // can input 1 , 3 , 10 , 40
            CaptivePortalPaymentInfo.put("Duration", "1"); // can input 1 , 3
            new InsightServicesPage().buyCaptivePortalProducts(CaptivePortalPaymentInfo);
        }
        handle.gotoLoction();

        ssidInfo.put("SSID", "apwp14549");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        icpInfo.put("Portal Name", "showad");
        icpInfo.put("Welcome Headline", "dhfjsdfjasdfhjsdhfshdfhsdjhfsdfjsdgfsk;fgjsdgsdfjsfdgsgfd");
        icpInfo.put("Captive Portal Logo", "DEFAULT_LOGO");
        icpInfo.put("Desktop Background Image Path", System.getProperty("user.dir") + "/src/test/resources/displayad.bmp");
        icpInfo.put("Desktop Background Image", "test14543new");
        icpInfo.put("Landing Page URL", "https://www.rediff.com");
        icpInfo.put("Session Duration", "5 min");
        icpInfo.put("Step Type", "Authentication Method");
        icpInfo.put("Login Modes", "Register.");
        new WirelessQuickViewPage().enableCaptivePortalType(ssidInfo.get("SSID"), icpInfo);

        new WirelessQuickViewPage(false).clickEditSsid(ssidInfo.get("SSID"));
        new WirelessQuickViewPage(false).entercaptiveportal.click();
        MyCommonAPIs.sleepi(10);
        Selenide.switchTo().frame(0);
        if ($x("//option[text()='" + icpInfo.get("Desktop Background Image") + "']").exists()) {
            String optionName = MyCommonAPIs.getText($x("//select[@id='mySelectBg']/option[2]"));
            new WirelessQuickViewPage(false).addbackgroundimage.selectOption(1);
            MyCommonAPIs.sleepi(3);
            Selenide.switchTo().defaultContent();
            MyCommonAPIs.sleepi(3);
            new WirelessQuickViewPage(false).savecaptive.click();
            MyCommonAPIs.sleepi(10);
            new WirelessQuickViewPage(false).captiveok.click();
            MyCommonAPIs.sleepi(5);

            new WirelessQuickViewPage(false).clickEditSsid(ssidInfo.get("SSID"));
            new WirelessQuickViewPage(false).entercaptiveportal.click();
            MyCommonAPIs.sleepi(10);
            Selenide.switchTo().frame(0);
            assertTrue($x("//option[text()='" + optionName + "']").exists(), "Select logo image error.");

            new WirelessQuickViewPage(false).addbackgroundimage.selectOption(2);
            MyCommonAPIs.sleepi(3);
        } else {
            assertTrue(false, "Upload logo image failed.");
        }
    }

}
