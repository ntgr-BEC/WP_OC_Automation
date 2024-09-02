package businessrouter.BusinessRouterFunction.Firewall.BR_T453;
import static org.testng.Assert.assertTrue;

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

    public String sTestStr = "BR_T453";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    
    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T453") // 对应testrunkey
    @Description("012-Port Forwarding - Verify the function can work after reboot DUT") // 对应用例名字
    @TmsLink("1455492") // 对应用例详情页的URL最后几位数字

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
          // MyCommonAPIs.sleepi(30);
            BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.defaultLogin(); 
			handle.sleepi(10);
            
        }
        @Step("Test Step 2:Add http,ftp,telnet portforwarding rules")
         public void step2() {
            boolean Result= false;
              brporttrigger.OpenFirewallPortForwardingTriggeringPage();
			  handle.sleepi(6);
              brporttrigger.AddcustomerPortForwardingRule(Firewallinboundhttp);
			  handle.sleepi(10);

              Result = brporttrigger.ForwardingRuleExistOrNot(Firewallinboundhttp.get("Rule Name"));
              if (Result == true ) {
                  micResult =  true;
                  assertTrue(micResult,"Pass:set port forwarding rule http successfully!"  );             
              } else {
                  micResult =  false;
                  assertTrue(micResult,"Failed:set port forwarding rule http successfully!"  );
              }
              
                 
         }
         
         @Step("Test Step 3: wan pc can access lan side http")
         public void step3() {
             HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brwanclientip);
             TmsPageHandle= Brtmspage.GetBrowserHandles();
             boolean TMSTCPResult= false;
         
             TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPHTTPCommands);   
             if (TMSTCPResult == true ) {
                 micResult =  true;
                 assertTrue(micResult,"Pass:wan pc can access lan side http!"  );             
             } else {
                 micResult =  false;
                 assertTrue(micResult,"Failed:wan pc can't access lan side http!"  );
             }
             
         }
         @Step("Test Step 4: reboot DUT ")
         public void step4() {
             Brtmspage.ChangeBrowserHandles(HistroyHandle);
             brdashboard.OpenDashboardPage();
             brdashboard.RebootDUT();
             Selenide.sleep(30000);
         }
         @Step("Test Step 5: wan pc can access lan side http")
         public void step5() {
             Selenide.sleep(30000);
             Brtmspage.ChangeBrowserHandles(TmsPageHandle);
             Brtmspage.BackTMSConfigPafe();
             boolean TMSTCPResult= false;
         
             TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPHTTPCommands); 
             if (TMSTCPResult == true ) {
                 micResult =  true;
                 assertTrue(micResult,"Pass:wan pc can access lan side http!"  );             
             } else {
                 micResult =  false;
                 assertTrue(micResult,"Failed:wan pc can't access lan side http!"  );
             }
             
         }
       
       @Step("Test Step 6: Delete port forwarding rules")
       public void step6() {
          Brtmspage.ChangeBrowserHandles(HistroyHandle);
          brporttrigger.OpenFirewallPortForwardingTriggeringPage();      
          brporttrigger.DeleteForwardingRule(Firewallinboundhttp.get("Rule Name"));
          CaseResult = true;
          brlogin.BrLogout();
           
        }

}
