package businessrouter.BusinessRouterFunction.Firewall.BR_T451;
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

    public String sTestStr = "BR_T451";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T451") // 对应testrunkey
    @Description("010-Port Forwarding - Max. Entries(64)") // 对应用例名字
    @TmsLink("1455490") // 对应用例详情页的URL最后几位数字

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
        
       @Step("Test Step 2: Able to add up to 65 Custom entires")
        public void step2() {
            boolean Result1 = false; 
            
            brporttrigger.OpenFirewallPortForwardingTriggeringPage();
            Selenide.sleep(20000);
            brporttrigger.EnableFirewallPortForwardingOrPortTriggering(0);
            for(int i =0; i < 65 ; i++){
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
                
                if (i == 64) {
                    Result1 =  true;
                            
                } else {
                    Result1 =  false;                   
                } 
             }
            if (Result1 == true) {
                micResult =  true;
                assertTrue(micResult, "Pass:Add 65 portforwarding rules successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult, "Failed:Add 65 portforwarding rules unsuccessfully!!"  );
            }
            
         }
       /*  @Step("Test Step 3:Can't add 66th rules")
        public void step3() {
            boolean Result2 = false;  
            brporttrigger.OpenFirewallPortForwardingTriggeringPage();
            Result2 = brporttrigger.AddPortForwardingruleYesOrNo();
            if (Result2 == false) {
                micResult =  true;
                assertTrue(micResult, "Pass:Can add 66 portforwarding rules unsuccessfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult, "Failed:Can't add 65 portforwarding rules unsuccessfully!!"  );
            }
            
         }*/

       @Step("Test Step 4: Delete port forwarding rules")
        public void step4() {
           brporttrigger.OpenFirewallPortForwardingTriggeringPage();
           for(int i =0; i < 65 ; i++){
               String tt = String.valueOf(i);
               String ForwardingName = "test" + tt;
               brporttrigger.DeleteForwardingRule(ForwardingName);
			   handle.sleepi(5);
           }
           CaseResult = true;
          brlogin.BrLogout();
            
         }


}
