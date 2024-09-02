package businessrouter.BusinessRouterFunction.SetupWAN.BR_T5;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config {
    String                       tclname = getClass().getName();
    String                       tmpStr;

    public String sTestStr = "BR_T5";
    public String sCurrentValue;
    public String sExpectedtValue;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup WAN") // 必须要加，对应目录名
        @Story("BR_T5") // 对应testrunkey
        @Description("001-Get WAN IP address via DHCP)") // 对应用例名字
        @TmsLink("1455008") // 对应用例详情页的URL最后几位数字

        @Test(alwaysRun = true, groups = "p2") // 标记测试用例
        public void test() throws Exception {
            runTest(this);
        }

        @AfterMethod(alwaysRun = true)
        public void tearDown() {
            System.out.println("start to do tearDown");
        }
        @Step("Test Step 1: Open Device")
        public void step1() {
            BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.defaultLogin();
            MyCommonAPIs.sleepi(20);
        }
        
        @Step("Test Step 2: Go to Basic->WAN page, Configure WAN interface use DHCP")
        public void step2() {
            boolean Result = false;
            brwanpage.OpenBasicWanSetupPage();
            MyCommonAPIs.sleepi(10);
            brwanpage.ChangeWanPortModeDhcpOrStatic(WanPortModeInfo);
            Result = brwanpage.CheckWanPortIpInfo(WanPorInfo);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:configure WAN interface use DHCP, and can get IP normally."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:configure WAN interface use DHCP, and can get IP abnormally."  );
            }
            CaseResult = true;
         }
        

}
