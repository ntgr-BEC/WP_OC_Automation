package webportal.CustomReports.PRJCBUGEN_T9669;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MailHandler;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String      mailRecv1  = null;
    String      mailRecv2  = null;
    String      reportName = "PRJCBUGEN_T9669";
    MailHandler mh         = new MailHandler();

    @Feature("Custom Reports") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9669") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify user able to send the report to multiple emails at once") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9669") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        mailRecv1 = mh.getRandomAddress();
        mailRecv2 = mh.getRandomAddress();
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

//     Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to Insight Webportal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: create report with 2 mails")
    public void step2() {
        crPage.dataReportName = reportName;
        crPage.dataMailAddress = WebportalParam.adminName + "," + "w2proacc@yopmail.com";
        crPage.gotoPage();
        crPage.setReport(reportName, 0);
    }
    
    @Step("Test Step 3: check mails")
    public void step3() {
//        mh.initMail(mailRecv1);
//        assertTrue(mh.isReportMail(), "check mail for user: " + mailRecv1);
//        mh.initMail(mailRecv2);
//        assertTrue(mh.isReportMail(), "check mail for user: " + mailRecv2);

        assertTrue(new HamburgerMenuPage(false).checkEmailMessageForCustomReports(WebportalParam.adminName), "Not received verify email.");
        MyCommonAPIs.sleepi(10);
        Selenide.switchTo().window(0);
        assertTrue(new HamburgerMenuPage(false).checkEmailMessageForCustomReports("w2proacc@yopmail.com"), "Not received verify email.");
        Selenide.switchTo().window(0);
    }
}
