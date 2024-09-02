package businessrouter.BusinessRouterFunction.Security.BR_T498;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.BusinessRouterFunction.Security.BR_T497.Config;
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

    public String sTestStr = "BR_T498";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security") // 必须要加，对应目录名
    @Story("BR_T498") // 对应testrunkey
    @Description("011-Switch support reset password") // 对应用例名字
    @TmsLink("1455823") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Change admin password,check login")
    public void step2() {
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(10);
        BrAdvancedBackupSettingsPage.DefaultDevice();

        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.CheckNewPasswordLoginSuccess("password");
        BrBasicSetPasswordPage BrBasicSetPasswordPage = new BrBasicSetPasswordPage();
        BrBasicSetPasswordPage.OpenSetPasswordPage();
        MyCommonAPIs.sleepi(10);
        BrBasicSetPasswordPage.ChangePassword("password", "Netgear1@");
        boolean result = BrBasicSetPasswordPage.CheckChangePasswordError("change");
        if (result && BrLoginPage.CheckNewPasswordLoginSuccess("Netgear1@")) {
            micResult = true;
            assertTrue(micResult, "Pass:Use new password login success");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Use new password login fail");
        }
    }

    @Step("Test Step 3: Use default password,check login")
    public void step3() {
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(10);
        BrAdvancedBackupSettingsPage.DefaultDevice();

        BrLoginPage BrLoginPage = new BrLoginPage();
        if (BrLoginPage.CheckNewPasswordLoginSuccess("password")) {
            micResult = true;
            assertTrue(micResult, "Pass:Use default password login success");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Use default password login fail");
        }
        // restore setting
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
