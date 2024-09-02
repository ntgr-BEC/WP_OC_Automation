package webportal.CustomReports.PRJCBUGEN_T11202;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String      mailRecv   = null;
    String      reportName = "PRJCBUGEN_T11202";
    MailHandler mh         = new MailHandler();
    
    @Feature("Custom Reports") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11202") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the troubleshoot report accurately plots Top APs, SW, Clients.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11202") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: create troubleshoot reports for 4 top widgets")
    public void step2() {
        crPage.dataReportName = reportName;
        crPage.dataMailAddress = mailRecv;
        crPage.dataReportType = 1;
        crPage.gotoPage();
        crPage.setReport(reportName, 6);
        assertTrue(crPage.getReportList().contains(reportName), "check report with defalut value has created");
    }
    
    @Step("Test Step 3: preview of the report")
    public void step3() {
        crPage.gotoPage();
        crPage.doReportList(reportName, 3);
        crPage.gotoStep("5");
        crPage.btnReview.click();
        micResult = false;
        MyCommonAPIs.waitElement(crPage.txtReviewHeader.getSearchCriteria());
        MyCommonAPIs.sleepi(10);
        int nWidget = 0;
        for (String s : MyCommonAPIs.getTexts(crPage.txtReviewHeader.getSearchCriteria())) {
            if (s.contains("Top")) {
                nWidget++;
                micResult = true;
            }
        }
        assertTrue(micResult, "check must have Top widgets");
        assertTrue(nWidget == 3, "check must have 4 Top widgets");
        handle.clickBoxLastButton();
    }
}
