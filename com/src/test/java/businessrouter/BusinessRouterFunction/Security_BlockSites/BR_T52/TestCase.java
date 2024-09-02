package businessrouter.BusinessRouterFunction.Security_BlockSites.BR_T52;

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
    public String sTestStr = "BR_T52";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockSites") // 必须要加，对应目录名
    @Story("BR_T52") // 对应testrunkey
    @Description("011-Block Sites - Never") // 对应用例名字
    @TmsLink("1455055") // 对应用例详情页的URL最后几位数字

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
        MyCommonAPIs.sleepi(10);
    }

    @Step("Test Step 2: Set block site with option \"NEVER\",Config some keyword to be blocked,check result")
    public void step2() {
        String[] keyword = {
                "www.baidu.com", "renren", "www.qq.com", "www.weixin.com"
        };
        boolean TMSTCPResult;
        BrAdvancedSecurityBlockSitesPage BrAdvancedSecurityBlockSitesPage = new BrAdvancedSecurityBlockSitesPage();
        BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        MyCommonAPIs.sleepi(10);
        for (int i = 0; i < keyword.length; i++) {
            BrAdvancedSecurityBlockSitesPage.blocksiteselectnev(keyword[i]);
            //
            MyCommonAPIs.sleepi(5);
            BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        }
        boolean result1 = BrAdvancedSecurityBlockSitesPage.checkdomainlistarray(keyword);
        if (result1) {
            micResult = true;
            assertTrue(micResult, "Pass:It would be blocked");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:It would not be blocked");
        }
        MyCommonAPIs.sleepi(2);
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        TmsPageHandle= Brtmspage.GetBrowserHandles();
        TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlbaiduCommands);   
       // MyCommonAPIs.sleepi(40);
        if (TMSTCPResult == true ) {
            micResult =  true;
            assertTrue(micResult,"Pass:Can broswer baidu URL!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:Can't broswer baidu URL!"  );
        }
        Brtmspage.BackTMSConfigPafe();
        TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlrenrenCommands);   
        // MyCommonAPIs.sleepi(40);
         if (TMSTCPResult == true ) {
             micResult =  true;
             assertTrue(micResult,"Pass:Can broswer renren URL!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult,"Failed:Can't broswer renren URL!"  );
         }
         Brtmspage.BackTMSConfigPafe();
         TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlqqCommands);   
         // MyCommonAPIs.sleepi(40);
          if (TMSTCPResult == true ) {
              micResult =  true;
              assertTrue(micResult,"Pass:Can broswer qq URL!"  );             
          } else {
              micResult =  false;
              assertTrue(micResult,"Failed:Can't broswer qq URL!"  );
          }
          Brtmspage.BackTMSConfigPafe();
          TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlweixinCommands);   
          // MyCommonAPIs.sleepi(40);
           if (TMSTCPResult == true ) {
               micResult =  true;
               assertTrue(micResult,"Pass:Can broswer weixin URL!"  );             
           } else {
               micResult =  false;
               assertTrue(micResult,"Failed:Can't broswer weixin URL!"  );
           }
           Brtmspage.BackTMSConfigPafe();

            Brtmspage.ChangeBrowserHandles(HistroyHandle);
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
