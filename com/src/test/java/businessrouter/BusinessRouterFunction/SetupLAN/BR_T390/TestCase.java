package businessrouter.BusinessRouterFunction.SetupLAN.BR_T390;
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

    public String sTestStr = "BR_T390";
    public String sCurrentValue;
    public String sExpectedtValue;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup LAN") // 必须要加，对应目录名
        @Story("BR_T390") // 对应testrunkey
        @Description("003-LAN setup - Address Reservation - MAX Device Name length(15 characters)") // 对应用例名字
        @TmsLink("1455411") // 对应用例详情页的URL最后几位数字

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
        
        @Step("Test Step 2: Add a record to the address reservation table Modify its Device name to 15 characters")
        public void step2() {
            boolean Result = false;
            brlansetuppage.OpenBasicLanSetupPage();
            String ipreservation = Brtmspage.GetNewIPAddress(IPReservation.get("IP Address")); 
            System.out.println(ipreservation);
            IPReservation.replace("IP Address", ipreservation);  
            handle.sleepi(2); 
            brlansetuppage.AddIPReservation(IPReservation);
            handle.sleepi(40);  
            Result =  brlansetuppage.IPReservationExistYesOrNot(IPReservation);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Add a record to the address reservation table Modify its Device name to 15 characters successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Add a record to the address reservation table Modify its Device name to 15 characters unsuccessfully!"  );
            }
         }
        @Step("Test Step 3: Delete record to the address reservation table")
        public void step3() {
            //brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(10); 
           brlansetuppage.DeleteIPReservation(IPReservation.get("Device Name"));
           handle.sleepi(40);  
           CaseResult = true;
           brlogin.BrLogout();
            
         }

}
