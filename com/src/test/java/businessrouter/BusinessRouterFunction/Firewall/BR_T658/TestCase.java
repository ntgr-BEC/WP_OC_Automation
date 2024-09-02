package businessrouter.BusinessRouterFunction.Firewall.BR_T658;
import static org.testng.Assert.assertTrue;

import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

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
    final static Logger logger = Logger.getLogger("BR_T658");
    public String sTestStr = "BR_T658";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle; 
    String TmsPageHandle;
    String LanClientMac="";
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T658") // 对应testrunkey
    @Description("104-TrafficRules-LAN to WAN-Accept the traffic match the single source mac address") // 对应用例名字
    @TmsLink("1461334") // 对应用例详情页的URL最后几位数字

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
        
        
    }
  @Step("Test Step 2:Get LAN Client MAC address.")
  public void step2() {
      
      HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
      TmsPageHandle= Brtmspage.GetBrowserHandles();
      LanClientMac = Brtmspage.GetNetworkCardMacAddress(TmsGetMacCommands);
      if (LanClientMac != "") {
          logger.info("Can get Cleint MAC address !");  
      }else {
          logger.info("Can't get Cleint MAC address!"); 
      }  
      
      
      
  }
   @Step("Test Step 3:Config LAN to WAN rule1:\r\n" + 
           "--Protocol:All,config single mac address: xx:xx….xx,action:Drop,other use default value.Config LAN to WAN rule2 to block all traffic from lan to wan")
    public void step3() {
         boolean Result = false; 
         Brtmspage.ChangeBrowserHandles(HistroyHandle);
         brfirewall.OpenFirewallTrafficRulesPage();
         handle.sleepi(15);
         Firewallrule1.replace("Source MAC address", LanClientMac);
         logger.info(Firewallrule1.get("Source MAC address")); 
         Result = brfirewall.AddLANWANTrafficRule(Firewallrule1);
         if (Result == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Set firewall rule1 successfully!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Set firewall rule1 unsuccessfully!"  );
         }
         Result = brfirewall.AddLANWANTrafficRule(Firewallrule3);
         if (Result == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Set firewall rule2 successfully!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Set firewall rule2 unsuccessfully!"  );
         }
            
    }
    
    @Step("Test Step 4: Send TCP traffic from lan side to wan side with the configed mac address")
    public void step4() {
        boolean TMSTCPResult= false;
        Brtmspage.ChangeBrowserHandles(TmsPageHandle);
        Brtmspage.BackTMSConfigPafe();
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);      
        if (TMSTCPResult == true ) {
            micResult =  true;
            assertTrue(micResult,"Pass:TCP traffic which mac match rule is allowed!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:TCP traffic which mac match rule is blocked!"  );
        }
        
    }
    @Step("Test Step 5:Send TCP traffic from lan side to wan side with the other mac address")
    public void step5() {
        boolean TMSTCPResult= false;
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        brfirewall.OpenFirewallTrafficRulesPage();
        Selenide.sleep(2000);
        Firewallrule2.replace("Source MAC address", "00:00:00:00:ad:e1");
        brfirewall.EditLANWANTrafficRuleByName(Firewallrule2);  
        Selenide.sleep(2000);
        Brtmspage.ChangeBrowserHandles(TmsPageHandle);
        Brtmspage.BackTMSConfigPafe();
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);      
        if (TMSTCPResult == false ) {
            micResult =  true;
            assertTrue(micResult,"Pass:TCP traffic which mac doesn't match rule is blocked!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:TCP traffic which mac doesn't match rule is allowed!"  );
        }
        
    }
    
    
    @Step("Test Step 6: Delete all Firewall rules to resotre DUT configuration")
    public void step6() {
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        brfirewall.OpenFirewallTrafficRulesPage();  
        Selenide.sleep(5000);
        System.out.println("delete rules");
        brfirewall.DeleteallTrafficRules();
        CaseResult = true;
        brlogin.BrLogout();
        
     }
        

}
