package webportal.InsightProOrganizationPolicyImprovements.PRJCBUGEN_T35475;

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
import webportal.weboperation.ManagerPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Anusha H
 *
 */
public class Testcase extends TestCaseBase {
    String organizationName = "PRJCBUGEN_T35463";

    @Feature("InsightProOrganizationPolicyImprovements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35475") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user creating an organisation with existing admin email then \"Select admin as Owner\" popup will be displayed when we click save button.") // It's a testcase title
                                                                                                                 // from Jira Test Case.
    @TmsLink("PRJCBUGEN_T35475") // It's a testcase id/link from Jira Test Case.

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
            
    }
    
     @Step("Test Step 2:Add organisation with the same name and email as ADMIN;")
     public void step2() {
         
            OrganizationPage page = new OrganizationPage();
            page.gotoPage();

            Map<String, String> organizationInfo = new HashMap<String, String>();
            organizationInfo.put("Name", organizationName);
            organizationInfo.put("Owner Name", "Same is admin");
            organizationInfo.put("Email Address", WebportalParam.adminName);
            
            assertTrue(new OrganizationPage(false).addOrganization1(organizationInfo),"Not getting an error message even when the owner name is same as admin");
    }
            
    

  
}
