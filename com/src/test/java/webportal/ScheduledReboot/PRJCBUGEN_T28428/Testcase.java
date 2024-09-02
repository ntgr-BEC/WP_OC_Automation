package webportal.ScheduledReboot.PRJCBUGEN_T28428;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    String Name = "Schedule1";

    
    @Feature("ScheduledReboot") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28428") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, 'Search' button helps to search particular location") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T28428") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        System.out.println("start to do tearDown");
        AccountPage AccountPage =new AccountPage();
        AccountPage.deleteLocation("office2");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(WebportalParam.Organizations);

    }
    
    @Step("Test Step 2:Click on Add location icon")
    public void step2() {
        HashMap<String, String> locationInfo = new HashMap<String, String>();      
        locationInfo.put("Location Name", "office2");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "India");
        new AccountPage().addNetwork(locationInfo);
        MyCommonAPIs.waitReady();
     
    }   

    @Step("Test Step 3: go to Organization and check Reboot option is their or not;")
    public void step3() {
       new OrganizationPage().gotoSchedulereboot();
       assertTrue(new OrganizationPage(false).SearchLoc(WebportalParam.location1), "search have issue");
    }

   
   
}
