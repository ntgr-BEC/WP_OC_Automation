package businessrouter.BusinessRouterFunction.Logs.BR_T203;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
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
    public String sTestStr = "BR_T203";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Logs") // 必须要加，对应目录名
    @Story("BR_T203") // 对应testrunkey
    @Description("002-Logs-Attemped access to allowed sites") // 对应用例名字
    @TmsLink("1455206") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2:Check \"Attemped access to allowed sites\" is correct")
    public void step2() {
        boolean TMSTCPResult = false;
        BrAdvancedLogsPage BrAdvancedLogsPage = new BrAdvancedLogsPage();
        BrAdvancedLogsPage.OpenLogsPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedLogsPage.selectcheckbox(1);
        MyCommonAPIs.sleepi(3);
        BrAdvancedLogsPage.OpenLogsPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedLogsPage.clearLogoutput();
        brdashboard.OpenDashboardPage();
        brdashboard.RebootDUT();
        MyCommonAPIs.sleepi(60);
        
        String url = "www.12306.cn";
        BrAdvancedSecurityBlockSitesPage BrAdvancedSecurityBlockSitesPage = new BrAdvancedSecurityBlockSitesPage();
        BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        MyCommonAPIs.sleepi(10);
        BrAdvancedSecurityBlockSitesPage.blocksiteselectnev(url);
        //BrAdvancedSecurityBlockSitesPage.allowtrustedIP("8");
       // Selenide.open("http://" + url);
        //MyCommonAPIs.sleepi(5);
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        TmsPageHandle= Brtmspage.GetBrowserHandles();
        
        TMSTCPResult =  Brtmspage.CheckUrlPassOrFailed(TmsUrlCommands);      
        if (TMSTCPResult == true ) {
            micResult =  true;
            assertTrue(micResult,"Pass:Can access the block URL with the allowed keys!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:Can't access the block URL with the allowed keys!"  );
        }
        Brtmspage.BackTMSConfigPafe();
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.defaultLogin();
        BrAdvancedLogsPage.OpenLogsPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedLogsPage.refreshLogoutput();
        if (BrAdvancedLogsPage.checkLogoutput("[site allowed: " + url + "]")) {
            MyCommonAPIs.sleepi(5);
            BrAdvancedLogsPage.disablecheckbox(1);
//            Selenide.open(url);
//            MyCommonAPIs.sleepi(5);
//            BrLoginPage.defaultLogin();
//            BrAdvancedLogsPage.OpenLogsPage();
            MyCommonAPIs.sleepi(5);
            BrAdvancedLogsPage.refreshLogoutput();
            if (!BrAdvancedLogsPage.checkLogoutput("[site allowed: " + url + "]")) {
                micResult = true;
                assertTrue(micResult, "Pass:\"Attemped access to allowed sites\" is correct");
            } else {
                micResult = false;
                assertTrue(micResult, "Failed:\"Attemped access to allowed sites\" is incorrect.");
            }
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:\"Attemped access to allowed sites\" is incorrect.");
        }

        if (micResult) {
            BrAdvancedLogsPage.selectallheckbox();
            MyCommonAPIs.sleepi(5);
            BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
            MyCommonAPIs.sleepi(10);
            BrAdvancedSecurityBlockSitesPage.blocksiteselectnevandclearlist();
            BrAdvancedSecurityBlockSitesPage.disableallowtrustedIP();
            MyCommonAPIs.sleepi(10);
        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
