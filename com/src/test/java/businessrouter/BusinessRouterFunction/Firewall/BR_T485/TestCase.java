package businessrouter.BusinessRouterFunction.Firewall.BR_T485;
import static org.testng.Assert.assertTrue;

import java.util.logging.Logger;

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

    public String sTestStr = "BR_T485";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2;
    String LanClientMac;
    final static Logger logger = Logger.getLogger("BR_T485");
    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T485") // 对应testrunkey
    @Description("043-Access Control - Remove device from the list of allowed devices not currently connected to the network.") // 对应用例名A字
    @TmsLink("1455524") // 对应用例详情页的URL最后几位数字

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
            
        }
     

       @Step("Test Step 2: Turn on access control,Access rule select  “Allow all new devices to connect.")
        public void step2() {
           boolean Result = false;
           boolean Result1 = false;
           braccesscontrol.OpenFirewallAccessControlPage();
           braccesscontrol.AllowOrBlockNewDevice("Allow");
           handle.sleepi(20);
           Result = braccesscontrol.AddAllowNotConnectDev(NewDeviceInfo);
           handle.sleepi(5);
           Result1 = braccesscontrol.AddAllowNotConnectDev(NewDeviceInfo1);
           if (Result == true && Result1 == true ) { 
               micResult =  true;
               assertTrue(micResult, "Pass:Add 2 lists allowed but not connected device successfully"  );             
           } else {
               micResult =  false;
               assertTrue(micResult, "Failed:Add 2 lists allowed but not connected device unsuccessfully!"  );
           }
          
       }
       @Step("Test Step 3:Use \"Remove from the list \" button to delete all devices under \"the list of allowed devices not currently connected to the network\"")
       public void step3() {
           boolean Result1 = false; 
           braccesscontrol.DeletenotConnectedAllowedDevice(NewDeviceInfo);
           
        }   
          
 
       @Step("Test Step 4:Turn off Access Control!")
       public void step4() {
          
           braccesscontrol.OpenFirewallAccessControlPage();
           handle.sleepi(20);
           braccesscontrol.TunoffAccessControl();
           CaseResult = true;
           brlogin.BrLogout(); 
        }


}