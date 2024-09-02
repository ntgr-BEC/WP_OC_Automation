package businessrouter.BusinessRouterFunction.Firewall.BR_T682;
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
    final static Logger logger = Logger.getLogger("BR_T682");
    public String sTestStr = "BR_T682";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle; 
    String TmsPageHandle;
    String LanClientMac="";
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T682") // 对应testrunkey
    @Description("128-TrafficRules-LAN to WAN-Drop the traffic match multiple destination ip address") // 对应用例名字
    @TmsLink("1461358") // 对应用例详情页的URL最后几位数字

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
           "--Protocol:all,des ip address set to 10.1.1.100-10.1.1.120,action:Drop,other use default value")
    public void step2() {
         boolean Result = false; 
         
         brfirewall.OpenFirewallTrafficRulesPage();
         handle.sleepi(5);   
         String SoureIp = Firewallrule1.get("Destnation address end");
         System.out.println(SoureIp);
         String []SoureIpList = SoureIp.split("\\.");
        
         
         int b = Integer.valueOf(SoureIpList[3]).intValue();
         if ( b < 254) {
             b = b + 1;
         } else {
             int a= Integer.valueOf(SoureIpList[0]).intValue();
             if (a < 254) {
                 a = a + 1;
             }
         }
         System.out.print(b);
         String SIPAddress = String.valueOf(b);
         String Newdeip = SoureIpList[0]+"." + SoureIpList[1]+"."+ SoureIpList[2]+"."+SIPAddress;
         
         Firewallrule1.replace("Destnation address end", Newdeip);
         Result = brfirewall.AddLANWANTrafficRule(Firewallrule1);
         if (Result == true) {
             micResult =  true;
             assertTrue(micResult, "Pass:Set firewall rule1 successfully!"  );             
         } else {
             micResult =  false;
             assertTrue(micResult, "Failed:Set firewall rule1 unsuccessfully!"  );
         }
            
    }
    
    @Step("Test Step 3:lan side send traffic with des ip address 10.1.1.100-10.1.1.120")
    public void step3() {
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        TmsPageHandle= Brtmspage.GetBrowserHandles();
        boolean TMSTCPResult= false;
        //Brtmspage.ChangeBrowserHandles(TmsPageHandle);
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);      
        if (TMSTCPResult == false ) {
            micResult =  true;
            assertTrue(micResult,"Pass:TCP traffic which mac match rule is blocked!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:TCP traffic which mac match rule is allowed!"  );
        }
        
    }
    @Step("Test Step 4:lan side send traffic with des ip address 10.1.1.101")
    public void step4() {
        boolean TMSTCPResult= false;
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        //brfirewall.OpenFirewallTrafficRulesPage();
        Selenide.sleep(2000);    
        Firewallrule2.replace("Destnation address start", Firewallrule1.get("Destnation address end"));
        logger.info(Firewallrule2.get("Source address start"));
        Firewallrule2.replace("Destnation address end", Firewallrule1.get("Destnation address end"));
        brfirewall.EditLANWANTrafficRuleByName(Firewallrule2);  
        Selenide.sleep(2000);
        Brtmspage.ChangeBrowserHandles(TmsPageHandle);
        Brtmspage.BackTMSConfigPafe();
        TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);      
        if (TMSTCPResult == true ) {
            micResult =  true;
            assertTrue(micResult,"Pass:TCP traffic which mac doesn't match rule is allowed!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:TCP traffic which mac doesn't match rule is blocked!"  );
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
