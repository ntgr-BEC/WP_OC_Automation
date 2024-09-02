package businessrouter.BusinessRouterFunction.Security_Schedule.BR_T73;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.BusinessRouterFunction.Administration.BR_T211.Config;
import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedSecuritySchedulePage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;

    public String sTestStr = "BR_T73";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_Schedule") // 必须要加，对应目录名
    @Story("BR_T73") // 对应testrunkey
    @Description("005-Schedule - Invalid Start or End Time") // 对应用例名字
    @TmsLink("1455076") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: The Router will open a new error information window if start or end time is invalid")
    public void step2() {
        BrAdvancedSecuritySchedulePage BrAdvancedSecuritySchedulePage = new BrAdvancedSecuritySchedulePage();
        BrAdvancedSecuritySchedulePage.OpenSecuritySchedulePage();
        MyCommonAPIs.sleepi(10);
        boolean result1 = BrAdvancedSecuritySchedulePage.checkAllDaysError("2", "60", "", "59");
        MyCommonAPIs.sleepi(5);
        boolean result2 = BrAdvancedSecuritySchedulePage.checkAllDaysError("23", "59", "23", "59");
        if (result1 && result2) {
            micResult = true;
            assertTrue(micResult, "Pass:Error information window is open");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Error information window is not open");
        }
    }
    
    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }
}
