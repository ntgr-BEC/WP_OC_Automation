package businessrouter.BusinessRouterFunction.Security_BlockSites.BR_T44;

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
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;
    String HistroyHandle;
    String TmsPageHandle;
    public String sTestStr = "BR_T44";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockSites") // 必须要加，对应目录名
    @Story("BR_T44") // 对应testrunkey
    @Description("003-Block Sites - By Schedule") // 对应用例名字
    @TmsLink("1455047") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Set Keyword Blocking to Per Schedule")
    public void step2() {
        boolean result1 = false;
        boolean TMSTCPResult;
        BrAdvancedSecuritySchedulePage BrAdvancedSecuritySchedulePage = new BrAdvancedSecuritySchedulePage();
        BrAdvancedSecuritySchedulePage.OpenSecuritySchedulePage();
        MyCommonAPIs.sleepi(10);
        String hour = BrAdvancedSecuritySchedulePage.getCurrentTime();
        if (Integer.parseInt(hour) < 23 && Integer.parseInt(hour) > 0) {
            result1 = BrAdvancedSecuritySchedulePage.setAllDays(String.valueOf(Integer.parseInt(hour) - 1), "30",
                    String.valueOf(Integer.parseInt(hour) + 1), "30");
        } else if (Integer.parseInt(hour) == 0) {
            result1 = BrAdvancedSecuritySchedulePage.setAllDays("0", "1", "1", "59");
        } else if (Integer.parseInt(hour) == 23) {
            result1 = BrAdvancedSecuritySchedulePage.setAllDays("22", "1", "23", "59");
        }

        if (result1) {
            micResult = true;
            assertTrue(micResult, "Failed:Add a schedule successfully");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Add a schedule unsuccessfully");
        }
        String url = "www.12306.cn";
        BrAdvancedSecurityBlockSitesPage BrAdvancedSecurityBlockSitesPage = new BrAdvancedSecurityBlockSitesPage();
        BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        MyCommonAPIs.sleepi(10);
        BrAdvancedSecurityBlockSitesPage.blocksiteselectpre(url);
        boolean result2 = BrAdvancedSecurityBlockSitesPage.checkdomainlist(url);

        if (result2) {
             micResult = true;
             assertTrue(micResult, "Failed:Binding the schedule to a Block site/service item successfully");
        } else {
             micResult = false;
             assertTrue(micResult, "Failed:Binding the schedule to a Block site/service item failed");
        }
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        TmsPageHandle= Brtmspage.GetBrowserHandles();
        
        TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlCommands);      
        if (TMSTCPResult == false ) {
            micResult =  true;
            assertTrue(micResult,"Pass:Can't access the block URL with the blocked keys in schedule!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:Can access the block URL with the blocked in sechedule!"  );
        }
        Brtmspage.BackTMSConfigPafe();
        Brtmspage.ChangeBrowserHandles(HistroyHandle);       
        MyCommonAPIs.sleepi(5);
       BrLoginPage BrLoginPage = new BrLoginPage();
       BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
       BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
       MyCommonAPIs.sleepi(10);
       BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
       MyCommonAPIs.sleepi(120);
            
        } 

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
