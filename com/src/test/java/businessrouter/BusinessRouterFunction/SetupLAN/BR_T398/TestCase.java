package businessrouter.BusinessRouterFunction.SetupLAN.BR_T398;
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
    final static Logger logger = Logger.getLogger("BR_T398");
    public String sTestStr = "BR_T398";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle;
    String TmsPageHandle;
    String NewIP;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup LAN") // 必须要加，对应目录名
        @Story("BR_T398") // 对应testrunkey
        @Description("011-LAN setup - Select from Table(Address Reservation") // 对应用例名字
        @TmsLink("1455419") // 对应用例详情页的URL最后几位数字

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
        
        @Step("Test Step 2: Have 1 lan PC connects to router lan side, get the lan PC MAC and IP")
        public void step2() {
   
            boolean Result = false;
            HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
            TmsPageHandle  = Brtmspage.GetBrowserHandles();
            Brtmspage.RuncmdByTMS(TmsUpAdpaterCommands);
            Brtmspage.BackTMSConfigPafe();
            String LanPcMac = Brtmspage.GetNetworkCardMacAddress(TmsGetIpAndMacCommands);
            Brtmspage.BackTMSConfigPafe();
            String LanPcIP = Brtmspage.GetNetworkCardIpAddress(TmsGetIpAndMacCommands);
            Brtmspage.BackTMSConfigPafe();           
            IPReservation.replace("MAC Address", LanPcMac.toUpperCase());
            
           
            
         }
        @Step("Test Step 3: Verify adding a record that selected from table for Address Reservation field.")
        public void step3() {
            boolean Result1 = false;
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(5);
            brlansetuppage.ChangeConnectedIPToIPReservation(IPReservation);
            handle.sleepi(20);
            Result1 = brlansetuppage.IPReservationExistYesOrNot(IPReservation);           
            if (Result1 == true) {
                micResult =  true;
                assertTrue(micResult,"Pass:add it to address reservation table successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:add it to address reservation table unsuccessfully!"  );
            }
         
         }
    
        
        @Step("Test Step 4: Delete record to restore DUT configuration.")
        public void step4() {
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            //brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(10); 
            brlansetuppage.DeleteIPReservation(IPReservation.get("Device Name"));
            handle.sleepi(50);  
            CaseResult = true;
            brlogin.BrLogout();
            
         }
        
}
