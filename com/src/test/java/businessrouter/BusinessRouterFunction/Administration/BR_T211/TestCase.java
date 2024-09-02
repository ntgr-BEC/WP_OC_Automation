package businessrouter.BusinessRouterFunction.Administration.BR_T211;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;

    public String sTestStr = "BR_T211";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Administration") // 必须要加，对应目录名
    @Story("BR_T211") // 对应testrunkey
    @Description("010-Backup configuration") // 对应用例名字
    @TmsLink("1455214") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Backup a current settings")
    public void step2() {
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(5);
        if (BrAdvancedBackupSettingsPage.SaveSetting(filepath)) {
            micResult = true;
            assertTrue(micResult, "Pass:Backup configuration success.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Backup configuration fali.");
        }
    }

    @Step("Test Step 3: Restore a current settings")
    public void step3() {
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
//        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedBackupSettingsPage.RestoreSetting(filepath);
        if (BrAdvancedBackupSettingsPage.CheckRestoreSuccess()) {
            micResult = true;
            assertTrue(micResult, "Pass:Restore configuration success.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Restore configuration fali.");
        }
    }

    @Step("Test Step 4: Check login after restore")
    public void step4() {
        MyCommonAPIs.sleepi(100);
        Selenide.refresh();
        BrLoginPage BrLoginPage = new BrLoginPage();
        if (BrLoginPage.CheckNewPasswordLoginSuccess("Test@123")) {
            micResult = true;
            assertTrue(micResult, "Pass:Login after restore success.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Login after restore fali.");
        }
    }

    @Step("Test Step 5: Return CaseResult")
    public void step5() {
        CaseResult = true;
    }
}
