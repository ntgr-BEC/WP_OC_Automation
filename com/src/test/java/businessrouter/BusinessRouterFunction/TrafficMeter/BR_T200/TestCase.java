package businessrouter.BusinessRouterFunction.TrafficMeter.BR_T200;

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

    public String sTestStr = "BR_T200";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.TrafficMeter") // 必须要加，对应目录名
    @Story("BR_T200") // 对应testrunkey
    @Description("017-Traffic Meter - Traffic reset") // 对应用例名字
    @TmsLink("1455203") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: When reset traffic counter manually, DUT should not be restarted.")
    public void step2() {
        BrAdvancedTrafficMeterPage BrAdvancedTrafficMeterPage = new BrAdvancedTrafficMeterPage();
        BrAdvancedTrafficMeterPage.OpenTrafficMeterPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedTrafficMeterPage.EnableTrafficMeter();
        if (BrAdvancedTrafficMeterPage.CheckTrafficCounterRestartInput("checkdut")) {
            micResult = true;
            assertTrue(micResult, "Pass:When reset traffic counter manually, DUT not restart.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:When reset traffic counter manually, DUT restart");
        }
//        if (micResult) {
//            Selenide.refresh();
//            MyCommonAPIs.sleepi(5);
//            BrAdvancedTrafficMeterPage.DisableTrafficMeter();
//        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
