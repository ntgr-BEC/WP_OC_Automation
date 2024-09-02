package businessrouter.BusinessRouterFunction.SetupLAN.BR_T397;
import static org.testng.Assert.assertTrue;

import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

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
    final static Logger logger = Logger.getLogger("BR_T397");
    public String sTestStr = "BR_T397";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle;
    String TmsPageHandle;
    String NewIP;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup LAN") // 必须要加，对应目录名
        @Story("BR_T397") // 对应testrunkey
        @Description("010-LAN setup - Select from Table and Change IP(Address Reservation)") // 对应用例名字
        @TmsLink("1455418") // 对应用例详情页的URL最后几位数字

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
            NewIP = Brtmspage.GetNewIPAddress(WebportalParam.brlanclientip);
            System.out.print(NewIP);
            System.out.print("LanPcIP:"+ LanPcIP);
            if (NewIP.contentEquals(LanPcIP)) {
                System.out.print("dddddddddddd222111");
                NewIP = Brtmspage.GetNewIPAddress(NewIP);
            }
            IPReservation.replace("MAC Address", LanPcMac.toUpperCase());
            IPReservation.replace("IP Address", NewIP);
           
            
         }
        @Step("Test Step 3: Go to lan setup page,add address reservation and change the reservation IP ")
        public void step3() {
            boolean Result1 = false;
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(5);
            brlansetuppage.ChangeConnectedIPToIPReservation(IPReservation);
            handle.sleepi(40);
            Result1 = brlansetuppage.IPReservationExistYesOrNot(IPReservation);           
            if (Result1 == true) {
                micResult =  true;
                assertTrue(micResult,"Pass:add it to address reservation table successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:add it to address reservation table unsuccessfully!"  );
            }
            brdashboard.OpenDashboardPage();
            handle.sleepi(5);
            brdashboard.RebootDUT();
            handle.sleepi(80);
         }
    
        @Step("Test Step 4: The LAN PC get IP again.")
        public void step4() {
            boolean Result = false;
            Brtmspage.ChangeBrowserHandles(TmsPageHandle);
            Brtmspage.RuncmdByTMS(TmsRestartAdpaterCommands);
            Brtmspage.BackTMSConfigPafe();
            String LanPcNewIP = Brtmspage.GetNetworkCardIpAddress(TmsGetIpAndMacCommands);           
            handle.sleepi(10);
            Brtmspage.BackTMSConfigPafe();
            Brtmspage.RuncmdByTMS(TmsDownAdpaterCommands);
            System.out.print(NewIP);
            System.out.print(LanPcNewIP);
            if (LanPcNewIP.trim().contentEquals(NewIP)) {
                System.out.print("d55555555555555555555555555555555555");
                Result = true;
            }
            if (Result == true) {
                micResult =  true;
                assertTrue(micResult,"Pass:LAN PC can get the address reservation ip successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:LAN PC can't get the ddress reservation ip successfully!"  );
            }
            
         }
        @Step("Test Step 5: Delete record to restore DUT configuration.")
        public void step5() {
            if (WebportalParam.DUTType.contentEquals("BR500")) {
                Brtmspage.ChangeBrowserHandles(HistroyHandle);
                brlansetuppage.OpenBasicLanSetupPage();
            }else if(WebportalParam.DUTType.contentEquals("BR100")) {
                //Brtmspage.ChangeBrowserHandles(HistroyHandle);
                Selenide.close();
                BrLoginPage BrLoginPage = new BrLoginPage();
                BrLoginPage.defaultLogin();
                MyCommonAPIs.sleepi(10);
                brlansetuppage.OpenBasicLanSetupPage();                      
            }
           
            handle.sleepi(10); 
            brlansetuppage.DeleteIPReservation(IPReservation.get("Device Name"));
            handle.sleepi(40);  
            CaseResult = true;
            brlogin.BrLogout();
            
         }
        
}
