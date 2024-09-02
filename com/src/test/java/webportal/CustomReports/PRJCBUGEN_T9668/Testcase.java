package webportal.CustomReports.PRJCBUGEN_T9668;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MailHandler;
import webportal.param.WebportalParam;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String      mailRecv       = null;
    String      reportName     = "PRJCBUGEN_T9668";
    MailHandler mh             = new MailHandler();
    String      orgName        = "Orgt9668";
    boolean     bNeedDeleteOrg = false;

    @Feature("Custom Reports") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9668") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if a device, location, Organization is deleted , the filter for the report is also is updated.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9668") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        mailRecv = mh.getRandomAddress();
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        crPage.gotoPage();
        crPage.deleteAll();
        if (bNeedDeleteOrg) {
            OrganizationPage op = new OrganizationPage();
            op.gotoPage();
            op.deleteOrganization(orgName);
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to Insight Webportal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: create a org to be used for report")
    public void step2() {
        handle.gotoOrganization();
        new OrganizationPage().addOrganization(orgName, "1");
        bNeedDeleteOrg = true;

        crPage.dataReportName = reportName;
        crPage.dataMailAddress = mailRecv;
        crPage.gotoPage();
        crPage.setReport(reportName, 0);
    }

    @Step("Test Step 3: after deleted org check update filter")
    public void step3() {
        handle.gotoOrganization();
        new OrganizationPage().deleteOrganization(orgName);
        bNeedDeleteOrg = false;
        
        crPage.dataOrgName = orgName;
        crPage.gotoPage();
        crPage.setCheckPointStep(6);
        crPage.setReport(reportName, 0);
        assertTrue(crPage.getCheckPointResult(), "check report filter has new org");
    }
}
