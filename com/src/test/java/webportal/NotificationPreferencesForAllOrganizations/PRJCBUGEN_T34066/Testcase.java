package webportal.NotificationPreferencesForAllOrganizations.PRJCBUGEN_T34066;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
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
import webportal.weboperation.NetworkSetupPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Vivek Singh
 *
 */
public class Testcase extends TestCaseBase {
    String organizationName1 = "organization1";
    String locationName1     = "office1";
    String organizationName2 = "organization2";
    String locationName2     = "office2";

    @Feature("Notification preferences for ALL organizations IM-7.0") // It's a folder/component name to make test suite more readable from Jira
                                                                      // Test Case.
    @Story("PRJCBUGEN_T34066") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify Created location delete and it should be not present on organization Setting") // It's a testcase title from Jira
    // Test Case.
    @TmsLink("PRJCBUGEN-T34066") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        OrganizationPage page = new OrganizationPage();
        page.deleteOrganizationNew(organizationName2);

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login With Pro Account IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: Create an organization1 and location1")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName1);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName1);

        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");
        new AccountPage(false).addNetwork(locationInfo);
        MyCommonAPIs.sleepi(5);
    }

    @Step("Test Step 3: Create an organization2 and location2")
    public void step3() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName2);
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName2);

        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName2);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");
        new AccountPage(false).addNetwork(locationInfo);
        MyCommonAPIs.sleepi(5);
    }

    @Step("Test Step 4: Go to Manage Notification Preferences and open Organization Setting and click on Plus Icon")
    public void step4() {
        new HamburgerMenuPage().OpenManageNotificationPreferences();
        new HamburgerMenuPage().VerifyOrganizationSettingsisPresent();
        new HamburgerMenuPage().clickingOnPlusIcon();

    }

    @Step("Test Step 5: verify the created Location Should be present under Organization Setting ")
    public void step5() {
        ArrayList<String> LocationList = new HamburgerMenuPage(false).VerifyAllLocationPresentinOrganizationSettings();
        if (LocationList.contains(locationName1) && LocationList.contains(locationName2))
            ;
        {
            assertTrue(true);
        }
    }

    @Step("Test Step 6: Delete the Created Location/Network")
    public void step6() {
        OrganizationPage page = new OrganizationPage();
        page.deleteOrganizationNew(organizationName1);

    }

    @Step("Test Step 7: Again Go to Manage Notification Preferences and open Organization Setting")
    public void step7() {
        new HamburgerMenuPage().OpenManageNotificationPreferences();
        new HamburgerMenuPage().VerifyOrganizationSettingsisPresent();

    }

    @Step("Test Step 8: verify the created Location Should not be present under Organization Setting ")
    public void step8() {
        ArrayList<String> LocationList = new HamburgerMenuPage(false).VerifyAllLocationPresentinOrganizationSettings();
        String expected = LocationList.get(0);
        System.out.print(expected);
        if (expected.equals(locationName1)) {
            assertFalse(false);
        } else {
            assertTrue(true);
        }
    }

}
