package businessrouter.BusinessRouterFunction.Firewall.BR_T476;
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

    public String sTestStr = "BR_T476";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2;
    String LanClientMac;
    final static Logger logger = Logger.getLogger("BR_T476");
    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T476") // 对应testrunkey
    @Description("034-Access Control - Test Disable the rules") // 对应用例名A字
    @TmsLink("1455515") // 对应用例详情页的URL最后几位数字

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
          // MyCommonAPIs.sleepi(30);
            BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.defaultLogin();
           // braccesscontrol.OpenFirewallAccessControlPage();
           // braccesscontrol.EditConnectedDeviceInfo(NewDeviceInfo1);
        }
        
      @Step("Test Step 2:Get new lan client MAC")
        public void step2() {
            boolean Result1 = false; 
            
            HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
            TmsPageHandle= Brtmspage.GetBrowserHandles();
            LanClientMac = Brtmspage.GetNetworkCardMacAddress(TmsGetMacCommands);
            if (LanClientMac != "") {
                micResult =  true;
                assertTrue(micResult,"Pass:Can get Cleint MAC address !");  
            }else {
                assertTrue(micResult,"Failed:Can't get Cleint MAC address!"); 
            } 
            NewAllowDeviceInfo.replace("MAC", LanClientMac.toUpperCase());
            NewBlockedDeviceInfo.replace("MAC", LanClientMac.toUpperCase());
            NewDeviceInfo1.replace("MAC", LanClientMac.toUpperCase());
            NewDeviceInfo2.replace("MAC", LanClientMac.toUpperCase());
            Brtmspage.BackTMSConfigPafe();
          
         }
         

   
      @Step("Test Step 3: Turn on access control, A LAN PC conenct to Lan side,the status is block.")
      public void step3() {
          boolean TMSTCPResult = false;
          Brtmspage.ChangeBrowserHandles(HistroyHandle);
          braccesscontrol.OpenFirewallAccessControlPage();
          handle.sleepi(10);
          braccesscontrol.AllowOrBlockNewDevice("Allow");
      
         //Brtmspage.ChangeBrowserHandles(HistroyHandle);
         braccesscontrol.OpenFirewallAccessControlPage();
         handle.sleepi(10);
         braccesscontrol.EditConnectedDeviceInfo(NewDeviceInfo1);
     
         
         Brtmspage.ChangeBrowserHandles(TmsPageHandle);
         TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands); 
         Brtmspage.BackTMSConfigPafe();
         if (TMSTCPResult == false ) {
             micResult =  true;
             assertTrue(micResult,"Pass:New Lan PC can't access wan side!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult,"Failed:New Lan PC can access wan side!"  );
         }
         
     }
   
      @Step("Test Step 4: Turn off access control,then the LAN PC access wan side.")
      public void step4() {
          boolean TMSTCPResult = false;
          
          Brtmspage.ChangeBrowserHandles(HistroyHandle);
          braccesscontrol.OpenFirewallAccessControlPage();
          handle.sleepi(10);
          braccesscontrol.TunoffAccessControl();  
         Brtmspage.ChangeBrowserHandles(TmsPageHandle);
         TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands); 
         Brtmspage.BackTMSConfigPafe();
         if (TMSTCPResult == true ) {
             micResult =  true;
             assertTrue(micResult,"Pass:New Lan PC can't access wan side!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult,"Failed:New Lan PC can access wan side!"  );
         }
      }
       @Step("Test Step 5:Turn off Access Control!")
       public void step5() {
          
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           
           braccesscontrol.OpenFirewallAccessControlPage();
           handle.sleepi(10);
           braccesscontrol.AllowOrBlockNewDevice("Allow"); 
           handle.sleepi(5);
           braccesscontrol.OpenFirewallAccessControlPage();
           handle.sleepi(10);
           braccesscontrol.EditConnectedDeviceInfo(NewDeviceInfo2);
           handle.sleepi(5);
           braccesscontrol.TunoffAccessControl();
           CaseResult = true;
           brlogin.BrLogout(); 
        }


}