package businessrouter.BusinessRouterFunction.SetupLAN.BR_T402;
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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase implements Config {
    String                       tclname = getClass().getName();
    String                       tmpStr;
    final static Logger logger = Logger.getLogger("BR_T402");
    public String sTestStr = "BR_T402";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup LAN") // 必须要加，对应目录名
        @Story("BR_T402") // 对应testrunkey
        @Description("015-Do not use router as dhcp server") // 对应用例名字
        @TmsLink("1455423") // 对应用例详情页的URL最后几位数字

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
            MyCommonAPIs.sleepi(10);
        }
        
        @Step("Test Step 2: Do not use router as dhcp server.")
        public void step2() {
   
            boolean Result = false;
            brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(10); 
            Result = brlansetuppage.EnableOrDisableDHCPServer("Disable");
            handle.sleepi(6);  
     
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Disable DHCP server successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Disable DHCP server unsuccessfully!"  );
            }
         }
        @Step("Test Step 3: Pca configured static IP,access router wan side ")
        public void step3() {
            boolean PingResult = false;
            HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
            
            PingResult = Brtmspage.CheckPingPassOrFailed(TmsICMPCommands);
            if (PingResult == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:ping  is allowed !"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:ping is blocked!"  );
            }
         }
    
        @Step("Test Step 4: Restore DUT configuration")
        public void step4() {
            boolean Result = false;
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            //brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(10); 
            Result = brlansetuppage.EnableOrDisableDHCPServer("Enable");
            handle.sleepi(6);  
     
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Enable lan DHCP server successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Enable lan DHCP server unsuccessfully!"  );
            }
            CaseResult = true;  
         }
        
}
