package businessrouter.BusinessRouterFunction.Logs.BR_T206;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedLogsPage;
import businessrouter.webpageoperation.BrAdvancedVlanPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;

    public String sTestStr = "BR_T206";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Logs") // 必须要加，对应目录名
    @Story("BR_T206") // 对应testrunkey
    @Description("005-Logs-Router operation") // 对应用例名字
    @TmsLink("1455209") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p2") // 标记测试用例
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    @Step("Test Step 1: Open Device")
    public void step1() {
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.defaultLogin();
    }

    @Step("Test Step 2: Check \"Router operation\" is correct.")
    public void step2() {
        BrAdvancedLogsPage BrAdvancedLogsPage = new BrAdvancedLogsPage();
        BrAdvancedLogsPage.OpenLogsPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedLogsPage.selectcheckbox(4);
        MyCommonAPIs.sleepi(3);
        BrAdvancedLogsPage.clearLogoutput();
        MyCommonAPIs.sleepi(3);
        brdashboard.OpenDashboardPage();
        brdashboard.RebootDUT();
        MyCommonAPIs.sleepi(60);
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.defaultLogin();
        BrAdvancedLogsPage.OpenLogsPage();
        MyCommonAPIs.sleepi(5);
        if (BrAdvancedLogsPage.checkLogoutput("[Initialized, firmware version")) {
            MyCommonAPIs.sleepi(5);
            BrAdvancedVlanPage BrAdvancedVlanPage = new BrAdvancedVlanPage();
            BrAdvancedVlanPage.OpenAdvancedVlanPage();
            BrAdvancedVlanPage.OpenAdvancedVlanPage();
            MyCommonAPIs.sleepi(3);
            BrAdvancedVlanPage.AddVlan(VLAN);
            MyCommonAPIs.sleepi(3);
            BrAdvancedVlanPage.DeleteVLAN(VLAN.get("VLAN ID"));
            MyCommonAPIs.sleepi(3);

            BrAdvancedLogsPage.OpenLogsPage();
            MyCommonAPIs.sleepi(5);
            boolean result = BrAdvancedLogsPage.checkLogoutput("[Web Operation] INFO add vlan 3 entry ok")
                    && BrAdvancedLogsPage.checkLogoutput("[Web Operation] INFO delete vlan 3 entry ok")
                    && BrAdvancedLogsPage.checkLogoutput("[Web Operation] INFO edit port pvid ok");
            MyCommonAPIs.sleepi(5);
            BrAdvancedLogsPage.disablecheckbox(4);
            MyCommonAPIs.sleepi(5);
            BrAdvancedLogsPage.refreshLogoutput();
            MyCommonAPIs.sleepi(5);
            if (result && BrAdvancedLogsPage.checkLogoutput("")) {
                micResult = true;
                assertTrue(micResult, "Pass:\"Connections to the Web-based interface of this Router\" is correct.");
            } else {
                micResult = false;
                assertTrue(micResult, "Failed:\"Connections to the Web-based interface of this Router\" is incorrect.");
            }
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:\"Connections to the Web-based interface of this Router\" is incorrect.");
        }

        if (micResult) {
            BrAdvancedLogsPage.OpenLogsPage();
            MyCommonAPIs.sleepi(5);
            BrAdvancedLogsPage.selectallheckbox();
        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
