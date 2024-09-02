package businessrouter.BusinessRouterFunction.Administration.BR_T213;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

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

    public String sTestStr = "BR_T213";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Administration") // 必须要加，对应目录名
    @Story("BR_T213") // 对应testrunkey
    @Description("012-Restore configuration file use empty file") // 对应用例名字
    @TmsLink("1455216") // 对应用例详情页的URL最后几位数字

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
        MyCommonAPIs.sleepi(5);
    }

    @Step("Test Step 2: Restore a empty setting file")
    public void step2() {
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedBackupSettingsPage.RestoreEmptySetting(filepath);
        if (BrAdvancedBackupSettingsPage.CheckRestoreSuccess()) {
            micResult = true;
            assertTrue(micResult, "Pass:Restore empty configuration fail.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Restore empty configuration success.");
        }
    }
    
    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }
}
