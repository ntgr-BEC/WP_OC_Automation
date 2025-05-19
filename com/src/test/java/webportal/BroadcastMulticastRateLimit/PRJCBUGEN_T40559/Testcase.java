package webportal.BroadcastMulticastRateLimit.PRJCBUGEN_T40559;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Pratik
 *
 */
public class Testcase extends TestCaseBase {
    

    @Feature("BroadcastMulticastRateLimit") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40559") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the default rate limit value should be 64") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40559") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();

    }

    @Step("Test Step 2: Varify the default rate limit value should be 64")
    public void step2() {

        new WirelessQuickViewPage().GoToWirelessSettings();
        String defaultratelimit = new WirelessQuickViewPage(false).valueContainerBCMCRateLimit.getText().trim();
        System.out.println("Default rate limit value is: " + defaultratelimit);
        assertTrue(defaultratelimit.equals("64"),"Default rate limit value is not 64");

    }

}
