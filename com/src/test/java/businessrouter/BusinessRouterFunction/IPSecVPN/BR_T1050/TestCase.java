package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1050;

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
    public String sTestStr = "BR_T1050";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("IPSec VPN") // 必须要加，对应目录名
    @Story("BR_T1050") // 对应testrunkey
    @Description("031-GUI gateway policy:Local and remote are same") // 对应用例名字
    @TmsLink("1514033") // 对应用例详情页的URL最后几位数字

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
    @Step("Test Step 2:Create a vpn policy,config local subnet same as remote subnet ")
    public void step2() {
        boolean Result1 ; 
        
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.GetWarningWithWrongConfig(DUT1IPSecPolicy);
        
        if (Result1 == true) {
            micResult =  true;
            assertTrue(micResult, "Pass:Create a vpn policy,config local subnet same as remote subnet failed and DUT gives warning!"  );             
        } else  {
            micResult =  false;
            assertTrue(micResult, "Failed:Create a vpn policy,config local subnet same as remote subnet successfully abd DUT doesn't give warning!"  );
        }
        brlogin.BrLogout();
        
     }
   
    @Step("Test Step 3:Restore DUT configuration.")
    public void step3() {
        
       CaseResult = true;
       
    }

}