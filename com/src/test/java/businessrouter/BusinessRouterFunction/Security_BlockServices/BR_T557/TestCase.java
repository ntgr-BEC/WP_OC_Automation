package businessrouter.BusinessRouterFunction.Security_BlockServices.BR_T557;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrAdvancedSecurityBlockServicesPage;
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
    public String sTestStr = "BR_T557";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockServices") // 必须要加，对应目录名
    @Story("BR_T557") // 对应testrunkey
    @Description("003-Block Services - Block by Schedule") // 对应用例名字
    @TmsLink("1460197") // 对应用例详情页的URL最后几位数字

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
        boolean result1, result2;
        boolean TMSTCPResult, TMSTCPResult2;
        BrAdvancedSecurityBlockServicesPage BrAdvancedSecurityBlockServicesPage = new BrAdvancedSecurityBlockServicesPage();
        BrAdvancedSecurityBlockServicesPage.OpenSecurityBlockServicesPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedSecurityBlockServicesPage.blockserviceselect(blockservicesconfig);
        BrAdvancedSecuritySchedulePage BrAdvancedSecuritySchedulePage = new BrAdvancedSecuritySchedulePage();
        BrAdvancedSecuritySchedulePage.OpenSecuritySchedulePage();
        MyCommonAPIs.sleepi(5);
        String hour = BrAdvancedSecuritySchedulePage.getCurrentTime();
        String minute = BrAdvancedSecuritySchedulePage.getCurrentTimeMin();
        if (Integer.parseInt(minute) > 2 && Integer.parseInt(minute) < 58) {
            BrAdvancedSecuritySchedulePage.setAllDays(hour, String.valueOf(Integer.parseInt(minute) - 1), hour,
                    String.valueOf(Integer.parseInt(minute) + 3));
        } else if (Integer.parseInt(minute) < 2) {
            MyCommonAPIs.sleepi(180);
            minute = BrAdvancedSecuritySchedulePage.getCurrentTimeMin();
            BrAdvancedSecuritySchedulePage.setAllDays(hour, String.valueOf(Integer.parseInt(minute) - 1), hour,
                    String.valueOf(Integer.parseInt(minute) + 3));
        } else if (Integer.parseInt(minute) > 58) {
            MyCommonAPIs.sleepi(180);
            hour = BrAdvancedSecuritySchedulePage.getCurrentTime();
            minute = BrAdvancedSecuritySchedulePage.getCurrentTimeMin();
            BrAdvancedSecuritySchedulePage.setAllDays(hour, String.valueOf(Integer.parseInt(minute) - 1), hour,
                    String.valueOf(Integer.parseInt(minute) + 3));
        }
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        TmsPageHandle= Brtmspage.GetBrowserHandles();
        
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsHttpCommands);      
        if (TMSTCPResult == false ) {
            micResult =  true;
            assertTrue(micResult,"Pass:Can't access the service in Service Table in the time range of time schedule!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed: Can access the service in Service Table in the time range of time schedule!"  );
        }
        Brtmspage.BackTMSConfigPafe();
        
            MyCommonAPIs.sleepi(150);
            
            TMSTCPResult2 =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsHttpCommands);      
            if (TMSTCPResult2 == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Can access the service in Service Table out of  the time range of time schedule!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed: Can't access the service in Service Table out of  the time range of time schedule!"  );
            }
        
        MyCommonAPIs.sleepi(5);
         BrLoginPage BrLoginPage = new BrLoginPage();
        //BrLoginPage.defaultLogin();
       // if (micResult) {
            // restore setting
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
            BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
            BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
            MyCommonAPIs.sleepi(10);
            BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
            MyCommonAPIs.sleepi(100);
            //Selenide.refresh();
            //BrLoginPage.defaultLogin();
        //}
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
