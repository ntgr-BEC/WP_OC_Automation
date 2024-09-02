package businessrouter.BusinessRouterFunction.SetupWAN.BR_T16;
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
    public String sTestStr = "BR_T16";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle;
    String PptpServerHandle;
    String PptpL2tpServerHandle;
    String TMHandle;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup WAN") // 必须要加，对应目录名
        @Story("BR_T16") // 对应testrunkey
        @Description("012-Config WAN use L2TP (Manually Connect)") // 对应用例名字
        @TmsLink("1455019") // 对应用例详情页的URL最后几位数字

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
           // brdashboard.OpenDashboardPage();
           // MyCommonAPIs.sleepi(60);
           // brdashboard.DisconnectOrConnectWan("Connect");
        }
        @Step("Test Step 2: Enable L2TP server")
        public void step2() {
            boolean Result = false;
            boolean Result1 = false;          
            HistroyHandle = brpptpl2tpserver.LoginPptpL2tpServer(WebportalParam.brpptpl2tpserver);
            PptpServerHandle = Brtmspage.GetBrowserHandles();
            Result = brpptpl2tpserver.AddNewPptpl2tpUser(L2tpServerUser);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Add L2TP user successfully."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Add L2TP user unsuccessfully."  );
            }
            Result1 = brpptpl2tpserver.AddNewL2tpServer(L2tpServerInfo);
            if (Result1 == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Enable L2TP server successfully."  );             
            } else {
                micResult =  false;
                brpptpl2tpserver.DeleteallUser();
                assertTrue(micResult,"Failed:Enable L2TP user unsuccessfully."  );
            }
        
        }
        
        @Step("Test Step 3: Go to Basic->WAN,ISP use L2TP,Connection Mode select manually connect")
        public void step3() {
            boolean Result = false;
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            brwanpage.OpenBasicWanSetupPage();
            MyCommonAPIs.sleepi(30);
            String NewGateway = WanL2tpInfo.get("Gateway");
            String  []NewGatewayList = NewGateway.split("\\.");
            NewGateway = NewGatewayList[0]+"."+NewGatewayList[1]+"."+NewGatewayList[2]+".1";
            WanL2tpInfo.replace("Gateway", NewGateway);
            Result = brwanpage.SetPptpL2tpAndPppoe(WanL2tpInfo);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Set L2TP,Connection Mode select manually connect successfully."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Set L2TP,Connection Mode select manually connect unsuccessfully."  );
            }
           handle.sleepi(50);
         }
        @Step("Test Step 4: Check the WAN port status under Advanced Home page")
        public void step4() {      
            boolean Result = false;
            brdashboard.OpenDashboardPage();
            handle.sleepi(30);
            Result = brdashboard.CheckinterWanStatus(L2tpIpdownInfo);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"The WAN port is down."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:The WAN port is up.");
            }
           
         }
        @Step("Test Step 5: Connect WAN port manually under connection status on advanced home page.")
        public void step5() {
            boolean Result = false;
            
            //brdashboard.OpenDashboardPage();
            //MyCommonAPIs.sleepi(60);
            brdashboard.DisconnectOrConnectWan("Connect");
            MyCommonAPIs.sleepi(100);
            Result = brdashboard.CheckinterWanStatus(L2tpIpInfo);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"The WAN port status is up."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:The WAN port status is down."  );
            }
            
         }
                 
    
        @Step("Test Step 6: Restore DUT configuration")
        public void step6() {
            boolean Result = false;
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            brwanpage.OpenBasicWanSetupPage();
            MyCommonAPIs.sleepi(20);
            brwanpage.ChangeWanPortModeDhcpOrStatic(WanPortModeInfo);   
            CaseResult = true;
         }

}
