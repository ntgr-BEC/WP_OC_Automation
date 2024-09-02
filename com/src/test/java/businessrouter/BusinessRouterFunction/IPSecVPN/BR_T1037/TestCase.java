package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1037;

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
    public String sTestStr = "BR_T1037";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("IPSec VPN") // 必须要加，对应目录名
    @Story("BR_T1037") // 对应testrunkey
    @Description("014-Establish gateway to gateway VPN tunnel in IKE policy with 32 chars policy name") // 对应用例名字
    @TmsLink("1514020") // 对应用例详情页的URL最后几位数字

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
    @Step("Test Step 2: Add DUT1 IPsec VPN Policy with 32 chars policy name")
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
    @Step("Test Step 3: Add DUT2 IPsec VPN Policy ")
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
            assertTrue(micResult, "Pass:VPN tunnel status is on!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult, "Failed:VPN tunnel status is off!"  );
        }
        //brlogin.BrLogout();     
     }
    @Step("Test Step 5:Check that taffic can be forward by  VPN tunnel")
    public void step5() {
        boolean Result1 = false; 
        boolean Result2 = false;
        boolean Result0 = false;
         
        HistroyHandle= Brtmspage.LoginTMS(WebportalParam.bripsecoppositelanclient);
        TmsPageHandle= Brtmspage.GetBrowserHandles();
        Result1 =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands); 
        Brtmspage.BackTMSConfigPafe();
        if (Result1 == true ) {
            micResult =  true;
            assertTrue(micResult,"Pass:TCP taffic can be forward by  VPN tunnel!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:TCP taffic can't be forward by  VPN tunnel!"  );
        }
        Result2 =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsUDPCommands); 
        Brtmspage.BackTMSConfigPafe();
        if (Result1 == true ) {
            micResult =  true;
            assertTrue(micResult,"Pass:UDP taffic can be forward by  VPN tunnel!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:UDP taffic can't be forward by  VPN tunnel!"  );
        }
        Result0 =  Brtmspage.CheckPingPassOrFailed(TmsICMPCommands);
        //Brtmspage.CloseTMSWindows();
        if (Result0 == true ) {
            micResult =  true;
            assertTrue(micResult,"Pass: ICMP taffic can be forward by  VPN tunnel."  );             
        } else {
            micResult =  false;
            assertTrue(micResult,"Failed:ICMP taffic can be forward by  VPN tunnel.");
        }
     }
    @Step("Test Step 6:Restore DUT configuration.")
    public void step6() {
        Brtmspage.ChangeBrowserHandles(HistroyHandle);
        bripsecvpnpage.OpenIPSecVPNPage();
        handle.sleepi(15);
        bripsecvpnpage.DeleteallVpnPolicyRules();
        brlogin.BrLogout();
        brlogin.defaultLogin();
        handle.sleepi(15);
        bripsecvpnpage.OpenIPSecVPNPage();
        handle.sleepi(15);
        bripsecvpnpage.DeleteallVpnPolicyRules();
        brlogin.BrLogout();
       CaseResult = true;
       
    }

    

}