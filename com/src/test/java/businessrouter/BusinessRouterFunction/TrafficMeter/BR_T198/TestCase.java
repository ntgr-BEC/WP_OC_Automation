package businessrouter.BusinessRouterFunction.TrafficMeter.BR_T198;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.BusinessRouterFunction.Administration.BR_T211.Config;
import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedSecuritySchedulePage;
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

    public String sTestStr = "BR_T198";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.TrafficMeter") // 必须要加，对应目录名
    @Story("BR_T198") // 对应testrunkey
    @Description("015-Traffic Meter - Time Zone changed") // 对应用例名字
    @TmsLink("1455201") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Change Time Zone,check traffic counter.")
    public void step2() {
        String time = "1100am";
        String day = "16";
        BrAdvancedTrafficMeterPage BrAdvancedTrafficMeterPage = new BrAdvancedTrafficMeterPage();
        BrAdvancedTrafficMeterPage.OpenTrafficMeterPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedTrafficMeterPage.EnableTrafficMeter();
        MyCommonAPIs.sleepi(3);
        BrAdvancedTrafficMeterPage.TrafficCounterConfig(time, day);
        MyCommonAPIs.sleepi(3);
        BrAdvancedSecuritySchedulePage BrAdvancedSecuritySchedulePage = new BrAdvancedSecuritySchedulePage();
        BrAdvancedSecuritySchedulePage.OpenSecuritySchedulePage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedSecuritySchedulePage.TimeZoneSelect("GMT+05");
        MyCommonAPIs.sleepi(3);
        BrAdvancedTrafficMeterPage.OpenTrafficMeterPage();
        MyCommonAPIs.sleepi(5);
        if (BrAdvancedTrafficMeterPage.CheckTrafficCounterTimeAndDate(time, day)) {
            micResult = true;
            assertTrue(micResult, "Pass:Traffic counter not change.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Traffic counter change.");
        }
        if (micResult) {
            // restore setting
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
