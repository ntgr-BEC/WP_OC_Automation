package businessrouter.BusinessRouterFunction.Firewall.BR_T445;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

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

    public String sTestStr = "BR_T445";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T445") // 对应testrunkey
    @Description("004-Port Forwarding - Test big Custom Port") // 对应用例名字
    @TmsLink("1455484") // 对应用例详情页的URL最后几位数字

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
            System.out.print("CaseResult:");
            System.out.print(CaseResult);
            BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.defaultLogin(); 
			handle.sleepi(10);
        }
        
       @Step("Test Step 2: Verify port forwarding function works with a big customized port e.g. 30000.")
        public void step2() {
            boolean Result1 = false; 
            
            brporttrigger.OpenFirewallPortForwardingTriggeringPage();
            Selenide.sleep(20000);
            String ipreservation = PortForwardingRule1.get("Internal IP Address");
            System.out.println(ipreservation);
            String []ipreservationlist = ipreservation.split("\\.");
            String Newdeip = ipreservationlist[0]+"." + ipreservationlist[1]+"."+ ipreservationlist[2]+"."+"6";
            PortForwardingRule1.replace("Internal IP Address", Newdeip);
            brporttrigger.AddcustomerPortForwardingRule(PortForwardingRule1);
            Result1 = brporttrigger.ForwardingRuleExistOrNot(PortForwardingRule1.get("Rule Name"));  
            if (Result1 == true) {
                micResult =  true;
                assertTrue(micResult, "Pass: Port forwarding function works with a big customized port e.g. 30000 successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult, "Failed: Port forwarding function works with a big customized port e.g. 30000 unsuccessfully!!"  );
            }
            
         }
       @Step("Test Step 3: Config port to 65534")
       public void step3() {
           boolean Result2 = false; 
           
           //brporttrigger.OpenFirewallPortForwardingTriggeringPage();
           Selenide.sleep(20000);
           String ipreservation = PortForwardingRule2.get("Internal IP Address");
           System.out.println(ipreservation);
           String []ipreservationlist = ipreservation.split("\\.");
           String Newdeip = ipreservationlist[0]+"." + ipreservationlist[1]+"."+ ipreservationlist[2]+"."+"6";
           PortForwardingRule2.replace("Internal IP Address", Newdeip);
           brporttrigger.AddcustomerPortForwardingRule(PortForwardingRule2);
           handle.sleepi(6);
           Result2 = brporttrigger.ForwardingRuleExistOrNot(PortForwardingRule2.get("Rule Name"));  
           if (Result2 == true) {
               micResult =  true;
               assertTrue(micResult, "Pass: Config port to 65534 successfully!"  );             
           } else {
               micResult =  false;
               assertTrue(micResult, "Failed: Config port to 65534 unsuccessfully!!"  );
           }
           
        }
       @Step("Test Step 4: Delete port forwarding rules")
       public void step4() {
          //brporttrigger.OpenFirewallPortForwardingTriggeringPage();      
          brporttrigger.DeleteForwardingRule(PortForwardingRule1.get("Rule Name"));
          brporttrigger.DeleteForwardingRule(PortForwardingRule2.get("Rule Name"));
          CaseResult = true;
          brlogin.BrLogout();
           
        }

}
