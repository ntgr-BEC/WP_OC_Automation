package businessrouter.BusinessRouterFunction.Firewall.BR_T720;
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
    final static Logger logger = Logger.getLogger("BR_T720");
    public String sTestStr = "BR_T720";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle; 
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T720") // 对应testrunkey
    @Description("166-TrafficRules-Test traffic rule name") // 对应用例名字
    @TmsLink("1461396") // 对应用例详情页的URL最后几位数字

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
   @Step("Test Step 2:Traffic rule name use special characters.")
    public void step2() {
         boolean Result = false;  
         brfirewall.OpenFirewallTrafficRulesPage();
         handle.sleepi(15);
         Result = brfirewall.AddLANWANTrafficRule(Firewallrule1);
         if (Result == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Set firewall rule1 with special characters name successfully!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Set firewall rule1 with special characters name unsuccessfully!"  );
         }
            
    }
    @Step("Test Step 3: Traffic rule name input 32 characters")
    public void step3() {
         boolean Result1 = false;  
         //brfirewall.OpenFirewallTrafficRulesPage();
         //handle.sleepi(15);
         Result1 = brfirewall.AddLANWANTrafficRule(Firewallrule2);
         if (Result1 == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Set firewall rule1 with 32 characters name successfully!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Set firewall rule1 with 32 characters name unsuccessfully!"  );
         }
            
    }
    @Step("Test Step 4: Traffic rule name input 33 characters")
    public void step4() {
         boolean Result1 = false; 
         String RealRuleName;
         //brfirewall.OpenFirewallTrafficRulesPage();
         handle.sleepi(15);
         RealRuleName = brfirewall.GetOjectText("Rule Name",Firewallrule3.get("Rule Name"));
         int Reallength = RealRuleName.length();
         System.out.print(Reallength);
         if (Reallength == 32) {
             Result1 = true;
         } else {
             Result1 = false;
         }
         if (Result1 == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Set firewall rule1 with 32 characters name successfully!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Set firewall rule1 with 32 characters name unsuccessfully!"  );
         }
            
    }
    
    
    @Step("Test Step 5: Delete all Firewall rules to resotre DUT configuration")
    public void step5() {
        
        //brfirewall.OpenFirewallTrafficRulesPage();  
        handle.sleepi(15);
        System.out.println("delete rules");
        brfirewall.DeleteallTrafficRules();
        CaseResult = true;
        brlogin.BrLogout();
        
     }
        

}