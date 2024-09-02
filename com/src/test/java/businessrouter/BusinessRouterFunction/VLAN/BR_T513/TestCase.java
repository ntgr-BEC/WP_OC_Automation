package businessrouter.BusinessRouterFunction.VLAN.BR_T513;
import static org.testng.Assert.assertTrue;

import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
public class TestCase extends TestCaseBase implements Config {
    String tclname = getClass().getName();
    String tmpStr;
    String HistroyHandle;
    String TmsPageHandle;
    final static Logger logger = Logger.getLogger("BR_T513");
    public String sTestStr = "BR_T513";
    public String sCurrentValue;
    public String sExpectedtValue;
    public String NewIP;
    public String LanPcMac;

    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.VLAN") // 必须要加，对应目录名
    @Story("BR_T513") // 对应testrunkey
    @Description("026- Verify the funtion of Address Reservation") // 对应用例名字
    @TmsLink("1458420") // 对应用例详情页的URL最后几位数字

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
        //handle.sleepi(30);
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.defaultLogin(); 
        handle.sleepi(12);
        
           
    }
  @Step("Test Step 2: Createa a lan and enable Address Reservationt")
   public void step2() {
        boolean Result = false;  
        brvlanpage.OpenAdvancedVlanPage();
        handle.sleepi(12);
        //brvlanpage.ChangePortTagOrUntag(VlanPortInfo);
        brvlanpage.DeleteVlanMember(VlanPortInfo);
        handle.sleepi(10);
        brvlanpage.AddVlan(NewVLANInfo);
        handle.sleepi(10);
        brlansetuppage.OpenBasicLanSetupPage();
        handle.sleepi(15);
        brlansetuppage.AddNewLanWithDhcp(NewLANInfo);
        handle.sleepi(10);  
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanconnectip2);
        TmsPageHandle  = Brtmspage.GetBrowserHandles();
        LanPcMac = Brtmspage.GetNetworkCardMacAddress(TmsGetIpAndMacCommands);
        System.out.println(LanPcMac);
        NewIP = Brtmspage.GetNewIPAddress(WebportalParam.brlanclientip2);
        Brtmspage.BackTMSConfigPafe();
        IPReservation.replace("MAC Address", LanPcMac.toUpperCase());
        System.out.println(IPReservation.get("MAC Address")); 
        IPReservation.replace("IP Address", NewIP);
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        System.out.println("66666666666666666666666666666666666");
        brlansetuppage.ChangeLAN("LAN2");
        System.out.println("666666666666666666666666666666666667");
        brlansetuppage.AddIPReservationForDefinedLan(IPReservation);
        handle.sleepi(5);  
   }
  @Step("Test Step 3: Create a item for a pc, and pc can get the reserved ip addrss,ip1")
  public void step3() {
       boolean Result = false;  
       Brtmspage.ChangeBrowserHandles(TmsPageHandle);
       Brtmspage.RuncmdByTMS(TmsRestartAdpaterCommands);
       handle.sleepi(6);
       Brtmspage.BackTMSConfigPafe();
       String LanPcNewIP = Brtmspage.GetNetworkCardIpAddress(TmsGetIpAndMacCommands);         
       handle.sleepi(10);

       Brtmspage.BackTMSConfigPafe();
       if (NewIP.contentEquals(LanPcNewIP)) {
           System.out.print("dddddddddddd222111");
           Result = true;
       }
       if (Result == true) {
           micResult =  true;
           assertTrue(micResult,"Pass:PC can get reservation ip address in this lan successfully!"  );             
       } else {
           micResult =  false;
           assertTrue(micResult,"Failed:PC can get reservation ip address in this lan unsuccessfully!"  );
       }
              
  }
  @Step("Test Step 4:Delete the item")
  public void step4() {
       boolean Result = false;  
       Brtmspage.ChangeBrowserHandles(HistroyHandle);
       brlansetuppage.OpenBasicLanSetupPage();
       handle.sleepi(10);
       brlansetuppage.ChangeLAN("LAN2");
       brlansetuppage.DeleteLan2IPReservation(IPReservation.get("Device Name"));
       handle.sleepi(16);
       NewIP = Brtmspage.GetNewIPAddress(NewIP);
       IPReservation.replace("MAC Address", LanPcMac.toUpperCase());
       System.out.println(IPReservation.get("MAC Address")); 
       IPReservation.replace("IP Address", NewIP);
       System.out.println(IPReservation.get("IP Address"));
       brlansetuppage.ChangeLAN("LAN2");
       brlansetuppage.AddIPReservationForDefinedLan(IPReservation);
       handle.sleepi(10);
       Brtmspage.ChangeBrowserHandles(TmsPageHandle);
       Brtmspage.RuncmdByTMS(TmsRestartAdpaterCommands);
       handle.sleepi(6);
       Brtmspage.BackTMSConfigPafe();
       String LanPcNewIP2 = Brtmspage.GetNetworkCardIpAddress(TmsGetIpAndMacCommands);   
       System.out.println("LanPcNewIP2");
       handle.sleepi(10);
       if (NewIP.contentEquals(LanPcNewIP2)) {
           System.out.print("dddddddddddd222111");
           Result = true;
       }
       if (Result == true) {
           micResult =  true;
           assertTrue(micResult,"Pass:PC can get reservation ip address in this lan successfully!"  );             
       } else {
           micResult =  false;
           assertTrue(micResult,"Failed:PC can get reservation ip address in this lanunsuccessfully!"  );
       }
       Brtmspage.ChangeBrowserHandles(HistroyHandle);
       BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
       BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
       MyCommonAPIs.sleepi(10);
       if (WebportalParam.DUTType.contentEquals("BR500")) {
           BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
       } else if(WebportalParam.DUTType.contentEquals("BR100")) {
           BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR100_default.cfg");
           
       }
       MyCommonAPIs.sleepi(150);
              
  }
  @Step("Test Step 5: Return CaseResult")
  public void step5() {
      CaseResult = true;
  }
}
