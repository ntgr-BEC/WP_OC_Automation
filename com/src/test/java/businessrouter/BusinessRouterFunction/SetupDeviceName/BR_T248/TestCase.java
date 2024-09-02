package businessrouter.BusinessRouterFunction.SetupDeviceName.BR_T248;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrBasicDeviceNameSetupPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
public class TestCase extends TestCaseBase {
    
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr = "BR_T248";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Setup DeviceName") // 必须要加，对应目录名
    @Story("BR_T248") // 对应testrunkey
    @Description("001-Check the default device name") // 对应用例名字
    @TmsLink("1455251") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p5") // 标记测试用例
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    
        @Step("Test Step 1: Open Device")
        public void step1() {
           //MyCommonAPIs.sleepi(30);
           BrLoginPage BrLoginPage = new BrLoginPage();
           BrLoginPage.defaultLogin();
           MyCommonAPIs.sleepi(20);
        }
        
        @Step("Test Step 2: Check default device name")
        public void step2() {
            boolean CompareResult = false;
            
            BrBasicDeviceNameSetupPage BrBasicDeviceNameSetupPage = new BrBasicDeviceNameSetupPage();
            BrBasicDeviceNameSetupPage.OpenDeviceNameSetupPage();
            MyCommonAPIs.sleepi(15);
            //String DefaultDevicename = "BR500";
            CompareResult = BrBasicDeviceNameSetupPage.ComparedefaultDevicename("BR200");
            System.out.print(CompareResult);
            if(CompareResult == true) {
                micResult =  true;
                CaseResult = true; 
                assertTrue(micResult, "Pass:Default device name is" + WebportalParam.DUTType  );
                
                
                
            } else {
                micResult = false;
                assertTrue(micResult, "Falied:Default device name isn't " + WebportalParam.DUTType );
                
            }
            
            
         }
        
      
}
