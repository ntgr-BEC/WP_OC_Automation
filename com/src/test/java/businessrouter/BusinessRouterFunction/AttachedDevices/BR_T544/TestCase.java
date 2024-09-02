package businessrouter.BusinessRouterFunction.AttachedDevices.BR_T544;
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
    final static Logger logger = Logger.getLogger("BR_T544");
    public String sTestStr = "BR_T544";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle;
    String TmsPageHandle;
    String NewIP;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Attached Devices") // 必须要加，对应目录名
        @Story("BR_T544") // 对应testrunkey
        @Description("005-Attached Devices - Reserve IP- Device name") // 对应用例名字
        @TmsLink("1460184") // 对应用例详情页的URL最后几位数字

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
        
        @Step("Test Step 2: Have 1 lan PC connects to router lan side, get the lan PC MAC ")
        public void step2() {
   
            boolean Result = false;
            HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
            TmsPageHandle  = Brtmspage.GetBrowserHandles();
            Brtmspage.RuncmdByTMS(TmsUpAdpaterCommands);
            Brtmspage.BackTMSConfigPafe();
            String LanPcMac = Brtmspage.GetNetworkCardMacAddress(TmsGetIpAndMacCommands);
            Brtmspage.BackTMSConfigPafe(); 
            if(WebportalParam.DUTType.contentEquals("BR500")) {
                IPReservation.replace("MAC Address", LanPcMac.toUpperCase());
                NewDeviceInfo.replace("MAC", "MAC:"+ LanPcMac.toUpperCase());
             } else {
                 IPReservation.replace("MAC Address", LanPcMac);
                 NewDeviceInfo.replace("MAC", "MAC:"+ LanPcMac);
             }
            
           
            
         }
        
        @Step("Test Step 3: go to Advance->setup->lan setup page,add the device name and MAC addr and IP addr for the reserve PC")
        public void step3() {
            boolean Result = false;
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            brlansetuppage.OpenBasicLanSetupPage();
            String ipreservation = Brtmspage.GetNewIPAddress(IPReservation.get("IP Address")); 
            System.out.println(ipreservation);
            IPReservation.replace("IP Address", ipreservation);  
            NewDeviceInfo.replace("IP", "IP:"+ ipreservation);
            handle.sleepi(2); 
            brlansetuppage.AddIPReservation(IPReservation);
            handle.sleepi(20);  
            Result =  brlansetuppage.IPReservationExistYesOrNot(IPReservation);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Add the device name and MAC addr and IP addr for the reserve PC successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Add the device name and MAC addr and IP addr for the reserve PC unsuccessfully!"  );
            }
         }
        @Step("Test Step 4: Connect the PC to DUT.")
        public void step4() {
            boolean Result1 = false;
            Brtmspage.ChangeBrowserHandles(TmsPageHandle);
            Brtmspage.RuncmdByTMS(TmsRestartAdpaterCommands);
            handle.sleepi(6);
         
         }
    
        
        @Step("Test Step 5: Check the device name in Attach device lists.")
        public void step5() {
            boolean Result = false;
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            brattachdevice.OpenAttachedDevicesPage();
            handle.sleepi(20);
            Result = brattachdevice.CheckConnectedDutDeviceInfo(NewDeviceInfo);
            System.out.print(Result);
            if (Result == true) {
                micResult =  true;
                assertTrue(micResult,"Pass:Connected LAN PC to DUT LAN port information are correct in attached devices page!");  
            }else {
                micResult =  false;
                assertTrue(micResult,"Failed:Connected LAN PC to DUT LAN port information are correct in attached devices page!"); 
            }         
            
       }
        @Step("Test Step 6: Down LAN ETH2.")
        public void step6() {
            boolean Result1 = false;
            Brtmspage.ChangeBrowserHandles(TmsPageHandle);
            Brtmspage.RuncmdByTMS(TmsDownAdpaterCommands);
            handle.sleepi(6);
         
         }
        @Step("Test Step 7: Delete record to the address reservation table")
        public void step7() {
            brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(10); 
           brlansetuppage.DeleteIPReservation(IPReservation.get("Device Name"));
           handle.sleepi(20);  
           CaseResult = true;
           brlogin.BrLogout();
            
         }
        
}
