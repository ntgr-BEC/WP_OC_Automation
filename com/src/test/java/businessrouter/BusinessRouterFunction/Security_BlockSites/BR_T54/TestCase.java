package businessrouter.BusinessRouterFunction.Security_BlockSites.BR_T54;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.BusinessRouterFunction.Administration.BR_T211.Config;
import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrAdvancedSecurityBlockSitesPage;
import businessrouter.webpageoperation.BrDashboardPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;

    public String sTestStr = "BR_T54";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockSites") // 必须要加，对应目录名
    @Story("BR_T54") // 对应testrunkey
    @Description("013-Block Sites - Reboot") // 对应用例名字
    @TmsLink("1455057") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Do some block sites config and reboot DUT, check configurations")
    public void step2() {
        String[] keyword = {
                "baidu", "renren", "qq", "weixin"
        };
        BrAdvancedSecurityBlockSitesPage BrAdvancedSecurityBlockSitesPage = new BrAdvancedSecurityBlockSitesPage();
        BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        MyCommonAPIs.sleepi(5);
        for (int i = 0; i < keyword.length; i++) {
            BrAdvancedSecurityBlockSitesPage.blocksiteselectnev(keyword[i]);
            MyCommonAPIs.sleepi(5);
            BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        }
        boolean result1 = BrAdvancedSecurityBlockSitesPage.checkdomainlistarray(keyword);
        if (result1) {
            BrDashboardPage BrDashboardPage = new BrDashboardPage();
            BrDashboardPage.OpenDashboardPage();
            MyCommonAPIs.sleepi(5);
            BrDashboardPage.RebootDUT();
            MyCommonAPIs.sleepi(60);
            BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.defaultLogin();
            BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
            MyCommonAPIs.sleepi(5);
            boolean result2 = BrAdvancedSecurityBlockSitesPage.checkdomainlistarray(keyword);
            if (result2) {
                micResult = true;
                assertTrue(micResult, "Pass:The router can be rebooted with all the configurations saved");
            } else {
                micResult = false;
                assertTrue(micResult, "Failed:The router can not be rebooted with all the configurations saved");
            }
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Config fail");
        }
        MyCommonAPIs.sleepi(5);
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.defaultLogin();
        if (micResult) {
            // restore setting
            BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
            BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
            MyCommonAPIs.sleepi(10);
            BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
            MyCommonAPIs.sleepi(120);
        
        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }
}
