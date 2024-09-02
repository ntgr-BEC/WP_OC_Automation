package webportal.Multipack.PRJCBUGEN_T17903;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random              r                 = new Random();
    int                 num               = r.nextInt(10000000);
    String              mailname          = "wptest" + String.valueOf(num);
    Map<String, String> DeviceCreditsInfo = new CommonDataType().PAYMENT_INFO;

    @Feature("Multipack") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17903") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify Current Subscription is showing all required details:- Type,Activation,Expiration,Price Per Device,Yearly Billing.") // It's
                                                                                                                                                      // a
                                                                                                                                                      // testcase
                                                                                                                                                      // title
                                                                                                                                                      // from
                                                                                                                                                      // Jira
                                                                                                                                                      // Test
                                                                                                                                                      // Case.
    @TmsLink("PRJCBUGEN-T17903") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new HamburgerMenuPage().cancelDeviceCredits();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);
    }

    @Step("Test Step 2: Buy insight premium subscriptions and check subscriptions page;")
    public void step2() {
        new HamburgerMenuPage().gotoInsightPremiumSubscriptions();

        DeviceCreditsInfo.put("First Name", "New");
        DeviceCreditsInfo.put("Last Name", "New");
        DeviceCreditsInfo.put("Email", mailname + "@mailinator.com");
        DeviceCreditsInfo.put("Device Credits Pack", "5");
        DeviceCreditsInfo.put("Buy Year", "1");
        new InsightServicesPage(false).buyInsightPremiumSubscriptions(DeviceCreditsInfo);

        assertTrue(new HamburgerMenuPage(false).checkSubscriptionsPage("Insight Premium", DeviceCreditsInfo.get("Device Credits Pack")),
                "Subscriptions page display error.");
    }

}
