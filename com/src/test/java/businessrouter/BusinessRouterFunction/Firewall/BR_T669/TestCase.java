package businessrouter.BusinessRouterFunction.Firewall.BR_T669;
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
    final static Logger logger = Logger.getLogger("BR_T669");
    public String sTestStr = "BR_T669";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2; 
    String TmsPageHandle3;
    String LanClientMac;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T669") // 对应testrunkey
    @Description("115-TrafficRules-LAN to WAN-Accept the traffic match source mac address and source port.") // 对应用例名字
    @TmsLink("1461345") // 对应用例详情页的URL最后几位数字

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
        //handle.sleepi(30);
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.defaultLogin(); 
        handle.sleepi(10);
        
    }
   @Step("Step 2:Config LAN to WAN rule1:\r\n" + 
           "--Protocol:all,config source mac address and source port,action:accept,other use default value.")
    public void step2() {
         boolean Result = false;  
         HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
         TmsPageHandle  = Brtmspage.GetBrowserHandles();
         LanClientMac = Brtmspage.GetNetworkCardMacAddress(TmsGetMacCommands);
         Brtmspage.BackTMSConfigPafe();
         if (LanClientMac != "") {
             micResult =  true;
             assertTrue(micResult,"Pass:Can get Cleint MAC address !");  
         }else {
             assertTrue(micResult,"Failed:Can't get Cleint MAC address!"); 
         } 
         Firewallrule1.replace("Source MAC address", LanClientMac);
         Brtmspage.ChangeBrowserHandles(HistroyHandle);
         brfirewall.OpenFirewallTrafficRulesPage();
         handle.sleepi(10);
         Result = brfirewall.AddLANWANTrafficRule(Firewallrule1);
         if (Result == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Set firewall rule1 successfully!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Set firewall rule1 unsuccessfully!"  );
         }
            
    }
   @Step("Step 3:Config LAN to WAN rule2 to block all traffic from lan to wan.")
    public void step3() {
         boolean Result = false;  
         brfirewall.OpenFirewallTrafficRulesPage();
         handle.sleepi(10);
         Result = brfirewall.AddLANWANTrafficRule(Firewallrule2);
         if (Result == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Set firewall rule2 successfully!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Set firewall rule2 unsuccessfully!"  );
         }
            
    }
    
    
    @Step("Test Step 4:Lan side send traffic match the rule1.")
    public void step4() {
        //HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        boolean TMSTCPResult = false;
        Brtmspage.ChangeBrowserHandles(TmsPageHandle);
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);
        Brtmspage.BackTMSConfigPafe();
        if (TMSTCPResult == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:That TCP traffic match the rule1  is allowed!"); 
        }else {
            micResult =  false;
            assertTrue(micResult, "Failed:That TCP traffic match the rule1  is blocked !"); 
        } 
  
        
    }
    @Step("Test Step 5: Lan side send traffic do not match the rule1.")
    public void step5() {
        boolean TMSTCPResult = false;
        boolean Result1 = false;
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        brfirewall.OpenFirewallTrafficRulesPage();
        handle.sleepi(10);
        //Firewallrule11.replace("Source MAC address", "00:0C:29:C6:AD:56");
        //Firewallrule1.replace("Rule Name", "edit1");
        Result1 = brfirewall.EditLANWANTrafficRuleByName(Firewallrule11);
        if (Result1 == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:Edit firewall rule1 successfully!"  );             
        } else {
            Result1 =  false;
            assertTrue(micResult, "Failed:Edit firewall rule1 unsuccessfully!"  );
        }
        
        Brtmspage.ChangeBrowserHandles(TmsPageHandle);
        //TmsTCPCommands.replace("Host IP", NewSourceIP);
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);
        Brtmspage.BackTMSConfigPafe();
        if (TMSTCPResult == false) {
            micResult =  true;
            assertTrue(micResult, "Pass::That TCP traffic not match the rule1 is blocked!"); 
        }else {
            micResult =  false;
            assertTrue(micResult, "Failed::That TCP traffic not match the rule1 is allowed !"); 
        } 
        
        
    }
        
    

    
    @Step("Test Step 6: Delete all Firewall rules to resotre DUT configuration")
    public void step6() {
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        //brfirewall.OpenFirewallTrafficRulesPage();  
        handle.sleepi(15);
        System.out.println("delete rules");
        brfirewall.DeleteallTrafficRules();
        CaseResult = true;
        brlogin.BrLogout();
     }
        

}
