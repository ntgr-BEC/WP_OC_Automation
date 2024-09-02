package businessrouter.BusinessRouterFunction.Firewall.BR_T469;
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

    public String sTestStr = "BR_T469";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2;
    String LanClientMac;
    final static Logger logger = Logger.getLogger("BR_T469");
    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T469") // 对应testrunkey
    @Description("027-Access Control - Change Allow/Block status 5 times") // 对应用例名A字
    @TmsLink("1455508") // 对应用例详情页的URL最后几位数字

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
         

       @Step("Test Step 3: Turn on access control,Access rule select  “Allow all new devices to connect. and Lan PC access wan side and can access WAN PC")
        public void step3() {
           
           boolean TMSTCPResult = false;
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           braccesscontrol.OpenFirewallAccessControlPage();
           handle.sleepi(10);
           braccesscontrol.AllowOrBlockNewDevice("Allow");
      
           Brtmspage.ChangeBrowserHandles(TmsPageHandle);
           TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands); 
           Brtmspage.BackTMSConfigPafe();
           if (TMSTCPResult == true ) {
               micResult =  true;
               assertTrue(micResult,"Pass:New Lan PC can access wan side!"  );             
           } else {
               micResult =  false;
               assertTrue(micResult,"Failed:New Lan PC can't access wan side!"  );
           }
           
        }
      @Step("Test Step 4: Change the status to blocked and The Lan PC can't access wan PC.")
      public void step4() {
          boolean TMSTCPResult = false; 
         Brtmspage.ChangeBrowserHandles(HistroyHandle);
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
     @Step("Test Step 5:Repeat step3/4 5 times,")
     public void step5() {
           boolean TMSTCPResult = false; 
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           braccesscontrol.OpenFirewallAccessControlPage();
           handle.sleepi(10);
           for(int i =0; i < 5 ; i++) {
               braccesscontrol.EditConnectedDeviceInfo(NewDeviceInfo2);
               handle.sleepi(6);
               braccesscontrol.EditConnectedDeviceInfo(NewDeviceInfo1);
               System.out.print(i);
               handle.sleepi(6);
            }
           System.out.print("djdjdjdjdjddjdjdjddjdjdj");
           braccesscontrol.EditConnectedDeviceInfo(NewDeviceInfo2);
           System.out.print("djdjdjdjdjddjdjdjddjdjdj2222222222222222222");
          Brtmspage.ChangeBrowserHandles(TmsPageHandle);
          TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands); 
          Brtmspage.BackTMSConfigPafe();
          if (TMSTCPResult == true ) {
              micResult =  true;
              assertTrue(micResult,"Pass:New Lan PC can access wan side!"  );             
           } else {
              micResult =  false;
              assertTrue(micResult,"Failed:New Lan PC can't access wan side!"  );
           }

}
       @Step("Test Step 6:Turn off Access Control!")
       public void step6() {
          
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           braccesscontrol.OpenFirewallAccessControlPage();
           handle.sleepi(20);
           braccesscontrol.TunoffAccessControl();
           CaseResult = true;
           brlogin.BrLogout(); 
        }


}