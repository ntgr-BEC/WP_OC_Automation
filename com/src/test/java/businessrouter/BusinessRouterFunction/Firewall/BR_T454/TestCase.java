package businessrouter.BusinessRouterFunction.Firewall.BR_T454;
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

    public String sTestStr = "BR_T454";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    
    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T454") // 对应testrunkey
    @Description("013-Port Forwarding - Verify default services") // 对应用例名字
    @TmsLink("1455493") // 对应用例详情页的URL最后几位数字

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
              
              brporttrigger.OpenFirewallPortForwardingTriggeringPage();
			  handle.sleepi(10);
              brporttrigger.AddcustomerPortForwardingRule(Firewallinboundhttp);
			  handle.sleepi(5);
              brporttrigger.AddcustomerPortForwardingRule(Firewallinboundftp);
			  handle.sleepi(5);
              brporttrigger.AddcustomerPortForwardingRule(Firewallinboundtelnet);
              
                 
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
         @Step("Test Step 4: wan pc can access lan side ftp")
         public void step4() {
             Brtmspage.BackTMSConfigPafe();
             boolean TMSTCPResult= false;
         
             TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPFTPCommands); 
             if (TMSTCPResult == true ) {
                 micResult =  true;
                 assertTrue(micResult,"Pass:wan pc can access lan side ftp!"  );             
             } else {
                 micResult =  false;
                 assertTrue(micResult,"Failed:wan pc can't access lan side ftp!"  );
             }
             
         }
         @Step("Test Step 5: wan pc can access lan side telnet")
         public void step5() {
             Brtmspage.BackTMSConfigPafe();
             boolean TMSTCPResult= false;
         
             TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPTELNETCommands); 
             if (TMSTCPResult == true ) {
                 micResult =  true;
                 assertTrue(micResult,"Pass:wan pc can access lan side telnet!"  );             
             } else {
                 micResult =  false;
                 assertTrue(micResult,"Failed:wan pc can't access lan side telnet!"  );
             }
             
         }
       
       @Step("Test Step 6: Delete port forwarding rules")
       public void step6() {
          Brtmspage.ChangeBrowserHandles(HistroyHandle);
          brporttrigger.OpenFirewallPortForwardingTriggeringPage();      
          brporttrigger.DeleteForwardingRule(Firewallinboundhttp.get("Rule Name"));
          brporttrigger.DeleteForwardingRule(Firewallinboundftp.get("Rule Name"));
          brporttrigger.DeleteForwardingRule(Firewallinboundtelnet.get("Rule Name"));
          CaseResult = true;
          brlogin.BrLogout();
           
        }

}
