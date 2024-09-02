package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1093;

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
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase implements Config {

    String tclname = getClass().getName();
    String tmpStr;
    String HistroyHandle;
    String TmsPageHandle;
    String TmsPageHandle2;
    public String sTestStr = "BR_T1093";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("IPSec VPN") // 必须要加，对应目录名
    @Story("BR_T1093") // 对应testrunkey
    @Description("081-Phase-2 Settings:Establish vpn after edit proposal") // 对应用例名字
    @TmsLink("1517437") // 对应用例详情页的URL最后几位数字

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
    @Step("On DUT1 config phase-2 setting:\r\n" + 
            "proposal1:esp-md5-3des ")
    public void step2() {
        boolean Result1 = false; 
        
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.ConfigIPSecVPNPolicy(DUT1IPSecPolicy);
        
        if (Result1 == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:Add DUT1 IPsec VPN Policy successfully!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult, "Failed:Add DUT1 IPsec VPN Policy unsuccessfully!!"  );
        }
        brlogin.BrLogout();
        
     }
    @Step("Test Step 3: On DUT2 config phase-2 setting:\r\n" + 
            "proposal1:esp-md5-aes128. ")
    public void step3() {
        boolean Result1 = false; 
        brlogin.LoginWithNewLanIP(WebportalParam.bripsecoppositelangateway);
        handle.sleepi(10);
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.ConfigIPSecVPNPolicy(DUT2IPSecPolicy);
        
        if (Result1 == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:Add DUT2 IPsec VPN Policy successfully!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult, "Failed:Add DUT2 IPsec VPN Policy unsuccessfully!!"  );
        }
        //brlogin.BrLogout();     
     }
    @Step("Test Step 4: Check VPN tunnel is normally ")
    public void step4() {
        boolean Result1 = false; 
        handle.sleepi(20);
        bripsecvpnpage.OpenIPSecVPNPage();
        handle.sleepi(10);
        Result1 = bripsecvpnpage.GetVpnTunnelStatus(DUT2IPSecPolicy.get("Policy Name"));
        
        if (Result1 == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:VPN tunnel status is up!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult, "Failed:VPN tunnel status is down!"  );
        }
        //brlogin.BrLogout();     
     }
    @Step("Test Step 5: .Edit proposal1 to esp-md5-aes128,establish vpn again ")
    public void step5() {
        boolean Result1 = false; 
    
        Result1 = bripsecvpnpage.EditIPSecVPNPolicy(DUT2IPSecPolicy2);
        
        if (Result1 == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:edit phase-1 setting on DUT2:\"proposal2:esp-md5-aes128successfully!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult, "Failed:edit phase-1 setting on DUT2:\"proposal2:esp-md5-aes128 unsuccessfully!"  );
        }
        brlogin.BrLogout(); 
        brlogin.defaultLogin();
        handle.sleepi(15);
        bripsecvpnpage.OpenIPSecVPNPage();
        handle.sleepi(15);
        Result1 = bripsecvpnpage.EditIPSecVPNPolicy(DUT1IPSecPolicy2);
        if (Result1 == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:edit phase-1 setting on DUT1 :\"proposal2:esp-md5-aes1282 successfully!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult, "Failed:edit phase-1 setting on DUT1 :\"proposal2:esp-md5-aes128 unsuccessfully!"  );
        }
     }
    @Step("Test Step6: Check VPN tunnel is normally ")
    public void step6() {
        boolean Result1 = false; 
        handle.sleepi(10);
        bripsecvpnpage.OpenIPSecVPNPage();
        handle.sleepi(10);
        Result1 = bripsecvpnpage.GetVpnTunnelStatus(DUT1IPSecPolicy2.get("Policy Name"));
        
        if (Result1 == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:VPN tunnel status is up!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult, "Failed:VPN tunnel status is down!"  );
        }
        //brlogin.BrLogout();     
     }
    @Step("Test Step 7:Restore DUT configuration.")
    public void step7() {  
        bripsecvpnpage.OpenIPSecVPNPage();
        handle.sleepi(15);
        bripsecvpnpage.DeleteallVpnPolicyRules();
        brlogin.BrLogout();
        brlogin.LoginWithNewLanIP(WebportalParam.bripsecoppositelangateway);
        handle.sleepi(15);
        bripsecvpnpage.OpenIPSecVPNPage();
        handle.sleepi(15);
        bripsecvpnpage.DeleteallVpnPolicyRules();
        brlogin.BrLogout();
       CaseResult = true;
       
    }
}