package businessrouter.BusinessRouterFunction.QosSetup.BR_T427;

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
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;

    public String sTestStr = "BR_T427";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.QosSetup") // 必须要加，对应目录名
    @Story("BR_T427") // 对应testrunkey
    @Description("021-QoS - Manual set downlink and uplink value") // 对应用例名字
    @TmsLink("1455466") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Check downlink and uplink value.")
    public void step2() {
        BrAdvancedQosSetupPage BrAdvancedQosSetupPage = new BrAdvancedQosSetupPage();
        BrAdvancedQosSetupPage.OpenAdvancedQosSetupPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedQosSetupPage.DefineInternetBandwidth(" ", " ");
        boolean result1 = BrAdvancedQosSetupPage.CheckErrorDialog();
        MyCommonAPIs.sleepi(5);
        BrAdvancedQosSetupPage.DefineInternetBandwidth("0.09", "1000");
        boolean result2 = BrAdvancedQosSetupPage.CheckErrorDialog();
        MyCommonAPIs.sleepi(5);
        BrAdvancedQosSetupPage.DefineInternetBandwidth("0.1", "1000.1");
        boolean result3 = BrAdvancedQosSetupPage.CheckErrorDialog();
        MyCommonAPIs.sleepi(5);
        BrAdvancedQosSetupPage.DefineInternetBandwidth("0.1", " ");
        boolean result4 = BrAdvancedQosSetupPage.CheckErrorDialog();
        MyCommonAPIs.sleepi(5);
        BrAdvancedQosSetupPage.DefineInternetBandwidth(" ", "1000");
        boolean result5 = BrAdvancedQosSetupPage.CheckErrorDialog();
        if (result1 && result2 && result3 && result4 && result5) {
            micResult = true;
            assertTrue(micResult, "Pass:Downlink and uplink value input is correct.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Downlink and uplink value input is incorrect.");
        }
        if (micResult) {
            BrAdvancedQosSetupPage.SelectSpeedtest();
        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
