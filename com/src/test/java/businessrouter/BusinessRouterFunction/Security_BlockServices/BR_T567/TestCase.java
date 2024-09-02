package businessrouter.BusinessRouterFunction.Security_BlockServices.BR_T567;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedSecurityBlockServicesPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;

    public String sTestStr = "BR_T567";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockServices") // 必须要加，对应目录名
    @Story("BR_T567") // 对应testrunkey
    @Description("014-Block Services - Service Type is duplicated") // 对应用例名字
    @TmsLink("1460207") // 对应用例详情页的URL最后几位数字

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
        MyCommonAPIs.sleepi(15);
    }

    @Step("Test Step 2: Add two same block service rules.")
    public void step2() {
        BrAdvancedSecurityBlockServicesPage BrAdvancedSecurityBlockServicesPage = new BrAdvancedSecurityBlockServicesPage();
        BrAdvancedSecurityBlockServicesPage.OpenSecurityBlockServicesPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedSecurityBlockServicesPage.blockserviceselect(blockservicesconfig1);
        MyCommonAPIs.sleepi(3);
        BrAdvancedSecurityBlockServicesPage.blockserviceselect(blockservicesconfig2);
        if (BrAdvancedSecurityBlockServicesPage.checkdialog()) {
            micResult = true;
            assertTrue(micResult, "Pass:Not allow to add");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Allow to add");
        }
        if (micResult) {
            BrAdvancedSecurityBlockServicesPage.blockservicesdeleteconfig();
        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
