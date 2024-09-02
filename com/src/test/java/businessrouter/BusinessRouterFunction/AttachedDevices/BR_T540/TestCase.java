package businessrouter.BusinessRouterFunction.AttachedDevices.BR_T540;
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

    public String sTestStr = "BR_T540";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2;
    String LanClientMac;
    final static Logger logger = Logger.getLogger("BR_T540");
    @Feature("Business Router.AttachedDevices") // 必须要加，对应目录名
    @Story("BR_T540") // 对应testrunkey
    @Description("001-All Attached Devices shown") // 对应用例名A字
    @TmsLink("1460180") // 对应用例详情页的URL最后几位数字

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
            handle.sleepi(20);
        }
        
      @Step("Test Step 2:Get new lan client MAC .")
        public void step2() {
            boolean Result1 = false; 
            
            HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
            LanClientMac = Brtmspage.GetNetworkCardMacAddress(TmsGetMacCommands);
            System.out.println(LanClientMac);
            if (LanClientMac != "") {
                micResult =  true;
                assertTrue(micResult,"Pass:Can get Cleint MAC address !");  
            }else {
                assertTrue(micResult,"Failed:Can't get Cleint MAC address!"); 
            } 
            NewDeviceInfo.replace("MAC", "MAC:"+LanClientMac.toUpperCase().toLowerCase());
            Brtmspage.BackTMSConfigPafe();
         }
         

       @Step("Test Step 3: Connect LAN PC to DUT LAN port;Show all attached device,the information(MAC address, ip address, device name) of device should be correct;.")
        public void step3() {
           boolean Result = false;
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           brattachdevice.OpenAttachedDevicesPage();
           Result = brattachdevice.CheckConnectedDutDeviceInfo(NewDeviceInfo);
           System.out.print(Result);
           if (Result == true) {
               micResult =  true;
               assertTrue(micResult,"Pass:Connected LAN PC to DUT LAN port information are correct in attached devices page!");  
           }else {
               micResult =  false;
               assertTrue(micResult,"Failed:Connected LAN PC to DUT LAN port information are correct in attached devices page!"); 
           } 
           CaseResult = true;
           brlogin.BrLogout(); 
        }


}