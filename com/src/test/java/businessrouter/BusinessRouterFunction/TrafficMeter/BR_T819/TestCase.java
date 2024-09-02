package businessrouter.BusinessRouterFunction.TrafficMeter.BR_T819;

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

    public String sTestStr = "BR_T819";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.TrafficMeter") // 必须要加，对应目录名
    @Story("BR_T819") // 对应testrunkey
    @Description("019-Set Poll Interval") // 对应用例名字
    @TmsLink("1500978") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Config poll interval,check error.")
    public void step2() {
        boolean result1, result2, result3, result4;
        BrAdvancedTrafficMeterPage BrAdvancedTrafficMeterPage = new BrAdvancedTrafficMeterPage();
        BrAdvancedTrafficMeterPage.OpenTrafficMeterPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedTrafficMeterPage.EnableTrafficMeter();
        BrAdvancedTrafficMeterPage.ClickTrafficStatusButton();
        BrAdvancedTrafficMeterPage.SetPollInterval("0");
        result1 = BrAdvancedTrafficMeterPage.Checkdialogtext();
        BrAdvancedTrafficMeterPage.SetPollInterval("5");
        result2 = !BrAdvancedTrafficMeterPage.Checkdialogtext();
        BrAdvancedTrafficMeterPage.SetPollInterval("86400");
        result3 = !BrAdvancedTrafficMeterPage.Checkdialogtext();
        BrAdvancedTrafficMeterPage.SetPollInterval("99999");
        result4 = BrAdvancedTrafficMeterPage.Checkdialogtext();
        if (result1 && result2 && result3 && result4) {
            micResult = true;
            assertTrue(micResult, "Pass:Set poll interval is correct.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Set poll interval is incorrect.");
        }
        if (micResult) {
            BrAdvancedTrafficMeterPage.ClickTrafficStatusButton();
            BrAdvancedTrafficMeterPage.SetPollInterval("10");
            BrAdvancedTrafficMeterPage.CloseTrafficStatus();
        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
