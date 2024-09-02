package businessrouter.BusinessRouterFunction.AttachedDevices.BR_T545;
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

    public String sTestStr = "BR_T545";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2;
    String LanClientMac;
    final static Logger logger = Logger.getLogger("BR_T545");
    @Feature("Business Router.Attached Devices") // 必须要加，对应目录名
    @Story("BR_T545") // 对应testrunkey
    @Description("006-Access Control - Block a device") // 对应用例名A字
    @TmsLink("1460185") // 对应用例详情页的URL最后几位数字

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
            
        }
        
      @Step("Test Step 2:Get new lan client MAC ")
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
            if(WebportalParam.DUTType.contentEquals("BR500")) {
                NewDeviceInfo.replace("MAC", LanClientMac.toUpperCase());
                AttachedDeviceInfo.replace("MAC", "MAC:"+ LanClientMac.toUpperCase());
             } else {
                 NewDeviceInfo.replace("MAC", LanClientMac);
                 AttachedDeviceInfo.replace("MAC", "MAC:"+ LanClientMac);
             }
            
            Brtmspage.BackTMSConfigPafe();
            handle.sleepi(10);
         }
         

       @Step("Test Step 3: Enable Access Control and  Block a device by manual.")
        public void step3() {
           boolean Result = false;
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           braccesscontrol.OpenFirewallAccessControlPage();
           braccesscontrol.AllowOrBlockNewDevice("Allow");
           handle.sleepi(20);
           Result = braccesscontrol.BlockedOrAllowedConnectedDeviceInfo(NewDeviceInfo);
           if (Result == true) {
               micResult =  true;
               assertTrue(micResult,"Pass:Enable Access Control and  Block a device by manual succssfully");  
           }else {
               micResult =  false;
               assertTrue(micResult,"Failed:Enable Access Control and  Block a device by manual unsuccessfully!"); 
           } 
       }
       @Step("Test Step 4:Go to Administration-Attached Devices pagee,This device status should be Blocked In Attached Devices table.")
       public void step4() {
           boolean Result;
           brattachdevice.OpenAttachedDevicesPage();
           handle.sleepi(20);
           Result = brattachdevice.CheckConnectedDutDeviceInfo(AttachedDeviceInfo);
           System.out.print(Result);
           if (Result == true) {
               micResult =  true;
               assertTrue(micResult,"Pass:This device status is Blocked In Attached Devices table!");  
           }else {
               micResult =  false;
               assertTrue(micResult,"Failed:This device status isn't Blocked In Attached Devices table!"); 
           } 
        }   
          
   
       @Step("Test Step 5:Turn off Access Control to restore DUT configuration!")
       public void step5() {
          
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           braccesscontrol.OpenFirewallAccessControlPage();
           handle.sleepi(20);
           NewDeviceInfo.replace("Status", "Allowed");
           braccesscontrol.BlockedOrAllowedConnectedDeviceInfo(NewDeviceInfo);
           braccesscontrol.TunoffAccessControl();
           CaseResult = true;
           brlogin.BrLogout(); 
        }


}