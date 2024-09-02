package businessrouter.BusinessRouterFunction.Firewall.BR_T679;
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
    final static Logger logger = Logger.getLogger("BR_T679");
    public String sTestStr = "BR_T679";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle; 
    String TmsPageHandle;
    String LanClientMac="";
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T679") // 对应testrunkey
    @Description("125-TrafficRules-LAN to WAN-Drop the traffic match the single source port") // 对应用例名字
    @TmsLink("1461355") // 对应用例详情页的URL最后几位数字

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

   @Step("Test Step 2:Config LAN to WAN rule1:\r\n" + 
           "--Protocol:tcp,source port config to 21,action:Drop,other use default value")
    public void step2() {
         boolean Result = false; 
         
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
    
    @Step("Test Step 3: lan pc send tcp traffic with source port 21")
    public void step3() {
        handle.sleepi(15);   
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        TmsPageHandle= Brtmspage.GetBrowserHandles();
        boolean TMSTCPResult= false;
    
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);      
        if (TMSTCPResult == false ) {
            micResult =  true;
            assertTrue(micResult,"Pass:TCP traffic which port match rule is blocked!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:TCP traffic which port match rule is allowed!"  );
        }
        
    }
    @Step("Test Step 4:lan pc send tcp traffic with source port 22")
    public void step4() {
        Brtmspage.BackTMSConfigPafe();
        boolean TMSTCPResult= false; 
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands2);      
        if (TMSTCPResult == true ) {
            micResult =  true;
            assertTrue(micResult,"Pass:TCP traffic which port doesn't match rule is allowed!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:TCP traffic which port doesn't match rule is blocked!"  );
        }
        
    }
    
    
    @Step("Test Step 5: Delete all Firewall rules to resotre DUT configuration")
    public void step5() {
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        //brfirewall.OpenFirewallTrafficRulesPage();  
        handle.sleepi(15);
        System.out.println("delete rules");
        brfirewall.DeleteallTrafficRules();
        CaseResult = true;
        brlogin.BrLogout();
        
     }
        

}
