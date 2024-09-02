package businessrouter.BusinessRouterFunction.Advanced_DDNS.BR_T227;
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

    public String sTestStr = "BR_T227";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2;
    String LanClientMac;
    final static Logger logger = Logger.getLogger("BR_T227");
    @Feature("Business Router.Advanced_DDNS") // 必须要加，对应目录名
    @Story("BR_T227") // 对应testrunkey
    @Description("003-Netgear - update info with Correct DDNS account(Email) and hostname") // 对应用例名A字
    @TmsLink("1455230") // 对应用例详情页的URL最后几位数字

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
        
      @Step("Test Step 2:Enable use a DDNS service,sevice provider select NETGEAR,input correct host name,email,password.")
        public void step2() {
          boolean Result = false;
          boolean Result2 = false;
          brddnspage.OpenDynamicDNSPage();
          handle.sleepi(15);
          Result = brddnspage.ConfigMyNetgearAccount(NewDDNSAccountInfo);
          if (Result == true) {
              micResult =  true;
              assertTrue(micResult,"Pass:Register successfully!");  
          }else {
              micResult =  false;
              assertTrue(micResult,"Failed:Register unsuccessfully!"); 
          } 
          Result2 = brddnspage.ShowDdnsStatusUpOrDown("Yes");
          if (Result == true) {
              micResult =  true;
              assertTrue(micResult,"Pass:WAN Dynamic DNS Status show correct information!");  
          }else {
              micResult =  false;
              assertTrue(micResult,"Failed:WAN Dynamic DNS Status show incorrect information!"); 
          } 
            
       }
      @Step("Test Step 3: fter ping DDNS domain,IP can show;can nslookup domain.")
      public void step3() {
          //BrTMSPage BrTMSPage = new BrTMSPage();
          HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
        
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

       @Step("Test Step 4:Restore DUT configuration.")
        public void step4() {
           //boolean Result = false;
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           brddnspage.OpenDynamicDNSPage();
           handle.sleepi(15);
           brddnspage.ResetDdnsConfiguration();
           CaseResult = true;
           brlogin.BrLogout(); 
        }


}