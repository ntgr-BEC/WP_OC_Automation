package businessrouter.BusinessRouterFunction.Advanced_StaticRoutes.BR_T90;

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

public class TestCase extends TestCaseBase implements Config {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr = "BR_T90";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Advanced_Static Routes") // 必须要加，对应目录名
    @Story("BR_T90") // 对应testrunkey
    @Description("004-Add a default route") // 对应用例名字
    @TmsLink("1455093") // 对应用例详情页的URL最后几位数字

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
        handle.sleepi(20);
    }

    @Step("Test Step 2: Add a route with destination address 0.0.0.0")
    public void step2() {
        brstaticroutepage.OpenStaticRoutePage();
        handle.sleepi(10);
        boolean Result = false; 
        Result = brstaticroutepage.AddInvalidStaticRule(STATICROUTE1);
        if (Result == true) {
            micResult =  true;
            assertTrue(micResult,"Pass:Not allow to config a route with destination address 0.0.0.0!");  
        }else {
            micResult =  false;
            assertTrue(micResult,"Failed:allow to config a route with destination address 0.0.0.0!"); 
        } 
        CaseResult = true;
        brlogin.BrLogout(); 

    }
    

}
