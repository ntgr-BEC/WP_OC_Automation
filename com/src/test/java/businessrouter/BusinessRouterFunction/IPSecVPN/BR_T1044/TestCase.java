package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1044;

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
    public String sTestStr = "BR_T1044";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("IPSec VPN") // 必须要加，对应目录名
    @Story("BR_T1044") // 对应testrunkey
    @Description("021-Remote gateway input domain name that length force =maxmum chars") // 对应用例名字
    @TmsLink("1514027") // 对应用例详情页的URL最后几位数字

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
    @Step("Test Step 2: Under remote gateway input domain name,the length is 127 characters ")
    public void step2() {
        int Result1 ; 
        
        bripsecvpnpage.OpenIPSecVPNPage();
        Selenide.sleep(20000);
        Result1 = bripsecvpnpage.CheckDomainName(DUT1IPSecPolicy.get("Remote Gateway"));
        
        if (Result1 == 2) {
            micResult =  true;
            assertTrue(micResult, "Pass:Can  input domain name,the length is 127 characters  under remote gateway!"  );             
        } else  {
            micResult =  false;
            assertTrue(micResult, "Failed:Can't input domain name,the length is 127 characters  under remote gateway!"  );
        }
        brlogin.BrLogout();
        
     }
   
    @Step("Test Step 3:Restore DUT configuration.")
    public void step3() {
        
       CaseResult = true;
       
    }

}
