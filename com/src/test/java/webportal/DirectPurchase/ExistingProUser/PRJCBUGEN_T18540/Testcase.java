package webportal.DirectPurchase.ExistingProUser.PRJCBUGEN_T18540;

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
import webportal.param.WebportalParam;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);
    String              account     = mailname + "@mailinator.com";
    Map<String, String> paymentInfo = new HashMap<String, String>();

    @Feature("DirectPurchase.ExistingProUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18540") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify existing Pro user able to purchase direct Pro credits") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T18540") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        String url = MyCommonAPIs.getCurrentUrl();
        if (url.contains("dashboard")) {
            new HamburgerMenuPage(true).checkDisplayCancelDirectPurchase("pro");
        } else {
            Selenide.close();
            startBrowser();
            WebportalLoginPage webportallogin = new WebportalLoginPage(true);
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Buy pro subscription;")
    public void step1() {
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T18540");
        paymentInfo.put("Email", account);
        paymentInfo.put("Street Address", "Street 4568 James Avenue");
        paymentInfo.put("City", "INVERNESS");
        paymentInfo.put("Zip", "34451");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        paymentInfo.put("Card Number", "4112344112344113");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");

        new HamburgerMenuPage(false).openInsightSubscriptionPlanPage();
//        new HamburgerMenuPage(false).clickLoginAndSubscribe("pro");

        new WebportalLoginPage(true).inputLogin(WebportalParam.adminName, WebportalParam.adminPassword);

        new InsightServicesPage(false).inputPaymentPage(paymentInfo);
        MyCommonAPIs.sleepsync();

        String url = MyCommonAPIs.getCurrentUrl();
        assertTrue(url.contains("dashboard"), "Place order failed.");
    }

}
