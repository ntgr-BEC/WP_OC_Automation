package businessrouter.BusinessRouterFunction.SetupDeviceName.BR_T250;
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
public class TestCase  extends TestCaseBase implements Config {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr = "BR_T250";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Setup DeviceName") // 必须要加，对应目录名
    @Story("BR_T250") // 对应testrunkey
    @Description("003-Edit correct device name") // 对应用例名字
    @TmsLink("1455253") // 对应用例详情页的URL最后几位数字
 

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
        
        @Step("Test Step 2: Device name use 1 characters")
        public void step2() {
            boolean CompareResult = false;  
            brdevname.OpenDeviceNameSetupPage();
            MyCommonAPIs.sleepi(5);
            CompareResult = brdevname.Editdevicename(MinDeviceName);  
            System.out.print(CompareResult);
            if(CompareResult == true) {
                micResult =  true;
                assertTrue(micResult, "Pass:Set device name use 1 characters successfully!"  );                             
            } else {
                micResult = false;
                assertTrue(micResult, "Falied:Set device name use 1 characters unsuccessfully! " );                
            }
            
         }
        @Step("Test Step 3: Device name use 15 characters")
        public void step3() {
            boolean CompareResult = false;  
           // brdevname.OpenDeviceNameSetupPage();
            MyCommonAPIs.sleepi(5);
            CompareResult = brdevname.Editdevicename(MaxDeviceName);    
            System.out.print(CompareResult);
            if(CompareResult == true) {
                micResult =  true;
                assertTrue(micResult, "Pass:Set device name use 15 characters successfully!"  );              
                
            } else {
                micResult = false;
                assertTrue(micResult, "Falied:Set device name use 15 characters unsuccessfully! " );
                
            }
            
         }
        @Step("Test Step 4: Restore default Device Name")
        public void step4() {
            boolean CompareResult2 = false;  
            //brdevname.OpenDeviceNameSetupPage();
            MyCommonAPIs.sleepi(5);
            CompareResult2 = brdevname.Editdevicename(DefaultDeviceName);    
            System.out.print(CompareResult2);
            if(CompareResult2 == true) {
                micResult =  true;
                CaseResult = true; 
                assertTrue(micResult, "Pass:Restore default device name  successfully!"  );              
                
            } else {
                micResult = false;
                assertTrue(micResult, "Falied:Restore default device name  unsuccessfully! " );
                
            }
            
         }

}
