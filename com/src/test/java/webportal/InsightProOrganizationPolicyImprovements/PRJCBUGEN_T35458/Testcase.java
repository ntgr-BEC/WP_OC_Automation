package webportal.InsightProOrganizationPolicyImprovements.PRJCBUGEN_T35458;

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
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceScreenNavigationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Anusha H
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("InsightProOrganizationPolicyImprovements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35458") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify enable icon is working fine for scheduled reports.") // It's a testcase title
                                                                                                                 // from Jira Test Case.
    @TmsLink("PRJCBUGEN_T35458") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
 
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to the pro account ;")
    public void step1() {
 
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
            
            new OrganizationPage(false).openOrg(WebportalParam.Organizations);
            
    }

    @Step("Test Step 2:Enable icon for scheduled reports. ;")
    public void step2() {
        
        assertTrue(new OrganizationPage(false).ScheduleReportsEnableIcon(),"Enable icon is not working fine for scheduled reports.");
     
        
    }
    
}
