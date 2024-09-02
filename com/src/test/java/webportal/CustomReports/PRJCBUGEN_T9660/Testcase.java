package webportal.CustomReports.PRJCBUGEN_T9660;

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
    String      reportName = "PRJCBUGEN_T9660";
    MailHandler mh         = new MailHandler();
    
    @Feature("Custom Reports") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_TT9660") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the user is able to create reports of type Troubleshooting with different widgets") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-TT9660") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        mailRecv = mh.getRandomAddress();
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        crPage.gotoPage();
        crPage.deleteAll();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to Insight Webportal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: create a custom report of type Troubleshooting with different widgets")
    public void step2() {
        crPage.dataReportName = reportName;
        crPage.dataMailAddress = mailRecv;
        crPage.dataReportType = 1;
        crPage.gotoPage();
        crPage.setReport(reportName, 2);
        assertTrue(crPage.getReportList().contains(reportName), "check report with defalut value has created");
    }
}
