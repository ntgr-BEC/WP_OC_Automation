package webportal.LongRunStabilityVPN.PRJCBUGEN_T16292;

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
import webportal.publicstep.PublicButton;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    final String    tcName       = "[T16292] Test VPN server connection";
    String          stepInfo     = "";
    String          expectResult = "";
    String          actualResult = "";
    SQLiteStability dbHandle     = null;

    @Feature("LongRunStabilityVPN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16292") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description(tcName) // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16292") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        dbHandle = new SQLiteStability();
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    @Step("Test Step 1: Log into Web Portal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: View device detail, and click \"Test VPN\" on right-upper menu")
    public void step2() {
        stepInfo = "Check test result of device to VPN server connection";
        handle.openOneBRDevice(true);
        expectResult = "Connection to VPN Server is okay";
        actualResult = "Connection to VPN Server is fail";
        boolean bRet = false;
        for (int i = 0; i < 10; i++) {
            bRet = new PublicButton().CheckTestVPNServer();
            if (bRet) {
                break;
            }
            MyCommonAPIs.sleepsync();
        }
        dbHandle.writeResult(bRet, tcName, stepInfo, expectResult, actualResult);
    }
}
