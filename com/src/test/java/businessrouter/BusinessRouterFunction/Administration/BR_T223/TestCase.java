package businessrouter.BusinessRouterFunction.Administration.BR_T223;

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
import util.XMLManagerForTest;
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase {
    String tmpStr;

    public String sTestStr = "BR_T223";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Administration") // 必须要加，对应目录名
    @Story("BR_T223") // 对应testrunkey
    @Description("022-Change password to 32 characters include uppercase letters, lowercase letters, numbers and symbols") // 对应用例名字
    @TmsLink("1455226") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Change password to 32 characters include uppercase letters, lowercase letters, numbers and symbols")
    public void step2() {
        XMLManagerForTest xmlManager = new XMLManagerForTest();
        String OldPassword = xmlManager.getValueFromWebPortalXml("//config/loginInfo/loginPassword");
        String NewPassword = "Test!@@$%^&*()1234567890abcd1212";

        BrBasicSetPasswordPage BrBasicSetPasswordPage = new BrBasicSetPasswordPage();
        BrBasicSetPasswordPage.OpenSetPasswordPage();
        MyCommonAPIs.sleepi(10);
        BrBasicSetPasswordPage.ChangePassword(OldPassword, NewPassword);
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
        if (micResult) {
            // restore setting
            BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
            BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
            MyCommonAPIs.sleepi(10);
            if (WebportalParam.DUTType.contentEquals("BR500")) {
                BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
            } else if(WebportalParam.DUTType.contentEquals("BR100")) {
                BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR100_default.cfg");
                
            }
            MyCommonAPIs.sleepi(180);
            Selenide.refresh();
            BrLoginPage.defaultLogin();
        }
    }
    
    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }
}
