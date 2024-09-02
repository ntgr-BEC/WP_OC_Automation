package businessrouter.BusinessRouterFunction.VLAN.BR_T510;
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
    final static Logger logger = Logger.getLogger("BR_T510");
    public String sTestStr = "BR_T510";
    public String sCurrentValue;
    public String sExpectedtValue;
    public String NewIP;
    public String LanPcMac;

    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.VLAN") // 必须要加，对应目录名
    @Story("BR_T510") // 对应testrunkey
    @Description("022-Check the fuction of DHCP Server") // 对应用例名字
    @TmsLink("1458416") // 对应用例详情页的URL最后几位数字

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
  @Step("Test Step 2: Add a new LAN2 and enable DHCP server on LAN2")
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
         
   }
  @Step("Test Step 3: Create a item for a pc, and pc can get the reserved ip addrss,ip1")
  public void step3() {
       boolean Result = false;  
       Brtmspage.ChangeBrowserHandles(TmsPageHandle);
       Brtmspage.RuncmdByTMS(TmsRestartAdpaterCommands);
       handle.sleepi(6);
       Brtmspage.BackTMSConfigPafe();
       String LanPcNewIP = Brtmspage.GetNetworkCardIpAddress(TmsGetIpAndMacCommands);   
       System.out.println(LanPcNewIP);
       handle.sleepi(10);

       Brtmspage.BackTMSConfigPafe();
       if (LanPcNewIP.length() > 0) {
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
  @Step("Test Step 4:Restore DUT configuration")
  public void step4() {
       Brtmspage.ChangeBrowserHandles(HistroyHandle);
       BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
       BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
       MyCommonAPIs.sleepi(10);
       if (WebportalParam.DUTType.contentEquals("BR500")) {
           BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
       }else if(WebportalParam.DUTType.contentEquals("BR100")) {
           BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR100_default.cfg");                       
       }
       
       MyCommonAPIs.sleepi(150);
              
  }
  @Step("Test Step 5: Return CaseResult")
  public void step5() {
      CaseResult = true;
  }
}
