package businessrouter.BusinessRouterFunction.Security_BlockSites.BR_T534;

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

    public String sTestStr = "BR_T534";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockSites") // 必须要加，对应目录名
    @Story("BR_T534") // 对应testrunkey
    @Description("018-Block site combine with Automatically adjust for daylight savings time schedule") // 对应用例名字
    @TmsLink("1459045") // 对应用例详情页的URL最后几位数字

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
        MyCommonAPIs.sleepi(10);
    }

    @Step("Test Step 2: Block site combine with Automatically adjust for daylight savings time schedule")
    public void step2() {
        BrAdvancedSecuritySchedulePage BrAdvancedSecuritySchedulePage = new BrAdvancedSecuritySchedulePage();
        BrAdvancedSecuritySchedulePage.OpenSecuritySchedulePage();
        MyCommonAPIs.sleepi(15);
        BrAdvancedSecuritySchedulePage.disabledaylight();
        String dishour = BrAdvancedSecuritySchedulePage.getCurrentTime();
        BrAdvancedSecuritySchedulePage.enabledaylight();
        String enhour = BrAdvancedSecuritySchedulePage.getCurrentTime();
        System.out.println(dishour);
        System.out.println(enhour);
        if (Integer.valueOf(enhour) - Integer.valueOf(dishour) == 1) {
            micResult = true;
            assertTrue(micResult, "Pass:Time display correct");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Time display incorrect");
        }
        if (micResult) {
            BrAdvancedSecuritySchedulePage.disabledaylight();
        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }
}
