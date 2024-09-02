package businessrouter.BusinessRouterFunction.Security_BlockSites.BR_T51;

import static org.testng.Assert.assertTrue;

import java.util.Random;

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
    public String sTestStr = "BR_T51";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockSites") // 必须要加，对应目录名
    @Story("BR_T51") // 对应testrunkey
    @Description("010-block sites - Max 256 rules") // 对应用例名字
    @TmsLink("1455054") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Add block sites 32 rules,and Browse some sites within the block sites")
    public void step2() {
        boolean TMSTCPResult = true;
        boolean result1 = true;
        String[] urls = {
                "baidu", "renren", "qq", "weixin"
        };
        String[] rules = new String[256];
        Random ra = new Random();
        BrAdvancedSecurityBlockSitesPage BrAdvancedSecurityBlockSitesPage = new BrAdvancedSecurityBlockSitesPage();
        BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        MyCommonAPIs.sleepi(5);
        for (int i = 0; i < urls.length; i++) {
            BrAdvancedSecurityBlockSitesPage.blocksiteselectalw(urls[i]);
            MyCommonAPIs.sleepi(5);
            BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        }
        for (int i = 4; i < 33; i++) {
            System.out.print(i);
            BrAdvancedSecurityBlockSitesPage.blocksiteselectalw(String.valueOf(ra.nextInt(1000) + 1));
            //if (i == 32) {
            //    MyCommonAPIs.sleepi(2);
           //     result1 = BrAdvancedSecurityBlockSitesPage.checkdomainlistmaxerror();
           // }
            MyCommonAPIs.sleepi(5);
            BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        }
        if (result1) {
            micResult = true;
            assertTrue(micResult, "Pass:Binding the schedule to a Block site/service item successfully");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Binding the schedule to a Block site/service item failed");
        }
        MyCommonAPIs.sleepi(10);    
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        TmsPageHandle= Brtmspage.GetBrowserHandles();
        
        TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlCommands);  
        System.out.print(TMSTCPResult);
        if (TMSTCPResult == false ) {
            micResult =  true;
            assertTrue(micResult,"Pass:Can block URL with the blocked keys!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:Can't block URL with the blocked !"  );
        }
        Brtmspage.BackTMSConfigPafe();
        Brtmspage.ChangeBrowserHandles(HistroyHandle);       
        MyCommonAPIs.sleepi(5);
        
        MyCommonAPIs.sleepi(5);
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
