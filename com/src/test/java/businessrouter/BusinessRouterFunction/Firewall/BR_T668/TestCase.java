package businessrouter.BusinessRouterFunction.Firewall.BR_T668;
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
    final static Logger logger = Logger.getLogger("BR_T668");
    public String sTestStr = "BR_T668";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2; 
    String TmsPageHandle3;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T668") // 对应testrunkey
    @Description("114-TrafficRules-LAN to WAN-Accept the traffic match source ip address and destionation ip address.") // 对应用例名字
    @TmsLink("1461344") // 对应用例详情页的URL最后几位数字

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
        handle.sleepi(15);
        
    }
   @Step("Step 2:Config LAN to WAN rule1:\r\n" + 
           "--Protocol:all,source ip address:192.168.200.10,des ip address 10.1.1.100,action:accept,other use default value.")
    public void step2() {
         boolean Result = false;  
         brfirewall.OpenFirewallTrafficRulesPage();
         handle.sleepi(15);
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
         //brfirewall.OpenFirewallTrafficRulesPage();
         handle.sleepi(15);
         Result = brfirewall.AddLANWANTrafficRule(Firewallrule2);
         if (Result == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Set firewall rule2 successfully!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Set firewall rule2 unsuccessfully!"  );
         }
            
    }
    
    
    @Step("Test Step 4:lan pc (192.168.200.10) access wan side PC (10.1.1.100).")
    public void step4() {
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        boolean TMSTCPResult = false;
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);
        Brtmspage.BackTMSConfigPafe();
        if (TMSTCPResult == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:That TCP traffic source ip and destination ip are in rule1  is allowed!"); 
        }else {
            micResult =  false;
            assertTrue(micResult, "Failed:That TCP traffic source ip and destination ip aren't in rule1  is blocked !"); 
        } 
  
        
    }
    @Step("Test Step 5: lan pc (192.168.200.11) access wan side PC (10.1.1.101)")
    public void step5() {
        boolean TMSTCPResult = false;
        boolean Result1 = false;
        String NewSourceIP = Brtmspage.GetNewIPAddress(TmsTCPCommands.get("Host IP"));
        TmsPageHandle= Brtmspage.LoginTMS(WebportalParam.brwanconnectip);
        TmsPageHandle2= Brtmspage.GetBrowserHandles();
        String NewCommand = "ifconfig eth0 " + NewSourceIP +" netmask 255.255.255.0";
        TmsChangeIPCommands.replace("Protocol", NewCommand);
        Result1 = Brtmspage.RuncmdByTMS(TmsChangeIPCommands);
        Brtmspage.BackTMSConfigPafe();
        String NewCommand2 = "route add default gw " + WebportalParam.brwangateway;
        TmsChangeIPCommands.replace("Protocol", NewCommand2);
        Result1 = Brtmspage.RuncmdByTMS(TmsChangeIPCommands);
        Brtmspage.BackTMSConfigPafe();
        
        
        Brtmspage.ChangeBrowserHandles(TmsPageHandle);
        TmsTCPCommands.replace("Host IP", NewSourceIP);
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);
        Brtmspage.BackTMSConfigPafe();
        if (TMSTCPResult == false) {
            micResult =  true;
            assertTrue(micResult, "Pass::That TCP traffic source ip and destination ip are in rule1 is blocked!"); 
        }else {
            
            TmsChangeIPCommands.replace("Protocol", "reboot");
            Result1 = Brtmspage.RuncmdByTMS(TmsChangeIPCommands);
            micResult =  false;
            assertTrue(micResult, "Failed::That TCP traffic source ip and destination ip aren't in rule1 is allowed !"); 
        } 
        
        Brtmspage.ChangeBrowserHandles(TmsPageHandle2);
        NewCommand = "ifconfig eth0 " + WebportalParam.brwanclientip +" netmask 255.255.255.0";
           TmsChangeIPCommands.replace("Protocol", NewCommand);        
           Result1 = Brtmspage.RuncmdByTMS(TmsChangeIPCommands);
           Brtmspage.BackTMSConfigPafe();
           NewCommand2 = "route add default gw " + WebportalParam.brwangateway;
           TmsChangeIPCommands.replace("Protocol", NewCommand2);
           Result1 = Brtmspage.RuncmdByTMS(TmsChangeIPCommands);
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
