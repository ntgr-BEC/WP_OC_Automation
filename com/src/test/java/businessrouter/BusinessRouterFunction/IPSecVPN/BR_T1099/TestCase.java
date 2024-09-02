package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1099;

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
    public String sTestStr = "BR_T1099";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("IPSec VPN") // 必须要加，对应目录名
    @Story("BR_T1099") // 对应testrunkey
    @Description("087-DPD Interval(seconds 1-300)") // 对应用例名字
    @TmsLink("1517443") // 对应用例详情页的URL最后几位数字

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
    @Step("Test Step 2: input DPD Interval:1. ")
    public void step2() {
        int Result1 ; 
        
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.CheckDpdinterval(DUT1IPSecPolicy.get("DPD Interval"));
        
        if (Result1 == 3) {
            micResult =  true;
            assertTrue(micResult, "Pass:DPD Interval set 1 successfully."  );             
        } else  {
            micResult =  false;
            assertTrue(micResult, "Failed:DPD Interval set 1 unsuccessfully!"  );
        }
        //brlogin.BrLogout();
        
     }
    @Step("Test Step 3: input DPD Interval:60. ")
    public void step3() {
        int Result1 ; 
        
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.CheckDpdinterval(DUT1IPSecPolicy2.get("DPD Interval"));
        
        if (Result1 == 3) {
            micResult =  true;
            assertTrue(micResult, "Pass:DPD Interval set 60 successfully!"  );             
        } else  {
            micResult =  false;
            assertTrue(micResult, "Failed:DPD Interval set 60 unsuccessfully!"  );
        }
       // brlogin.BrLogout();
        
     }
    @Step("Test Step 4: input DPD Interval:300. ")
    public void step4() {
        int Result1 ; 
        
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.CheckDpdinterval(DUT1IPSecPolicy3.get("DPD Interval"));
        
        if (Result1 == 3) {
            micResult =  true;
            assertTrue(micResult, "Pass:DPD Interval set 300 successfully!"  );             
        } else  {
            micResult =  false;
            assertTrue(micResult, "Failed:DPD Interval set 300 unsuccessfully!"  );
        }
       // brlogin.BrLogout();
        
     }
    @Step("Test Step 5: input DPD Interval:301. ")
    public void step5() {
        int Result1 ; 
        
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.CheckDpdinterval(DUT1IPSecPolicy4.get("DPD Interval"));
        
        if (Result1 == 1) {
            micResult =  true;
            assertTrue(micResult, "Pass:DPD Interval set 301 unsuccessfully!"  );             
        } else  {
            micResult =  false;
            assertTrue(micResult, "Failed:DPD Interval set 301 successfully!"  );
        }
        brlogin.BrLogout();
        
     }
   
    @Step("Test Step 6:Restore DUT configuration.")
    public void step6() {
        
       CaseResult = true;
       
    }

}
