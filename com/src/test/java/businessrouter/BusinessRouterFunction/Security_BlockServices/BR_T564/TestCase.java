package businessrouter.BusinessRouterFunction.Security_BlockServices.BR_T564;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrAdvancedLogsPage;
import businessrouter.webpageoperation.BrAdvancedSecurityBlockServicesPage;
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
    public String sTestStr = "BR_T564";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockServices") // 必须要加，对应目录名
    @Story("BR_T564") // 对应testrunkey
    @Description("010-Block Services - Logs check") // 对应用例名字
    @TmsLink("1460204") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: ")
    public void step2() {
        boolean result1;
        boolean TMSTCPResult;
        String log = "[service blocked: HTTP]";
        BrAdvancedSecurityBlockServicesPage BrAdvancedSecurityBlockServicesPage = new BrAdvancedSecurityBlockServicesPage();
        BrAdvancedSecurityBlockServicesPage.OpenSecurityBlockServicesPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedSecurityBlockServicesPage.blockserviceselect(blockservicesconfig);
        MyCommonAPIs.sleepi(5);
        BrAdvancedLogsPage BrAdvancedLogsPage = new BrAdvancedLogsPage();
        BrAdvancedLogsPage.OpenLogsPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedLogsPage.selectcheckbox(2);
        MyCommonAPIs.sleepi(5);
        BrAdvancedLogsPage.clearLogoutput();
        MyCommonAPIs.sleepi(10);
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        TmsPageHandle= Brtmspage.GetBrowserHandles();
        
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsHttpCommands);      
        if (TMSTCPResult == false ) {
            micResult =  true;
            assertTrue(micResult,"Pass:Can't access Access the service in the table !"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed: Can access Access the service in the table!"  );
        }
   
            MyCommonAPIs.sleepi(10);
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            BrAdvancedLogsPage.OpenLogsPage();
            MyCommonAPIs.sleepi(5);
            if (BrAdvancedLogsPage.checkLogoutput(log)) {
                micResult = true;
                assertTrue(micResult, "Pass:Block Services loge success");
            } else {
                micResult = false;
                assertTrue(micResult, "Failed:Block Services log fail");
            }
        
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
            BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
            MyCommonAPIs.sleepi(10);
            if (WebportalParam.DUTType.contentEquals("BR500")) {
                BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
            }else if(WebportalParam.DUTType.contentEquals("BR100")) {
                BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR100_default.cfg");
            }
            MyCommonAPIs.sleepi(150);
            Selenide.refresh();
            BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.defaultLogin();
        
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }
}
