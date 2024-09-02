package businessrouter.BusinessRouterFunction.Security.BR_T496;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.BusinessRouterFunction.Administration.BR_T211.Config;
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

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;

    public String sTestStr = "BR_T496";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security") // 必须要加，对应目录名
    @Story("BR_T496") // 对应testrunkey
    @Description("006-Force users to change the default username and password") // 对应用例名字
    @TmsLink("1455821") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Factory default the switch,then check use default password login")
    public void step2() {
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(10);
        BrAdvancedBackupSettingsPage.DefaultDevice();

        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.CheckNewPasswordLoginSuccess("password");
        MyCommonAPIs.sleepi(10);
        BrLoginPage.BrLogout();

        if (BrLoginPage.CheckNewPasswordLoginSuccess("password")) {
            micResult = true;
            assertTrue(micResult, "Pass:Continue use default password login success.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Continue use default password login fail.");
        }
    }

    @Step("Test Step 3: Change the password with valid characters,then check use new password login")
    public void step3() {
        String NewPassword = "Test!@@$%^&*()1234567890abcd1212";

        BrBasicSetPasswordPage BrBasicSetPasswordPage = new BrBasicSetPasswordPage();
        BrBasicSetPasswordPage.OpenSetPasswordPage();
        MyCommonAPIs.sleepi(10);
        BrBasicSetPasswordPage.ChangePassword("password", NewPassword);
        BrBasicSetPasswordPage.CheckChangePasswordError("change");
        MyCommonAPIs.sleepi(3);
        BrLoginPage BrLoginPage = new BrLoginPage();
        if (BrLoginPage.CheckNewPasswordLoginSuccess(NewPassword)) {
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
