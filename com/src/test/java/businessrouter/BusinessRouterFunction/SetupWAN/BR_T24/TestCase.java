package businessrouter.BusinessRouterFunction.SetupWAN.BR_T24;
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

    public String sTestStr = "BR_T24";
    public String sCurrentValue;
    public String sExpectedtValue;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup WAN") // 必须要加，对应目录名
        @Story("BR_T24") // 对应testrunkey
        @Description("020-Config Device name on Wan setup page") // 对应用例名字
        @TmsLink("1455027") // 对应用例详情页的URL最后几位数字

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
        
        @Step("Test Step 2: Go to Basic->Config device name use alpha numericals up to 15 characters")
        public void step2() {
            boolean Result = false;
            brwanpage.OpenBasicWanSetupPage();
            MyCommonAPIs.sleepi(10);
            Result = brwanpage.EditDeviceNameInfo("BR5001234567890");
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Config device name use alpha numericals up to 15 characters successfully."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Config device name use alpha numericals up to 15 characters unsuccessfully."  );
            }
     
         }
        @Step("Test Step 3: Config device name use special characters")
        public void step3() {
            boolean Result = false;
            MyCommonAPIs.sleepi(10);
            Result = brwanpage.EditDeviceNameInfoWithSpecialCha(DeviceWarning);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Config device name use special characters unsuccessfully."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Config device name use special characters successfully."  );
            }
           
         }
        @Step("Test Step 4: Restore DUT configuration")
        public void step4() {
            boolean Result = false;
    
            MyCommonAPIs.sleepi(5);
            Result = brwanpage.EditDeviceNameInfo("BR500");
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Restore DUT configuration successfully."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Restore DUT configuration unsuccessfully."  );
            }
            CaseResult = true;
         } 

}
