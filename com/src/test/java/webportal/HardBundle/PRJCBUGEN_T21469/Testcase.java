package webportal.HardBundle.PRJCBUGEN_T21469;

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
import webportal.weboperation.DeviceBulkOnboardingPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Tejeshwini
 *
 */
public class Testcase extends TestCaseBase {
    
    String                  organizationName = "OnBoardingTest";
    String                  locationName     = "OnBoardingTest";

    @Feature("Hardbundle") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T21469") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify Bulk addition of hard bundle devices") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T21469") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: Create Organization and Location ")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName);
        HashMap<String, String> locationInfo     = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfo);
        
        new HamburgerMenuPage().configCreditAllocation(organizationName, 4, 0, 0);
    }
    
    @Step("Test Step 3:  Add hardbundle device")
    public void step3() {
        
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(organizationName);
        new AccountPage(false).enterLocation(locationName);
        new DeviceBulkOnboardingPage().GoToSummaryPage(locationName);
        String filePath = new HardBundlePage().GetcurrentPath(locationName);
        filePath = filePath + "PRJCBUGEN_T21469\\Device.csv";
        System.out.println(filePath);
        new DeviceBulkOnboardingPage().ImportCvsFilesummaryscreen(filePath);
//        new HardBundlePage().OnboardBulk();
        
    }
    

}
