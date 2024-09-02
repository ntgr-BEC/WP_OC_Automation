package businessrouter.BusinessRouterFunction.Firewall.BR_T446;
import static org.testng.Assert.assertTrue;

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

    public String sTestStr = "BR_T446";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    
    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T446") // 对应testrunkey
    @Description("005-Port Forwarding - Check the “Use the same port range for Internal port” option") // 对应用例名字
    @TmsLink("1455485") // 对应用例详情页的URL最后几位数字

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
          // MyCommonAPIs.sleepi(30);
            BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.defaultLogin(); 
            handle.sleepi(15);
            
        }
        @Step("Test Step 2:Add tcp portforwarding rule with external port range input 40-50")
         public void step2() {
            boolean Result= false;
              brporttrigger.OpenFirewallPortForwardingTriggeringPage();
              handle.sleepi(10);
              brporttrigger.AddcustomerPortForwardingRule(Firewallinboundhttp);

			  handle.sleepi(10);

              Result = brporttrigger.ForwardingRuleExistOrNot(Firewallinboundhttp.get("Rule Name"));
              if (Result == true ) {
                  micResult =  true;
                  assertTrue(micResult,"Pass:set port forwarding rule  with external port range input 40-50 successfully!"  );             
              } else {
                  micResult =  false;
                  assertTrue(micResult,"Failed:set port forwarding rule  with external port range input 40-50 successfully!"  );
              }
              
                 
         }
         
         @Step("Test Step 3: wan pc can access lan side tcp port 40")
         public void step3() {
             HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brwanclientip);
             TmsPageHandle= Brtmspage.GetBrowserHandles();
             boolean TMSTCPResult= false;
         
             TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPHTTPCommands0);   
             if (TMSTCPResult == true ) {
                 micResult =  true;
                 assertTrue(micResult,"Pass:wan pc can access lan side tcp port 40!"  );             
             } else {
                 micResult =  false;
                 assertTrue(micResult,"Failed:wan pc can't access lan side tcp port 40!"  );
             }
             
         }
         @Step("Test Step 4: wan pc can access lan side tcp port 45")
         public void step4 () {
             //HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brwanclientip);
             //TmsPageHandle= Brtmspage.GetBrowserHandles();
             Brtmspage.BackTMSConfigPafe();
             boolean TMSTCPResult= false;
         
             TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPHTTPCommands1);   
             if (TMSTCPResult == true ) {
                 micResult =  true;
                 assertTrue(micResult,"Pass:wan pc can access lan side tcp port 45!"  );             
             } else {
                 micResult =  false;
                 assertTrue(micResult,"Failed:wan pc can't access lan side tcp port 45!"  );
             }
             
         }
         @Step("Test Step 5: wan pc can access lan side tcp 50")
         public void step5() {
            // HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brwanclientip);
            // TmsPageHandle= Brtmspage.GetBrowserHandles();
             Brtmspage.BackTMSConfigPafe();
             boolean TMSTCPResult= false;
         
             TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPHTTPCommands2);   
             if (TMSTCPResult == true ) {
                 micResult =  true;
                 assertTrue(micResult,"Pass:wan pc can access lan side tcp port 50!"  );             
             } else {
                 micResult =  false;
                 assertTrue(micResult,"Failed:wan pc can't access lan side tcp port 50!"  );
             }
             
         }
         
       @Step("Test Step 4: Delete port forwarding rules")
       public void step6() {
          Brtmspage.ChangeBrowserHandles(HistroyHandle);
         // brporttrigger.OpenFirewallPortForwardingTriggeringPage();      
          brporttrigger.DeleteForwardingRule(Firewallinboundhttp.get("Rule Name"));
          CaseResult = true;
          brlogin.BrLogout();
           
        }

}
