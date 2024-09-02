package businessrouter.BusinessRouterFunction.Firewall.BR_T468;
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

    public String sTestStr = "BR_T468";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2;
    String LanClientMac;
    final static Logger logger = Logger.getLogger("BR_T468");
    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T468") // 对应testrunkey
    @Description("026-Access Control - Change device status from block to allow") // 对应用例名A字
    @TmsLink("1455507") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p5") // 标记测试用例
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    
        @Step("Test Step 1: Open Device")
        public void step1() {
          // MyCommonAPIs.sleepi(30);
            BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.defaultLogin(); 
            //braccesscontrol.OpenFirewallAccessControlPage();
            //boolean result = false;
            //result = braccesscontrol.DeletenotConnectedAllowedDevice(NewDeviceInfo);
        }
        
      @Step("Test Step 2:Get new lan client MAC and disable LAN client adapter")
        public void step2() {
            boolean Result1 = false; 
            
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
         

       @Step("Test Step 3: Turn on access control,select Block all new devices from connecting.")
        public void step3() {
           boolean Result = false;
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           braccesscontrol.OpenFirewallAccessControlPage();
           braccesscontrol.AllowOrBlockNewDevice("Block");
           
       }
       @Step("Test Step 4:A new PC connect to lan side")
       public void step4() {
           boolean Result1 = false; 
           
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
           handle.sleepi(200);
        }   
          
       @Step("Test Step 5: Check Refresh button,check the new PC list on the page")
       public void step5() {
          boolean Result1 = false;
          Brtmspage.ChangeBrowserHandles(HistroyHandle);
          braccesscontrol.OpenFirewallAccessControlPage();
          handle.sleepi(20);
          Result1 = braccesscontrol.CheckConnectedDeviceInfo(NewDeviceInfo);
          if (Result1 == true) {
              micResult =  true;
              assertTrue(micResult, "Pass:The new PC list on the page,the status is allowed,IP,mac address,device name is correctlyy"  );             
          } else {
              micResult =  false;
              assertTrue(micResult, "Failed:The new PC isn't list on the page!"  );
          }
          
      }
      @Step("Test Step 6:New Lan PC access wan side")
       public void step6() {
           boolean TMSTCPResult = false; 
           
           Brtmspage.ChangeBrowserHandles(TmsPageHandle);
           TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);      
           if (TMSTCPResult == false ) {
               micResult =  true;
               assertTrue(micResult,"Pass:New Lan PC can't access wan side!"  );             
           } else {
               micResult =  false;
               assertTrue(micResult,"Failed:New Lan PC can access wan side!"  );
           }
           
        }
       @Step("Test Step 7:Turn off Access Control!")
       public void step7() {
          
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           braccesscontrol.OpenFirewallAccessControlPage();
           handle.sleepi(20);
           braccesscontrol.TunoffAccessControl();
           CaseResult = true;
           brlogin.BrLogout(); 
        }


}