package businessrouter.BusinessRouterFunction.Firewall.BR_T661;
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
    final static Logger logger = Logger.getLogger("BR_T661");
    public String sTestStr = "BR_T661";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2; 
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T661") // 对应testrunkey
    @Description("107-TrafficRules-LAN to WAN-Accept the traffic match the multiple source ip address") // 对应用例名字
    @TmsLink("1461337") // 对应用例详情页的URL最后几位数字

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
        handle.sleepi(15);
        
    }
   @Step("Step 2:Config LAN to WAN rule1:\r\n" + 
           "--Protocol:TCP,config single ip address: 192.168.1.10~192.168.1.20,action:accept,other use default value.")
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
         brfirewall.OpenFirewallTrafficRulesPage();
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
    
    
    @Step("Test Step 4: Send TCP traffic from 192.168.1.10~192.168.1.20 .")
    public void step4() {
        //BrTMSPage BrTMSPage = new BrTMSPage();
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
       
        boolean TMSTCPResult = false;
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);
        Brtmspage.BackTMSConfigPafe();
        if (TMSTCPResult == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:That TCP traffic source ip is in rule1 source ip is forwarded normally !"); 
        }else {
            micResult =  false;
            assertTrue(micResult, "Failed:That TCP trafficsource ip isn't in rule1 source ip isn't forwarded normally !"); 
        } 
  
        
    }
    @Step("Test Step 5: Send TCP traffic from other source ip addresses")
    public void step5() {
        boolean TMSTCPResult = false;
        boolean TMSUDPResult = false;
        boolean Result1 = false;
        String NewSourceIP = Brtmspage.GetNewIPAddress(TmsTCPCommands.get("Dut IP"));
        TmsPageHandle= Brtmspage.LoginTMS(WebportalParam.brlanconnectip);
        TmsPageHandle2= Brtmspage.GetBrowserHandles();
        String NewCommand = "ifconfig eth0 " + NewSourceIP +" netmask 255.255.255.0";
        TmsChangeIPCommands.replace("Protocol", NewCommand);
        Result1 = Brtmspage.RuncmdByTMS(TmsChangeIPCommands);
        Brtmspage.BackTMSConfigPafe();
        String NewCommand2 = "route add default gw " + WebportalParam.brlangateway;
        TmsChangeIPCommands.replace("Protocol", NewCommand2);
        //TmsChangeIPCommands.replace("Dut IP", NewSourceIP);
        //TmsChangeIPCommands.replace("Host IP", NewSourceIP);
        Result1 = Brtmspage.RuncmdByTMS(TmsChangeIPCommands);
        Brtmspage.BackTMSConfigPafe();
        TmsTCPCommands.replace("Dut IP", NewSourceIP);
        TmsPageHandle= Brtmspage.LoginTMS(NewSourceIP);
       
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);
        Brtmspage.BackTMSConfigPafe();
        if (TMSTCPResult == false) {
            micResult =  true;
            assertTrue(micResult, "Pass:That TCP traffic soure ip isn't in rule1 source ip isn't forwarded normally !"); 
        }else {
            micResult =  false;
            TmsChangeIPCommands.replace("Protocol", "reboot");
            Result1 = Brtmspage.RuncmdByTMS(TmsChangeIPCommands);
            assertTrue(micResult, "Failed:That TCP traffic source ip isn't in rule1 source ip isforwarded normally !"); 
        }
       
        Brtmspage.ChangeBrowserHandles(TmsPageHandle2);
     //   TmsPageHandle= Brtmspage.LoginTMS(WebportalParam.brlanconnectip);
        NewCommand = "ifconfig eth0 " + WebportalParam.brlanclientip +" netmask 255.255.255.0";
        TmsChangeIPCommands.replace("Protocol", NewCommand);        
        Result1 = Brtmspage.RuncmdByTMS(TmsChangeIPCommands);
        Brtmspage.BackTMSConfigPafe();
        NewCommand2 = "route add default gw " + WebportalParam.brlangateway;
        TmsChangeIPCommands.replace("Protocol", NewCommand2);
        //TmsChangeIPCommands.replace("Dut IP", WebportalParam.brlanclientip);
        //TmsChangeIPCommands.replace("Host IP", WebportalParam.brlanclientip);
        Result1 = Brtmspage.RuncmdByTMS(TmsChangeIPCommands);
        //handle.sleepi(30);
    }
        
    

    
    @Step("Test Step 6: Delete all Firewall rules to resotre DUT configuration")
    public void step6() {
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        brfirewall.OpenFirewallTrafficRulesPage();  
        Selenide.sleep(10000);
        System.out.println("delete rules");
        brfirewall.DeleteallTrafficRules();
        CaseResult = true;
        brlogin.BrLogout();
     }
        

}
