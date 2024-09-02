package businessrouter.BusinessRouterFunction.Firewall.BR_T666;
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
    final static Logger logger = Logger.getLogger("BR_T666");
    public String sTestStr = "BR_T666";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2; 
    String TmsPageHandle3;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T666") // 对应testrunkey
    @Description("112-TrafficRules-LAN to WAN-Accept the traffic match single destination port.") // 对应用例名字
    @TmsLink("1461342") // 对应用例详情页的URL最后几位数字

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
   @Step("Step 2:Config LAN to WAN rule1:\r\n" + 
           "--Protocol:UDP,des port to:1000,action:accept,other use default value.")
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
    
    
    @Step("Test Step 4:lan side send udp traffic with specified des port.")
    public void step4() {
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        boolean TMSTCPResult = false;
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsUDPCommands);
        Brtmspage.BackTMSConfigPafe();
        if (TMSTCPResult == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:That UDP traffic destination port is in rule1 source ip is allowed!"); 
        }else {
            micResult =  false;
            assertTrue(micResult, "Failed:That UDP traffic destination port isn't in rule1 source ip is blocked !"); 
        } 
  
        
    }
    @Step("Test Step 5: lan side send udp traffic with non-specified des port")
    public void step5() {
        boolean TMSTCPResult = false;
        TmsUDPCommands.replace("WAN port", "1001");
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsUDPCommands);
        Brtmspage.BackTMSConfigPafe();
        if (TMSTCPResult == false) {
            micResult =  true;
            assertTrue(micResult, "Pass:That UDP traffic destination port isn't in rule1 source ip is blocked!"); 
        }else {
            micResult =  false;
            assertTrue(micResult, "Failed:That UDP traffic destination port is in rule1 source ip is allowed !"); 
        } 
        
    }
    @Step("Test Step 6: Lan side send tcp traffic with the specified des port.")
    public void step6() {
        boolean TMSTCPResult = false;
        //TmsTCPCommands.replace("Destination Port", "1001");
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);
        Brtmspage.BackTMSConfigPafe();
        if (TMSTCPResult == false) {
            micResult =  true;
            assertTrue(micResult, "Pass:That TCP traffic destination port isn't in rule1 source ip is blocked!"); 
        }else {
            micResult =  false;
            assertTrue(micResult, "Failed:That TCP traffic destination port is in rule1 source ip is allowed !"); 
        } 
        
    }
        
    

    
    @Step("Test Step 7: Delete all Firewall rules to resotre DUT configuration")
    public void step7() {
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        //brfirewall.OpenFirewallTrafficRulesPage();  
        Selenide.sleep(10000);
        System.out.println("delete rules");
        brfirewall.DeleteallTrafficRules();
        CaseResult = true;
        brlogin.BrLogout();
     }
        

}
