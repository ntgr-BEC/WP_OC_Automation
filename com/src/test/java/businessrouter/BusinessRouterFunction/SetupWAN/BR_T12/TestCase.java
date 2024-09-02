package businessrouter.BusinessRouterFunction.SetupWAN.BR_T12;
import static org.testng.Assert.assertTrue;

import java.util.logging.Logger;

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
    final static Logger logger = Logger.getLogger("BR_T678");
    public String sTestStr = "BR_T12";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle;
    String PptpServerHandle;
    String PptpL2tpServerHandle;
    String TMHandle;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup WAN") // 必须要加，对应目录名
        @Story("BR_T12") // 对应testrunkey
        @Description("008-Config WAN use PPTP (Dial on Demand)") // 对应用例名字
        @TmsLink("1455015") // 对应用例详情页的URL最后几位数字

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
            //brdashboard.OpenDashboardPage();
            //MyCommonAPIs.sleepi(60);
            //boolean Result;
            //Result = brdashboard.CheckinterWanStatus(PptpIpInfo);
        }
        @Step("Test Step 2: Enable PPTP server")
        public void step2() {
            boolean Result = false;
            boolean Result1 = false;          
            HistroyHandle = brpptpl2tpserver.LoginPptpL2tpServer(WebportalParam.brpptpl2tpserver);
            PptpServerHandle = Brtmspage.GetBrowserHandles();
            Result = brpptpl2tpserver.AddNewPptpl2tpUser(PptpServerUser);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Add PPTP user successfully."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Add PPTP user unsuccessfully."  );
            }
            Result1 = brpptpl2tpserver.AddNewPptpServer(PptpServerInfo);
            if (Result1 == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Enable PPTP server successfully."  );             
            } else {
                micResult =  false;
                brpptpl2tpserver.DeleteallUser();
                assertTrue(micResult,"Failed:Enable PPTP user unsuccessfully."  );
            }
        
        }
        
        @Step("Test Step 3: Go to Basic->WAN,ISP use PPTP,Connection Mode select Always on")
        public void step3() {
            boolean Result = false;
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            brwanpage.OpenBasicWanSetupPage();
            MyCommonAPIs.sleepi(30);
            String NewGateway = WanPptpInfo.get("Gateway");
            String  []NewGatewayList = NewGateway.split("\\.");
            NewGateway = NewGatewayList[0]+"."+NewGatewayList[1]+"."+NewGatewayList[2]+".1";
            WanPptpInfo.replace("Gateway", NewGateway);
            Result = brwanpage.SetPptpL2tpAndPppoe(WanPptpInfo);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Set PPTP,Connection Mode select Always on successfully."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Set PPTP,Connection Mode select Always on unsuccessfully."  );
            }
           handle.sleepi(50);
         }
        @Step("Test Step 4: Access Internet from LAN,Check the WAN port status under Advanced Home page")
        public void step4() {
            PptpL2tpServerHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
            TMHandle = Brtmspage.GetBrowserHandles();
            boolean PingResult = false;
            
            PingResult = Brtmspage.CheckPingPassOrFailed(TmsICMPCommands);
            Brtmspage.BackTMSConfigPafe();
            handle.sleepi(30);
            boolean Result = false;
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            brdashboard.OpenDashboardPage();
            MyCommonAPIs.sleepi(30);
          
            Result = brdashboard.CheckinterWanStatus(PptpIpInfo);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"The WAN port status is correct under Advanced Home page.."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:The WAN port status is incorrect under Advanced Home page.."  );
            }
           handle.sleepi(350);
           
         }
        @Step("Test Step 5: No any traffic from Lan side to WAN,wait 5 minutes,check the WAN status")
        public void step5() {
            boolean Result = false;
            brwanpage.OpenBasicWanSetupPage();
            handle.sleepi(30);
            brdashboard.OpenDashboardPage();
            MyCommonAPIs.sleepi(60);
          
            Result = brdashboard.CheckinterWanStatus(PptpIpInfo);
            if (Result == false ) {
                micResult =  true;
                assertTrue(micResult,"The WAN port status is down."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:The WAN port status is up."  );
            }
            
         }
        @Step("Test Step 6: Access Internet from LAN")
        public void step6() {
            boolean PingResult = false;
            boolean Result = false;
            Brtmspage.ChangeBrowserHandles(TMHandle);
            PingResult = Brtmspage.CheckPingPassOrFailed(TmsICMPCommands);
            MyCommonAPIs.sleepi(60);
            
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            brwanpage.OpenBasicWanSetupPage();
            handle.sleepi(30);
            brdashboard.OpenDashboardPage();
            handle.sleepi(60);
        
          
            Result = brdashboard.CheckinterWanStatus(PptpIpInfo);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"The WAN port status is correct under Advanced Home page.."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:The WAN port status is incorrect under Advanced Home page.."  );
            }
           
         }
        @Step("Test Step 7: Restore DUT configuration")
        public void step7() {
            boolean Result = false;
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            brwanpage.OpenBasicWanSetupPage();
            MyCommonAPIs.sleepi(20);
            brwanpage.ChangeWanPortModeDhcpOrStatic(WanPortModeInfo);
            //Brtmspage.ChangeBrowserHandles(PptpServerHandle);
            PptpServerHandle = brpptpl2tpserver.LoginPptpL2tpServer(WebportalParam.brpptpl2tpserver);
            brpptpl2tpserver.DeleteallUser(); 
            
            CaseResult = true;
         } 

}
