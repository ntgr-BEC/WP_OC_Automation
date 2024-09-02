package businessrouter.BusinessRouterFunction.QosSetup.BR_T432;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.BusinessRouterFunction.Administration.BR_T211.Config;
import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrAdvancedQosSetupPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;

    public String sTestStr = "BR_T432";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.QosSetup") // 必须要加，对应目录名
    @Story("BR_T432") // 对应testrunkey
    @Description("026-QoS - Quality of Service page") // 对应用例名字
    @TmsLink("1455471") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Check Dynamic QoS and Internet Bandwidth.")
    public void step2() {
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedBackupSettingsPage.DefaultDevice();
        System.out.println("#################12");
        BrLoginPage BrLoginPage1 = new BrLoginPage();
        BrLoginPage1.CheckNewPasswordLoginSuccess("password");
        System.out.println("#################12");
        BrAdvancedQosSetupPage BrAdvancedQosSetupPage = new BrAdvancedQosSetupPage();
        BrAdvancedQosSetupPage.OpenAdvancedQosSetupPage();
        MyCommonAPIs.sleepi(5);
        if (!BrAdvancedQosSetupPage.CheckDynamicQoS() && BrAdvancedQosSetupPage.CheckSpeedtest()) {
            micResult = true;
            assertTrue(micResult, "Pass:Dynamic QoS is disabled and speedtest is selected.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Dynamic QoS is not disabled and speedtest is not selected.");
        }
        if (micResult) {
            BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
            MyCommonAPIs.sleepi(5);
            BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_current.cfg");
            MyCommonAPIs.sleepi(180);
            brlogin.defaultLogin();
        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
