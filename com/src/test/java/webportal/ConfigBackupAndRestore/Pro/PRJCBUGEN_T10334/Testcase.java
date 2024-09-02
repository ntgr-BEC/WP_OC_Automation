package webportal.ConfigBackupAndRestore.Pro.PRJCBUGEN_T10334;

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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    String organizationName = "test10334";

    @Feature("ConfigBackupAndRestore.Pro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T10334") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify user is able to edit the auto created locations with Pro account login") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T10334") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new OrganizationPage().deleteOrganizationNew(organizationName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

    }

    @Step("Test Step 2: Check locations automatically create;")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        organizationInfo.put("Automatically Created", "Yes");
        organizationInfo.put("Number of Locations", "1");
        organizationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        organizationInfo.put("Zip Code", "12345");

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName);

        assertTrue(new AccountPage(false).checkLocationNumber(organizationInfo.get("Number of Locations")),
                "Locations automatically create failed.");
    }

    @Step("Test Step 3: Edit location name, check it's correct;")
    public void step3() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "edit10334");
        new AccountPage(false).editLocation(organizationName + "_Location_1", locationInfo);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(organizationName);
        assertTrue(new AccountPage(false).locationName(locationInfo.get("Location Name")).exists(), "Edit location name failed.");
    }

}
