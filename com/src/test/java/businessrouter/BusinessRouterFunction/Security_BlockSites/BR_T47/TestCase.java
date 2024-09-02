package businessrouter.BusinessRouterFunction.Security_BlockSites.BR_T47;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrAdvancedSecurityBlockSitesPage;
import businessrouter.webpageoperation.BrAdvancedSecuritySchedulePage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;

    public String sTestStr = "BR_T47";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockSites") // 必须要加，对应目录名
    @Story("BR_T47") // 对应testrunkey
    @Description("006-Block Sites - Erase Router config") // 对应用例名字
    @TmsLink("1455050") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Config some keywords, schedule")
    public void step2() {
        BrAdvancedSecuritySchedulePage BrAdvancedSecuritySchedulePage = new BrAdvancedSecuritySchedulePage();
        BrAdvancedSecuritySchedulePage.OpenSecuritySchedulePage();
        MyCommonAPIs.sleepi(10);
        boolean result1 = BrAdvancedSecuritySchedulePage.setAllDays("12", "0", "23", "59");
        if (result1) {
            micResult = true;
            assertTrue(micResult, "Pass:Configuration pass");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Configuration fail");
        }
        String[] keyword = {
          "www.baidu.com", "renren", "www.qq.com", "www.12306.cn"
        };
         BrAdvancedSecurityBlockSitesPage BrAdvancedSecurityBlockSitesPage = new BrAdvancedSecurityBlockSitesPage();
         BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
         MyCommonAPIs.sleepi(10);
         for (int i = 0; i < keyword.length; i++) {
                BrAdvancedSecurityBlockSitesPage.blocksiteselectalw(keyword[i]);
                MyCommonAPIs.sleepi(5);
                BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
          }
            boolean result2 = BrAdvancedSecurityBlockSitesPage.checkdomainlistarray(keyword);
            if (result2) {
                micResult = true;
                assertTrue(micResult, "Pass:Configuration successfully");
            } else {
                micResult = false;
                assertTrue(micResult, "Failed:Configuration fail");
            }
        
    }

    @Step("Test Step 3:Factory default DUT,Check security settings")
    public void step3() {
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(10);
        BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
        MyCommonAPIs.sleepi(150);
        //MyCommonAPIs.sleepi(10);
       // BrAdvancedBackupSettingsPage.DefaultDevice();
       // BrLoginPage BrLoginPage = new BrLoginPage();
       // BrLoginPage.CheckNewPasswordLoginSuccess("password");
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.defaultLogin();
        BrAdvancedSecuritySchedulePage BrAdvancedSecuritySchedulePage = new BrAdvancedSecuritySchedulePage();
        BrAdvancedSecuritySchedulePage.OpenSecuritySchedulePage();
        MyCommonAPIs.sleepi(10);
        boolean result1 = BrAdvancedSecuritySchedulePage.checkAllDayadnEveryDay();
        if (result1) {
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:The configged keywords ，schedule are not all erased");
        }
        
            String[] keyword = {
                    "www.baidu.com", "renren", "www.qq.com", "www.12306.cn"
            };
            BrAdvancedSecurityBlockSitesPage BrAdvancedSecurityBlockSitesPage = new BrAdvancedSecurityBlockSitesPage();
            BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
            MyCommonAPIs.sleepi(10);
            boolean result2 = BrAdvancedSecurityBlockSitesPage.checkdomainlistarray(keyword);
            if (result2) {
                micResult = true;
                assertTrue(micResult, "Pass:The configged keywords ，schedule are all erased");
            } else {
                micResult = false;
                assertTrue(micResult, "Failed:The configged keywords ，schedule are not all erased");
            }
            //BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
            BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
            MyCommonAPIs.sleepi(10);
            BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
            //BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.defaultLogin();
            MyCommonAPIs.sleepi(150);
        
    }

    @Step("Test Step 4: Return CaseResult")
    public void step4() {
        CaseResult = true;
    }

}
