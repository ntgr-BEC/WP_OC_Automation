package businessrouter.BusinessRouterFunction.SetupDeviceName.BR_T249;
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
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr = "BR_T249";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Setup DeviceName") // 必须要加，对应目录名
    @Story("BR_T249") // 对应testrunkey
    @Description("002-Input wrong device name") // 对应用例名字
    @TmsLink("1455252") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p2") // 标记测试用例
    public void test() throws Exception {
        runTest(this);
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    
        @Step("Test Step 1: Login DUT")
        public void step1() {
            BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.defaultLogin();
            MyCommonAPIs.sleepi(20);
        }
        
        @Step("Test Step 2: Input sepcial characters")
        public void step2() {
            boolean CompareResult = false;  
            brdevname.OpenDeviceNameSetupPage();
            MyCommonAPIs.sleepi(5);
            CompareResult = brdevname.Editdevicenamewithspecialchar(SpecialChar);          
            if(CompareResult == true) {
                micResult =  true;
                assertTrue(micResult, "Pass:Default device name can't be set with sepecial charaters"  );
                
                
                
            } else {
                micResult = false;
                assertTrue(micResult, "Falied:Default device name can be set with sepecial charaters " );
                
            }
        }
        @Step("Test Step 3: Input sepcial characters")
        public void step3() {
             boolean CompareResult1 = false;  
            // brdevname.OpenDeviceNameSetupPage();
             MyCommonAPIs.sleepi(5);
             
             CompareResult1 = brdevname.Editdevicenamewithlongermax(DeviceNamelongermax,DeviceNamemaxlength);
             System.out.print(CompareResult1);
             if(CompareResult1 == true) {
                  micResult =  true;
                  CaseResult = true; 
                  assertTrue(micResult, "Pass:Default device name can't be set with 16 charaters"  );                        
             } else {
                  micResult = false;
                  assertTrue(micResult, "Falied:Default device name can be set with 16 charaters " );                    
             }
             
            }
        

}
