package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1063;

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
    public String sTestStr = "BR_T1063";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("IPSec VPN") // 必须要加，对应目录名
    @Story("BR_T1063") // 对应testrunkey
    @Description("052- Configure max VPN policy") // 对应用例名字
    @TmsLink("1514046") // 对应用例详情页的URL最后几位数字

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
    @Step("Test Step 2: Add Max policy in VPN page ")
    public void step2() {
        boolean Result1 = false; 
        
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        
        
        for (int i = 0; i < 4; i++) {
            String routername = DUTIPSecPolicy.get("Policy Name");
            String tt = String.valueOf(i);
            routername = "Tunnel" + tt;
            System.out.println(routername);
            String remoteGateay = "test" + tt + ".com";
            DUTIPSecPolicy.replace("Policy Name", routername);
            DUTIPSecPolicy.replace("Remote Gateway", remoteGateay);
            int Desiplastnumber = i + 2;
            String SDesiplastnumber = String.valueOf(Desiplastnumber);
            String remoteSub = DUTIPSecPolicy.get("Remote Subnet");
            String[] remoteSubList = remoteSub.split("\\.");
            String Newdeip = remoteSubList[0] + "." + remoteSubList[1] + "." + SDesiplastnumber + ".0" ;
            DUTIPSecPolicy.replace("Remote Subnet", Newdeip);
            Result1 = bripsecvpnpage.ConfigIPSecVPNPolicy(DUTIPSecPolicy);
            if (Result1 == true) {
                micResult =  true;
                assertTrue(micResult, "Pass:Add DUT1 IPsec VPN Policy successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult, "Failed:Add DUT1 IPsec VPN Policy unsuccessfully!"  );
            }
            handle.sleepi(10);
        }
       
        //brlogin.BrLogout();     
     }
    @Step("Test Step 3: Add Max+1 policy in VPN page ")
    public void step3() {
        boolean Result1 = false; 
        Result1 = bripsecvpnpage.AddIpSecVpnYesOrNot();
        System.out.print(Result1);
        System.out.println("23333333333333333333333333333");
        if (Result1 == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:Can't add Max+1 policy in VPN page!"  );             
        } else {
            micResult =  false;
            assertTrue(micResult, "Failed:Can add Max+1 policy in VPN page!"  );
        }
    }
   
   
    @Step("Test Step 4:Restore DUT configuration.")
    public void step4() {
        bripsecvpnpage.OpenIPSecVPNPage();
        handle.sleepi(15);
        bripsecvpnpage.DeleteallVpnPolicyRules();
        brlogin.BrLogout();
       CaseResult = true;
       
    }  

}