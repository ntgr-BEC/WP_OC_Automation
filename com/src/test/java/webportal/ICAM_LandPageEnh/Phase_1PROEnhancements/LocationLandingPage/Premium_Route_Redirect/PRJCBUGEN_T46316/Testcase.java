package webportal.ICAM_LandPageEnh.Phase_1PROEnhancements.LocationLandingPage.Premium_Route_Redirect.PRJCBUGEN_T46316;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Premium_Route_Redirect") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T46316") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that on clicking “Total devices” , it should route to location device table with filter option selected")
    @TmsLink("PRJCBUGEN_T46316") // It's a testcase id/link from Jira Test Case.

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
    }

    @Step("Test Step 2: Check whether connected connect is shown in Connected Client header tab count;")
    public void step2() {

        MyCommonAPIs.sleepi(5);
        String totaldevices = new OrganizationPage(false).locDashTotalDevice.shouldBe(Condition.visible).getText();
        System.out.println("Total devices count on Location dashboard page : " + totaldevices);
        assertTrue(totaldevices.trim().equals("4"), "Total devices count is not shown in Connected Client header tab count");
        assertTrue(new OrganizationPage(false).verifyTotalDevicesCountCliableAndredirecttodevicesPageVerifyActiveFilter(),
                "on devices dashboard page active filter is visisble");
    }

}
