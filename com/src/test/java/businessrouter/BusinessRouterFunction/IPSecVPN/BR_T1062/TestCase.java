package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1062;

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
    public String sTestStr = "BR_T1062";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("IPSec VPN") // 必须要加，对应目录名
    @Story("BR_T1062") // 对应testrunkey
    @Description("051-The configure can not change after reboot") // 对应用例名字
    @TmsLink("1514045") // 对应用例详情页的URL最后几位数字

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
    @Step("Test Step 2: Add DUT1 2 IPsec VPN Policies ")
    public void step2() {
        boolean Result1 = false; 
        
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.ConfigIPSecVPNPolicy(DUT1IPSecPolicy);
        
        if (Result1 == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:Add one DUT1 IPsec VPN Policy successfully!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult, "Failed:Add one DUT1 IPsec VPN Policy unsuccessfully!!"  );
        }
       Result1 = bripsecvpnpage.ConfigIPSecVPNPolicy(DUT1IPSecPolicy2);
        
        if (Result1 == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:Add the second DUT1 IPsec VPN Policy successfully!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult, "Failed:Add the second  DUT1 IPsec VPN Policy unsuccessfully!!"  );
        }
        
     }
    @Step("Test Step 3: Reboot DUT and check whehter the 2 policies exist")
    public void step3() {
        boolean Result1 = false; 
        boolean Result2 = false; 
        brdashboard.OpenDashboardPage();
        handle.sleepi(15);
        brdashboard.RebootDUT();
        handle.sleepi(100);
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.defaultLogin(); 
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.VpnPolicyRulesExistOrNot(DUT1IPSecPolicy.get("Policy Name"));
        Result2 = bripsecvpnpage.VpnPolicyRulesExistOrNot(DUT1IPSecPolicy2.get("Policy Name"));
        if (Result1 == true && Result2 == true ) {
            micResult =  true;
            assertTrue(micResult, "Pass:The 2 IPsec VPN Policies exist!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult, "Failed:The 2 IPsec VPN Policies don't exist!"  );
        }
        //brlogin.BrLogout();     
     }
   
    @Step("Test Step 4:Restore DUT configuration.")
    public void step4() {
    
        handle.sleepi(15);
        bripsecvpnpage.OpenIPSecVPNPage();
        handle.sleepi(15);
        bripsecvpnpage.DeleteallVpnPolicyRules();
        brlogin.BrLogout();
       CaseResult = true;
       
    }   

}
