package businessrouter.BusinessRouterFunction.Advanced_DDNS.BR_T229;
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

    public String sTestStr = "BR_T229";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2;
    String LanClientMac;
    final static Logger logger = Logger.getLogger("BR_T229");
    @Feature("Business Router.Advanced_DDNS") // 必须要加，对应目录名
    @Story("BR_T229") // 对应testrunkey
    @Description("005- Netgear - Max. Email(48) & Password(6-32).") // 对应用例名A字
    @TmsLink("1455232") // 对应用例详情页的URL最后几位数字

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
        
      @Step("Test Step 2:Able to Email support 48 characters input\r\n" + 
              "Able to Password support 6-32 characters input.")
        public void step2() {
          boolean Result = false;
          boolean Result2 = false;
          brddnspage.OpenDynamicDNSPage();
          handle.sleepi(15);
          Result = brddnspage.NetgearAccountMaxEmailAndMaxPassword(NewDDNSAccountInfo);
          if (Result == true) {
              micResult =  true;
              assertTrue(micResult,"Pass:Able to Email support 48 characters input and Able to Password support 6-32 characters input");  
          }else {
              micResult =  false;
              assertTrue(micResult,"Failed: isn't able to Email support 48 characters input and isn't to Password support 6-32 characters input!"); 
          } 
          
          CaseResult = true;
          brlogin.BrLogout();  
       }
     

       

}