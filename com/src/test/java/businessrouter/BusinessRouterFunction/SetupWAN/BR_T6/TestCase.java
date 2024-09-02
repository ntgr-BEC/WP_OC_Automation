package businessrouter.BusinessRouterFunction.SetupWAN.BR_T6;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase implements Config {
    String                       tclname = getClass().getName();
    String                       tmpStr;

    public String sTestStr = "BR_T6";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle;
    String TmsPageHandle;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup WAN") // 必须要加，对应目录名
        @Story("BR_T6") // 对应testrunkey
        @Description("002-Set WAN IP address use static ip") // 对应用例名字
        @TmsLink("1455009") // 对应用例详情页的URL最后几位数字

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
            MyCommonAPIs.sleepi(20);
        }
        
        @Step("Test Step 2: Go to Basic->WAN page, Configure WAN interface use Static")
        public void step2() {
            boolean Result = false;
            brwanpage.OpenBasicWanSetupPage();
            MyCommonAPIs.sleepi(10);
            WanPortModeInfo.replace("WAN IP", Brtmspage.GetNewIPAddress(WebportalParam.brwanclientip));
            brwanpage.ChangeWanPortModeDhcpOrStatic(WanPortModeInfo);
            Result = brwanpage.CheckWanPortIpInfo(WanPorInfo);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:configure WAN interface use Static normally."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:configure WAN interface use Static abnormally."  );
            }
           
         }
        @Step("Test Step 3: wan pc can access lan side http")
        public void step3() {
            boolean TMSTCPResult =false;
            HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
            TmsPageHandle= Brtmspage.GetBrowserHandles();
        
            TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);   
            if (TMSTCPResult == true ) {
                micResult =  true;
                assertTrue(micResult,"LAN pc can access WAN side http!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:LAN pc can't access WAN side http!"  );
            }
            
        }
        @Step("Test Step 4:  resotre DUT configuration")
        public void step4() {
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            brwanpage.OpenBasicWanSetupPage();
            MyCommonAPIs.sleepi(10);
            brwanpage.ChangeWanPortModeDhcpOrStatic(WanPortModeInfo);
            MyCommonAPIs.sleepi(10);
            CaseResult = true;
            brlogin.BrLogout();
         }
        

}
