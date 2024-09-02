package businessrouter.BusinessRouterFunction.Security_BlockSites.BR_T42;

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
    public String sTestStr = "BR_T42";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockSites") // 必须要加，对应目录名
    @Story("BR_T42") // 对应testrunkey
    @Description("001-Block Sites - Always-add/delete keywords") // 对应用例名字
    @TmsLink("1455045") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: can add some keywords in the box")
    public void step2() {
        boolean TMSTCPResult;
        String url = "www.12306.cn";
        BrAdvancedSecurityBlockSitesPage BrAdvancedSecurityBlockSitesPage = new BrAdvancedSecurityBlockSitesPage();
        BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        MyCommonAPIs.sleepi(10);
        BrAdvancedSecurityBlockSitesPage.blocksiteselectalw(url);
        boolean result1 = BrAdvancedSecurityBlockSitesPage.checkdomainlist(url);

        if (result1) {
            micResult = true;
            assertTrue(micResult, "Pass:Binding the schedule to a Block site/service item successfully");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Binding the schedule to a Block site/service item failed");
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
        
    }

    @Step("Test Step 3: Delete the keywords")
    public void step3() {
        boolean TMSTCPResult;
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        BrLoginPage BrLoginPage = new BrLoginPage();
      
        BrAdvancedSecurityBlockSitesPage BrAdvancedSecurityBlockSitesPage = new BrAdvancedSecurityBlockSitesPage();
        BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        MyCommonAPIs.sleepi(10);
        BrAdvancedSecurityBlockSitesPage.blocksiteselectnevandclearlist();
        boolean result1 = BrAdvancedSecurityBlockSitesPage.checkdomainlist("www.12306.com");
        Brtmspage.ChangeBrowserHandles(TmsPageHandle);

        if (result1) {
            micResult = true;
            assertTrue(micResult, "Pass:url can access");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:url can't access");
        }
        
        TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlCommands);      
        if (TMSTCPResult == true ) {
            micResult =  true;
            assertTrue(micResult,"Pass:Can access the URL which is deleted "  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:Can't access the block URL which is deleted!"  );
        }
            MyCommonAPIs.sleepi(5);
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
            BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
            MyCommonAPIs.sleepi(10);
            BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
            MyCommonAPIs.sleepi(120);
  
        
    }

    @Step("Test Step 4: Return CaseResult")
    public void step4() {
        CaseResult = true;
    }

}
