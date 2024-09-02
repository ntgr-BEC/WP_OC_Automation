package businessrouter.BusinessRouterFunction.FirewallBasicSetup.BR_T357;
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

    public String sTestStr = "BR_T357";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    
    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T357") // 对应testrunkey
    @Description("005-Enable and Disable default DMZ server") // 对应用例名字
    @TmsLink("1455361") // 对应用例详情页的URL最后几位数字

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
            handle.sleepi(10);
            
        }
        @Step("Test Step 2:Click the default DMZ Server check box,input ip address,then Apply")
         public void step2() {
            boolean Result= false;
            brfirewallbasicpage.OpenFirewallBasicSetupPage();
            Result = brfirewallbasicpage.EnableOrDisableDMZ(DmzInfo);
            if (Result == true ) {
                  micResult =  true;
                  assertTrue(micResult,"Pass:Save DMZ successfully!"  );             
            } else {
                  micResult =  false;
                  assertTrue(micResult,"Failed:Save DMZ unsuccessfully!"  );
            }
              
                 
         }
         
         @Step("Test Step 3:On WAN side,access router wan use ftp")
         public void step3() {
             handle.sleepi(10);
             HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brwanclientip);
             TmsPageHandle= Brtmspage.GetBrowserHandles();
             boolean TMSTCPResult= false;
         
             TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPFtpCommands);   
             if (TMSTCPResult == true ) {
                 micResult =  true;
                 assertTrue(micResult,"Pass:wan pc can access lan side ftp!"  );             
             } else {
                 micResult =  false;
                 assertTrue(micResult,"Failed:wan pc can't access lan side ftp!"  );
             }
             Brtmspage.BackTMSConfigPafe();
             
         }
         @Step("Test Step 4: Disable the default DMZ Server")
         public void step4() {
             boolean Result = false;
             Brtmspage.ChangeBrowserHandles(HistroyHandle);
             brfirewallbasicpage.OpenFirewallBasicSetupPage();
             handle.sleepi(15);
             Result = brfirewallbasicpage.EnableOrDisableDMZ(DisabeDmzInfo);
             System.out.print(Result);
             if (Result == true ) {
                   micResult =  true;
                   assertTrue(micResult,"Pass:Disable DMZ successfully!"  );             
             } else {
                   micResult =  false;
                   assertTrue(micResult,"Failed:Disable DMZ unsuccessfully!"  );
             }
         }
         @Step("Test Step 5:On WAN side,access router wan use ftp ")
         public void step5() {
             Brtmspage.ChangeBrowserHandles(TmsPageHandle);
             boolean TMSTCPResult= false;
         
             TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPFtpCommands);   
             if (TMSTCPResult == false) {
                 micResult =  true;
                 assertTrue(micResult,"Pass:wan pc can't access lan side ftp after disable DMZ!"  );             
             } else {
                 micResult =  false;
                 assertTrue(micResult,"Failed:wan pc can access lan side ftp after disable DMZ!"  );
             }
             CaseResult = true;
         }
       

}
