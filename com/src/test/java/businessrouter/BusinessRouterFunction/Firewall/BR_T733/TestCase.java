package businessrouter.BusinessRouterFunction.Firewall.BR_T733;
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
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr = "BR_T733";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T733") // 对应testrunkey
    @Description("047-Port Forwarding-Delete rules") // 对应用例名字
    @TmsLink("1465775") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p1") // 标记测试用例
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
        }
        
       @Step("Test Step 2: Able to add 10 Custom entires")
        public void step2() {
            boolean Result1 = false; 
            
            brporttrigger.OpenFirewallPortForwardingTriggeringPage();
            Selenide.sleep(20000);
            brporttrigger.EnableFirewallPortForwardingOrPortTriggering(0);
            for(int i =0; i < 10 ; i++){
               System.out.print(i);
                String tt = String.valueOf(i);
                String VLANname = "test" + tt;
                PortForwardingRule.replace("Rule Name", VLANname);
                int Port = i + 1024;
                String Sport = String.valueOf(Port);                
                PortForwardingRule.replace("ExternalPort", Sport);
                int IPAddressNum = i + 3;
                String SIPAddress = String.valueOf(IPAddressNum);
                String ipreservation = PortForwardingRule.get("Internal IP Address");
                System.out.println(ipreservation);
                String []ipreservationlist = ipreservation.split("\\.");
                String Newdeip = ipreservationlist[0]+"." + ipreservationlist[1]+"."+ ipreservationlist[2]+"."+SIPAddress;
                PortForwardingRule.replace("Internal IP Address", Newdeip);
                brporttrigger.AddcustomerPortForwardingRule(PortForwardingRule);
                MyCommonAPIs.sleep(6000); 
                
                if (i == 9) {
                    Result1 =  true;
                            
                } else {
                    Result1 =  false;                   
                } 
             }
            if (Result1 == true) {
                micResult =  true;
                assertTrue(micResult, "Pass:Add 10 portforwarding rules successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult, "Failed:Add 10 portforwarding rules unsuccessfully!!"  );
            }
            
         }

       @Step("Test Step 3: Delete port forwarding rules")
        public void step3() {
           boolean result = false;
           brporttrigger.OpenFirewallPortForwardingTriggeringPage();
           for(int i =0; i <= 9 ; i++){
               String tt = String.valueOf(i);
               String ForwardingName = "test" + tt;
               brporttrigger.DeleteForwardingRule(ForwardingName);
               if( i < 9) {
                   result = brporttrigger.ForwardingRuleExistOrNot(ForwardingName);
                   if (result == false) {
                       micResult =  true;
                       assertTrue(micResult, "Pass:Delete the  portforwarding rule successfully!"  );             
                   } else {
                       micResult =  false;
                       assertTrue(micResult, "Failed:Delete the portforwarding rule unsuccessfully!!"  );
                   }
                     
               } 
              
           }
           CaseResult = true;
           brlogin.BrLogout(); 
         }


}
