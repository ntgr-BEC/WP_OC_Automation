package businessrouter.BusinessRouterFunction.Advanced_StaticRoutes.BR_T95;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedStaticRoutesPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;

public class TestCase extends TestCaseBase implements Config {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr = "BR_T95";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Advanced_Static Routes") // 必须要加，对应目录名
    @Story("BR_T95") // 对应testrunkey
    @Description("009-Add max number of static route") // 对应用例名字
    @TmsLink("1455098") // 对应用例详情页的URL最后几位数字

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

   @Step("Test Step 2: Open Device")
    public void step2() {
        BrAdvancedStaticRoutesPage BrAdvancedStaticRoutesPage1 = new BrAdvancedStaticRoutesPage();
        brstaticroutepage.OpenStaticRoutePage();
        handle.sleepi(20);
        for (int i = 0; i < 32; i++) {
            STATICROUTE1.replace("Metric", "3");
            String routername = STATICROUTE1.get("Route Name");
            String tt = String.valueOf(i);
            routername = "test" + tt;
            STATICROUTE1.replace("Route Name", routername);
            int Desiplastnumber = i + 2;
            String SDesiplastnumber = String.valueOf(Desiplastnumber);
            String deip = STATICROUTE1.get("Destination IP Address");
            String[] deiplist1 = deip.split("\\.");
            String Newdeip = deiplist1[0] + "." + deiplist1[1] + "." + deiplist1[2] + "." + SDesiplastnumber;
            STATICROUTE1.replace("Destination IP Address", Newdeip);
            int Gatewaylastnumber = i + 2;
            String SGatewaylastnumber = String.valueOf(Gatewaylastnumber);
            String gatewayip = STATICROUTE1.get("Gateway IP Address");
            String[] gatewayiplist1 = gatewayip.split("\\.");
            String Newgatewayip = gatewayiplist1[0] + "." + gatewayiplist1[1] + "." + gatewayiplist1[2] + "." + SGatewaylastnumber;
            STATICROUTE1.replace("Gateway IP Address", Newgatewayip);
            BrAdvancedStaticRoutesPage1.AddStaticRule(STATICROUTE1);
            handle.sleepi(10);
        }

    }
    @Step("Test Step 3: Delete the static route")
    public void step3() {
        boolean Result = false;
        //handle.sleepi(15);
        brstaticroutepage.OpenStaticRoutePage();
        handle.sleepi(20);
        for (int i = 0; i < 32; i++) {
        
            String routername = STATICROUTE1.get("Route Name");
            String tt = String.valueOf(i);
            routername = "test" + tt;
            STATICROUTE1.replace("Route Name", routername);
            
            Result =  brstaticroutepage.DeleteStaticRule(STATICROUTE1.get("Route Name"));
            if (Result == true) {
                micResult =  true;
                assertTrue(micResult,"Pass:Delete the static route successfully!");  
            }else {
                micResult =  false;
                assertTrue(micResult,"Pass:Delete the static route unsuccessfully!");  
            }
            handle.sleepi(10);

        }
        
        CaseResult = true;
        brlogin.BrLogout(); 
    }
}
