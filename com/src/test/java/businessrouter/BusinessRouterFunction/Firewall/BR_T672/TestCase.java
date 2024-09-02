package businessrouter.BusinessRouterFunction.Firewall.BR_T672;
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
    final static Logger logger = Logger.getLogger("BR_T672");
    public String sTestStr = "BR_T672";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle;
    String TmsPageHandle;
    boolean Result1 = false;
    boolean Result2 = false;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T672") // 对应testrunkey
    @Description("Delete lan to wan rules") // 对应用例名字
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
       // handle.sleepi(30);
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.defaultLogin(); 
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
         //brfirewall.OpenFirewallTrafficRulesPage();
         handle.sleepi(15);
         Result1 = brfirewall.AddLANWANTrafficRule(Firewallrule2);
         if (Result1 == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Set firewall rule1 successfully!");             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Set firewall rule1 unsuccessfully!");
         }
            
    }
    @Step("Test Step 4: Send ICMP traffic from lan side to wan side")
    public void step4() {
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        TmsPageHandle= Brtmspage.GetBrowserHandles();
        logger.info(HistroyHandle); 
        logger.info(TmsPageHandle); 
        boolean PingResult = false;
    
        PingResult = Brtmspage.CheckPingPassOrFailed(TmsICMPCommands);
        if (PingResult == true) {
            logger.info("ping  is allowed !");  
        }else {
            logger.info("ping is blocked !"); 
        }
         
        if (PingResult == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:ping successfully!");                
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:ping unsuccessfully!"  );
        }
           
    }
    @Step("Test Step 5: Detele the rule1 and check ping")
    public void step5() {
        //HistroyHandle= Brtmspage.LoginTMS();
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        //brfirewall.OpenFirewallTrafficRulesPage();
        Selenide.sleep(2000);
        brfirewall.DeleteFirewall(Firewallrule1.get("Rule Name"));  
        Selenide.sleep(2000);
        
        boolean PingResult = false;
        
        Brtmspage.ChangeBrowserHandles(TmsPageHandle);
        Brtmspage.BackTMSConfigPafe();
        PingResult = Brtmspage.CheckPingPassOrFailed(TmsICMPCommands);
        if (PingResult == true) {
            
            micResult =  false;
            assertTrue(micResult,"Pass:ping  is allowed !");  
        }else {
            micResult =  true;
            assertTrue(micResult,"Pass:ping  is blocked !");
        }
         
           
    }
    @Step("Test Step 6: Delete all Firewall rules to resotre DUT configuration")
    public void step6() {
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
       // brfirewall.OpenFirewallTrafficRulesPage(); 
        handle.sleepi(15);
        System.out.println("delete rules");
        brfirewall.DeleteallTrafficRules();
        /*if (Result1 == true && Result2 == true) {
            micResult = true;
            assertTrue(micResult, "Pass:UDP,TCP, ICMP can work normally !"  );             
        } else {
            micResult =  false;
            assertTrue(micResult, "Failed:UDP,TCP, ICMP can't work normally!"  );
        }*/
        CaseResult = true;
        brlogin.BrLogout();
    }   
     
        

}
