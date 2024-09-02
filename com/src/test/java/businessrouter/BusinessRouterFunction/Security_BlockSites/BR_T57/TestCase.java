package businessrouter.BusinessRouterFunction.Security_BlockSites.BR_T57;

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
    public String sTestStr = "BR_T57";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockSites") // 必须要加，对应目录名
    @Story("BR_T57") // 对应testrunkey
    @Description("016-Block sites test with many keywords") // 对应用例名字
    @TmsLink("1455060") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Router reset to factory default,and Add keyword on WebGUI")
    public void step2() {
        boolean TMSTCPResult;
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
       // BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
       // MyCommonAPIs.sleepi(10);
        //BrAdvancedBackupSettingsPage.DefaultDevice();
        //BrLoginPage BrLoginPage = new BrLoginPage();
        //BrLoginPage.CheckNewPasswordLoginSuccess("password");
        BrAdvancedSecurityBlockSitesPage BrAdvancedSecurityBlockSitesPage = new BrAdvancedSecurityBlockSitesPage();
        BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        MyCommonAPIs.sleepi(5);
        String[] urls = {
                "www.baidu.com", "youku", "tudou", "www.qq.com", "www.weixin.com", "renren", "163", "hotmail", "sina", "taobao", "v2ex"
        };
        for (int i = 0; i < urls.length; i++) {
            BrAdvancedSecurityBlockSitesPage.blocksiteselectalw(urls[i]);
            MyCommonAPIs.sleepi(5);
            BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
            MyCommonAPIs.sleepi(5);
        }
        boolean result1 = BrAdvancedSecurityBlockSitesPage.checkdomainlistarray(urls);
        System.out.println("dddddddddddddddddddddd");
        System.out.print(result1);
        if (result1) {
            micResult = true;
            assertTrue(micResult, "Pass:Config successfully");
        } else {
            BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
            MyCommonAPIs.sleepi(10);
            BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
            MyCommonAPIs.sleepi(150);
            micResult = false;
            assertTrue(micResult, "Failed:Config unsuccessfully");
        }
        MyCommonAPIs.sleepi(15);
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        TmsPageHandle= Brtmspage.GetBrowserHandles();
        TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlbaiduCommands);   
       // MyCommonAPIs.sleepi(40);
        if (TMSTCPResult == false ) {
            micResult =  true;
            assertTrue(micResult,"Pass:Can't broswer baidu URL!"  );             
        } else {
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            // restore setting
            BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
            MyCommonAPIs.sleepi(10);
            BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
            MyCommonAPIs.sleepi(150);
            micResult =  false;
            assertTrue(micResult,"Failed:Can broswer baidu URL!"  );
        }
        Brtmspage.BackTMSConfigPafe();
        TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlweixinCommands);   
        // MyCommonAPIs.sleepi(40);
         if (TMSTCPResult == false ) {
             micResult =  true;
             assertTrue(micResult,"Pass:Can't broswer weixin URL!"  );             
         } else {
             Brtmspage.ChangeBrowserHandles(HistroyHandle);
             // restore setting
             BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
             MyCommonAPIs.sleepi(10);
             BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
             MyCommonAPIs.sleepi(150);
             micResult =  false;
             assertTrue(micResult,"Failed:Can broswer weixin URL!"  );
         }
         Brtmspage.BackTMSConfigPafe();
         TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlqqCommands);   
         // MyCommonAPIs.sleepi(40);
          if (TMSTCPResult == false ) {
              micResult =  true;
              assertTrue(micResult,"Pass:Can't broswer qq URL!"  );             
          } else {
              Brtmspage.ChangeBrowserHandles(HistroyHandle);
              // restore setting
              BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
              MyCommonAPIs.sleepi(10);
              BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
              MyCommonAPIs.sleepi(150);
              micResult =  false;
              assertTrue(micResult,"Failed:Can broswer qq URL!"  );
          }
          Brtmspage.BackTMSConfigPafe();
          TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlweixinCommands);   
          // MyCommonAPIs.sleepi(40);
          // if (TMSTCPResult == false ) {
          //     micResult =  true;
          //     assertTrue(micResult,"Pass:Can't broswer weixin URL!"  );             
          // } else {
           //    Brtmspage.ChangeBrowserHandles(HistroyHandle);
               // restore setting
           //    BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
           //    MyCommonAPIs.sleepi(10);
           //    BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
            //   MyCommonAPIs.sleepi(180);
            //   micResult =  false;
            //   assertTrue(micResult,"Failed:Can broswer weixin URL!"  );
           //}
           //Brtmspage.BackTMSConfigPafe();
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
            // restore setting
            BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
            MyCommonAPIs.sleepi(10);
            BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
            //BrLoginPage.defaultLogin();
            MyCommonAPIs.sleepi(120);
         
        
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }
}
