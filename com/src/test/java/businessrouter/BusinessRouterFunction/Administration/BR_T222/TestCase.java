package businessrouter.BusinessRouterFunction.Administration.BR_T222;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrBasicSetPasswordPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;
import util.XMLManagerForTest;

public class TestCase extends TestCaseBase {
    String tmpStr;

    public String sTestStr = "BR_T222";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Administration") // 必须要加，对应目录名
    @Story("BR_T222") // 对应testrunkey
    @Description("021-Change password to special characters") // 对应用例名字
    @TmsLink("1455225") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Change password to special characters")
    public void step2() {
        XMLManagerForTest xmlManager = new XMLManagerForTest();
        String OldPassword = xmlManager.getValueFromWebPortalXml("//config/loginInfo/loginPassword");
        String NewPassword = "~!@#$%^&*()_+-={}[]|\\:\"<>?";

        BrBasicSetPasswordPage BrBasicSetPasswordPage = new BrBasicSetPasswordPage();
        BrBasicSetPasswordPage.OpenSetPasswordPage();
        MyCommonAPIs.sleepi(10);
        BrBasicSetPasswordPage.ChangePassword(OldPassword, NewPassword);
        if (BrBasicSetPasswordPage.CheckChangePasswordError("not change")) {
            micResult = true;
            assertTrue(micResult, "Pass:show message \"The new password validation failed! \"");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Don't show message \"The new password validation failed! \"");
        }
    }
    
    @Step("Test Step 3: Return CaseResult")
    public void step53() {
        CaseResult = true;
    }
}
