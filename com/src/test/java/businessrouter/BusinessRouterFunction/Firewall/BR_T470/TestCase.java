package businessrouter.BusinessRouterFunction.Firewall.BR_T470;
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

    public String sTestStr = "BR_T470";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String Status;
    String GenerlRule;
    final static Logger logger = Logger.getLogger("BR_T470");
    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T470") // 对应testrunkey
    @Description("028-Access Control - Check the information on Attached Devices page") // 对应用例名A字
    @TmsLink("1455509") // 对应用例详情页的URL最后几位数字

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
            handle.sleepi(5);

        }
        
       @Step("Test Step 2: Turn on access control,select allow all new devices from connecting.")
        public void step2() {
           braccesscontrol.OpenFirewallAccessControlPage();
           handle.sleepi(11);
           braccesscontrol.AllowOrBlockNewDevice("Allow");
           brattachdevice.OpenAttachedDevicesPage();
           handle.sleepi(8);
           Status = brattachdevice.GetAccessControlStatus();         
           GenerlRule = brattachdevice.GetAccessControlGeneralRule();
           if (Status.contentEquals("Turned ON")&& GenerlRule.contentEquals("Allow all new devices to connect")){
               micResult =  true;
               assertTrue(micResult, "Pass:On Attached Devices page, Access Control status and gernel rule are correct."  );     
           } else {
               micResult =  false;
               assertTrue(micResult, "Falied:On Attached Devices page, Access Control status and gernel rule are incorrect."  );
           }
       }
       @Step("Test Step 3:Turn on access control,select block all new devices from connecting.")
       public void step3() {
           braccesscontrol.OpenFirewallAccessControlPage();
           handle.sleepi(8);
           braccesscontrol.AllowOrBlockNewDevice("Block");
           brattachdevice.OpenAttachedDevicesPage();
           handle.sleepi(8);
           Status = brattachdevice.GetAccessControlStatus(); 
           GenerlRule = brattachdevice.GetAccessControlGeneralRule();
           
           if (Status.contentEquals("Turned ON")&& GenerlRule.contentEquals("Block all new devices from connecting")){
               micResult =  true;
               assertTrue(micResult, "Pass:On Attached Devices page, Access Control status and gernel rule are correct."  );     
           } else {
               micResult =  false;
               assertTrue(micResult, "Falied:On Attached Devices page, Access Control status and gernel rule are incorrect."  );
           }
        }   
          
       @Step("Test Step 5: Turn off Access Control,check Attached Devices page")
       public void step5() {
           braccesscontrol.OpenFirewallAccessControlPage();
           handle.sleepi(20);
           braccesscontrol.TunoffAccessControl();
           brattachdevice.OpenAttachedDevicesPage();
           handle.sleepi(8);
           Status = brattachdevice.GetAccessControlStatus(); 
          if (Status.contentEquals("Turned OFF")) {
              micResult =  true;
              assertTrue(micResult, "Pass:On Attached Devices page, Access Control status is correct."  );             
          } else {
              micResult =  false;
              assertTrue(micResult, "Failed:On Attached Devices page, Access Control status isn't correct.!"  );
          }
          CaseResult = true;
      }
     


}