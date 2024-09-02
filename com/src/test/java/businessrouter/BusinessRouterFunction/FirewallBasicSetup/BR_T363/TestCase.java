package businessrouter.BusinessRouterFunction.FirewallBasicSetup.BR_T363;
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

    public String sTestStr = "BR_T363";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    
    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T363") // 对应testrunkey
    @Description("011-NAT filtring setting is secured") // 对应用例名字
    @TmsLink("1455367") // 对应用例详情页的URL最后几位数字

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
        @Step("Test Step 2:Set NAT filtering to Secured,save")
         public void step2() {
            boolean Result= false;
            brfirewallbasicpage.OpenFirewallBasicSetupPage();
            Result = brfirewallbasicpage.ChangeNatFilterMode("Secured");
            if (Result == true ) {
                  micResult =  true;
                  assertTrue(micResult,"Pass:Set NAT filtering to Secured,save successfully!"  );             
            } else {
                  micResult =  false;
                  assertTrue(micResult,"Failed:Set NAT filtering to Secured,save unsuccessfully!"  );
            }
            CaseResult = true; 
                 
         }


}
