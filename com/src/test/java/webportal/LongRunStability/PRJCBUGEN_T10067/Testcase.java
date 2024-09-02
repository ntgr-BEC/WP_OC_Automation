package webportal.LongRunStability.PRJCBUGEN_T10067;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SQLiteStability;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    final String    tcName       = "[T10067] Check no critical logs for all networks";
    String          stepInfo     = "";
    String          expectResult = "";
    String          actualResult = "";
    SQLiteStability dbHandle     = null;

    @Feature("LongRunStability") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T10067") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description(tcName) // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T10067") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        dbHandle = new SQLiteStability();
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        evtp.gotoPage();
        evtp.deleteAllEvent();
    }

    @Step("Test Step 1: Log into Web Portal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
    }

    @Step("Test Step 2: Go to notification to check if there is any critical event;")
    public void step2() {
        stepInfo = "Check there is no critical event";
        evtp.gotoPage();
        evtp.Filter4Stability();
        MyCommonAPIs.sleepi(5);
        expectResult = "0";
        actualResult = "" + evtp.getEventCount();
        if (actualResult.equals(expectResult)) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, "Number of critical event should be 0",
                    actualResult + ": " + evtp.getEventSummary().toString());
        }
    }
}
