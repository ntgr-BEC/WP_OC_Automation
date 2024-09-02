package businessrouter.BusinessRouterFunction.AttachedDevices.BR_T541;
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

    public String sTestStr = "BR_T541";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2;
    String LanClientMac;
    final static Logger logger = Logger.getLogger("BR_T541");
    @Feature("Business Router.AttachedDevices") // 必须要加，对应目录名
    @Story("BR_T541") // 对应testrunkey
    @Description("002-Auto detect Device name and edit device name") // 对应用例名A字
    @TmsLink("1460181") // 对应用例详情页的URL最后几位数字

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
            if (LanClientMac != "") {
                micResult =  true;
                assertTrue(micResult,"Pass:Can get Cleint MAC address !");  
            }else {
                assertTrue(micResult,"Failed:Can't get Cleint MAC address!"); 
            } 
            if(WebportalParam.DUTType.contentEquals("BR500")) {
                NewDeviceInfo.replace("MAC", "MAC:"+LanClientMac.toUpperCase());
                OldDeviceInfo.replace("MAC", "MAC:"+LanClientMac.toUpperCase());
                CustomDeviceInfo.replace("MAC", "MAC:"+LanClientMac.toUpperCase());
             } else {
                 NewDeviceInfo.replace("MAC", "MAC:"+LanClientMac);
                 OldDeviceInfo.replace("MAC", "MAC:"+LanClientMac);
                 CustomDeviceInfo.replace("MAC", "MAC:"+LanClientMac); 
             }
           
            Brtmspage.BackTMSConfigPafe();
         }
         

       @Step("Test Step 3: Connect LAN PC to device Lan port,check the device name,The device name display correctly.")
        public void step3() {
           boolean Result = false;
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           brattachdevice.OpenAttachedDevicesPage();
           handle.sleepi(10);
           Result = brattachdevice.CheckConnectedDutDeviceInfo(OldDeviceInfo);
           System.out.print(Result);
           if (Result == true) {
               micResult =  true;
               assertTrue(micResult,"Pass:Connected LAN PC to DUT LAN port information are correct in attached devices page!");  
           }else {
               micResult =  false;
               assertTrue(micResult,"Failed:Connected LAN PC to DUT LAN port information are correct in attached devices page!"); 
           } 
       
        }
       @Step("Test Step 4:Click device icon,enter edit device page,modify the device name up to 30 characters.")
       public void step4() {
          boolean Result = false;
          Brtmspage.ChangeBrowserHandles(HistroyHandle);
          brattachdevice.OpenAttachedDevicesPage();
          Result = brattachdevice.EditLanDeviceName(CustomDeviceInfo);
          System.out.print(Result);
          if (Result == true) {
              micResult =  true;
              assertTrue(micResult,"Pass:Click device icon,enter edit device page,modify the device name up to 30 characters successfully!");  
          }else {
              micResult =  false;
              assertTrue(micResult,"Failed:Click device icon,enter edit device page,modify the device name up to 30 characters unsuccessfully!"); 
          } 
      
       }
       @Step("Test Step 5:Modify the device name to 31 characters.")
       public void step5() {
          boolean Result = false;
          Brtmspage.ChangeBrowserHandles(HistroyHandle);
          //brattachdevice.OpenAttachedDevicesPage();
          Result = brattachdevice.EditLanDeviceName(CustomDeviceInfo1);
          System.out.print(Result);
          if (Result == false) {
              micResult =  true;
              assertTrue(micResult,"Pass:Modify the device name to 31 characters unsuccessfully!");  
          }else {
              micResult =  false;
              assertTrue(micResult,"Failed:Modify the device name to 31 characters successfully!"); 
          } 
      
       }
       @Step("Test Step 6:Input special characters.")
       public void step6() {
          boolean Result = false;
          Brtmspage.ChangeBrowserHandles(HistroyHandle);
         // brattachdevice.OpenAttachedDevicesPage();
          Result = brattachdevice.EditLanDeviceNameWithSpecialchar(CustomSpecialChar);
          System.out.print(Result);
          if (Result == true) {
              micResult =  true;
              assertTrue(micResult,"Pass:Input special characters,show correct warn message!");  
          }else {
              micResult =  false;
              assertTrue(micResult,"Failed:Input special characters,don't show correct warn message!"); 
          } 
      
       }
       @Step("Test Step 7:Restore DUT configuration ")
       public void step7() {
          boolean Result = false;
          Brtmspage.ChangeBrowserHandles(HistroyHandle);
          brattachdevice.OpenAttachedDevicesPage();
          Result = brattachdevice.EditLanDeviceName(OldDeviceInfo);
          CaseResult = true;
          brlogin.BrLogout(); 
      
       }

}