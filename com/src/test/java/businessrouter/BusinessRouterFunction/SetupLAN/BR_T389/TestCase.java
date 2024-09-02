package businessrouter.BusinessRouterFunction.SetupLAN.BR_T389;
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

    public String sTestStr = "BR_T389";
    public String sCurrentValue;
    public String sExpectedtValue;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup LAN") // 必须要加，对应目录名
        @Story("BR_T389") // 对应testrunkey
        @Description("002-LAN setup - Address Reservation - IP Not In LAN Subnet)") // 对应用例名字
        @TmsLink("1455410") // 对应用例详情页的URL最后几位数字

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
        
        @Step("Test Step 2: Verify adding a record with IP not in LAN subnet for Address Reservation field.")
        public void step2() {
            boolean Result = false;
            brlansetuppage.OpenBasicLanSetupPage();
            MyCommonAPIs.sleepi(10);
            String ipreservation = Brtmspage.GetNewIPAddress(IPReservation.get("IP Address")); 
            System.out.println(ipreservation);
            IPReservation.replace("IP Address", ipreservation);  
            handle.sleepi(2); 
            Result = brlansetuppage.GetWaringForAddingIPReservation(IPReservation);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass: adding a record with IP not in LAN subnet for Address Reservation field,pop up warn message!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:adding a record with IP not in LAN subnet for Address Reservation field,not pop up warn message!"  );
            }
            CaseResult = true;
         }
        

}
