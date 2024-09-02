package businessrouter.BusinessRouterFunction.Administration.BR_T212;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config{
    String tmpStr;

    public String sTestStr = "BR_T212";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Administration") // 必须要加，对应目录名
    @Story("BR_T212") // 对应testrunkey
    @Description("011-Restore configuration use wrong file") // 对应用例名字
    @TmsLink("1455215") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Backup wrong file")
    public void step2() {
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(5);
        if (BrAdvancedBackupSettingsPage.SaveSetting(filepath)) {
            micResult = true;
            assertTrue(micResult, "Pass:Backup wrong configuration success.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Backup wrong configuration fali.");
        }
    }

    @Step("Test Step 3: Restore wrong file")
    public void step3() {
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(10);
        BrAdvancedBackupSettingsPage.RestoreSetting1(filepath);
        if (BrAdvancedBackupSettingsPage.CheckRestorWrongMessage()) {
            micResult = true;
            assertTrue(micResult, "Pass:Restore wrong configuration fail.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Restore wrong configuration success.");
        }
    }
    
    @Step("Test Step 4: Return CaseResult")
    public void step4() {
        CaseResult = true;
    }
}
