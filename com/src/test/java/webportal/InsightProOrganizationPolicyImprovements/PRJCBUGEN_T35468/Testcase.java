package webportal.InsightProOrganizationPolicyImprovements.PRJCBUGEN_T35468;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

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
    String organizationName = "PRJCBUGEN_T35468";

    @Feature("InsightProOrganizationPolicyImprovements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35468") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user select apply to all organisation while adding a new organisation then check that will be reflected in all organisation.") // It's a testcase title
                                                                                                                 // from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35468") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        OrganizationPage page = new OrganizationPage();
        page.deleteOrganizationNew(organizationName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to the pro account ;")
    public void step1() {
 
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
     
    }

    @Step("Test Step 2:Add a organization and  make changes in the policy and Apply to all organizations;")
    public void step2() {
        OrganizationPage page = new OrganizationPage();
        page.gotoPage();

        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        
        page.addOrganization(organizationInfo);    
        new OrganizationPage(false).openOrg(organizationName);
        new OrganizationPage(false).OrgApplytoAllOrg();          
        
    }
    
    @Step("Test Step 3:Check whether the Appy to all Organisations is present in existing organisation;")
    public void step3() {

        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        assertTrue(new OrganizationPage(false).VerifyOrgApplytoAllOrg(),"Applied policy changes are not reflected in existing organization;");          
        
    }
    
}
