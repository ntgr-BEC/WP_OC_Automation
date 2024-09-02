package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1040;

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
    public String sTestStr = "BR_T1040";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("IPSec VPN") // 必须要加，对应目录名
    @Story("BR_T1040") // 对应testrunkey
    @Description("007-Select All and Delete All vpn Policy") // 对应用例名字
    @TmsLink("1514023") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p2") // 标记测试用例
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }
    @Step("Test Step 1: Open DUT1")
    public void step1() {
      // MyCommonAPIs.sleepi(30);
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.defaultLogin(); 
    
    }
    @Step("Test Step 2: Add 2 DUT IPsec VPN Policies ")
    public void step2() {
        boolean Result1 = false; 
        
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.ConfigIPSecVPNPolicy(DUTIPSecPolicy);
        
        if (Result1 == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:Add one DUT1 IPsec VPN Policy successfully!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult, "Failed:Add one DUT1 IPsec VPN Policy unsuccessfully!!"  );
        }
       Result1 = bripsecvpnpage.ConfigIPSecVPNPolicy(DUTIPSecPolicy2);
        
        if (Result1 == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:Add two DUT1 IPsec VPN Policy successfully!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult, "Failed:Add two DUT1 IPsec VPN Policy unsuccessfully!!"  );
        }
       
        
     }
    @Step("Test Step 3: Delete all  IPsec VPN Policies ")
    public void step3() {
        boolean Result1 = false; 
        boolean Result2 = false;
        bripsecvpnpage.DeleteallVpnPolicyRules();
        Result1 = bripsecvpnpage.VpnPolicyRulesExistOrNot(DUTIPSecPolicy.get("Policy Name"));
        Result2 = bripsecvpnpage.VpnPolicyRulesExistOrNot(DUTIPSecPolicy2.get("Policy Name"));
        if (Result1  == false && Result2 == false) {
            micResult =  true;
            assertTrue(micResult, "Pass: Delete all IPsec VPN Policy successfully!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult, "Failed: Delete all IPsec VPN Policy unsuccessfully!!"  );
        }
        //brlogin.BrLogout();     
     }

    @Step("Test Step 4:Restore DUT configuration.")
    public void step4() {
  
        brlogin.BrLogout();
       CaseResult = true;
       
    }

    

}