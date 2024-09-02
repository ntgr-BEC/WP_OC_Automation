package businessrouter.BusinessRouterFunction.FirewallBasicSetup.BR_T362;
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
public class TestCase extends TestCaseBase implements Config {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr = "BR_T362";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    
    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T362") // 对应testrunkey
    @Description("010-Test the range of MTU size") // 对应用例名字
    @TmsLink("1455366") // 对应用例详情页的URL最后几位数字

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
        @Step("Test Step 2:MTU side input 10")
         public void step2() {
            boolean Result= false;
            brfirewallbasicpage.OpenFirewallBasicSetupPage();
            handle.sleepi(10);
            Result = brfirewallbasicpage.ChangeUnormalMUTSize("10");
            if (Result == true ) {
                  micResult =  true;
                  assertTrue(micResult,"Pass:MTU side input 10,pop up warn message!"  );             
            } else {
                  micResult =  false;
                  assertTrue(micResult,"Failed:MTU side input 10 successfully!"  );
            }
            
         }
        @Step("Test Step 3:MTU side input 616")
        public void step3() {
           boolean Result= false;
           brfirewallbasicpage.OpenFirewallBasicSetupPage();
           Result = brfirewallbasicpage.ChangeMUTSize("616");
           if (Result == true ) {
                 micResult =  true;
                 assertTrue(micResult,"Pass:MTU side input 616 successfully!"  );             
           } else {
                 micResult =  false;
                 assertTrue(micResult,"Failed:MTU side input 616 unsuccessfully!"  );
           }
           
        }
        @Step("Test Step 4:MTU side input 1400")
        public void step4() {
           boolean Result= false;
           brfirewallbasicpage.OpenFirewallBasicSetupPage();
           Result = brfirewallbasicpage.ChangeMUTSize("1400");
           if (Result == true ) {
                 micResult =  true;
                 assertTrue(micResult,"Pass:MTU side input 1400 successfully!"  );             
           } else {
                 micResult =  false;
                 assertTrue(micResult,"Failed:MTU side input 1400 unsuccessfully!"  );
           }
           
        }
        @Step("Test Step 5:MTU side input 1500")
        public void step5() {
           boolean Result= false;
           brfirewallbasicpage.OpenFirewallBasicSetupPage();
           Result = brfirewallbasicpage.ChangeMUTSize("1500");
           if (Result == true ) {
                 micResult =  true;
                 assertTrue(micResult,"Pass:MTU side input 1500 successfully!"  );             
           } else {
                 micResult =  false;
                 assertTrue(micResult,"Failed:MTU side input 1500 unsuccessfully!"  );
           }
           
        }
        @Step("Test Step 6:MTU side input 0")
        public void step6() {
           boolean Result= false;
           brfirewallbasicpage.OpenFirewallBasicSetupPage();
           Result = brfirewallbasicpage.ChangeUnormalMUTSize("1501");
           if (Result == true ) {
                 micResult =  true;
                 assertTrue(micResult,"Pass:MTU side input 1501,pop up warn message!"  );             
           } else {
                 micResult =  false;
                 assertTrue(micResult,"Failed:MTU side input 1501 successfully!"  );
           }
           CaseResult = true;  
        }

}
