package businessrouter.BusinessRouterFunction.Advanced_DDNS.BR_T231;
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

    public String sTestStr = "BR_T231";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2;
    String LanClientMac;
    final static Logger logger = Logger.getLogger("BR_T231");
    @Feature("Business Router.Advanced_DDNS") // 必须要加，对应目录名
    @Story("BR_T231") // 对应testrunkey
    @Description("007- Netgear - Reboot DUT") // 对应用例名A字
    @TmsLink("1455234") // 对应用例详情页的URL最后几位数字

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
          
            BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.defaultLogin(); 
            handle.sleepi(20);
        }
        
      @Step("Test Step 2: Register DDNS service using correct username and password.")
        public void step2() {
          boolean Result = false;
          boolean Result2 = false;
          brddnspage.OpenDynamicDNSPage();
          handle.sleepi(15);
          Result = brddnspage.ConfigMyNetgearAccount(NewDDNSAccountInfo);
          if (Result == true) {
              micResult =  true;
              assertTrue(micResult,"Pass:The DDNS service can be work well!");  
          }else {
              micResult =  false;
              assertTrue(micResult,"Failed:The DDNS service can't be work well!"); 
          } 
          Result2 = brddnspage.ShowDdnsStatusUpOrDown("Yes");
          if (Result2 == true) {
              micResult =  true;
              assertTrue(micResult,"Pass:WAN Dynamic DNS Status show correct information!");  
          }else {
              micResult =  false;
              assertTrue(micResult,"Failed:WAN Dynamic DNS Status show incorrect information!"); 
          } 
            
       }
      @Step("Test Step 3: After ping DDNS domain,IP can show;can nslookup domain.")
      public void step3() {
          //BrTMSPage BrTMSPage = new BrTMSPage();
          HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
          TmsPageHandle = Brtmspage.GetBrowserHandles();
          boolean TMSTCPResult =  Brtmspage.CheckPingPassOrFailed(TmsICMPCommands);
          //Brtmspage.CloseTMSWindows();
          if (TMSTCPResult == true ) {
              micResult =  true;
              assertTrue(micResult,"Pass:fter ping DDNS domain,IP can show;can nslookup domain."  );             
          } else {
              micResult =  false;
              assertTrue(micResult,"Failed:fter ping DDNS domain,IP can't show;can nslookup domain.");
          }
          Brtmspage.BackTMSConfigPafe();
          
      }  
      @Step("Test Step 4: Reboot DUT, make sure DUT 's WAN connection is vaild..")
      public void step4() {
        boolean Result = false;
        boolean Result2 = false;
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        brdashboard.OpenDashboardPage();
        handle.sleepi(20);
        brdashboard.RebootDUT();
        handle.sleepi(80);
        brddnspage.OpenDynamicDNSPage();
        handle.sleepi(185);
        Result2 = brddnspage.ShowDdnsStatusUpOrDown("Yes");
        if (Result2 == true) {
            micResult =  true;
            assertTrue(micResult,"Pass:WAN Dynamic DNS Status show correct information!");  
        }else {
            micResult =  false;
            assertTrue(micResult,"Failed:WAN Dynamic DNS Status show incorrect information!"); 
        } 
          
     }
      @Step("Test Step 5: After ping DDNS domain,IP can show;can nslookup domain.")
      public void step5() {
          //BrTMSPage BrTMSPage = new BrTMSPage();
          Brtmspage.ChangeBrowserHandles(TmsPageHandle);
          boolean TMSTCPResult =  Brtmspage.CheckPingPassOrFailed(TmsICMPCommands);
          //Brtmspage.CloseTMSWindows();
          if (TMSTCPResult == true ) {
              micResult =  true;
              assertTrue(micResult,"Pass:fter ping DDNS domain,IP can show;can nslookup domain."  );             
          } else {
              micResult =  false;
              assertTrue(micResult,"Failed:fter ping DDNS domain,IP can't show;can nslookup domain.");
          }
          
      }  

       @Step("Test Step 6:Restore DUT configuration.")
        public void step6() {
           //boolean Result = false;
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           brddnspage.OpenDynamicDNSPage();
           handle.sleepi(15);
           brddnspage.ResetDdnsConfiguration();
           CaseResult = true;
           brlogin.BrLogout(); 
        }


}