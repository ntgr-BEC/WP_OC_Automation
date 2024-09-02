package businessrouter.BusinessRouterFunction.SetupWAN.BR_T20;
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
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config {
    String                       tclname = getClass().getName();
    String                       tmpStr;

    public String sTestStr = "BR_T20";
    public String sCurrentValue;
    public String sExpectedtValue;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup WAN") // 必须要加，对应目录名
        @Story("BR_T20") // 对应testrunkey
        @Description("016-Test Idle Timeout") // 对应用例名字
        @TmsLink("1455023") // 对应用例详情页的URL最后几位数字

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
            BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.defaultLogin();
            MyCommonAPIs.sleepi(20);
        }
        
        @Step("Test Step 2: Idle timeout input 0")
        public void step2() {
            boolean Result = false;
            brwanpage.OpenBasicWanSetupPage();
            MyCommonAPIs.sleepi(30);
            Result = brwanpage.Editidletime("0");
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Idle timeout input 0 successfully."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Idle timeout input 0 unsuccessfully."  );
            }
     
         }
        @Step("Test Step 3: Idle timeout input 1")
        public void step3() {
            boolean Result = false;
            brwanpage.OpenBasicWanSetupPage();
            MyCommonAPIs.sleepi(20);
            Result = brwanpage.Editidletime("1");
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Idle timeout input 1 successfully"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Idle timeout input 1 unsuccessfully"  );
            }
           
         }
        @Step("Test Step 4: IIdle timeout input 99999")
        public void step4() {
            boolean Result = false;
            brwanpage.OpenBasicWanSetupPage();
            MyCommonAPIs.sleepi(20);
            Result = brwanpage.Editidletime("99999");
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Idle timeout input 99999 successfully"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Idle timeout input 99999 unsuccessfully"  );
            }
           
         }
        @Step("Test Step 5: Restore DUT configuration")
        public void step5() {
            boolean Result = false;
            brwanpage.OpenBasicWanSetupPage();
            MyCommonAPIs.sleepi(20);
            Result = brwanpage.Editidletime("5");
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Idle timeout input 99999 successfully"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Idle timeout input 99999 unsuccessfully"  );
            }
            MyCommonAPIs.sleepi(20);
            brwanpage.ChangeWanPortModeDhcpOrStatic(WanPortModeInfo);
            CaseResult = true;
         } 

}
