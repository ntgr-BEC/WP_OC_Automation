package businessrouter.BusinessRouterFunction.Firewall.BR_T486;
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

    public String sTestStr = "BR_T486";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2;
    String LanClientMac;
    final static Logger logger = Logger.getLogger("BR_T486");
    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T486") // 对应testrunkey
    @Description("044-Access Control - Edit device info for currently connected device") // 对应用例名A字
    @TmsLink("1455525") // 对应用例详情页的URL最后几位数字

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
         

   
      @Step("Test Step 3: User can block a device manually by selecting the specific block device and clicking on Block..")
      public void step3() {
          boolean Result = false;
          Brtmspage.ChangeBrowserHandles(HistroyHandle);
          braccesscontrol.OpenFirewallAccessControlPage();
          handle.sleepi(10);
          braccesscontrol.AllowOrBlockNewDevice("Allow");
      
         handle.sleepi(5);
         braccesscontrol.OpenFirewallAccessControlPage();
         handle.sleepi(10);
         braccesscontrol.EditConnectedDeviceInfo(NewDeviceInfo1);
         handle.sleepi(5);
         Result = braccesscontrol.CheckConnectedDeviceInfo(NewBlockedDeviceInfo);
         if (Result == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Change access control from allow to block successfully"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Change access control from allow to block unsuccessfully!"  );
         }
         
  
     }
     @Step("Test Step 4: Edit deive name to sepecial characters.")
     public void step4() {
          boolean Result = false; 
         braccesscontrol.OpenFirewallAccessControlPage();
         handle.sleepi(10);
         NewDeviceInfo1.replace("Device Name", "test_1-0");
         NewBlockedDeviceInfo.replace("Device Name", "test_1-0");
         braccesscontrol.EditConnectedDeviceInfo(NewDeviceInfo1);  
         handle.sleepi(5);
         Result = braccesscontrol.CheckConnectedDeviceInfo(NewBlockedDeviceInfo);
         if (Result == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Edit deive name to sepecial characters successfully"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Edit deive name to sepecial characters unsuccessfully!"  );
         }
         
  
     }
     @Step("Test Step 5: Edit deive name to sepecial characters.")
     public void step5() {
          boolean Result = false; 
         braccesscontrol.OpenFirewallAccessControlPage();
         handle.sleepi(10);
         NewDeviceInfo1.replace("Device Name", "Test1234567890");
         NewDeviceInfo1.replace("Status","Allow");
         NewAllowDeviceInfo.replace("Device Name", "Test1234567890");
         braccesscontrol.EditConnectedDeviceInfo(NewDeviceInfo1);
         handle.sleepi(5);
         Result = braccesscontrol.CheckConnectedDeviceInfo(NewAllowDeviceInfo);
         if (Result == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Edit deive name to sepecial characters successfully"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Edit deive name to sepecial characters unsuccessfully!"  );
         }
         
  
     }

       @Step("Test Step 6:Turn off Access Control!")
       public void step6() {
          
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           
           braccesscontrol.OpenFirewallAccessControlPage();
           
         
           handle.sleepi(20);
           braccesscontrol.EditConnectedDeviceInfo(NewDeviceInfo2);
           handle.sleepi(5);
           braccesscontrol.TunoffAccessControl();
           CaseResult = true;
           brlogin.BrLogout(); 
        }


}