package businessrouter.BusinessRouterFunction.AttachedDevices.BR_T552;
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
    final static Logger logger = Logger.getLogger("BR_T552");
    public String sTestStr = "BR_T552";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle;
    String TmsPageHandle;
    String NewIP;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Attached Devices") // 必须要加，对应目录名
        @Story("BR_T552") // 对应testrunkey
        @Description("014-Remove a device") // 对应用例名字
        @TmsLink("1460192") // 对应用例详情页的URL最后几位数字

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
            String LanPcIP = Brtmspage.GetNetworkCardIpAddress(TmsGetIpAndMacCommands);
            Brtmspage.BackTMSConfigPafe();  
            if(WebportalParam.DUTType.contentEquals("BR500")) {
                NewDeviceInfo.replace("MAC", "MAC:"+ LanPcMac.toUpperCase());
            } else {
                NewDeviceInfo.replace("MAC", "MAC:"+ LanPcMac);
            }
          
            
            NewDeviceInfo.replace("IP", "IP:"+ LanPcIP);
            
         }
        
        
        @Step("Test Step 4: Disconnect LAN PC .")
        public void step4() {
       
            Brtmspage.RuncmdByTMS(TmsDownAdpaterCommands);
            Brtmspage.BackTMSConfigPafe();
            handle.sleepi(6);
         
         }
    
        
        @Step("Test Step 5: Go to Administration-Attached Devices page,LAN pc doesn't exist in attached device lists.")
        public void step5() {
            boolean Result = false;
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            brattachdevice.OpenAttachedDevicesPage();
            handle.sleepi(20);
            Result = brattachdevice.CheckConnectedDutDeviceInfo(NewDeviceInfo);
            System.out.print(Result);
            if (Result == false) {
                micResult =  true;
                assertTrue(micResult,"Pass:LAN pc doesn't exist in attached device lists!");  
            }else {
                micResult =  false;
                assertTrue(micResult,"Failed:LAN pc exists in attached device lists!"); 
            }         
            CaseResult = true;
       }
       
        
}
