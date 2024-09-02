package businessrouter.BusinessRouterFunction.Security_Schedule.BR_T72;

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
    public String sTestStr = "BR_T72";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_Schedule") // 必须要加，对应目录名
    @Story("BR_T72") // 对应testrunkey
    @Description("004-Schedule - Individual Days") // 对应用例名字
    @TmsLink("1455075") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p1") // 标记测试用例
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

    @Step("Test Step 2: Add a schedule , in the Days to Block select current day")
    public void step2() {
        boolean TMSTCPResult;
        BrAdvancedSecuritySchedulePage BrAdvancedSecuritySchedulePage = new BrAdvancedSecuritySchedulePage();
        BrAdvancedSecuritySchedulePage.OpenSecuritySchedulePage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedSecuritySchedulePage.selectdayofweek();
        BrAdvancedSecurityBlockSitesPage BrAdvancedSecurityBlockSitesPage = new BrAdvancedSecurityBlockSitesPage();
        BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedSecurityBlockSitesPage.blocksiteselectpre(url);
        MyCommonAPIs.sleepi(5);
        BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        boolean result1 = BrAdvancedSecurityBlockSitesPage.checkdomainlist(url);
        if (result1) {

            micResult = true;
            assertTrue(micResult, "Pass:Configuration pass");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Configuration fail");
        }
        MyCommonAPIs.sleepi(10);
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        TmsPageHandle= Brtmspage.GetBrowserHandles();
        
        TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlCommands);      
        if (TMSTCPResult == false) {
            micResult =  true;
            assertTrue(micResult,"Pass:Binding the schedule to a Block site/service item is blocked!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:Binding the schedule to a Block site/service item isn't blocked!"  );
        }
       Brtmspage.BackTMSConfigPafe();
       Brtmspage.ChangeBrowserHandles(HistroyHandle); 
        MyCommonAPIs.sleepi(5);
    }

    @Step("Test Step 3: Change the Days to a different day from current day")
    public void step3() {
        
        boolean TMSTCPResult;
    
        BrAdvancedSecuritySchedulePage BrAdvancedSecuritySchedulePage = new BrAdvancedSecuritySchedulePage();
        BrAdvancedSecuritySchedulePage.OpenSecuritySchedulePage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedSecuritySchedulePage.selectotherday();
        MyCommonAPIs.sleepi(5);
        Brtmspage.ChangeBrowserHandles(TmsPageHandle); 
        TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlCommands);      
        if (TMSTCPResult == true) {
            micResult =  true;
            assertTrue(micResult,"Pass:Out of the schedule  a Block site/service item isn't blocked!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:Out of the schedule  a Block site/service item is blocked!"  );
        }
       Brtmspage.BackTMSConfigPafe();
       Brtmspage.ChangeBrowserHandles(HistroyHandle); 
       MyCommonAPIs.sleepi(5);
       BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
       BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
       MyCommonAPIs.sleepi(10);
       BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
       MyCommonAPIs.sleepi(150);
         
        
    }

    @Step("Test Step 4: Return CaseResult")
    public void step4() {
        CaseResult = true;
    }

}
