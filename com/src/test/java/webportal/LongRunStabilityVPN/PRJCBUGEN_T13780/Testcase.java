package webportal.LongRunStabilityVPN.PRJCBUGEN_T13780;

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
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    final String    tcName       = "[T13780] Check Insight VPN tunnel status on Cloud";
    String          stepInfo     = "";
    String          expectResult = "";
    String          actualResult = "";
    SQLiteStability dbHandle     = null;

    @Feature("LongRunStabilityVPN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13780") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description(tcName) // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13780") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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
    }

    @Step("Test Step 2: On Web portal, verify VPN tunnel status")
    public void step2() {
        handle.gotoLoction();
        handle.openOneBRDevice(false);
        brdvgp.gotoPage();

        stepInfo = "Check Router VPN Group tunel status between all BR500s";
        expectResult = String.format("VPN group links under '%s' should be healthy", WebportalParam.longrunVpnGroupName);
        actualResult = String.format("'%s' link is/are broken or in connecting", WebportalParam.longrunVpnGroupName);
        boolean vpnStatus = false;
        for (int i = 0; i < 20; i++) {
            try {
                brdvgp.gotoPage();
                vpnStatus = brdvgp.isLinkHealthy(WebportalParam.longrunVpnGroupName);
            } catch (Throwable e) {
                e.printStackTrace();
                MyCommonAPIs.takess();
            }
            if (vpnStatus) {
                break;
            }

            handle.refresh();
            MyCommonAPIs.sleepsync();
        }
        dbHandle.writeResult(vpnStatus, tcName, stepInfo, expectResult, actualResult);

    }
}
