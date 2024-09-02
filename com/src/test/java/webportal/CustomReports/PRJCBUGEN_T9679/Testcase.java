package webportal.CustomReports.PRJCBUGEN_T9679;

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
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String      mailRecv   = null;
    String      reportName = "PRJCBUGEN_T9679";
    MailHandler mh         = new MailHandler();
    
    @Feature("Custom Reports") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9679") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the report search button is functional in the Reports menu.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9679") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        mailRecv = mh.getRandomAddress();
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to Insight Webportal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }
    
    @Step("Test Step 2: Verify search on no report")
    public void step2() {
        crPage.gotoPage();
        crPage.deleteAll();

        crPage.searchReport("nonexisted");
        assertTrue(crPage.getReportList().size() == 0, "check it should not show any report");
    }
    
    @Step("Test Step 3: Verify search on one report")
    public void step3() {
        crPage.dataReportName = reportName;
        crPage.dataMailAddress = mailRecv;
        crPage.gotoPage();
        crPage.setReport(reportName, 0);

        crPage.searchReport("nonexisted");
        assertTrue(crPage.getReportList().size() == 0, "check it should not show any report on non-match");
        
        crPage.searchReport(reportName);
        assertTrue(crPage.getReportList().size() == 1, "check it should show matched report");
    }
}
