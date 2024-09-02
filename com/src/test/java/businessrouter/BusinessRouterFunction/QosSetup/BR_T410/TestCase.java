package businessrouter.BusinessRouterFunction.QosSetup.BR_T410;

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

    public String sTestStr = "BR_T410";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.QosSetup") // 必须要加，对应目录名
    @Story("BR_T410") // 对应testrunkey
    @Description("004-QoS - Basic page(QoS)") // 对应用例名字
    @TmsLink("1455449") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Check \"Enable Dynamic QoS\" and apply, check if Dynamic QoS is enabled")
    public void step2() {
        BrAdvancedQosSetupPage BrAdvancedQosSetupPage = new BrAdvancedQosSetupPage();
        handle.sleepi(15);
        BrAdvancedQosSetupPage.OpenAdvancedQosSetupPage();
        MyCommonAPIs.sleepi(15);
        BrAdvancedQosSetupPage.DefineInternetBandwidth("10", "10");
        MyCommonAPIs.sleepi(5);
        BrAdvancedQosSetupPage.EnableOrDisableQoS("Enable");
        MyCommonAPIs.sleepi(15);
        if (BrAdvancedQosSetupPage.CheckDynamicQoS()) {
            micResult = true;
            assertTrue(micResult, "Pass:Dynamic QoS is enabled.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Dynamic QoS is not enabled.");
        }
        if (micResult) {
            BrAdvancedQosSetupPage.EnableOrDisableQoS("Disable");
            BrAdvancedQosSetupPage.SelectSpeedtest();
        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
