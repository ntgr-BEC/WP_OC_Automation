package businessrouter.BusinessRouterFunction.VLAN.BR_T373;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedVlanPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase implements Config {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr    = "BR_T373";
    public String sCurrentValue;
    public String sExpectedtValue;
    BrLoginPage   BrLoginPage = new BrLoginPage();
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.VLAN") // 必须要加，对应目录名
    @Story("BR_T373") // 对应testrunkey
    @Description("007-Add/delete all ports to VLAN") // 对应用例名字
    @TmsLink("1455392") // 对应用例详情页的URL最后几位数字

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
    }

    @Step("Test Step 2: Add all the ports as vlan 3 member,and delete vlan 3 member.")
    public void step2() {
        boolean result1 = false;
        boolean result2 = false;
        BrAdvancedVlanPage BrAdvancedVlanPage = new BrAdvancedVlanPage();
        BrAdvancedVlanPage.OpenAdvancedVlanPage();
        MyCommonAPIs.sleepi(5);
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            BrAdvancedVlanPage.AddVlan(VLAN);
            result1 = BrAdvancedVlanPage.CheckVlanMember(VLAN);
            result2 = BrAdvancedVlanPage.DeleteVlanMember(VLAN);
        } else if(WebportalParam.DUTType.contentEquals("BR100")) {
            BrAdvancedVlanPage.AddVlan(VLANBR100);
            result1 = BrAdvancedVlanPage.CheckVlanMember(VLANBR100); 
            result2 = BrAdvancedVlanPage.DeleteVlanMember(VLANBR100);
        }
        
        

        if (result1 && result2) {
            micResult = true;
            assertTrue(micResult, "Pass:Delete port one by one from vlan 3 is correct.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Delete port one by one from vlan 3 is incorrect.");
        }

        if (micResult) {
            BrAdvancedVlanPage.OpenAdvancedVlanPage();
            MyCommonAPIs.sleepi(5);
            BrAdvancedVlanPage.DeleteVLAN(VLAN.get("VLAN ID"));
            MyCommonAPIs.sleepi(15);
        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }
}
