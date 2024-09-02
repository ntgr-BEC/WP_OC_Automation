package businessrouter.BusinessRouterFunction.Security_BlockServices.BR_T563;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedSecurityBlockServicesPage;
import businessrouter.webpageoperation.BrDashboardPage;
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
    public String sTestStr = "BR_T563";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockServices") // 必须要加，对应目录名
    @Story("BR_T563") // 对应testrunkey
    @Description("009-Block Services - LAN, Address Range") // 对应用例名字
    @TmsLink("1460203") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Add a rule in block service, and set a ip range from ip1 to ip2,check it.")
    public void step2() {
        boolean TMSTCPResult;
        String keyword =  WebportalParam.brwanclientip;
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrDashboardPage BrDashboardPage = new BrDashboardPage();
        BrDashboardPage.OpenDashboardPage();
        MyCommonAPIs.sleepi(5);
        String ipaddress = BrDashboardPage.GetDeviceIp();
        blockservicesconfig.put("ipaddressfrom", WebportalParam.brlanclientip);
        String NewIp  = Brtmspage.GetNewIPAddress(WebportalParam.brlanclientip);
        blockservicesconfig.replace("ipaddressto", NewIp);
        BrAdvancedSecurityBlockServicesPage BrAdvancedSecurityBlockServicesPage = new BrAdvancedSecurityBlockServicesPage();
        BrAdvancedSecurityBlockServicesPage.OpenSecurityBlockServicesPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedSecurityBlockServicesPage.blockserviceselect(blockservicesconfig);
        MyCommonAPIs.sleepi(5);
        
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        TmsPageHandle= Brtmspage.GetBrowserHandles();
        
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsHttpCommands);      
        if (TMSTCPResult == false ) {
            micResult =  true;
            assertTrue(micResult,"Pass:Can't access Access the service in the range of ip1 and ip2 !"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed: Can access Access the service in  the range of ip1 and ip2!"  );
        }
        Brtmspage.BackTMSConfigPafe();
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        BrAdvancedSecurityBlockServicesPage.OpenSecurityBlockServicesPage();
        MyCommonAPIs.sleepi(5);
       // String NewIp  = Brtmspage.GetNewIPAddress(blockservicesconfig.get("onlyipaddress"));
        blockservicesconfig.replace("ipaddressfrom", NewIp);
        BrAdvancedSecurityBlockServicesPage.blockserviceedit(blockservicesconfig);
        MyCommonAPIs.sleepi(5);
        Brtmspage.ChangeBrowserHandles(TmsPageHandle);
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsHttpCommands);      
        if (TMSTCPResult == true ) {
            micResult =  true;
            assertTrue(micResult,"Pass:Can access Access the service out of range  of ip1 and ip2 !"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed: Can't access Access the service out of range  of ip1 and ip2!"  );
        }
        
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        BrAdvancedSecurityBlockServicesPage.OpenSecurityBlockServicesPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedSecurityBlockServicesPage.blockservicesdeleteconfig();
   
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
