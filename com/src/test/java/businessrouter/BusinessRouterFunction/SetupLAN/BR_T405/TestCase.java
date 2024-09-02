package businessrouter.BusinessRouterFunction.SetupLAN.BR_T405;
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
    final static Logger logger = Logger.getLogger("BR_T405");
    public String sTestStr = "BR_T405";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle;
    String TmsPageHandle;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup LAN") // 必须要加，对应目录名
        @Story("BR_T405") // 对应testrunkey
        @Description("018-Check on Add address reservation page will list attached devices automation") // 对应用例名字
        @TmsLink("1455426") // 对应用例详情页的URL最后几位数字

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
            MyCommonAPIs.sleepi(10);
        }
        
        @Step("Test Step 2: Have 2 PC connects to router lan side, get the 2 PC MACs")
        public void step2() {
   
            boolean Result = false;
            HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
            TmsPageHandle  = Brtmspage.GetBrowserHandles();
            Brtmspage.RuncmdByTMS(TmsUpAdpaterCommands);
            Brtmspage.BackTMSConfigPafe();
            String LanClient1Mac = Brtmspage.GetNetworkCardMacAddress(TmsGeteth0MacCommands);
            Brtmspage.BackTMSConfigPafe();
            String LanClient2Mac = Brtmspage.GetNetworkCardMacAddress(TmsGeteth2MacCommands);
            Brtmspage.BackTMSConfigPafe();
            IPReservation.replace("MAC Address", LanClient1Mac.toUpperCase());
            IPReservation2.replace("MAC Address", LanClient2Mac.toUpperCase());
            
         }
        @Step("Test Step 3: Go to lan setup page,add address reservation ")
        public void step3() {
            boolean Result1 = false;
            boolean Result2 = false;
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(5);
            brlansetuppage.ChangeConnectedIPToIPReservation(IPReservation);
            handle.sleepi(20);
            brlansetuppage.ChangeConnectedIPToIPReservation(IPReservation2);
            handle.sleepi(20);
            Result1 = brlansetuppage.IPReservationExistYesOrNot(IPReservation);
            handle.sleepi(5);
            Result2 = brlansetuppage.IPReservationExistYesOrNot(IPReservation2);
            if (Result1 == true && Result2 == true) {
                micResult =  true;
                assertTrue(micResult,"Pass:add it to address reservation table successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:add it to address reservation table unsuccessfully!"  );
            }
         }
    
        @Step("Test Step 4: Delete record to restore DUT configuration.")
        public void step4() {
            Brtmspage.ChangeBrowserHandles(TmsPageHandle);
            Brtmspage.RuncmdByTMS(TmsDownAdpaterCommands);
            brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(10); 
            brlansetuppage.DeleteIPReservation(IPReservation.get("Device Name"));
            handle.sleepi(20);  
            CaseResult = true;
            brlogin.BrLogout();
            
         }
        
}
