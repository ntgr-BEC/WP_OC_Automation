package businessrouter.BusinessRouterFunction.Security_BlockSites.BR_T45;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrAdvancedSecurityBlockSitesPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;
    String HistroyHandle;
    String TmsPageHandle;
    public String sTestStr = "BR_T45";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockSites") // 必须要加，对应目录名
    @Story("BR_T45") // 对应testrunkey
    @Description("004-Block Sites - Case insensitive") // 对应用例名字
    @TmsLink("1455048") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Add a keywork,and select Keyword Blocking is always")
    public void step2() {
        boolean TMSTCPResult;
        String keyword = "baidu";
        BrAdvancedSecurityBlockSitesPage BrAdvancedSecurityBlockSitesPage = new BrAdvancedSecurityBlockSitesPage();
        BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        MyCommonAPIs.sleepi(10);
        BrAdvancedSecurityBlockSitesPage.blocksiteselectalw(keyword);
        boolean result1 = BrAdvancedSecurityBlockSitesPage.checkdomainlist(keyword);

        if (result1) {
            micResult = true;
            assertTrue(micResult, "Failed:Configuration fail");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Configuration fail");
        }
            HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
            TmsPageHandle= Brtmspage.GetBrowserHandles();
            
            TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlCommands);      
            if (TMSTCPResult == false ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Can't access the block URL with the blocked keys!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Can access the block URL with the blocked keys!"  );
            }
           Brtmspage.BackTMSConfigPafe();
           Brtmspage.ChangeBrowserHandles(HistroyHandle); 
           MyCommonAPIs.sleepi(5);
           BrLoginPage BrLoginPage = new BrLoginPage();
       
            BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
            BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
            MyCommonAPIs.sleepi(10);
            BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
            MyCommonAPIs.sleepi(150);
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }
}
