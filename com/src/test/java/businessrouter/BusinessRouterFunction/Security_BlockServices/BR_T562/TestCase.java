package businessrouter.BusinessRouterFunction.Security_BlockServices.BR_T562;

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
    public String sTestStr = "BR_T562";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockServices") // 必须要加，对应目录名
    @Story("BR_T562") // 对应testrunkey
    @Description("008-Block Services - LAN, Address Single") // 对应用例名字
    @TmsLink("1460202") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Check block services only ip address.")
    public void step2() {
        boolean TMSTCPResult;
        String keyword =  WebportalParam.brwanclientip;
        System.out.println(keyword);
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrDashboardPage BrDashboardPage = new BrDashboardPage();
        BrDashboardPage.OpenDashboardPage();
        MyCommonAPIs.sleepi(5);
       // String ipaddress = BrDashboardPage.GetDeviceIp();
        //System.out.println(ipaddress);
       // blockservicesconfig.put("onlyipaddress", ipaddress);
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
            assertTrue(micResult,"Pass:Can't access Access the service in setp1 using source ip = ip !"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed: Can access Access the service in setp1 using source ip = ip!"  );
        }
        Brtmspage.BackTMSConfigPafe();
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        //BrAdvancedSecurityBlockServicesPage.OpenSecurityBlockServicesPage();
        MyCommonAPIs.sleepi(5);
        String NewIp  = Brtmspage.GetNewIPAddress(blockservicesconfig.get("onlyipaddress"));
        blockservicesconfig.replace("onlyipaddress", NewIp);
        BrAdvancedSecurityBlockServicesPage.blockserviceedit(blockservicesconfig);
        MyCommonAPIs.sleepi(5);
        Brtmspage.ChangeBrowserHandles(TmsPageHandle);
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsHttpCommands);      
        if (TMSTCPResult == true ) {
            micResult =  true;
            assertTrue(micResult,"Pass:Can access Access the service in setp1 using source ip = ip !"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed: Can't access Access the service in setp1 using source ip = ip!"  );
        }
        
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
            // restore setting
       // BrAdvancedSecurityBlockServicesPage.OpenSecurityBlockServicesPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedSecurityBlockServicesPage.blockservicesdeleteconfig();
       
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
