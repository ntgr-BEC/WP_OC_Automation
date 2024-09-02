package webportal.CustomReports.PRJCBUGEN_T9672;

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
    String      reportName = "PRJCBUGEN_T9672";
    MailHandler mh         = new MailHandler();
    
    @Feature("Custom Reports") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9672") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the changes in the reports from daily, to weekly, Monthly and vice versa is taking effect.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9672") // It's a testcase id/link from Jira Test Case.
    
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
    
    @Step("Test Step 2: create report with weekly")
    public void step2() {
        crPage.dataReportName = reportName;
        crPage.dataMailAddress = mailRecv;
        crPage.dataRepeatType = 1;
        crPage.gotoPage();
        crPage.setReport(reportName, 0);
    }

    @Step("Test Step 3: modify report with monthly")
    public void step3() {
        crPage.dataTimeFrequency = 1;
        crPage.gotoPage();
        crPage.setReport(reportName, 0);
    }
    
    @Step("Test Step 4: verify report with monthly")
    public void step4() {
        crPage.dataTimeFrequency = 1;
        crPage.gotoPage();
        crPage.setCheckPointStep(7);
        crPage.setReport(reportName, 0);
        assertTrue(crPage.getCheckPointResult(), "check report is modified to monthly");
    }
}
