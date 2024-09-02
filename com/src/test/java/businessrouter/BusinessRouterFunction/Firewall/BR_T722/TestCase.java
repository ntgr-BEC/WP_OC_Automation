package businessrouter.BusinessRouterFunction.Firewall.BR_T722;
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

public class TestCase extends TestCaseBase implements Config {
    String tclname = getClass().getName();
    String tmpStr;
    final static Logger logger = Logger.getLogger("BR_T722");
    public String sTestStr = "BR_T722";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle; 
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T722") // 对应testrunkey
    @Description("168-TrafficRules-Test the range of destination port") // 对应用例名字
    @TmsLink("1461398") // 对应用例详情页的URL最后几位数字

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
   @Step("Test Step 2:Destination port config 0")
    public void step2() {
         boolean Result = false;         
         brfirewall.OpenFirewallTrafficRulesPage();
         handle.sleepi(15);
         Result = brfirewall.GetWarningWithWrongConfig(Firewallrule1); 
         System.out.print(Result);
         if (Result == true ) {
             micResult =  true;
             assertTrue(micResult, "Pass:DUT show warn message!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:DUT doesn't show warn message!"  );
         }
            
    }
    @Step("Test Step 3: Destination port config 1")
    public void step3() {
         boolean Result1 = false;  
         //brfirewall.OpenFirewallTrafficRulesPage();
         handle.sleepi(3);
         Result1 = brfirewall.AddLANWANTrafficRule(Firewallrule2);
         if (Result1 == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Set firewall rule1 with Destination port 1 successfully!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Set firewall rule1 with Destination port 1 unsuccessfully!"  );
         }
            
    }
    @Step("Test Step 4: Destination port config 65535")
    public void step4() {
         boolean Result1 = false;  
         Result1 = brfirewall.AddLANWANTrafficRule(Firewallrule3);
         if (Result1 == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Set firewall rule1 with Destination port 65535 successfully!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Set firewall rule1 with Destination port 65535 unsuccessfully!"  );
         }
            
    }
    @Step("Test Step 5:Destination port config 65536")
    public void step5() {
         boolean Result = false;         
         //brfirewall.OpenFirewallTrafficRulesPage();
         handle.sleepi(5);
         Result = brfirewall.GetWarningWithWrongConfig(Firewallrule4);        
         if (Result == true ) {
             micResult =  true;
             assertTrue(micResult, "Pass:DUT show warn message with Destination port 65536 !"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:DUT doesn't show warn message with Destination port 65536!"  );
         }
            
    }
    
    @Step("Test Step 6: Delete all Firewall rules to resotre DUT configuration")
    public void step6() {
        
        //brfirewall.OpenFirewallTrafficRulesPage();  
        handle.sleepi(15);
        System.out.println("delete rules");
        brfirewall.DeleteallTrafficRules();
        CaseResult = true;
        brlogin.BrLogout();
        
     }
        

}