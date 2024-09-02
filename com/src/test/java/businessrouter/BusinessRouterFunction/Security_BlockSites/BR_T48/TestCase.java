package businessrouter.BusinessRouterFunction.Security_BlockSites.BR_T48;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrAdvancedLogsPage;
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
    public String sTestStr = "BR_T48";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockSites") // 必须要加，对应目录名
    @Story("BR_T48") // 对应testrunkey
    @Description("007-Block Sites - Logs check - Block") // 对应用例名字
    @TmsLink("1455051") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Set a block keyword, Broswer the URL contain the keywords")
    public void step2() {
        String log = "[site blocked: renren.com]";
        String url = "renren.com";
        boolean TMSTCPResult;
        BrAdvancedSecurityBlockSitesPage BrAdvancedSecurityBlockSitesPage = new BrAdvancedSecurityBlockSitesPage();
        BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedSecurityBlockSitesPage.blocksiteselectalw(url);
        MyCommonAPIs.sleepi(5);
        BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        boolean result1 = BrAdvancedSecurityBlockSitesPage.checkdomainlist(url);
        if (result1) {
            micResult = true;
            assertTrue(micResult, "Pass:It would not be blocked");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:It would be blocked");
        
        }
          boolean result2, result3;
          BrAdvancedLogsPage BrAdvancedLogsPage = new BrAdvancedLogsPage();
          BrAdvancedLogsPage.OpenLogsPage();
          MyCommonAPIs.sleepi(5);
          BrAdvancedLogsPage.clearLogoutput();
          MyCommonAPIs.sleepi(5);
          HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
          TmsPageHandle= Brtmspage.GetBrowserHandles();
          TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlCommands);   
         // MyCommonAPIs.sleepi(40);
          if (TMSTCPResult == false ) {
              micResult =  true;
              assertTrue(micResult,"Pass:Can not broswer URL!"  );             
          } else {
              micResult =  false;
              assertTrue(micResult,"Failed:Can broswer URL!"  );
          }
          Brtmspage.BackTMSConfigPafe();
           MyCommonAPIs.sleepi(5);
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
            MyCommonAPIs.sleepi(5);
            BrAdvancedLogsPage.OpenLogsPage();
            MyCommonAPIs.sleepi(5);
            if (BrAdvancedLogsPage.checkLogoutput(log)) {
                result3 = true;
            } else {
                result3 = false;
            }
            if (result3) {
                micResult = true;
                assertTrue(micResult, "Pass:It would not be blocked");
            } else {
                micResult = false;
                assertTrue(micResult, "Failed:It would be blocked");
            }
        
       
            // restore setting
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
