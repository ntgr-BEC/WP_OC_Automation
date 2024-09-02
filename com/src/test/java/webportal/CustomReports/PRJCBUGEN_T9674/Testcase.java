package webportal.CustomReports.PRJCBUGEN_T9674;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String      mailRecv   = null;
    String      reportName = "PRJCBUGEN_T9674";
    MailHandler mh         = new MailHandler();
    
    @Feature("Custom Reports") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9674") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if there is email address validation done on the send email  page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9674") // It's a testcase id/link from Jira Test Case.
    
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
    
    @Step("Test Step 2: Error message for invalid enetered emial address")
    public void step2() {
        crPage.dataReportName = reportName;
        crPage.dataMailAddress = "xxx";
        crPage.gotoPage();
        crPage.setCheckPointStep(11);
        crPage.setReport(reportName, 0);
        System.out.println("0000000000000");
        assertTrue(new HamburgerMenuPage(false).errorMSG(), "Not received error message.");
//        assertTrue(handle.getPageErrorMsg().length() > 4, "check there should has a error msg to mail");
    }
}
