package businessrouter.BusinessRouterFunction.IPv6.BR_T764;
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

    public String sTestStr = "BR_T764";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String DhcpServerHandle;
    
    final static Logger logger = Logger.getLogger("BR_T764");
    @Feature("Business Router.IPv6") // 必须要加，对应目录名
    @Story("BR_T764") // 对应testrunkey
    @Description("018-IPv6 -Verify Status refresh display or not") // 对应用例名A字
    @TmsLink("1474891") // 对应用例详情页的URL最后几位数字

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
        
      
         

       @Step("Test Step 2:Select IPv6 every connection type,check the button.")
        public void step2() {
           boolean Result = false;
           
           bripv6page.OpenAdvancedIPv6Page();
           handle.sleepi(15);
           bripv6page.ChangeIPv6ConnectionType("Auto Config");
           handle.sleepi(50);
           Result = bripv6page.IPv6RefreshStatusButtonExistOrNot();
           System.out.print(Result);
           if (Result == true) {
               micResult =  true;
               assertTrue(micResult,"Pass:there is a “Status Refresh” button!");  
           }else {
               micResult =  false;
               assertTrue(micResult,"Failed:there isn't a “Status Refresh” button!"); 
           } 
          
        }
       @Step("Test Step 4:Restore DUT configuration.")
       public void step4() {
           bripv6page.OpenAdvancedIPv6Page();
           handle.sleepi(15);
           bripv6page.ChangeIPv6ConnectionType("Disabled");
           CaseResult = true;
          
       }


}