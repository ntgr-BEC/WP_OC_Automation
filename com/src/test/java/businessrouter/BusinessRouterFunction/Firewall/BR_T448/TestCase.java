package businessrouter.BusinessRouterFunction.Firewall.BR_T448;
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

    public String sTestStr = "BR_T448";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    
    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T448") // 对应testrunkey
    @Description("007-Port Forwarding - Internal IP address select the IP address from Attached Devices list.") // 对应用例名字
    @TmsLink("1455487") // 对应用例详情页的URL最后几位数字

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
        @Step("Test Step 2:1.Add pre-set rules Service(e.g. FTP )")
         public void step2() {
            boolean Result= false;
              brporttrigger.OpenFirewallPortForwardingTriggeringPage();
              brporttrigger.AddcustomerPortForwardingRule(Firewallinboundftp);
			  handle.sleepi(10);

              Result = brporttrigger.ForwardingRuleExistOrNot(Firewallinboundftp.get("Rule Name"));
              if (Result == true ) {
                  micResult =  true;
                  assertTrue(micResult,"Pass:set port forwarding rule http successfully!"  );             
              } else {
                  micResult =  false;
                  assertTrue(micResult,"Failed:set port forwarding rule http successfully!"  );
              }
              
                 
         }
        @Step("Test Step 3:Edit the pre-set rules,Select the attached device,then save)")
        public void step3() {
           boolean Result= false;
             brporttrigger.OpenFirewallPortForwardingTriggeringPage();
             brporttrigger.EditcustomerPortForwardingRule(Firewallinboundftp2);
             handle.sleepi(20);
             //Result = brporttrigger.ForwardingRuleExistOrNot(Firewallinboundftp2.get("Rule Name"));
             //System.out.print(Result);
             //if (Result == true ) {
             //    micResult =  true;
             //    assertTrue(micResult,"Pass: Edit port forwarding rule http successfully!"  );             
             //} else {
            //     micResult =  false;
            //     assertTrue(micResult,"Failed:Edit port forwarding rule http successfully!"  );
           //  }
             
            // System.out.print("dddddddddddddddddddddddddddd");   
        }
         
         @Step("Test Step 4: Access LAN side FTP as edited rule")
         public void step4() {
             HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brwanclientip);
             TmsPageHandle= Brtmspage.GetBrowserHandles();
             boolean TMSTCPResult= false;
         
             TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPFTPCommands);   
             if (TMSTCPResult == true ) {
                 micResult =  true;
                 assertTrue(micResult,"Pass:Access LAN side FTP as edited rule successfully!"  );             
             } else {
                 micResult =  false;
                 assertTrue(micResult,"Failed:Access LAN side FTP as edited rule unsuccessfully!"  );
             }
             
         }
         
       @Step("Test Step 5: Delete port forwarding rules")
       public void step5() {
          Brtmspage.ChangeBrowserHandles(HistroyHandle);
          brporttrigger.OpenFirewallPortForwardingTriggeringPage();      
          brporttrigger.DeleteForwardingRule(Firewallinboundftp2.get("Rule Name"));
          CaseResult = true;
          brlogin.BrLogout();
           
        }

}
