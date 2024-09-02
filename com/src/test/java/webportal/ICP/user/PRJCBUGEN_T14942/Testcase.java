package webportal.ICP.user.PRJCBUGEN_T14942;

import static org.testng.Assert.assertTrue;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;

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

    @Feature("InstantCaptivePortal.IcpUnderAccount.BasicOrPremiumUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14942") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that user is able to cancel ICP supscription within 3 days of buying it") // It's a test case title from Jira TestCase.
    @TmsLink("PRJCBUGEN-T14942") // It's a testcase id/link from Jira Test Case.

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
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);
        
        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Check ICP credits;")
    public void step2() {
        mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num);
        CaptivePortalPaymentInfo.put("First Name", mailname);
        CaptivePortalPaymentInfo.put("Last Name", "T14942");
        CaptivePortalPaymentInfo.put("Email", mailname + "@mailinator.com");
        CaptivePortalPaymentInfo.put("Quantity", "3"); // can input 1 , 3 , 10 , 40
        CaptivePortalPaymentInfo.put("Duration", "1"); // can input 1 , 3
        new InsightServicesPage().buyCaptivePortalProducts(CaptivePortalPaymentInfo);

        if (new HamburgerMenuPage().checkCaptivePortalServicesCredits()) {
            new HamburgerMenuPage().cancelICPServices();
            assertTrue(!new HamburgerMenuPage().checkCaptivePortalServicesCredits(), "<2> Captive portal services credits is incorrect.");
        } else {
            assertTrue(false, "<1> Captive portal services credits is incorrect.");
        }
    }

}
