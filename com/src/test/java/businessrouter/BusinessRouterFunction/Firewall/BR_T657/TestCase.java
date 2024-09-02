package businessrouter.BusinessRouterFunction.Firewall.BR_T657;
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
    final static Logger logger = Logger.getLogger("BR_T657");
    public String sTestStr = "BR_T657";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle; 
    String HistroyHandle1;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T657") // 对应testrunkey
    @Description("103-TrafficRules-LAN to WAN-Accept the traffic match the source zone") // 对应用例名字
    @TmsLink("1461333") // 对应用例详情页的URL最后几位数字

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
  @Step("Test Step 2: Config LAN to WAN rule1:\r\n" + 
           "--Protocol:All,source:lan1 any devices,des:wan source any,action:accept")
    public void step2() {
         boolean Result = false;  
         brvlanpage.OpenAdvancedVlanPage();
         handle.sleepi(12);
         brvlanpage.DeleteVlanMember(VlanPortInfo);
         //brvlanpage.ChangePortTagOrUntag(VlanPortInfo);
         handle.sleepi(10);
         brvlanpage.AddVlan(NewVLANInfo);
         handle.sleepi(10);
         brlansetuppage.OpenBasicLanSetupPage();
         handle.sleepi(15);
         brlansetuppage.AddNewLan(NewLANInfo);
         handle.sleepi(10);
         brfirewall.OpenFirewallTrafficRulesPage();
         
         handle.sleepi(5);
         Result = brfirewall.AddLANWANTrafficRule(Firewallrule1);
         if (Result == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Set firewall rule1 successfully!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Set firewall rule1 unsuccessfully!"  );
         }
            
    }
    @Step("Test Step 3:Config LAN to WAN rule2 to block all traffic from lan2 to wan")
    public void step3() {
         boolean Result1 = false;  
         brfirewall.OpenFirewallTrafficRulesPage();
         handle.sleepi(15);
         Result1 = brfirewall.AddLANWANTrafficRule(Firewallrule2);
         if (Result1 == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Set firewall rule1 successfully!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Set firewall rule1 unsuccessfully!"  );
         }
            
    }
    @Step("Test Step 4: Send ICMP traffic from lan side to wan side")
    public void step4() {
        //BrTMSPage BrTMSPage = new BrTMSPage();
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
      
        boolean TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);
        //Brtmspage.CloseTMSWindows();
        if (TMSTCPResult == true ) {
            micResult =  true;
            assertTrue(micResult,"Pass:LAN1 TCP traffic is allowed!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:LAN1 TCP traffic is blocked!");
        }
        
    }
    @Step("Test Step 5: Send traffic from lan2")
    public void step5() {
        //BrTMSPage BrTMSPage = new BrTMSPage();
        HistroyHandle1= Brtmspage.LoginTMS(WebportalParam.brlanclientip2);
      
        boolean TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands2);
        //Brtmspage.CloseTMSWindows();
        if (TMSTCPResult == false ) {
            micResult =  true;
            assertTrue(micResult,"Pass:LAN2 TCP traffic is allowed!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:LAN2 TCP traffic is blocked!");
        }
        
    }
        
    
    @Step("Test Step 6: Delete all Firewall rules to resotre DUT configuration")
    public void step6() {
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        brfirewall.OpenFirewallTrafficRulesPage();  
        Selenide.sleep(2000);
        System.out.println("delete rules");
        brfirewall.DeleteallTrafficRules();
        brlansetuppage.OpenBasicLanSetupPage();
        Selenide.sleep(5000);
        brlansetuppage.DeleteNewLAN("LAN2");
        util.MyCommonAPIs.sleep(20000);
        brvlanpage.OpenAdvancedVlanPage();
        Selenide.sleep(10000);
        brvlanpage.DeleteallVlans();
        Selenide.sleep(8000);
        brvlanpage.ChangePortTagOrUntag(VlanPortRestoreInfo);
        CaseResult = true;
        brlogin.BrLogout();
     }
        

}