package businessrouter.BusinessRouterFunction.Security.BR_T497;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrBasicSetPasswordPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;

    public String sTestStr = "BR_T497";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security") // 必须要加，对应目录名
    @Story("BR_T497") // 对应testrunkey
    @Description("012-The complexity of password") // 对应用例名字
    @TmsLink("1455822") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Change error admin password,check error message.")
    public void step2() {
        BrBasicSetPasswordPage BrBasicSetPasswordPage = new BrBasicSetPasswordPage();
        BrBasicSetPasswordPage.OpenSetPasswordPage();
        MyCommonAPIs.sleepi(10);
        BrBasicSetPasswordPage.ChangePassword(WebportalParam.loginPassword, NewPassword1);
        boolean result1 = BrBasicSetPasswordPage.CheckChangePasswordError("no change");
        BrBasicSetPasswordPage.ChangePassword(WebportalParam.loginPassword, NewPassword2);
        boolean result2 = BrBasicSetPasswordPage.CheckChangePasswordError("no change");
        BrBasicSetPasswordPage.ChangePassword(WebportalParam.loginPassword, NewPassword3);
        boolean result3 = BrBasicSetPasswordPage.CheckChangePasswordError("no change");
        BrBasicSetPasswordPage.ChangePassword(WebportalParam.loginPassword, NewPassword4);
        boolean result4 = BrBasicSetPasswordPage.CheckChangePasswordError("no change");
        System.out.print(result1);
        System.out.print(result2);
        System.out.print(result3);
        System.out.print(result4);
        if (result1 && result2 && result3 && result4) {
            micResult = true;
            assertTrue(micResult, "Pass:Config error password failure.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Config error password success.");
        }
    }

    @Step("Test Step 3: Change admin password,check login.")
    public void step3() {
        BrBasicSetPasswordPage BrBasicSetPasswordPage = new BrBasicSetPasswordPage();
        BrBasicSetPasswordPage.OpenSetPasswordPage();
        MyCommonAPIs.sleepi(10);
        BrBasicSetPasswordPage.ChangePassword(WebportalParam.loginPassword, NewPassword);
        boolean result = BrBasicSetPasswordPage.CheckChangePasswordError("change");
        MyCommonAPIs.sleepi(3);
        BrLoginPage BrLoginPage = new BrLoginPage();
        if (result && BrLoginPage.CheckNewPasswordLoginSuccess(NewPassword)) {
            micResult = true;
            assertTrue(micResult, "Pass:Use new password login success");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Use new password login fail");
        }
        // restore setting
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(10);
        BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
        MyCommonAPIs.sleepi(180);
       
    }
    
    @Step("Test Step 4: Return CaseResult")
    public void step4() {
        CaseResult = true;
    }

}
