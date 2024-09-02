package businessrouter.BusinessRouterFunction.TrafficMeter.BR_T199;

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

    public String sTestStr = "BR_T199";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.TrafficMeter") // 必须要加，对应目录名
    @Story("BR_T199") // 对应testrunkey
    @Description("016-Traffic Meter - Traffic Counter Reset by per schedule") // 对应用例名字
    @TmsLink("1455202") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: check if correct traffic counter will be cleared when the traffic counter is reset by per schedule.")
    public void step2() {
        BrAdvancedTrafficMeterPage BrAdvancedTrafficMeterPage = new BrAdvancedTrafficMeterPage();
        BrAdvancedTrafficMeterPage.OpenTrafficMeterPage();
        MyCommonAPIs.sleepi(10);
        BrAdvancedTrafficMeterPage.EnableTrafficMeter();
        if (BrAdvancedTrafficMeterPage.CheckTrafficCounterRestart()) {
            micResult = true;
            assertTrue(micResult, "Pass:traffic counter clear.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:traffic counter will not clear.");
        }
        MyCommonAPIs.sleepi(5);
        if (micResult) {
            BrAdvancedTrafficMeterPage.OpenTrafficMeterPage();
            MyCommonAPIs.sleepi(5);
            BrAdvancedTrafficMeterPage.TrafficCounterDefault();
        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
