package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1046;

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
    public String sTestStr = "BR_T1046";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("IPSec VPN") // 必须要加，对应目录名
    @Story("BR_T1046") // 对应testrunkey
    @Description("023-VPN Policy page: pre-shared key > maxmum chars") // 对应用例名字
    @TmsLink("1514029") // 对应用例详情页的URL最后几位数字

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
    @Step("Test Step 2: input pre-shared key: 129 chars. ")
    public void step2() {
        int Result1 ; 
        
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.CheckPreSharedKey(DUT1IPSecPolicy.get("PreShared Key"));
        
        if (Result1 == 1) {
            micResult =  true;
            assertTrue(micResult, "Pass:Can't  input pre-shared key: 129 chars!"  );             
        } else  {
            micResult =  false;
            assertTrue(micResult, "Failed:Can input pre-shared key: 129 chars!"  );
        }
        brlogin.BrLogout();
        
     }
   
    @Step("Test Step 3:Restore DUT configuration.")
    public void step3() {
        
       CaseResult = true;
       
    }

}