package businessrouter.BusinessRouterFunction.TrafficMeter.BR_T195;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.BusinessRouterFunction.Administration.BR_T211.Config;
import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedTrafficMeterPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;

    public String sTestStr = "BR_T195";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.TrafficMeter") // 必须要加，对应目录名
    @Story("BR_T195") // 对应testrunkey
    @Description("012-Traffic Meter - Start date/time") // 对应用例名字
    @TmsLink("1455198") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Click \"Restart Counter Now\" button,check start date and time.")
    public void step2() {
        BrAdvancedTrafficMeterPage BrAdvancedTrafficMeterPage = new BrAdvancedTrafficMeterPage();
        BrAdvancedTrafficMeterPage.OpenTrafficMeterPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedTrafficMeterPage.EnableTrafficMeter();
        MyCommonAPIs.sleepi(3);
        BrAdvancedTrafficMeterPage.ClickTrafficRestartButton();
        if (BrAdvancedTrafficMeterPage.CheckStartTime()) {
            micResult = true;
            assertTrue(micResult, "Pass:Start date and time display correct.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Start date and time display incorrect.");
        }
//        if (micResult) {
//            // restore setting
//            BrAdvancedTrafficMeterPage.OpenTrafficMeterPage();
//            MyCommonAPIs.sleepi(5);
//            BrAdvancedTrafficMeterPage.DisableTrafficMeter();
//        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
