package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1096;

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
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2;
    public String sTestStr = "BR_T1096";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("IPSec VPN") // 必须要加，对应目录名
    @Story("BR_T1096") // 对应testrunkey
    @Description("084-Phase-2 settings:SA-Lifetime input check (600-604800)") // 对应用例名字
    @TmsLink("1517440") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p2") // 标记测试用例
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }
    @Step("Test Step 1: Open DUT")
    public void step1() {
      // MyCommonAPIs.sleepi(30);
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.defaultLogin(); 
    
    }
    @Step("Test Step 2: input Ph2 SA Lifetime:0. ")
    public void step2() {
        int Result1 ; 
        
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.CheckPhase2SALifetime(DUT1IPSecPolicy.get("Ph2 SA Lifetime"));
        
        if (Result1 == 1) {
            micResult =  true;
            assertTrue(micResult, "Pass:Show message \"The input is not valid Ph2 SA Lifetime.!"  );             
        } else  {
            micResult =  false;
            assertTrue(micResult, "Failed:Ph2 SA Lifetime set 0 successfully!"  );
        }
        //brlogin.BrLogout();
        
     }
    @Step("Test Step 3: input Ph2 SA Lifetime:604800. ")
    public void step3() {
        int Result1 ; 
        
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.CheckPhase2SALifetime(DUT1IPSecPolicy2.get("Ph2 SA Lifetime"));
        
        if (Result1 == 1) {
            micResult =  true;
            assertTrue(micResult, "Pass:Show message \"The input is not valid Ph2 SA Lifetime.!"  );             
        } else  {
            micResult =  false;
            assertTrue(micResult, "Failed:Ph2 SA Lifetime set 0 successfully!"  );
        }
       // brlogin.BrLogout();
        
     }
    @Step("Test Step 4: input Ph2 SA Lifetime:604800. ")
    public void step4() {
        int Result1 ; 
        
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.CheckPhase2SALifetime(DUT1IPSecPolicy3.get("Ph2 SA Lifetime"));
        
        if (Result1 == 3) {
            micResult =  true;
            assertTrue(micResult, "Pass:Ph2 SA Lifetime set 604800 successfully!"  );             
        } else  {
            micResult =  false;
            assertTrue(micResult, "Failed:Ph2 SA Lifetime set 604800 unsuccessfully!"  );
        }
       // brlogin.BrLogout();
        
     }
    @Step("Test Step 5: input Ph2 SA Lifetime:600. ")
    public void step5() {
        int Result1 ; 
        
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.CheckPhase2SALifetime(DUT1IPSecPolicy4.get("Ph2 SA Lifetime"));
        
        if (Result1 == 3) {
            micResult =  true;
            assertTrue(micResult, "Pass:Ph2 SA Lifetime set 600 successfully!"  );             
        } else  {
            micResult =  false;
            assertTrue(micResult, "Failed:Ph2 SA Lifetime set 600 successfully!"  );
        }
        brlogin.BrLogout();
        
     }
   
    @Step("Test Step 6:Restore DUT configuration.")
    public void step6() {
        
       CaseResult = true;
       
    }

}
