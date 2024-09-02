package businessrouter.BusinessRouterFunction.SetupLAN.BR_T401;
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

    public String sTestStr = "BR_T401";
    public String sCurrentValue;
    public String sExpectedtValue;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup LAN") // 必须要加，对应目录名
        @Story("BR_T401") // 对应testrunkey
        @Description("014-LAN setup - Router LAN IP over DHCP pool range") // 对应用例名字
        @TmsLink("1455422") // 对应用例详情页的URL最后几位数字

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
        
        @Step("Test Step 2: Input management IP higher than DHCP pool, such as 253.")
        public void step2() {
   
            boolean Result = false;
            brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(2); 
            Result = brlansetuppage.ChangLANDHCPRange(IPRange1);
            handle.sleepi(6);  
     
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Input management IP higher than DHCP pool, such as 253. successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Input management IP higher than DHCP pool, such as 253. unsuccessfully!"  );
            }
         }
        @Step("Test Step 3: Input starting & ending IP lower than 253, such as 10~100")
        public void step3() {
            boolean Result = false;
            //brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(2); 
            Result = brlansetuppage.ChangLANDHCPRange(IPRange2);
            handle.sleepi(6);  
     
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Input management IP higher than DHCP pool, such as 253. successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Input management IP higher than DHCP pool, such as 253. unsuccessfully!"  );
            }
         }
    
        @Step("Test Step 4: The starting IP is changed abnormally")
        public void step4() {
            boolean Result = false;
            //brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(2); 
            Result = brlansetuppage.ChangLANDHCPRange(IPRange3);
            handle.sleepi(6);  
     
            if (Result == false ) {
                micResult =  true;
                assertTrue(micResult,"Pass:The starting IP is changed abnormally successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:The starting IP is changed abnormally unsuccessfully!"  );
            }
         }
        @Step("Test Step 5: restore lan dhcp range")
        public void step5() {
            boolean Result = false;
            //brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(2); 
            Result = brlansetuppage.ChangLANDHCPRange(IPRangedefault);
            handle.sleepi(6);  
     
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:restore lan dhcp range successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:restore lan dhcp range unsuccessfully!"  );
            }
            CaseResult = true;  
         }

}
