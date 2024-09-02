package businessrouter.BusinessRouterFunction.Advanced_DDNS.BR_T228;
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

    public String sTestStr = "BR_T228";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2;
    String LanClientMac;
    final static Logger logger = Logger.getLogger("BR_T228");
    @Feature("Business Router.Advanced_DDNS") // 必须要加，对应目录名
    @Story("BR_T228") // 对应testrunkey
    @Description("004-Netgear - Incorrect DDNS account(Email).") // 对应用例名A字
    @TmsLink("1455231") // 对应用例详情页的URL最后几位数字

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
        
      @Step("Test Step 2:Click advanced -> advanced setup -> Dynamic DNS.\r\n" + 
              "3. Select \"NETGEAR\" from drop-down list at the top of left corner.\r\n" + 
              "4. Insert correct hostname,incorrect username and .")
        public void step2() {
          boolean Result = false;
          boolean Result2 = false;
          brddnspage.OpenDynamicDNSPage();
          handle.sleepi(15);
          Result = brddnspage.ConfigMyNetgearAccount(NewDDNSAccountInfo);
          if (Result == true) {
              micResult =  true;
              assertTrue(micResult,"Pass:Configure  correct hostname,incorrect username and password failed!");  
          }else {
              micResult =  false;
              assertTrue(micResult,"Failed:Configure  correct hostname,incorrect username and password succesfully!"); 
          } 
          Result2 = brddnspage.ShowDdnsStatusUpOrDown("Wrong");
          if (Result == true) {
              micResult =  true;
              assertTrue(micResult,"Pass:WAN Dynamic DNS Status show correct information!");  
          }else {
              micResult =  false;
              assertTrue(micResult,"Failed:WAN Dynamic DNS Status show incorrect information!"); 
          } 
            
       }
     

       @Step("Test Step 3:Restore DUT configuration.")
        public void step3() {
           //boolean Result = false;
          // Brtmspage.ChangeBrowserHandles(HistroyHandle);
           brddnspage.OpenDynamicDNSPage();
           handle.sleepi(15);
           brddnspage.ConfigMyNetgearAccount(DefaultDDNSAccountInfo);
           CaseResult = true;
           brlogin.BrLogout(); 
        }


}