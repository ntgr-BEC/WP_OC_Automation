package businessrouter.BusinessRouterFunction.Firewall.BR_T654;
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
    final static Logger logger = Logger.getLogger("BR_T654");
    public String sTestStr = "BR_T654";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle; 
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T654") // 对应testrunkey
    @Description("100-TrafficRules-LAN to WAN-Accept the traffic match protocol ICMP") // 对应用例名字
    @TmsLink("1461330") // 对应用例详情页的URL最后几位数字

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
        handle.sleepi(30);
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.defaultLogin(); 
        handle.sleepi(15);
    }
    @Step("Test Step 2: Config LAN to WAN rule1:--Protocol:ICMP,source:lan any devices,des:wan source any,action:accept")
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
    @Step("Test Step 3: Config LAN to WAN rule1:--Protocol:ICMP,source:lan any devices,des:wan source any,action:accept")
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
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        boolean TMSUDPResult = false;
        boolean TMSTCPResult = false;
        boolean PingResult = false;
        TMSUDPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsUDPCommands);
        if (TMSUDPResult == true) {
            logger.info("UDP traffic is allowed !");  
        }else {
            logger.info("UDP traffic is blocked !"); 
        }  
        Brtmspage.BackTMSConfigPafe();
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);
        if (TMSTCPResult == true) {
            logger.info("TCP traffic is allowed !");  
        }else {
            logger.info("TCP traffic is blocked !"); 
        } 
        Brtmspage.BackTMSConfigPafe();
        PingResult = Brtmspage.CheckPingPassOrFailed(TmsICMPCommands);
        if (PingResult == true) {
            logger.info("ping  is allowed !");  
        }else {
            logger.info("ping is blocked !"); 
        }
         
        if (TMSUDPResult == false && TMSTCPResult== false && PingResult == true) {
            micResult =  true;
            logger.info("Pass:UDP,TCP, ICMP can work normally !"  );             
        } else {
            micResult =  false;
            logger.info("Failed:UDP,TCP, ICMP can't work normally!"  );
        }
        /*Javasocket javasocket = new Javasocket();
        String tclRootPath = System.getProperty("user.dir").replace("\\", "/").replace("com", "")+"TCL_ROOT";
        String automationPath=System.getProperty("user.dir").replace("\\", "/").replace("com", "");
        System.out.println(tclRootPath);
        
        String tclname = this.getClass().getName();
        System.out.println(tclname);
        // 开始打流
        RunCommand command = new RunCommand();
        command.runTclShell(tclname);
        // 开始JAVA和TCL交互
        micResult = javasocket.readResultFromTclShell();
        // 用TestNG的assertTrue验证MicResult，它能标记这个case是否是正确的
        if (micResult == true) {
            assertTrue(micResult, "Pass:Firewall rules can work normally!"  );             
        } else {
            System.out.print("d222222222222222111");
            micResult =  false;
            assertTrue(micResult, "Failed:Firewall rules can work normally!"  );
        }
        //assertTrue(micResult);
        //javasocket.wakeUpTclShell();
        //javasocket.waitForTclShell();
        */    
    }
    @Step("Test Step 5: Delete all Firewall rules to resotre DUT configuration")
    public void step5() {
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        brfirewall.OpenFirewallTrafficRulesPage();  
        Selenide.sleep(10000);
        System.out.println("delete rules");
        brfirewall.DeleteallTrafficRules();
        if (micResult =  true) {
            
            assertTrue(micResult, "Pass:UDP,TCP, ICMP can work normally !"  );             
        } else {
            //micResult =  false;
            assertTrue(micResult, "Failed:UDP,TCP, ICMP can't work normally!"  );
        }
        CaseResult = true;
        brlogin.BrLogout();
        
     }
        

}
