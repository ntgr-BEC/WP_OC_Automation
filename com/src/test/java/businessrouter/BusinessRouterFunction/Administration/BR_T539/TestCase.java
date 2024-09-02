package businessrouter.BusinessRouterFunction.Administration.BR_T539;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

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

public class TestCase extends TestCaseBase {
    String tmpStr;

    public String sTestStr = "BR_T539";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Administration") // 必须要加，对应目录名
    @Story("BR_T539") // 对应testrunkey
    @Description("030-Factory default device on GUI") // 对应用例名字
    @TmsLink("1459897") // 对应用例详情页的URL最后几位数字

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
        MyCommonAPIs.sleepi(20);
    }

    @Step("Test Step 2: Go to Advanced->Backup Settings,click Erase button")
    public void step2() {
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(20);
        BrAdvancedBackupSettingsPage.DefaultDevice();
        System.out.println("2341fadsfastwert456365347657890980ijhg");
        MyCommonAPIs.sleepi(10);
        Selenide.close();
        BrLoginPage BrLoginPage = new BrLoginPage();
        System.out.println("2341fadsfastwert456365347657890980ijhg");
        if (BrLoginPage.CheckLoginSuccess()) {
            BrBasicSetPasswordPage BrBasicSetPasswordPage = new BrBasicSetPasswordPage();
            MyCommonAPIs.sleepi(10);
            BrBasicSetPasswordPage.ChangePassword("password", "Test@123");
            BrBasicSetPasswordPage.CheckChangePasswordError("change");
            micResult = true;
            assertTrue(micResult, "Pass:Factory default success");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Factory default fail");
        }
        Selenide.close();
        MyCommonAPIs.sleepi(20);
        BrLoginPage.defaultLogin();
        MyCommonAPIs.sleepi(20);
        //BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(10);
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
        } else if(WebportalParam.DUTType.contentEquals("BR100")) {
            BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR100_default.cfg");
            
        }
        MyCommonAPIs.sleepi(100);
    }
    
    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }
}
