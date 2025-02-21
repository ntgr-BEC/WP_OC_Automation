package webportal.CreateOrganization.PRJCBUGEN_T13879;

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
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String organizationName = "PRJCBUGEN_T13879";

    @Feature("CreateOrganization") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13879") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that the user is able to change the organization owner") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13879") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1", enabled = false)
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        OrganizationPage page = new OrganizationPage();
        page.deleteOrganizationNew(organizationName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to Insight Webportal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: to change the organization owner")
    public void step2() {
        OrganizationPage page = new OrganizationPage();
        page.gotoPage();
        
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        
        page.addOrganization(organizationInfo);

        if (page.checkOrganizationIsExist(organizationName)) {
            organizationInfo.put("Owner Name", WebportalParam.adminName);
            page.editOrganization(organizationInfo);
            assertTrue(new OrganizationPage().checkOrganizationOwner(organizationInfo), "Own name information is incorrect.");
        } else {
            assertTrue(false, "Created organization failed.");
        }
    }
}
