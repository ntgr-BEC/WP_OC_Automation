package webportal.LongRunStability.PRJCBUGEN_T9026;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SQLiteStability;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    final String    tcName       = "[T9026] Check NAS device info on Cloud";
    String          stepInfo     = "";
    String          expectResult = "";
    String          actualResult = "";
    SQLiteStability dbHandle     = null;

    @Feature("LongRunStability") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9026") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description(tcName) // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9026") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        dbHandle = new SQLiteStability();
        runTest(this);
    }

    @Step("Test Step 1: Log into Web Portal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
    }

    @Step("Test Step 2: Go to network1, check NAS Status is connected;")
    public void step2() {
        stepInfo = "Check NAS Status is connected in 1st network";
        handle.gotoLoction(WebportalParam.location1);
        ddp.gotoPage();
        String apNo = ddp.getNASDevice();
        expectResult = "Connected";
        actualResult = ddp.getDeviceStatus(apNo);
        if (actualResult.equals(expectResult)) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }

        stepInfo = "Check NAS uptime in 1st network";
        expectResult = "Not NA";
        actualResult = ddp.getDeviceUptime(apNo, false);
        if (!actualResult.equals("NA")) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }
    }
}
