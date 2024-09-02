package businessrouter.BusinessRouterFunction.Firewall.BR_T458;
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

    public String sTestStr = "BR_T458";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T458") // 对应testrunkey
    @Description("017-Port Triggering - Max. Entries(20)") // 对应用例名字
    @TmsLink("1455497") // 对应用例详情页的URL最后几位数字

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
			handle.sleepi(10);
        }
        
      @Step("Test Step 2:Able to add up to 20 entires")
        public void step2() {
            boolean Result1 = false; 
            
            brporttrigger.OpenFirewallPortForwardingTriggeringPage();
			handle.sleepi(10);
            brporttrigger.EnableFirewallPortForwardingOrPortTriggering(1);
            Selenide.sleep(20000);
            System.out.print("ddddddddddddddddddddddd");
            for(int i =1; i < 21 ; i++){
               System.out.print(i);
                String tt = String.valueOf(i);
                String TriggerName = "test" + tt;
                TriggeringRule.replace("Rule Name", TriggerName);
                int Port = i + 10;
                String Sport = String.valueOf(Port);                
                TriggeringRule.replace("Triggering Port", Sport);
                int tPort = i + 200;
                String StartingPort = String.valueOf(tPort);                
                TriggeringRule.replace("Starting Port", Sport);                
                TriggeringRule.replace("Ending Port", Sport);
                brporttrigger.AddcustomertriggeringRule(TriggeringRule);
                MyCommonAPIs.sleep(6000); 
                
                if (i == 20) {
                    Result1 =  true;
                            
                } else {
                    Result1 =  false;                   
                } 
             }
            if (Result1 == true) {
                micResult =  true;
                assertTrue(micResult, "Pass:Able to add up to 20 entires successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult, "Failed:Able to add up to 20 entires unsuccessfully!!"  );
            }
            
         }
         

       @Step("Test Step 3: Delete port forwarding rules")
        public void step3() {
           brporttrigger.OpenFirewallPortForwardingTriggeringPage();
           Selenide.sleep(10000);
           for(int i =1; i < 21 ; i++){
               String tt = String.valueOf(i);
               String TriggeringName = "test" + tt;
               brporttrigger.DeleteTriggeringRule(TriggeringName);
           }
           brbackup.OpenBackupSettingsPage();
           MyCommonAPIs.sleepi(10);
           brbackup.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
           MyCommonAPIs.sleepi(30);
           CaseResult = true;
          //brlogin.BrLogout();
            
         }


}