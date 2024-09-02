package businessrouter.BusinessRouterFunction.Firewall.BR_T487;
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
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase implements Config {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr = "BR_T487";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2;
    String LanClientMac;
    final static Logger logger = Logger.getLogger("BR_T487");
    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T487") // 对应testrunkey
    @Description("045-Access Control - Add new device to \"blocked devices not currently connected to the network") // 对应用例名A字
    @TmsLink("1455526") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p1") // 标记测试用例
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
        
      @Step("Test Step 2:Get new lan client MAC and disable LAN client adapter")
        public void step2() {
            boolean Result1 = false; 
            boolean Result2 = false; 
            HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
            LanClientMac = Brtmspage.GetNetworkCardMacAddress(TmsGetMacCommands);
            if (LanClientMac != "") {
                micResult =  true;
                assertTrue(micResult,"Pass:Can get Cleint MAC address !");  
            }else {
                assertTrue(micResult,"Failed:Can't get Cleint MAC address!"); 
            } 
            NewDeviceInfo.replace("MAC", LanClientMac.toUpperCase());
            Brtmspage.BackTMSConfigPafe();
            TmsPageHandle= Brtmspage.LoginTMS(WebportalParam.brlanconnectip);
            TmsPageHandle2= Brtmspage.GetBrowserHandles();
            Result1 = Brtmspage.RuncmdByTMS(TmsDownAdpaterCommands);
            Brtmspage.BackTMSConfigPafe();
            Result2 = Brtmspage.RuncmdByTMS(TmsDownAdpater2Commands);
            if (Result1 == true) {
                micResult =  true;
                assertTrue(micResult, "Pass:Down lan client adpater successfully"  );             
            } else {
                micResult =  false;
                assertTrue(micResult, "Failed:Down lan client adpater unsuccessfully!"  );
            }
            Brtmspage.BackTMSConfigPafe();
            handle.sleepi(100);
         }
         

       @Step("Test Step 3: Turn on access control,Access rule select  “Allow all new devices to connect.")
        public void step3() {
           boolean Result = false;
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           braccesscontrol.OpenFirewallAccessControlPage();
           braccesscontrol.AllowOrBlockNewDevice("Allow");
           handle.sleepi(20);
           braccesscontrol.DeletenotConnectedAllowedDevice(NewDeviceInfo);
       }
       @Step("Test Step 4: Add new deive to \"blocked device not currently connected to the network.")
       public void step4() {
          boolean Result = false;
          boolean Result1 = false;
          Result =  braccesscontrol.AddBlockNotConnectDev(NewDeviceInfo);
          if (Result == true) {
              micResult =  true;
              assertTrue(micResult, "Pass:Add new deive to blocked device not currently connected to the network successfully"  );             
          } else {
              micResult =  false;
              Brtmspage.BackTMSConfigPafe();
              TmsUpAdpaterCommands.replace("Protocol", "reboot");
              Result1 = Brtmspage.RuncmdByTMS(TmsUpAdpaterCommands);
              assertTrue(micResult, "Failed:Add new deive to blocked device not currently connected to the network unsuccessfully!"  );
          }
      }
       
       @Step("Test Step 5:The new deivce connect to router lan side")
       public void step5() {
           boolean Result1 = false; 
           boolean TMSTCPResult = false;
           boolean Result2 = false;
           Brtmspage.ChangeBrowserHandles(TmsPageHandle2);
           Result1 = Brtmspage.RuncmdByTMS(TmsUpAdpaterCommands);
           if (Result1 == true) {
               micResult =  true;
               assertTrue(micResult, "Pass:Up lan client adpater successfully"  );             
           } else {
               micResult =  false;
               assertTrue(micResult, "Failed:Up lan client adpater unsuccessfully!"  );
           }
           Brtmspage.BackTMSConfigPafe();
           TmsUpAdpaterCommands.replace("Protocol", "route add default gw "+WebportalParam.brlangateway);
           Result1 = Brtmspage.RuncmdByTMS(TmsUpAdpaterCommands);
           Brtmspage.BackTMSConfigPafe();
           handle.sleepi(10);
           Brtmspage.ChangeBrowserHandles(TmsPageHandle);
           TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);      
           if (TMSTCPResult == false ) {
               micResult =  true;
               assertTrue(micResult,"Pass:New Lan PC can't access wan side!"  );             
           } else {
               micResult =  false;
               assertTrue(micResult,"Failed:New Lan PC canaccess wan side!"  );
           }
           Brtmspage.BackTMSConfigPafe();
           Result2 = Brtmspage.RuncmdByTMS(TmsRestartAdpaterCommands);
        }   
          
       @Step("Test Step 6: Delete blocked not nonnect Device and turn off access control")
       public void step6() {
          boolean Result1 = false;
          Brtmspage.ChangeBrowserHandles(HistroyHandle);
          braccesscontrol.OpenFirewallAccessControlPage();
          handle.sleepi(20);
          braccesscontrol.DeletenotConnectedBlockedDevice(NewDeviceInfo);
          handle.sleepi(5);
          braccesscontrol.TunoffAccessControl();
          CaseResult = true;
          brlogin.BrLogout(); 
          
      }
      

}