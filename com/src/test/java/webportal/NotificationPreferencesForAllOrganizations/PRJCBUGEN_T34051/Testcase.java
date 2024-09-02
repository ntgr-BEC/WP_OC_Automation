package webportal.NotificationPreferencesForAllOrganizations.PRJCBUGEN_T34051;

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
import util.APUtils;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Vivek Singh
 *
 */
public class Testcase extends TestCaseBase {
    String organizationName = "organization";
    String locationName     = "office1";

    @Feature("Notification preferences for ALL organizations IM-7.0") // It's a folder/component name to make test suite more readable from Jira
                                                                      // Test Case.
    @Story("PRJCBUGEN_T34051") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the notification settings for organisation setting is present under Account management -> manage notification preferences") // It's
                                                                                                                                                             // a
                                                                                                                                                             // testcase
                                                                                                                                                             // title
                                                                                                                                                             // from
                                                                                                                                                             // Jira
    // Test Case.
    @TmsLink("PRJCBUGEN-T34051") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        OrganizationPage page = new OrganizationPage();
        page.deleteOrganizationNew(organizationName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login With Pro Account IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: Create a organization1 and location1")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName);

        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");
        new AccountPage(false).addNetwork(locationInfo);
        MyCommonAPIs.sleepi(5);
    }

    @Step("Test Step 3: Go to Manage Notification Preferencess Page")
    public void step3() {
        new HamburgerMenuPage().OpenManageNotificationPreferences();

    }

    @Step("Test Step 4: verify Organization Settings should be there")
    public void step4() {
        assertTrue(new HamburgerMenuPage(false).VerifyOrganizationSettingsisPresent(), "Organization Settings text is not Present");
    }

}
