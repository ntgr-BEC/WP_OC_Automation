package businessrouter.BusinessRouterFunction.Security_BlockServices.BR_T561;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
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
    public String sTestStr = "BR_T561";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockServices") // 必须要加，对应目录名
    @Story("BR_T561") // 对应testrunkey
    @Description("007-Block Services - Edit Rule") // 对应用例名字
    @TmsLink("1460201") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Add a rule at blcok service table,and change rule to other service")
    public void step2() {
        boolean TMSTCPResult;
        boolean result1, result2;
        BrAdvancedSecurityBlockServicesPage BrAdvancedSecurityBlockServicesPage = new BrAdvancedSecurityBlockServicesPage();
        BrAdvancedSecurityBlockServicesPage.OpenSecurityBlockServicesPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedSecurityBlockServicesPage.blockserviceselect(blockservicesconfig1);
        MyCommonAPIs.sleepi(5);
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        TmsPageHandle= Brtmspage.GetBrowserHandles();
        
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsHttpCommands);      
        if (TMSTCPResult == false ) {
            micResult =  true;
            assertTrue(micResult,"Pass:Try to access the service in the item from a PC that connected to DUT,Can not access the service in the Service Table!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:Try to access the service in the item from a PC that connected to DUT,Can access the service in the Service Table!"  );
        }
        Brtmspage.BackTMSConfigPafe();
        
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        BrLoginPage BrLoginPage = new BrLoginPage();
        //BrLoginPage.defaultLogin();
        //BrAdvancedSecurityBlockServicesPage.OpenSecurityBlockServicesPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedSecurityBlockServicesPage.blockserviceedit(blockservicesconfig2);
        MyCommonAPIs.sleepi(5);
        //HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        //TmsPageHandle= Brtmspage.GetBrowserHandles();
        Brtmspage.ChangeBrowserHandles(TmsPageHandle);
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsHttpsCommands);      
        if (TMSTCPResult == false ) {
            micResult =  true;
            assertTrue(micResult,"Pass:Try to access the service in the item from a PC that connected to DUT,Can not access the service in the Service Table!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:Try to access the service in the item from a PC that connected to DUT,Can access the service in the Service Table!"  );
        }
        Brtmspage.BackTMSConfigPafe();
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsHttpCommands);      
        if (TMSTCPResult == true ) {
            micResult =  true;
            assertTrue(micResult,"Pass:Try to access the service not in the item from a PC that connected to DUT,Can access the service not in the Service Table!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:Try to access the service not in the item from a PC that connected to DUT,Can't access the service not in the Service Table!"  );
        }
       
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        //BrLoginPage.defaultLogin();
        //BrAdvancedSecurityBlockServicesPage.OpenSecurityBlockServicesPage();
        MyCommonAPIs.sleepi(5);
            BrAdvancedSecurityBlockServicesPage.blockservicesdeleteconfig();
        
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
