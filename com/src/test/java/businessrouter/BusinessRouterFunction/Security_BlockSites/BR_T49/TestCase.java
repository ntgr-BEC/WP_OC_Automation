package businessrouter.BusinessRouterFunction.Security_BlockSites.BR_T49;

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
    public String sTestStr = "BR_T49";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockSites") // 必须要加，对应目录名
    @Story("BR_T49") // 对应testrunkey
    @Description("008-Block Sites - Logs check - Allow") // 对应用例名字
    @TmsLink("1455052") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Add a keyword  to the block list, Browse a websit that not in the block list")
    public void step2() {
        String log = "[site allowed: renren.com]";
        String url = "www.baidu.com";
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
            assertTrue(micResult, "Pass:It would be blocked");
         } else {
             micResult = false;
             assertTrue(micResult, "Failed:It would not be blocked");
            
         }
         BrAdvancedLogsPage BrAdvancedLogsPage = new BrAdvancedLogsPage();
         BrAdvancedLogsPage.OpenLogsPage();
         MyCommonAPIs.sleepi(5);
         BrAdvancedLogsPage.clearLogoutput();
         MyCommonAPIs.sleepi(5);
         boolean result2, result3;
         HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
         TmsPageHandle= Brtmspage.GetBrowserHandles();
         TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlCommands);   
          if (TMSTCPResult == true ) {
              micResult =  true;
              assertTrue(micResult,"Pass:Can  broswer URL which isn't  in the block list !"  );             
          } else {
              micResult =  false;
              assertTrue(micResult,"Failed:Can broswer URL  which isn't  in the block list !"  );
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
               assertTrue(micResult, "Pass:log is correct about broswering URL which isn't  in the block list.");
           } else {
               micResult = false;
               assertTrue(micResult, "Failed:log is incorrect about broswering URL which isn't  in the block list.");
           }
           
          
            // restore setting
            BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
            BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
            MyCommonAPIs.sleepi(10);
            BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
            MyCommonAPIs.sleepi(180);
         
        
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
