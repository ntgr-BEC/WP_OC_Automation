package businessrouter.BusinessRouterFunction.QosSetup.BR_T442;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.BusinessRouterFunction.Administration.BR_T211.Config;
import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedQosSetupPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;

    public String sTestStr = "BR_T442";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.QosSetup") // 必须要加，对应目录名
    @Story("BR_T442") // 对应testrunkey
    @Description("036-QoS - Check Reboot") // 对应用例名字
    @TmsLink("1455480") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: ")
    public void step2() {
        String downloadvalue = "10";
        String uploadvalue = "10";
        BrAdvancedQosSetupPage BrAdvancedQosSetupPage = new BrAdvancedQosSetupPage();
        BrAdvancedQosSetupPage.OpenAdvancedQosSetupPage();
        handle.sleepi(5);
        BrAdvancedQosSetupPage.DefineInternetBandwidth(downloadvalue, uploadvalue);
        handle.sleepi(5);
        System.out.println("1@@@@@@@@@@@@@@#$$$$$$$");
        BrAdvancedQosSetupPage.EnableOrDisableQoS("Enable");
        System.out.println("2@@@@@@@@@@@@@@#$$$$$$$");
        handle.sleepi(5);
        brdashboard.OpenDashboardPage();
        handle.sleepi(10);
        brdashboard.RebootDUT();
        handle.sleepi(80);
        brlogin.defaultLogin();
        handle.sleepi(15);
        BrAdvancedQosSetupPage.OpenAdvancedQosSetupPage();
        handle.sleepi(15);
        if (BrAdvancedQosSetupPage.CheckDynamicQoS() && BrAdvancedQosSetupPage.CheckDefineInternetBandwidth()
                && BrAdvancedQosSetupPage.CheckDefineInternetBandwidthValue(downloadvalue, uploadvalue)) {
            micResult = true;
            assertTrue(micResult, "Pass:Downlink and uplink value input is correct.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Downlink and uplink value input is incorrect.");
        }
        if (micResult) {
            BrAdvancedQosSetupPage.EnableOrDisableQoS("Disable");
            BrAdvancedQosSetupPage.SelectSpeedtest();
        }
        brlogin.BrLogout();
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
